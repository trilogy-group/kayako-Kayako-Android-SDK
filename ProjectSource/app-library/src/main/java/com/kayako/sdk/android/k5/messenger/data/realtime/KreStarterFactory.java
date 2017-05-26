package com.kayako.sdk.android.k5.messenger.data.realtime;

import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.android.k5.core.MessengerUserPref;

public class KreStarterFactory {

    private KreStarterFactory() {
    }

    public static KreStarter getKreStarterValues() {
        Long userId = MessengerUserPref.getInstance().getUserId();
        String url = MessengerPref.getInstance().getUrl();
        String fingerprintId = MessengerPref.getInstance().getFingerprintId();

        if (userId == null
                || url == null
                || fingerprintId == null) {
            throw new IllegalStateException("Invalid Arguments");
        }

        return new KreStarter(
                userId,
                url,
                fingerprintId
        );
    }

}
