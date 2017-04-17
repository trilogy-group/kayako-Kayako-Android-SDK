package com.kayako.sdk.android.k5.messenger.data.realtime;

import android.os.Handler;
import android.util.Log;

import com.kayako.sdk.android.k5.BuildConfig;
import com.kayako.sdk.android.k5.kre.base.KreSubscription;
import com.kayako.sdk.android.k5.kre.base.credentials.KreFingerprintCredentials;
import com.kayako.sdk.android.k5.kre.base.kase.KreCaseSubscription;
import com.kayako.sdk.android.k5.kre.data.Change;
import com.kayako.sdk.android.k5.kre.helpers.KreLogHelper;
import com.kayako.sdk.android.k5.kre.helpers.MinimalClientTypingListener;
import com.kayako.sdk.android.k5.kre.helpers.RawCaseChangeListener;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.UserViewModel;
import com.kayako.sdk.base.callback.ItemCallback;
import com.kayako.sdk.error.KayakoException;
import com.kayako.sdk.messenger.conversation.Conversation;
import com.kayako.sdk.utils.LogUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Ensures only one instance of a KreCaseSubscription is created for a single conversation presence channel.
 * <p>
 * Also, abstracts the KreCaseSubscription logic so that users of this class only needs to call one method to subscribe to changes.
 */
public class RealtimeConversationHelper {

    private static final String TAG = "RealtimeConversationHelper";
    private static Map<String, KreCaseSubscription> sMapSubscriptions = new HashMap<>();
    private static Map<String, KreSubscription.OnSubscriptionListener> sMapListeners = new HashMap<>();

    private static Set<OnConversationViewChangeListener> sOnConversationViewChangeListenerSet = new HashSet<>();

    static {
        if (BuildConfig.DEBUG) {
            KreLogHelper.setAddLogListener(new KreLogHelper.PrintLogListener() {
                @Override
                public void printDebugLogs(String tag, String message) {
                    Log.d(tag, message);
                }

                @Override
                public void printVerboseLogs(String tag, String message) {
                    Log.v(tag, message);
                }

                @Override
                public void printErrorLogs(String tag, String message) {
                    Log.e(tag, message);
                }

                @Override
                public void printStackTrace(String tag, Throwable e) {
                    Log.e(tag, "printStackTrace:");
                    e.printStackTrace();
                }

                @Override
                public void logPotentialCrash(String tag, Throwable e) {
                    Log.e(tag, "logPotentialCrash:");
                    e.printStackTrace();
                }
            });
        }
    }

    private static void addKreCaseSubscriptionIfNotExisting(String conversationPresenceChannelName, long conversationId) {
        if (!sMapSubscriptions.containsKey(conversationPresenceChannelName)) {
            addNewKreCaseSubscription(conversationPresenceChannelName, conversationId);
        }
    }

    private static void addNewKreCaseSubscription(String conversationPresenceChannelName, long conversationId) {
        // Retrieve Messenger Values
        KreStarter kreStarterValues = KreStarterFactory.getKreStarterValues();

        // Set up CaseSubscription
        KreCaseSubscription kreCaseSubscription = new KreCaseSubscription(conversationPresenceChannelName, kreStarterValues.getCurrentUserId());
        sMapSubscriptions.put(conversationPresenceChannelName, kreCaseSubscription);

        // Set up CaseSubscriptionListener
        KreSubscription.OnSubscriptionListener onSubscriptionListener = generateOnSubscriptionListener(kreCaseSubscription, conversationId);
        sMapListeners.put(conversationPresenceChannelName, onSubscriptionListener);

        // Subscribe for changes
        kreCaseSubscription.subscribe(
                new KreFingerprintCredentials(kreStarterValues.getInstanceUrl(), kreStarterValues.getFingerprintId()),
                conversationPresenceChannelName,
                onSubscriptionListener
        );
    }

    private static KreSubscription.OnSubscriptionListener generateOnSubscriptionListener(final KreCaseSubscription kreCaseSubscription, final long conversationId) {
        final Handler handler = new Handler();
        return new KreSubscription.OnSubscriptionListener() {
            @Override
            public void onSubscription() {
                KreLogHelper.d(TAG, "onSubscription()");

                kreCaseSubscription.addCaseChangeListener(new RawCaseChangeListener() {
                    @Override
                    public void onCaseChange(Change change) {
                        KreLogHelper.d(TAG, "onCaseChange()");

                        LoadResourceHelper.loadConversation(conversationId, new ItemCallback<Conversation>() {
                            @Override
                            public void onSuccess(final Conversation conversation) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        for (OnConversationViewChangeListener onConversationChangeListener : sOnConversationViewChangeListenerSet) {
                                            onConversationChangeListener.onChange(conversation);
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onFailure(KayakoException exception) {
                                KreLogHelper.printStackTrace(TAG, exception);
                            }
                        });
                    }

                    @Override
                    public void onConnectionError() {
                        KreLogHelper.e(TAG, "onConnectionError()");
                    }
                });

                kreCaseSubscription.addMinimalClientTypingListener(new MinimalClientTypingListener() {
                    @Override
                    public void onUserTyping(final long userId, final String userName, final String userAvatar, final boolean isTyping) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                KreLogHelper.d(TAG, "onClientTyping()");
                                for (OnConversationViewChangeListener onConversationChangeListener : sOnConversationViewChangeListenerSet) {
                                    onConversationChangeListener.onTyping(conversationId, new UserViewModel(userAvatar, userName, System.currentTimeMillis()), isTyping);
                                }
                            }
                        });
                    }

                    @Override
                    public void onConnectionError() {
                        KreLogHelper.e(TAG, "onConnectionError()");
                    }
                });
            }

            @Override
            public void onUnsubscription() {

            }

            @Override
            public void onError(String message) {

            }
        };
    }

    private static void unsubscribe(String conversationPresenceChannelName, KreSubscription.OnSubscriptionListener onSubscriptionListener) {
        if (!sMapSubscriptions.containsKey(conversationPresenceChannelName)) {
            throw new IllegalStateException("Can not call unsubscribe before subcribe is called!");
        }

        KreCaseSubscription kreCaseSubscription = sMapSubscriptions.get(conversationPresenceChannelName);
        kreCaseSubscription.unSubscribe(onSubscriptionListener);
    }

    // TODO: Ensure the closeAll() method is called whenever the Messenger is CLOSED!

    /**
     * Once KRE is no longer requried for any case.
     * <p>
     * Ensure the closeAll() method is called whenever the Messenger is CLOSED!
     * Otherwise, KRE sockets will continue to work in the background.
     */
    public static void closeAll() {
        // unsubscribe all
        for (String key : sMapListeners.keySet()) {
            unsubscribe(key, sMapListeners.get(key));
        }

        sMapSubscriptions.clear();
        sMapListeners.clear();
    }

    // TODO: Later, build another layer that ensures the code that calls the following method only receives events relating to the cases relevant to that code

    public static void trackConversationRealtimeChanges(String conversationPresenceChannelName, long conversationId, final OnConversationViewChangeListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Invalid Listener argument");
        }

        sOnConversationViewChangeListenerSet.add(listener);
        addKreCaseSubscriptionIfNotExisting(conversationPresenceChannelName, conversationId);
    }
}
