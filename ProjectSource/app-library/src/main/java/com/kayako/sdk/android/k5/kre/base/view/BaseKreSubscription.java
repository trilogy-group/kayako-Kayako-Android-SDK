package com.kayako.sdk.android.k5.kre.base.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.kre.base.KreSubscription;
import com.kayako.sdk.android.k5.kre.base.credentials.KreCredentials;
import com.kayako.sdk.android.k5.kre.data.Payload;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseKreSubscription {

    private KreSubscription mKreSubscription;
    private KreSubscription.OnSubscriptionListener mMainListener;
    private List<KreSubscription.OnSubscriptionListener> mChildListeners = new ArrayList<>();

    public BaseKreSubscription(String name) {
        mKreSubscription = new KreSubscription(name);

        if (getMainListener() == null) { // check after mKreSubscription gets initialized
            throw new IllegalStateException();
        }
    }

    public synchronized void subscribe(@NonNull KreCredentials kreCredentials, @NonNull final String channel, @NonNull final KreSubscription.OnSubscriptionListener onSubscriptionListener) {
        subscribe(kreCredentials, channel, onSubscriptionListener, null);
    }

    public synchronized <T extends Payload> void subscribe(@NonNull KreCredentials kreCredentials, @NonNull final String channel, @NonNull final KreSubscription.OnSubscriptionListener onSubscriptionListener, @Nullable T payloadObject) {
        if (mMainListener == null) {
            mKreSubscription.subscribe(kreCredentials, channel, mMainListener = getMainListener(), payloadObject);
        }

        mChildListeners.add(onSubscriptionListener);

        // KreSubscription already ensures that all subscriptions share the same channel name and are using a single socket shared across all new subscriptions
        mKreSubscription.subscribe(kreCredentials, channel, onSubscriptionListener, payloadObject);
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

    public KreSubscription getKreSubscription() {
        return mKreSubscription;
    }

    public abstract KreSubscription.OnSubscriptionListener getMainListener();
}
