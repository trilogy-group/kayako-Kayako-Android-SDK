package com.kayako.sdk.android.k5.kre.base;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kayako.sdk.android.k5.kre.base.credentials.KreCredentials;
import com.kayako.sdk.android.k5.kre.data.PushData;
import com.kayako.sdk.android.k5.kre.helpers.KreLogHelper;

import org.phoenixframework.channels.Channel;
import org.phoenixframework.channels.Envelope;
import org.phoenixframework.channels.IMessageCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Should be used for a single event in a channel - no more. Any following subscriptions will replace the previous one.
 * <p>
 * Currently treated as a Singleton (using the KreSubscriptionFactory) allowing multiple subscriptions relying on one actual subscription using callbacks
 * <p>
 * Fixes the following problems:
 * - Ensures reset if new channel name received : Only one subscription is allowed at a time for the same channel name. If a new subscription is made with a new channel name, the older channel is unsubscribed
 * - Ensures all subscriptions use one single connection : New subscriptions will receive the same events, although only one connection is made per channel (this is done using a list of listeners)
 * <p>
 * This does not fix the following problems:
 * - Mutliple calls to listenFor for the same event may cause ConcurrentModificationExceptions and inconsistentency when receicing events
 */
public class KreSubscription extends KreConnection {

    private static final String TAG = "KreSubscription";
    private static final String EVENT_OK = "ok";

    private String mCurrentChannel;

    private final Object mTriggerKey = new Object();
    private final Object mListenerKey = new Object();
    private AtomicReference<Channel> mChannel = new AtomicReference<Channel>();
    private AtomicBoolean mHasSubscribedSuccessfully = new AtomicBoolean(false);
    private List<OnSubscriptionListener> mOnSubscriptionListeners = new ArrayList<>();

    private AsyncTask mUnSubscribeTask;
    private AsyncTask mTriggerTask;

    private String mTagWithName; // to track what the KreSubscription is being used for

    public KreSubscription(@Nullable String name) {
        if (name != null) {
            mTagWithName = String.format("%s-%s", TAG, name);
        } else {
            mTagWithName = TAG;
        }
    }

    /**
     * Subscribe to a channel and listen for a particular event
     *
     * @param kreCredentials
     */
    public void subscribe(@NonNull final KreCredentials kreCredentials, @NonNull final String channelName, @NonNull final OnSubscriptionListener onSubscriptionListener) {
        // KreLogHelper.d(TAG, "subscribe");
        synchronized (mListenerKey) { // ensure mOnSubscriptionListeners is handled synchronously
            try {
                // If the new subscription is for another channel, unsubscribe from the old - expecting resetVariables to be called on unsubscription of previous channel
                if (mOnSubscriptionListeners.size() != 0 && mCurrentChannel != null && !channelName.equals(mCurrentChannel)) {
                    KreLogHelper.e(mTagWithName, mCurrentChannel + " == " + channelName + " ?");
                    KreLogHelper.logException(mTagWithName, new AssertionError("One KreSubscription should be used for only channel. Else, unsubscribe from old to support new subscription. While issue is handled, this is not recommended behaviour - Expected unsubscribe"));
                    unSubscribe(null); // unsubscribe older one while new subscription is being made
                    resetVariables(); // reset variables

                    onSubscriptionListener.onError("Failed to start new subscription since previous subscription wasn't closed. Open page again to make it work!");
                    return; // TODO: Stop subscription? If allowed to continue, then unsubscribe task running in background may accidently close the new socket
                }

                mCurrentChannel = channelName;
                mOnSubscriptionListeners.add(onSubscriptionListener);
                KreLogHelper.d(mTagWithName, "Add to Subscriptions, Total:" + mOnSubscriptionListeners.size());

                if (mOnSubscriptionListeners.size() == 1) { // First Subscription
                    super.connect(kreCredentials, channelName, new OnOpenConnectionListener() {
                        @Override
                        public void onOpen(Channel channel) {
                            // KreLogHelper.d(TAG, "onOpenConnection");
                            try {
                                // Connect and get channel
                                mChannel.set(channel);

                                // Null check to ensure successful connection
                                if (mChannel.get() == null) {
                                    callOnErrors("Failed to connect with Phoenix Client Library");
                                } else {
                                    mChannel
                                            .get()
                                            .join()
                                            .receive(EVENT_OK, new IMessageCallback() {
                                                public void onMessage(Envelope envelope) {
                                                    if (!mHasSubscribedSuccessfully.get()) { // Prevent this method from being called repeatedly - "ok" can be received multiple times, especially after a push event
                                                        KreLogHelper.d(mTagWithName, "Subscribe-START");
                                                        mHasSubscribedSuccessfully.set(true); // ensure the state is set before any other operations
                                                        callOnSubscriptions();
                                                    }
                                                }
                                            });
                                }

                            } catch (IOException e) {
                                KreLogHelper.printStackTrace(mTagWithName, e);
                                if (e.getMessage() != null) {
                                    callOnErrors(e.getMessage());
                                }
                            }
                        }

                        @Override
                        public void onError(String message) {
                            callOnErrors(message);
                        }
                    });
                } else {
                    if (mHasSubscribedSuccessfully.get()) {
                        callOnSubscription(onSubscriptionListener);
                    } else {
                        // allow onSubscribe to be called naturally
                    }
                }
            } finally {
                // KreLogHelper.d(TAG, " mOnSubscriptionListeners count " + mOnSubscriptionListeners.size());
            }
        }
    }

    /**
     * @param eventName
     * @param eventListener
     */
    public void listenFor(final String eventName, final OnEventListener eventListener) {
        // KreLogHelper.d(TAG, "listenFor:" + eventName);
        assertValidSubscription();

        // Listen for specific events
        mChannel
                .get()
                .on(eventName, new IMessageCallback() {
                    public void onMessage(Envelope envelope) {
                        KreLogHelper.d(mTagWithName, "onMessage:" + eventName + " - " + envelope.getPayload().toString());
                        callOnEvent(eventListener, envelope);
                    }
                });
    }

    /**
     * @param eventName
     * @param t
     * @param <T>
     * @return whether or not the event was successfully triggered
     */
    public <T extends PushData> boolean triggerEvent(@NonNull final String eventName, @Nullable final T t) {
        // KreLogHelper.d(TAG, "triggerEvent: " + eventName);
        if (mChannel.get() == null || !mHasSubscribedSuccessfully.get()) {
            KreLogHelper.e(mTagWithName, "Call subscribe() before triggering event. Ignoring!");
            return false;
        }

        try {
            assertValidSubscription();
        } catch (Throwable e) {
            // Do not throw exceptions when triggering events. Ignore failed cases with error messages
            KreLogHelper.printStackTrace(mTagWithName, e);
            return false;
        }

        performTriggerInBackground(eventName, t);
        return true;
    }

    /**
     * Unsubscribe from the channel
     *
     * @param onSubscriptionListener null to unconditionally clear all subscriptions, else clear only once all listeners = 0
     */
    public void unSubscribe(@Nullable final OnSubscriptionListener onSubscriptionListener) {
        synchronized (mListenerKey) { // ensure mOnSubscriptionListeners is handled synchronously
            // KreLogHelper.d(TAG, "unSubscribe");

            try {
                if (onSubscriptionListener != null) {
                    mOnSubscriptionListeners.remove(onSubscriptionListener);
                    KreLogHelper.d(mTagWithName, "Remove from Subscriptions, Remaining=" + mOnSubscriptionListeners.size());

                    callOnUnSubscription(onSubscriptionListener);

                    if (mOnSubscriptionListeners.size() == 0) {
                        resetVariables();
                        performUnSubscribeInBackground();
                    }
                } else {
                    resetVariables();
                    performUnSubscribeInBackground();
                }
            } finally {
                // KreLogHelper.d(TAG, " mOnSubscriptionListeners count " + mOnSubscriptionListeners.size());
            }
        }
    }

    /**
     * Checks if subsciption was successful. If true, one can expect proper listening of events and should not call subscribe again
     *
     * @return
     */
    public boolean hasSubscribed() {
        return mHasSubscribedSuccessfully.get();
    }

    ////// TASKS TO RUN IN BACKGROUND ////////

    private <T extends PushData> void performTrigger(@NonNull String eventName, @NonNull T t) {
        try {
            if (isConnected() && mChannel.get().getSocket().isConnected()) {
                KreLogHelper.d(mTagWithName, "Trigger Event: " + eventName);
                KreLogHelper.d(mTagWithName, "Trigger JsonPayload: " + t.toString());

                synchronized (mTriggerKey) { // Ensure this method is synchronized so that there's no concurrent modification exception in Phoenix Client library
                    mChannel.get().push(eventName, convertObjectToJsonNode(t));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void performUnSubscribe() {
        KreLogHelper.d(mTagWithName, "unSubscribe-FINAL");

        // Cancel running trigger tasks
        cancelTask(mTriggerTask);

        // Leave Channel
        if (mChannel.get() != null) {
            try {
                mChannel.get().leave();
            } catch (IOException | IllegalStateException e) {
                KreLogHelper.printStackTrace(mTagWithName, e);
            }
        }

        // Disconnect Socket connection
        disconnect(null);
    }

    private void resetVariables() {
        synchronized (mListenerKey) { // ensure mOnSubscriptionListeners is handled synchronously
            mOnSubscriptionListeners = new CopyOnWriteArrayList<>();
            mHasSubscribedSuccessfully.set(false);
            mCurrentChannel = null;
        }
    }


    ////// BACKGROUND PROCESSING ////////

    private void performUnSubscribeInBackground() {
        cancelTask(mUnSubscribeTask); // only unsubscribe once
        runUnSubscribeTask();
    }

    private <T extends PushData> void performTriggerInBackground(final String event, final T t) {
        // don't cancel trigger task - causes you to disconnect from socket
        runTriggerTask(event, t);
    }

    private void cancelTask(AsyncTask task) {
        if (task != null && task.getStatus() != AsyncTask.Status.FINISHED) {
            task.cancel(true);
        }
    }

    private void runUnSubscribeTask() {
        mUnSubscribeTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void[] params) {
                performUnSubscribe();
                return null;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void cancelUnSubscribeTask() {
        cancelTask(mUnSubscribeTask);
    }

    private <T extends PushData> void runTriggerTask(final String event, final T t) {
        mTriggerTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void[] params) {
                performTrigger(event, t);
                return null;
            }


        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    ////// OTHER METHODS ////////

    public static <T extends PushData> JsonNode convertObjectToJsonNode(@NonNull T t) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.valueToTree(t);
        return node;
    }

    private void assertValidSubscription() {
        if (mChannel.get() == null) {
            throw new AssertionError("call subscribe() before listening for an event");
        } else if (!hasSubscribed()) {
            throw new AssertionError("listenFor method should only be called once the subscription is successful");
        }
    }

    ////// LISTENERS ////////

    private void callOnError(@Nullable final OnErrorListener listener, @Nullable String message) {
        if (listener != null) {
            listener.onError(message);
            KreLogHelper.e(mTagWithName, message);
        }
    }

    private void callOnEvent(@Nullable final OnEventListener listener, Envelope envelope) {
        if (listener != null) {
            listener.onEvent(envelope.getEvent(), envelope.getPayload().toString()); // TODO: Validate if valid json - may cause errors
        }
    }

    private void callOnSubscriptions() {
        synchronized (mListenerKey) { // ensure mOnSubscriptionListeners is handled synchronously
            for (OnSubscriptionListener listener : mOnSubscriptionListeners) {
                callOnSubscription(listener);
            }
        }
    }

    private void callOnErrors(@Nullable String message) {
        synchronized (mListenerKey) { // ensure mOnSubscriptionListeners is handled synchronously
            for (OnSubscriptionListener listener : mOnSubscriptionListeners) {
                callOnError(listener, message);
            }
        }
    }

    private void callOnSubscription(@Nullable final OnSubscriptionListener listener) {
        if (listener != null) {
            listener.onSubscription();
        }
    }

    private void callOnUnSubscription(@Nullable final OnSubscriptionListener listener) {
        if (listener != null) {
            listener.onUnsubscription();
        }
    }

    public interface OnErrorListener {
        void onError(String message);
    }

    public interface OnSubscriptionListener extends OnErrorListener {
        void onSubscription();

        void onUnsubscription();
    }

    public interface OnEventListener extends OnErrorListener {
        void onEvent(String event, String jsonBody);
    }
}
