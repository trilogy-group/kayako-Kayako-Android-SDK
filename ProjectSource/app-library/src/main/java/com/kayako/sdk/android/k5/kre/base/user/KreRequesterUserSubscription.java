package com.kayako.sdk.android.k5.kre.base.user;

import com.kayako.sdk.android.k5.kre.base.KreSubscriptionFactory;

public class KreRequesterUserSubscription extends KreUserSubscription {

    protected KreRequesterUserSubscription() {
        super(KreSubscriptionFactory.getRequesterKreSubscription());
    }
}
