package com.kayako.sdk.android.k5.kre.base.user;

public class KreUserSubscriptionFactory {

    private KreUserSubscriptionFactory() {
    }

    private static KreRequesterUserSubscription mKreRequesterUserSubscription;
    private static KreCurrentUserSubscription mKreCurrentUserSubscription;

    public static KreRequesterUserSubscription getKreRequesterSubscription() {
        if (mKreRequesterUserSubscription == null) {
            mKreRequesterUserSubscription = new KreRequesterUserSubscription();
        }
        return mKreRequesterUserSubscription;
    }

    public static KreCurrentUserSubscription getKreCurrentUserSubscription() {
        if (mKreCurrentUserSubscription == null) {
            mKreCurrentUserSubscription = new KreCurrentUserSubscription();
        }
        return mKreCurrentUserSubscription;
    }
}
