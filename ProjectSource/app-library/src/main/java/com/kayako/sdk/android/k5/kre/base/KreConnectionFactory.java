package com.kayako.sdk.android.k5.kre.base;

import com.kayako.sdk.android.k5.kre.base.KreConnection;

/**
 * Created on 06/03/18.
 */

public class KreConnectionFactory {

    private static final Object mLock = new Object();
    private static KreConnection sMessengerKreConnection;
    private static KreConnection sAgentKreConnection;

    private KreConnectionFactory() {
    }

    public static KreConnection getConnection(boolean isAgent) {
        if (isAgent) {
            return getAgentKreConnection();
        } else {
            return getMessengerKreConnection();
        }
    }

    private static KreConnection getMessengerKreConnection() {
        if (sMessengerKreConnection == null) {
            synchronized (mLock) {
                if (sMessengerKreConnection == null) {
                    sMessengerKreConnection = new KreConnection();
                }
            }
        }

        return sMessengerKreConnection;
    }

    private static KreConnection getAgentKreConnection() {
        if (sAgentKreConnection == null) {
            synchronized (mLock) {
                if (sAgentKreConnection == null) {
                    sAgentKreConnection = new KreConnection();
                }
            }
        }

        return sAgentKreConnection;
    }

}
