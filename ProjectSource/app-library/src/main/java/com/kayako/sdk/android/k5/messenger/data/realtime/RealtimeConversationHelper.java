package com.kayako.sdk.android.k5.messenger.data.realtime;

import android.os.Handler;

import com.kayako.sdk.android.k5.kre.base.KreSubscription;
import com.kayako.sdk.android.k5.kre.base.credentials.KreFingerprintCredentials;
import com.kayako.sdk.android.k5.kre.base.kase.KreCaseSubscription;
import com.kayako.sdk.android.k5.kre.data.Change;
import com.kayako.sdk.android.k5.kre.helpers.MinimalClientTypingListener;
import com.kayako.sdk.android.k5.kre.helpers.RawCaseChangeListener;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.UserViewModel;

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

    private static Map<String, KreCaseSubscription> sMapSubscriptions = new HashMap<>();
    private static Map<String, KreSubscription.OnSubscriptionListener> sMapListeners = new HashMap<>();

    private static Set<OnConversationViewChangeListener> sOnConversationViewChangeListenerSet = new HashSet<>();

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
                kreCaseSubscription.addCaseChangeListener(new RawCaseChangeListener() {
                    @Override
                    public void onCaseChange(Change change) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                for (OnConversationViewChangeListener onConversationChangeListener : sOnConversationViewChangeListenerSet) {
                                    onConversationChangeListener.onChange(conversationId);
                                }
                            }
                        });
                    }

                    @Override
                    public void onConnectionError() {

                    }
                });

                kreCaseSubscription.addMinimalClientTypingListener(new MinimalClientTypingListener() {
                    @Override
                    public void onUserTyping(final long userId, final String userName, final String userAvatar, final boolean isTyping) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                for (OnConversationViewChangeListener onConversationChangeListener : sOnConversationViewChangeListenerSet) {
                                    onConversationChangeListener.onTyping(conversationId, new UserViewModel(userAvatar, userName, System.currentTimeMillis()), isTyping);
                                }
                            }
                        });
                    }

                    @Override
                    public void onConnectionError() {

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
     * Once KRE is no longer requried for any case
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
