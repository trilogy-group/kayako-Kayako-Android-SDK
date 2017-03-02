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
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

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

    private Channel mChannel;
    private String mCurrentChannel;

    private AtomicBoolean mHasSubscribedSuccessfully = new AtomicBoolean(false);
    private List<OnSubscriptionListener> mOnSubscriptionListeners = new CopyOnWriteArrayList<>();

    private AsyncTask mUnSubscribeTask;
    private AsyncTask mTriggerTask;

    KreSubscription() {
    }

    /**
     * Subscribe to a channel and listen for a particular event
     *
     * @param kreCredentials
     */
    public synchronized void subscribe(@NonNull final KreCredentials kreCredentials, @NonNull final String channelName, @NonNull final OnSubscriptionListener onSubscriptionListener) {
        // KreLogHelper.d(TAG, "subscribe");

        try {
            // If the new subscription is for another channel, unsubscribe from the old - expecting resetVariables to be called on unsubscription of previous channel
            if (mOnSubscriptionListeners.size() != 0 && mCurrentChannel != null && !channelName.equals(mCurrentChannel)) {
                KreLogHelper.e(TAG, mCurrentChannel + " == " + channelName + " ?");
                KreLogHelper.logException(TAG, new AssertionError("One KreSubscription should be used for only channel. Else, unsubscribe from old to support new subscription. While issue is handled, this is not recommended behaviour - Expected unsubscribe"));
                unSubscribe(null); // unsubscribe older one while new subscription is being made
                resetVariables(); // reset variables

                onSubscriptionListener.onError("Failed to start new subscription since previous subscription wasn't closed. Open page again to make it work!");
                return; // TODO: Stop subscription? If allowed to continue, then unsubscribe task running in background may accidently close the new socket
            }

            mCurrentChannel = channelName;
            mOnSubscriptionListeners.add(onSubscriptionListener);
            if (mOnSubscriptionListeners.size() == 1) { // First Subscription
                super.connect(kreCredentials, channelName, new OnOpenConnectionListener() {
                    @Override
                    public synchronized void onOpen(Channel channel) {
                        // KreLogHelper.d(TAG, "onOpenConnection");
                        try {
                            // Connect and get channel
                            mChannel = channel;

                            // Null check to ensure successful connection
                            if (mChannel == null) {
                                callOnErrors("Failed to connect with Phoenix Client Library");
                            } else {
                                mChannel
                                        .join()
                                        .receive(EVENT_OK, new IMessageCallback() {
                                            public synchronized void onMessage(Envelope envelope) {
                                                if (!mHasSubscribedSuccessfully.get()) { // Prevent this method from being called repeatedly - "ok" can be received multiple times, especially after a push event
                                                    KreLogHelper.d(TAG, "Subscribe-START");
                                                    mHasSubscribedSuccessfully.set(true); // ensure the state is set before any other operations
                                                    callOnSubscriptions();
                                                }
                                            }
                                        });
                            }

                        } catch (IOException e) {
                            KreLogHelper.printStackTrace(TAG, e);
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

    /**
     * @param eventName
     * @param eventListener
     */
    public synchronized void listenFor(final String eventName, final OnEventListener eventListener) {
        // KreLogHelper.d(TAG, "listenFor:" + eventName);
        assertValidSubscription();

        // Listen for specific events
        mChannel
                .on(eventName, new IMessageCallback() {
                    public void onMessage(Envelope envelope) {
                        KreLogHelper.d(TAG, "onMessage:" + eventName + " - " + envelope.getPayload().toString());
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
    public synchronized <T extends PushData> boolean triggerEvent(@NonNull final String eventName, @Nullable final T t) {
        // KreLogHelper.d(TAG, "triggerEvent: " + eventName);
        if (mChannel == null || !mHasSubscribedSuccessfully.get()) {
            KreLogHelper.e(TAG, "Call subscribe() before triggering event. Ignoring!");
            return false;
        }

        try {
            assertValidSubscription();
        } catch (Throwable e) {
            // Do not throw exceptions when triggering events. Ignore failed cases with error messages
            KreLogHelper.printStackTrace(TAG, e);
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
    public synchronized void unSubscribe(@Nullable final OnSubscriptionListener onSubscriptionListener) {
        // KreLogHelper.d(TAG, "unSubscribe");

        try {
            if (onSubscriptionListener != null) {
                mOnSubscriptionListeners.remove(onSubscriptionListener);
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

    /**
     * Checks if subsciption was successful. If true, one can expect proper listening of events and should not call subscribe again
     *
     * @return
     */
    public synchronized boolean hasSubscribed() {
        return mHasSubscribedSuccessfully.get();
    }

    ////// TASKS TO RUN IN BACKGROUND ////////

    private synchronized <T extends PushData> void performTrigger(@NonNull String eventName, @NonNull T t) {
        // Ensure this method is synchronized so that there's no Concurrent exception
        try {
            if (isConnected() && mChannel.getSocket().isConnected()) {
                KreLogHelper.d(TAG, "Trigger Event: " + eventName);
                KreLogHelper.d(TAG, "Trigger JsonPayload: " + t.toString());
                mChannel.push(eventName, convertObjectToJsonNode(t));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void performUnSubscribe() {
        KreLogHelper.d(TAG, "unSubscribe-FINAL");

        // Cancel running trigger tasks
        cancelTask(mTriggerTask);

        // Leave Channel
        if (mChannel != null) {
            try {
                mChannel.leave();
            } catch (IOException | IllegalStateException e) {
                KreLogHelper.printStackTrace(TAG, e);
            }
        }

        // Disconnect Socket connection
        disconnect(null);
    }

    private synchronized void resetVariables() {
        mOnSubscriptionListeners = new CopyOnWriteArrayList<>();
        mHasSubscribedSuccessfully.set(false);
        mCurrentChannel = null;
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
        if (mChannel == null) {
            throw new AssertionError("call subscribe() before listening for an event");
        } else if (!hasSubscribed()) {
            throw new AssertionError("listenFor method should only be called once the subscription is successful");
        }
    }

    ////// LISTENERS ////////

    private void callOnError(@Nullable final OnErrorListener listener, @Nullable String message) {
        if (listener != null) {
            listener.onError(message);
            KreLogHelper.e(TAG, message);
        }
    }

    private void callOnEvent(@Nullable final OnEventListener listener, Envelope envelope) {
        if (listener != null) {
            listener.onEvent(envelope.getEvent(), envelope.getPayload().toString()); // TODO: Validate if valid json - may cause errors
        }
    }

    private void callOnSubscriptions() {
        for (OnSubscriptionListener listener : mOnSubscriptionListeners) {
            callOnSubscription(listener);
        }
    }

    private void callOnErrors(@Nullable String message) {
        for (OnSubscriptionListener listener : mOnSubscriptionListeners) {
            callOnError(listener, message);
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
