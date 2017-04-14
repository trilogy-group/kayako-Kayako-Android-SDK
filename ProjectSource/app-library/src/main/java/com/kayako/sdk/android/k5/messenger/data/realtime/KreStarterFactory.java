package com.kayako.sdk.android.k5.messenger.data.realtime;

import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.android.k5.core.MessengerUserPref;

public class KreStarterFactory {

    private KreStarterFactory() {
    }

    public static KreStarter getKreStarterValues() {
        return new KreStarter(
                MessengerUserPref.getInstance().getUserId(),
                MessengerPref.getInstance().getUrl(),
                MessengerPref.getInstance().getFingerprintId()
        );
    }

}
