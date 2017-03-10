package com.kayako.sdk.android.k5.kre.base.user;

import android.support.annotation.NonNull;

import com.kayako.sdk.android.k5.kre.base.KreSubscription;
import com.kayako.sdk.android.k5.kre.base.credentials.KreCredentials;
import com.kayako.sdk.android.k5.kre.helpers.KreOnlinePresenceHelper;
import com.kayako.sdk.android.k5.kre.helpers.RawUserOnlinePresenceListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class KreUserSubscription {

    private KreSubscription mKreSubscription;
    private KreSubscription.OnSubscriptionListener mMainListener;
    private List<KreSubscription.OnSubscriptionListener> mChildListeners = new ArrayList<>();
    private List<RawUserOnlinePresenceListener> mOnlinePresenceListeners = new ArrayList<>();

    public KreUserSubscription(String name) {
        mKreSubscription = new KreSubscription(name);
    }

    public void addUserOnlinePresenceListener(RawUserOnlinePresenceListener listener) {
        mOnlinePresenceListeners.add(listener);
    }

    public synchronized void subscribe(@NonNull KreCredentials kreCredentials, @NonNull final String userPresenceChannel, @NonNull final long userId, @NonNull final KreSubscription.OnSubscriptionListener onSubscriptionListener) {
        if (mMainListener == null) {
            mKreSubscription.subscribe(kreCredentials, userPresenceChannel, mMainListener = new KreSubscription.OnSubscriptionListener() {
                @Override
                public void onSubscription() {
                    KreOnlinePresenceHelper.addRawOnlinePresenceHelper(mKreSubscription, userId, new RawUserOnlinePresenceListener() {
                        @Override
                        public void onUserOnline() {
                            for (RawUserOnlinePresenceListener listener : mOnlinePresenceListeners) {
                                listener.onUserOnline();
                            }
                        }

                        @Override
                        public void onUserOffline() {
                            for (RawUserOnlinePresenceListener listener : mOnlinePresenceListeners) {
                                listener.onUserOffline();
                            }
                        }

                        @Override
                        public void onConnectionError() {
                            for (RawUserOnlinePresenceListener listener : mOnlinePresenceListeners) {
                                listener.onConnectionError();
                            }
                        }
                    });
                }

                @Override
                public void onUnsubscription() {

                }

                @Override
                public void onError(String message) {
                }
            });
        }

        mChildListeners.add(onSubscriptionListener);

        // KreSubscription already ensures that all subscriptions share the same channel name and are using a single socket shared across all new subscriptions
        mKreSubscription.subscribe(kreCredentials, userPresenceChannel, onSubscriptionListener);
    }

    public synchronized void unSubscribe(@NonNull KreSubscription.OnSubscriptionListener onSubscriptionListener) {
        mChildListeners.remove(onSubscriptionListener);
        mKreSubscription.unSubscribe(onSubscriptionListener);

        if (mChildListeners.size() == 0) { // last subscription
            mKreSubscription.unSubscribe(mMainListener);
        }
    }

    public boolean isReady() {
        return mKreSubscription.isConnected() && mKreSubscription.hasSubscribed();
    }

}
