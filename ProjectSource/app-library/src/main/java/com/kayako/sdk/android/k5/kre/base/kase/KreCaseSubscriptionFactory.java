package com.kayako.sdk.android.k5.kre.base.kase;

public class KreCaseSubscriptionFactory {

    private KreCaseSubscriptionFactory() {
    }

    private static KreCaseSubscription mKreCaseSubscription;

    public static KreCaseSubscription getKreCaseSubscription(long currentUserId) {
        if (mKreCaseSubscription == null) {
            mKreCaseSubscription = new KreCaseSubscription(currentUserId);
        }
        return mKreCaseSubscription;
    }
}
