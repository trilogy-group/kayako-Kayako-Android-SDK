package com.kayako.sdk.android.k5.messenger.data.realtime;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.LongSparseArray;

import com.kayako.sdk.android.k5.common.activities.MessengerOpenTracker;
import com.kayako.sdk.android.k5.core.KayakoLogHelper;
import com.kayako.sdk.android.k5.kre.base.KreSubscription;
import com.kayako.sdk.android.k5.kre.base.credentials.KreFingerprintCredentials;
import com.kayako.sdk.android.k5.kre.base.kase.KreCaseSubscription;
import com.kayako.sdk.android.k5.kre.data.Change;
import com.kayako.sdk.android.k5.kre.helpers.MinimalClientTypingListener;
import com.kayako.sdk.android.k5.kre.helpers.RawCaseChangeListener;
import com.kayako.sdk.android.k5.kre.helpers.RawCasePostChangeListener;
import com.kayako.sdk.android.k5.kre.helpers.RawUserOnCasePresenceListener;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.UserViewModel;
import com.kayako.sdk.base.callback.ItemCallback;
import com.kayako.sdk.error.KayakoException;
import com.kayako.sdk.messenger.conversation.Conversation;
import com.kayako.sdk.messenger.message.Message;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Responsibilities of this class:
 * 1. Ensures only one instance of a KreCaseSubscription is created for a single conversation presence channel.
 * 2. Abstracts the KreCaseSubscription logic so that users of this class only needs to call one method to subscribe to changes.
 * 3. Ensure only a unique listener is added for the same instance - not multiple redundant listeners
 * <p>
 * Also:
 * - By subscribing to a conversation, the current customer presence is automatically tracked as well
 */
public class RealtimeConversationHelper {

    private static final String TAG = "RealtimeConversationHelper";
    private static Map<String, KreCaseSubscription> sMapSubscriptions = new HashMap<>();
    private static Map<String, KreSubscription.OnSubscriptionListener> sMapListeners = new HashMap<>();

    private static Set<OnConversationChangeListener> sOnConversationChangeListeners = new HashSet<>();
    private static Set<OnConversationClientActivityListener> sOnConversationClientActivityListeners = new HashSet<>();
    private static Set<OnConversationMessagesChangeListener> sOnConversationMessagesChangeListeners = new HashSet<>();
    private static Set<OnConversationUserOnlineListener> sOnConversationUserOnlineListeners = new HashSet<>();

    private static final Object mActiveUsersKey = new Object();
    private static LongSparseArray<Set<Long>> sMapActiveUsers = new LongSparseArray<>();

    private RealtimeConversationHelper() {
    }

    // Close Realtime subscriptions on Messenger close
    private static MessengerOpenTracker.OnCloseMessengerListener sOnCloseMessengerListener = new MessengerOpenTracker.OnCloseMessengerListener() {
        @Override
        public void onCloseMessenger() {
            closeAll();
            // DO NOT REMOVE removeOnCloseMessengerListener() - it should be available after messenger is opened, closed, opened, closed, repeatedly
        }
    };

    static {
        MessengerOpenTracker.addOnCloseMessengerListener(sOnCloseMessengerListener);
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
                false,
                onSubscriptionListener
        );
    }

    private static KreSubscription.OnSubscriptionListener generateOnSubscriptionListener(final KreCaseSubscription kreCaseSubscription, final long conversationId) {
        final Handler handler = new Handler();
        return new KreSubscription.OnSubscriptionListener() {
            @Override
            public void onSubscription() {
                KayakoLogHelper.d(TAG, "onSubscription()");

                kreCaseSubscription.addCaseChangeListener(new RawCaseChangeListener() {
                    @Override
                    public void onCaseChange(Change change) {
                        KayakoLogHelper.d(TAG, "onCaseChange()");

                        LoadResourceHelper.loadConversation(conversationId, new ItemCallback<Conversation>() {
                            @Override
                            public void onSuccess(final Conversation conversation) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        for (OnConversationChangeListener onConversationChangeListener : sOnConversationChangeListeners) {
                                            onConversationChangeListener.onChange(conversation);
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onFailure(KayakoException exception) {
                                KayakoLogHelper.printStackTrace(TAG, exception);
                            }
                        });
                    }

                    @Override
                    public void onConnectionError() {
                        KayakoLogHelper.e(TAG, "onConnectionError()");
                    }
                });

                kreCaseSubscription.addMinimalClientTypingListener(new MinimalClientTypingListener() {
                    @Override
                    public void onUserTyping(final long userId, final String userName, final String userAvatar, final boolean isTyping) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                KayakoLogHelper.d(TAG, "onClientTyping()");
                                for (OnConversationClientActivityListener onConversationChangeListener : sOnConversationClientActivityListeners) {
                                    onConversationChangeListener.onTyping(conversationId, new UserViewModel(userAvatar, userName, System.currentTimeMillis()), isTyping);
                                }
                            }
                        });
                    }

                    @Override
                    public void onConnectionError() {
                        KayakoLogHelper.e(TAG, "onConnectionError()");
                    }
                });


                kreCaseSubscription.addCasePostChangeListener(new RawCasePostChangeListener() {
                    @Override
                    public void onNewPost(final long messageId) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                for (OnConversationMessagesChangeListener onConversationMessagesChangeListener : sOnConversationMessagesChangeListeners) {
                                    onConversationMessagesChangeListener.onNewMessage(conversationId, messageId);
                                }
                            }
                        });
                    }

                    @Override
                    public void onChangePost(final long messageId) {
                        LoadResourceHelper.loadMessage(conversationId, messageId, new ItemCallback<Message>() {
                            @Override
                            public void onSuccess(final Message item) {

                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        for (OnConversationMessagesChangeListener onConversationMessagesChangeListener : sOnConversationMessagesChangeListeners) {
                                            onConversationMessagesChangeListener.onUpdateMessage(conversationId, item);
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onFailure(KayakoException exception) {
                                KayakoLogHelper.printStackTrace(TAG, exception);
                            }
                        });
                    }

                    @Override
                    public void onConnectionError() {
                        KayakoLogHelper.e(TAG, "onConnectionError()");
                    }
                });

                kreCaseSubscription.addUserPresenceListener(new RawUserOnCasePresenceListener() {
                    @Override
                    public void onUsersAlreadyViewingCase(final List<Long> onlineUserIds, long entryTime) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                // Precaution
                                if (onlineUserIds == null || onlineUserIds.contains(null)) {
                                    return;
                                }

                                // Save state BEFORE CALLBACKS
                                synchronized (mActiveUsersKey) {
                                    Set<Long> activeUsers = getActiveUsers(conversationId);
                                    activeUsers.addAll(onlineUserIds);
                                    sMapActiveUsers.put(conversationId, activeUsers);
                                }

                                // Callbacks
                                for (Long userId : onlineUserIds) {
                                    if (userId != null) {
                                        for (OnConversationUserOnlineListener onConversationUserOnlineListener : sOnConversationUserOnlineListeners) {
                                            onConversationUserOnlineListener.onUserOnline(conversationId, userId);
                                        }
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onNewUserViewingCase(final Long onlineUser, long entryTime) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (onlineUser != null) {

                                    // Save state BEFORE CALLBACKS
                                    synchronized (mActiveUsersKey) {
                                        Set<Long> activeUsers = getActiveUsers(conversationId);
                                        activeUsers.add(onlineUser);
                                        sMapActiveUsers.put(conversationId, activeUsers);
                                    }

                                    // Callbacks
                                    for (OnConversationUserOnlineListener onConversationUserOnlineListener : sOnConversationUserOnlineListeners) {
                                        onConversationUserOnlineListener.onUserOnline(conversationId, onlineUser);
                                    }
                                }

                            }
                        });
                    }

                    @Override
                    public void onUserNoLongerViewingCase(final Long offlineUserId) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                if (offlineUserId != null) {

                                    // Save state BEFORE CALLBACKS
                                    synchronized (mActiveUsersKey) {
                                        Set<Long> activeUsers = getActiveUsers(conversationId);
                                        activeUsers.remove(offlineUserId);
                                        sMapActiveUsers.put(conversationId, activeUsers);
                                    }

                                    // Callbacks
                                    for (OnConversationUserOnlineListener onConversationUserOnlineListener : sOnConversationUserOnlineListeners) {
                                        onConversationUserOnlineListener.onUserOffline(conversationId, offlineUserId);
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onExistingUserPerformingSomeActivity(Long onlineUser, long lastActiveTime) {

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

        sOnConversationChangeListeners.clear();
        sOnConversationClientActivityListeners.clear();
        sOnConversationMessagesChangeListeners.clear();
        sMapActiveUsers.clear();

        // DO NOT untrack onClose and onOpen
    }

    public static void triggerTyping(String conversationPresenceChannelName, long conversationId, boolean isTyping) {
        addKreCaseSubscriptionIfNotExisting(conversationPresenceChannelName, conversationId);

        KreCaseSubscription kreCaseSubscription = sMapSubscriptions.get(conversationPresenceChannelName);
        kreCaseSubscription.triggerTypingEvent(isTyping, true);
    }

    // TODO: Later, build another layer that ensures the code that calls the following method only receives events relating to the cases relevant to that code

    public static void trackChange(String conversationPresenceChannelName, long conversationId, final OnConversationChangeListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Invalid Listener argument");
        }

        sOnConversationChangeListeners.add(listener);
        addKreCaseSubscriptionIfNotExisting(conversationPresenceChannelName, conversationId);

        KayakoLogHelper.d(TAG, "trackChange, set = " + sOnConversationChangeListeners.size());
    }

    public static void trackClientActivity(String conversationPresenceChannelName, long conversationId, final OnConversationClientActivityListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Invalid Listener argument");
        }

        sOnConversationClientActivityListeners.add(listener);
        addKreCaseSubscriptionIfNotExisting(conversationPresenceChannelName, conversationId);

        KayakoLogHelper.d(TAG, "trackClientActivity, set = " + sOnConversationClientActivityListeners.size());
    }

    public static void trackMessageChange(String conversationPresenceChannelName, long conversationId, final OnConversationMessagesChangeListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Invalid Listener argument");
        }

        sOnConversationMessagesChangeListeners.add(listener);
        addKreCaseSubscriptionIfNotExisting(conversationPresenceChannelName, conversationId);

        KayakoLogHelper.d(TAG, "trackMessageChange, set = " + sOnConversationMessagesChangeListeners.size());
    }

    public static void trackPresenceUser(String conversationPresenceChannelName, long conversationId, final OnConversationUserOnlineListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Invalid Listener argument");
        }

        sOnConversationUserOnlineListeners.add(listener);
        addKreCaseSubscriptionIfNotExisting(conversationPresenceChannelName, conversationId);

        KayakoLogHelper.d(TAG, "trackMessageChange, set = " + sOnConversationMessagesChangeListeners.size());
    }

    public static void untrack(OnConversationChangeListener listener) {
        sOnConversationChangeListeners.remove(listener);
    }

    public static void untrack(OnConversationClientActivityListener listener) {
        sOnConversationClientActivityListeners.remove(listener);
    }

    public static void untrack(OnConversationMessagesChangeListener listener) {
        sOnConversationMessagesChangeListeners.remove(listener);
    }

    public static void untrack(OnConversationUserOnlineListener listener) {
        sOnConversationUserOnlineListeners.remove(listener);
    }

    @NonNull
    public static Set<Long> getActiveUsers(long conversationId) {
        synchronized (mActiveUsersKey) {
            if (sMapActiveUsers == null) {
                throw new IllegalStateException("No yet initialized! Method called too soon!");
            }

            return sMapActiveUsers.get(conversationId, new HashSet<Long>()); // Return empty set that can be added to! Not null!
        }
    }
}
