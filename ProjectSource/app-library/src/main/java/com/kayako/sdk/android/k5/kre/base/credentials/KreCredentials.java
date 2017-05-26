package com.kayako.sdk.android.k5.kre.base.credentials;

import android.support.annotation.NonNull;

public class KreCredentials {

    @NonNull
    private Type type;

    @NonNull
    private String instanceUrl;

    public KreCredentials(@NonNull Type type, @NonNull String instanceUrl) {
        if (type == null || instanceUrl == null) {
            throw new IllegalArgumentException("Null Values are not allowed");
        }

        this.type = type;
        this.instanceUrl = instanceUrl;
    }

    public enum Type {
        SESSION, FINGERPRINT
    }

    @NonNull
    public String getInstanceUrl() {
        return instanceUrl;
    }

    @NonNull
    public Type getType() {
        return type;
    }
}
