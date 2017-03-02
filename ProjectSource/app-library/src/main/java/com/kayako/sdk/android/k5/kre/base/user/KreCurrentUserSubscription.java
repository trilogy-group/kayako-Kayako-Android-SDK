package com.kayako.sdk.android.k5.kre.base.user;

import com.kayako.sdk.android.k5.kre.base.KreSubscriptionFactory;

public class KreCurrentUserSubscription extends KreUserSubscription {

    public KreCurrentUserSubscription() {
        super(KreSubscriptionFactory.getCurrentUserKreSubscription());
    }
}
