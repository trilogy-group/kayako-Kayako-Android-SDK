package com.kayako.sdk.android.k5.kre.base.kase;

import android.support.annotation.NonNull;

import com.kayako.sdk.android.k5.kre.base.KreSubscription;
import com.kayako.sdk.android.k5.kre.base.credentials.KreCredentials;
import com.kayako.sdk.android.k5.kre.data.Change;
import com.kayako.sdk.android.k5.kre.helpers.KreCaseChangeHelper;
import com.kayako.sdk.android.k5.kre.helpers.RawCaseChangeListener;
import com.kayako.sdk.android.k5.kre.helpers.RawClientActivityListener;
import com.kayako.sdk.android.k5.kre.helpers.RawClientTypingListener;
import com.kayako.sdk.android.k5.kre.helpers.RawUserOnCasePresenceListener;
import com.kayako.sdk.android.k5.kre.helpers.presence.KrePresenceHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class enforces that there in only ONE Kre Subscription for a single case realtime channel.
 * <p>
 * When there is an event (relevant to a case), those events are distributed to all registered listeners.
 * This set up ensures that multiple subscriptions of the same channel throughout the app usees only a single socket.
 * <p>
 * This was done to prevent the following issues:
 * - ConcurrentModificationExceptions if more than one instance was used sharing the same (happened on the onMessage method in library)
 * - Multiple subscriptions led to multiple bindings which led to only one unique binding being triggered on event. Other subscriptions did not receive any events. Therefore, avoided relying on library bindings to ensure event is passed to all subscriptions.
 * - Multiple unsubscriptions led to active ui widgets not receiving events - Unsubscribe called by one ui widget closes connection despite the fact that mutliple ui widgets are still relying on the connection
 * <p>
 * Note: Logs added to track and ensure that once a page closes, unsubscribe should be called properly
 */
public class KreCaseSubscription {

    private KreSubscription mKreSubscription;
    private KrePresenceHelper mKrePresenceHelper;
    private KreSubscription.OnSubscriptionListener mMainListener;
    private List<KreSubscription.OnSubscriptionListener> mChildListeners = new ArrayList<>();

    private AtomicBoolean hasSubscribeBeenCalledOnce = new AtomicBoolean(false);
    private List<RawClientTypingListener> mClientTypingListeners = new ArrayList<>();
    private List<RawClientActivityListener> mClientActivityListeners = new ArrayList<>();
    private List<RawCaseChangeListener> mCaseChangeListeners = new ArrayList<>();
    private List<RawUserOnCasePresenceListener> mUserPresenceListeners = new ArrayList<>();

    public KreCaseSubscription(@NonNull String name, long currentUserId) {
        mKreSubscription = new KreSubscription(name);
        mKrePresenceHelper = new KrePresenceHelper(mKreSubscription, true, currentUserId);
    }

    public void addClientTypingListener(RawClientTypingListener listener) {
        mClientTypingListeners.add(listener);
    }

    public void addClientActivityListener(RawClientActivityListener listener) {
        mClientActivityListeners.add(listener);
    }

    public void addCaseChangeListener(RawCaseChangeListener listener) {
        mCaseChangeListeners.add(listener);
    }

    public void addUserPresenceListener(RawUserOnCasePresenceListener listener) {
        mUserPresenceListeners.add(listener);
    }

    public void removeClientTypingListener(RawClientTypingListener listener) {
        mClientTypingListeners.remove(listener);
    }

    public void removeClientActivityListener(RawClientActivityListener listener) {
        mClientActivityListeners.remove(listener);
    }

    public void removeCaseChangeListener(RawCaseChangeListener listener) {
        mCaseChangeListeners.remove(listener);
    }

    public void removeUserPresenceListener(RawUserOnCasePresenceListener listener) {
        mUserPresenceListeners.remove(listener);
    }

    public void triggerUpdatingEvent(boolean isUpdating) {
        if (mKreSubscription.isConnected()) {
            mKrePresenceHelper.triggerClientUpdatingCaseEvent(isUpdating);
        }
    }

    public void triggerTypingEvent(boolean isTyping, boolean autoDisableTyping) {
        if (mKreSubscription.isConnected()) {
            mKrePresenceHelper.triggerClientTypingCaseEvent(isTyping, autoDisableTyping);
        }
    }

    public boolean isReady() {
        return mKreSubscription.isConnected() && mKreSubscription.hasSubscribed();
    }

    public synchronized void subscribe(@NonNull KreCredentials kreCredentials, @NonNull final String channel, @NonNull final KreSubscription.OnSubscriptionListener onSubscriptionListener) {
        if (kreCredentials == null || channel == null || onSubscriptionListener == null) {
            throw new IllegalArgumentException("Null arguments are not allowed");
        }

        if (mMainListener == null) {
            mMainListener = new KreSubscription.OnSubscriptionListener() {
                @Override
                public synchronized void onSubscription() {
                    // For Cases Presence Channels, subscribing alone does not imply a user is online, instead, we need to trigger an event to show the user is online
                    mKrePresenceHelper.triggerClientForegroundEvent(true, true);

                    // The listening of events should only be called once (except for a new case realtime channel). Every other subscription should only add a listener and expect events (from the first subscription)
                    mKrePresenceHelper.setRawUserOnCasePresenceListener(new RawUserOnCasePresenceListener() {
                        @Override
                        public void onUsersAlreadyViewingCase(List<Long> onlineUserIds, long entryTime) {
                            if (mUserPresenceListeners != null) {
                                for (RawUserOnCasePresenceListener listener : mUserPresenceListeners) {
                                    listener.onUsersAlreadyViewingCase(onlineUserIds, entryTime);
                                }
                            }
                        }

                        @Override
                        public void onNewUserViewingCase(Long onlineUser, long entryTime) {
                            if (mUserPresenceListeners != null) {
                                for (RawUserOnCasePresenceListener listener : mUserPresenceListeners) {
                                    listener.onNewUserViewingCase(onlineUser, entryTime);
                                }
                            }
                        }

                        @Override
                        public void onUserNoLongerViewingCase(Long offlineUserId) {
                            if (mUserPresenceListeners != null) {
                                for (RawUserOnCasePresenceListener listener : mUserPresenceListeners) {
                                    listener.onUserNoLongerViewingCase(offlineUserId);
                                }
                            }
                        }

                        @Override
                        public void onExistingUserPerformingSomeActivity(Long onlineUser, long lastActiveTime) {
                            if (mUserPresenceListeners != null) {
                                for (RawUserOnCasePresenceListener listener : mUserPresenceListeners) {
                                    listener.onExistingUserPerformingSomeActivity(onlineUser, lastActiveTime);
                                }
                            }
                        }

                        @Override
                        public void onConnectionError() {
                            if (mUserPresenceListeners != null) {
                                for (RawUserOnCasePresenceListener listener : mUserPresenceListeners) {
                                    listener.onConnectionError();
                                }
                            }
                        }
                    });

                    mKrePresenceHelper.setRawClientActivityListener(new RawClientActivityListener() {
                        @Override
                        public void onClientActivity(long userId, boolean isUpdating) {
                            if (mClientActivityListeners != null) {
                                for (RawClientActivityListener listener : mClientActivityListeners) {
                                    listener.onClientActivity(userId, isUpdating);
                                }
                            }
                        }

                        @Override
                        public void onConnectionError() {
                            if (mClientActivityListeners != null) {
                                for (RawClientActivityListener listener : mClientActivityListeners) {
                                    listener.onConnectionError();
                                }
                            }
                        }
                    });

                    mKrePresenceHelper.setRawClientTypingListener(new RawClientTypingListener() {
                        @Override
                        public void onUserTyping(long userId, boolean isTyping) {
                            if (mClientTypingListeners != null) {
                                for (RawClientTypingListener listener : mClientTypingListeners) {
                                    listener.onUserTyping(userId, isTyping);
                                }
                            }
                        }

                        @Override
                        public void onConnectionError() {
                            if (mClientTypingListeners != null) {
                                for (RawClientTypingListener listener : mClientTypingListeners) {
                                    listener.onConnectionError();
                                }
                            }
                        }
                    });

                    KreCaseChangeHelper.addRawCaseChangeListener(mKreSubscription, new RawCaseChangeListener() {
                        @Override
                        public void onCaseChange(final Change change) {
                            // KreLogHelper.e("KRE: CaseSubsctiption, Change object = ", change.toString());
                            if (mCaseChangeListeners != null) {
                                for (RawCaseChangeListener listener : mCaseChangeListeners) {
                                    listener.onCaseChange(change);
                                }
                            }
                        }

                        @Override
                        public void onConnectionError() {
                            if (mCaseChangeListeners != null) {
                                for (RawCaseChangeListener listener : mCaseChangeListeners) {
                                    listener.onConnectionError();
                                }
                            }
                        }
                    });

                    hasSubscribeBeenCalledOnce.set(true); // ensure state set once all events are subscribed to
                }

                @Override
                public void onUnsubscription() {
                }

                @Override
                public void onError(String message) {
                }
            };

            mKreSubscription.subscribe(kreCredentials, channel, mMainListener);
        }

        // Keep track of listeners
        mChildListeners.add(onSubscriptionListener);

        // KreSubscription already internally ensures that multiple connections are not made to the same socket and yet onSubscription() is called properly for every new subscription
        mKreSubscription.subscribe(kreCredentials, channel, onSubscriptionListener);
    }

    public synchronized void unSubscribe(@NonNull KreSubscription.OnSubscriptionListener onSubscriptionListener) {
        if (mChildListeners.size() == 1) { // last subscription

            // During final unsubscribe,  typing & updating events - set to false
            mKrePresenceHelper.triggerClientTypingCaseEvent(false, false);
            mKrePresenceHelper.triggerClientUpdatingCaseEvent(false);
            mKrePresenceHelper.triggerClientForegroundEvent(false, false);

            mKreSubscription.unSubscribe(onSubscriptionListener);
            mKreSubscription.unSubscribe(mMainListener);
            resetVariables();
        } else {
            mKreSubscription.unSubscribe(onSubscriptionListener);
            mChildListeners.remove(onSubscriptionListener);
        }
    }

    private void resetVariables() {
        hasSubscribeBeenCalledOnce.set(false);
        mMainListener = null;

        mClientTypingListeners = new CopyOnWriteArrayList<>();
        mClientActivityListeners = new CopyOnWriteArrayList<>();
        mCaseChangeListeners = new CopyOnWriteArrayList<>();
        mUserPresenceListeners = new CopyOnWriteArrayList<>();
        mChildListeners = new CopyOnWriteArrayList<>();
    }
}
