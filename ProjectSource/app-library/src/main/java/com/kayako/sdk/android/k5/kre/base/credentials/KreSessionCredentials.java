package com.kayako.sdk.android.k5.kre.base.credentials;

import android.support.annotation.NonNull;

public class KreSessionCredentials extends KreCredentials {

    @NonNull
    private String sessionId;

    @NonNull
    private String userAgent;

    public KreSessionCredentials(@NonNull String realtimeUrl, @NonNull String sessionId, @NonNull String instanceUrl, @NonNull String userAgent) {
        super(realtimeUrl, Type.SESSION, instanceUrl);

        if (sessionId == null || userAgent == null) {
            throw new IllegalArgumentException("Null values are not allowed");
        }

        this.sessionId = sessionId;
        this.userAgent = userAgent;
    }

    @NonNull
    public String getSessionId() {
        return sessionId;
    }

    @NonNull
    public String getUserAgent() {
        return userAgent;
    }
}
