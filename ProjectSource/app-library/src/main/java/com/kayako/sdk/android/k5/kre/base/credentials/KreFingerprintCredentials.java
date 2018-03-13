package com.kayako.sdk.android.k5.kre.base.credentials;

import android.support.annotation.NonNull;

public class KreFingerprintCredentials extends KreCredentials {

    @NonNull
    private String fingerprintId;

    public KreFingerprintCredentials(@NonNull String realtimeUrl, @NonNull String instanceUrl, @NonNull String fingerprintId) {
        super(realtimeUrl, Type.FINGERPRINT, instanceUrl);
        this.fingerprintId = fingerprintId;
    }

    @NonNull
    public String getFingerprintId() {
        return fingerprintId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        KreFingerprintCredentials that = (KreFingerprintCredentials) o;

        return fingerprintId.equals(that.fingerprintId);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + fingerprintId.hashCode();
        return result;
    }
}
