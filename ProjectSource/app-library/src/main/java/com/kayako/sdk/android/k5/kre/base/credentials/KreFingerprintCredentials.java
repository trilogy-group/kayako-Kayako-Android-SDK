package com.kayako.sdk.android.k5.kre.base.credentials;

import android.support.annotation.NonNull;

public class KreFingerprintCredentials extends KreCredentials {

    @NonNull
    private String fingerprintId;

    public KreFingerprintCredentials(@NonNull String instanceUrl, @NonNull String fingerprintId) {
        super(Type.FINGERPRINT, instanceUrl);
        this.fingerprintId = fingerprintId;
    }

    @NonNull
    public String getFingerprintId() {
        return fingerprintId;
    }
}
