package com.kayako.sdk.android.k5.messenger.data.realtime;

import android.os.Handler;

import com.kayako.sdk.android.k5.common.activities.MessengerActivityTracker;
import com.kayako.sdk.android.k5.common.activities.MessengerOpenTracker;
import com.kayako.sdk.android.k5.core.KayakoLogHelper;
import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.android.k5.core.MessengerUserPref;
import com.kayako.sdk.android.k5.kre.base.KreSubscription;
import com.kayako.sdk.android.k5.kre.base.credentials.KreFingerprintCredentials;
import com.kayako.sdk.android.k5.kre.base.kase.KreCaseSubscription;
import com.kayako.sdk.android.k5.kre.base.user.KreUserSubscription;
import com.kayako.sdk.android.k5.kre.data.Change;
import com.kayako.sdk.android.k5.kre.helpers.MinimalClientTypingListener;
import com.kayako.sdk.android.k5.kre.helpers.RawCaseChangeListener;
import com.kayako.sdk.android.k5.kre.helpers.RawCasePostChangeListener;
import com.kayako.sdk.android.k5.kre.helpers.RawUserOnlinePresenceListener;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.UserViewModel;
import com.kayako.sdk.base.callback.ItemCallback;
import com.kayako.sdk.error.KayakoException;
import com.kayako.sdk.messenger.conversation.Conversation;
import com.kayako.sdk.messenger.message.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Responsibilities:
 * - Track if a user is online or not
 * - Proper subscribing and unsubscribing should be handled here
 * - One common class for all user subscriptions
 * <p>
 * <p>
 * Required in:
 * - Toolbar - track online presence of Agent Assigned
 * - Background - subscribe to show online presence of current customer
 * <p>
 */
public class RealtimeUserHelper {

    private static final String TAG = "RealtimeUserHelper";

    private static Map<String, KreUserSubscription> sMapSubscriptions = new HashMap<>();
    private static Map<String, KreSubscription.OnSubscriptionListener> sMapListeners = new HashMap<>();

    private static final Object sListenerKey = new Object();
    private static List<UserPresenceListener> sOnlinePresenceListeners = new ArrayList();

    // Close Realtime subscriptions on Messenger close
    private static MessengerOpenTracker.OnCloseMessengerListener sOnCloseMessengerListener = new MessengerOpenTracker.OnCloseMessengerListener() {
        @Override
        public void onCloseMessenger() {
            closeAll();
            // DO NOT REMOVE onCloseMessengerListener() - it should be available irrespective of how many times messenger opened and closed
        }
    };

    static {
        MessengerOpenTracker.addOnCloseMessengerListener(sOnCloseMessengerListener);
    }

    private static void addKreUserSubscriptionIfNotAlreadyAdded(String userPresenceChannel, long userId) {
        if (!sMapSubscriptions.containsKey(userPresenceChannel)) {
            addNewKreCaseSubscription(userPresenceChannel, userId);
        }
    }

    private static void addNewKreCaseSubscription(String userPresenceChannel, long userId) {
        KreStarter kreStarterValues;
        try {
            // Retrieve Messenger Values
            kreStarterValues = KreStarterFactory.getKreStarterValues();
        } catch (IllegalStateException e) {
            KayakoLogHelper.e(TAG, "KRE Starter Values could not be created yet! Skipping...");
            KayakoLogHelper.logException(TAG, e);
            return;
        }

        // Set up CaseSubscription
        KreUserSubscription kreUserSubscription = new KreUserSubscription(userPresenceChannel);
        sMapSubscriptions.put(userPresenceChannel, kreUserSubscription);

        // Set up CaseSubscriptionListener
        KreSubscription.OnSubscriptionListener onSubscriptionListener = generateOnSubscriptionListener(kreUserSubscription, userId);
        sMapListeners.put(userPresenceChannel, onSubscriptionListener);

        // Subscribe for changes
        kreUserSubscription.subscribe(
                new KreFingerprintCredentials(kreStarterValues.getInstanceUrl(), kreStarterValues.getFingerprintId()),
                userPresenceChannel,
                userId,
                onSubscriptionListener
        );
    }

    private static KreSubscription.OnSubscriptionListener generateOnSubscriptionListener(final KreUserSubscription kreUserSubscription, final long userId) {
        final Handler handler = new Handler();
        return new KreSubscription.OnSubscriptionListener() {
            @Override
            public void onSubscription() {
                KayakoLogHelper.d(TAG, "onSubscription()");

                kreUserSubscription.addUserOnlinePresenceListener(new RawUserOnlinePresenceListener() {
                    @Override
                    public void onUserOnline() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                synchronized (sListenerKey) {
                                    for (UserPresenceListener userPresenceListener : sOnlinePresenceListeners) {
                                        userPresenceListener.onUserOnline(userId);
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onUserOffline() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                synchronized (sListenerKey) {
                                    for (UserPresenceListener userPresenceListener : sOnlinePresenceListeners) {
                                        userPresenceListener.onUserOffline(userId);
                                    }
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

    private static void unsubscribe(String presenceChannel, KreSubscription.OnSubscriptionListener onSubscriptionListener) {
        if (!sMapSubscriptions.containsKey(presenceChannel)) {
            throw new IllegalStateException("Can not call unsubscribe before subcribe is called!");
        }

        KreUserSubscription kreUserSubscription = sMapSubscriptions.get(presenceChannel);
        kreUserSubscription.unSubscribe(onSubscriptionListener);
    }

    public static void closeAll() {
        // unsubscribe all
        for (String key : sMapListeners.keySet()) {
            unsubscribe(key, sMapListeners.get(key));
        }

        sMapSubscriptions.clear();
        sMapListeners.clear();

        synchronized (sListenerKey) {
            sOnlinePresenceListeners.clear();
        }
    }

    public static void trackUser(String userPresenceChannel, long userId, UserPresenceListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Invalid Listener argument");
        }

        synchronized (sListenerKey) {
            sOnlinePresenceListeners.add(listener);
            addKreUserSubscriptionIfNotAlreadyAdded(userPresenceChannel, userId);

            KayakoLogHelper.d(TAG, "trackUser, set = " + sOnlinePresenceListeners.size());
        }
    }

    public static void untrackUser(UserPresenceListener listener) {
        synchronized (sListenerKey) {
            sOnlinePresenceListeners.remove(listener);
        }
    }

    public interface UserPresenceListener {

        void onUserOnline(long userId);

        void onUserOffline(long userId);
    }

}
