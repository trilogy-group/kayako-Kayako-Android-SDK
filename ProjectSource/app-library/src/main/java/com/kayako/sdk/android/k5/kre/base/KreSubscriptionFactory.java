package com.kayako.sdk.android.k5.kre.base;

public class KreSubscriptionFactory {

    private KreSubscriptionFactory() {
    }

    private static KreSubscription mConversationKreSubscription;
    private static KreSubscription mCurrentUserKreSubscription;
    private static KreSubscription mRequesterUserKreSubscription;

    public static KreSubscription getConversationKreSubscription() {
        if (mConversationKreSubscription == null) {
            mConversationKreSubscription = new KreSubscription();
        }
        return mConversationKreSubscription;
    }

    public static KreSubscription getCurrentUserKreSubscription() {
        if (mCurrentUserKreSubscription == null) {
            mCurrentUserKreSubscription = new KreSubscription();
        }
        return mCurrentUserKreSubscription;
    }

    public static KreSubscription getRequesterKreSubscription() {
        if (mRequesterUserKreSubscription == null) {
            mRequesterUserKreSubscription = new KreSubscription();
        }
        return mRequesterUserKreSubscription;
    }
}
