package com.kayako.sdk.android.k5.kre.helpers.presence;

import com.kayako.sdk.android.k5.kre.base.KreSubscription;
import com.kayako.sdk.android.k5.kre.data.ClientForegroundViewing;
import com.kayako.sdk.android.k5.kre.data.ClientTyping;
import com.kayako.sdk.android.k5.kre.data.ClientUpdating;

public class KrePresencePushDataHelper {

    private static final String EVENT_UPDATE_PRESENCE_META = "update-presence-meta";

    private KrePresencePushDataHelper() {
    }

    public static boolean triggerUpdatingEvent(KreSubscription kreSubscription, boolean isUpdating) {
        if (kreSubscription.hasSubscribed()) {
            kreSubscription.triggerEvent(EVENT_UPDATE_PRESENCE_META, new ClientUpdating(isUpdating, System.currentTimeMillis()));
            return true;
        } else {
            return false;
        }
    }

    public static boolean triggerTypingEvent(KreSubscription kreSubscription, boolean isTyping) {
        if (kreSubscription.hasSubscribed()) {
            kreSubscription.triggerEvent(EVENT_UPDATE_PRESENCE_META, new ClientTyping(isTyping, System.currentTimeMillis()));
            return true;
        } else {
            return false;
        }

    }

    public static boolean triggerForegroundViewingEvent(KreSubscription kreSubscription, boolean isViewing, boolean isForeground) {
        if (isForeground && !isViewing) {
            throw new IllegalArgumentException("A user first views the case, then keeps it in foreground. Not vice versa!");
        }

        if (kreSubscription.hasSubscribed()) {
            kreSubscription.triggerEvent(EVENT_UPDATE_PRESENCE_META, new ClientForegroundViewing(isViewing, isForeground, System.currentTimeMillis()));
            return true;
        } else {
            return false;
        }
    }

}

