package com.kayako.sdk.android.k5.kre.base.view;

import com.kayako.sdk.android.k5.kre.base.KreConnection;
import com.kayako.sdk.android.k5.kre.base.KreConnectionFactory;
import com.kayako.sdk.android.k5.kre.base.KreSubscription;
import com.kayako.sdk.android.k5.kre.data.ViewCountChange;
import com.kayako.sdk.android.k5.kre.helpers.KreChangeHelper;
import com.kayako.sdk.android.k5.kre.helpers.RawChangeListener;

import java.util.HashSet;
import java.util.Set;

public class KreViewCountSubscription extends BaseKreSubscription {

    private Set<RawChangeListener<ViewCountChange>> mRawChangeListeners = new HashSet<>();

    public KreViewCountSubscription(KreConnection kreConnection, String name) {
        super(kreConnection, name);
    }

    @Override
    public KreSubscription.OnSubscriptionListener getMainListener() {
        if (getKreSubscription() == null) {
            throw new IllegalStateException("This method should only be called once kreSubscription is initialized");
        }

        return new KreSubscription.OnSubscriptionListener() {
            @Override
            public void onSubscription() {
                KreChangeHelper.addRawChangeListener(getKreSubscription(), new RawChangeListener<ViewCountChange>() {
                    @Override
                    public void onChange(ViewCountChange pushData) {
                        if (mRawChangeListeners != null && mRawChangeListeners.size() != 0) {
                            for (RawChangeListener<ViewCountChange> listener : mRawChangeListeners) {
                                listener.onChange(pushData);
                            }
                        }
                    }

                    @Override
                    public void onConnectionError() {
                        if (mRawChangeListeners != null && mRawChangeListeners.size() != 0) {
                            for (RawChangeListener<ViewCountChange> listener : mRawChangeListeners) {
                                listener.onConnectionError();
                            }
                        }
                    }
                }, ViewCountChange.class);
            }

            @Override
            public void onUnsubscription() {

            }

            @Override
            public void onError(String message) {
            }
        };
    }

    public void addRawChangeListener(RawChangeListener listener) {
        mRawChangeListeners.add(listener);
    }

    public void removeRawChangeListener(RawChangeListener listener) {
        mRawChangeListeners.remove(listener);
    }

}
