package com.kayako.sdk.android.k5.kre.base.credentials;

import android.support.annotation.NonNull;
import android.text.TextUtils;

public class KreCredentials {

    @NonNull
    private String realtimeUrl;

    @NonNull
    private Type type;

    @NonNull
    private String instanceUrl;

    public KreCredentials(@NonNull String realtimeUrl, @NonNull Type type, @NonNull String instanceUrl) {
        if (type == null || TextUtils.isEmpty(instanceUrl) || TextUtils.isEmpty(realtimeUrl)) {
            throw new IllegalArgumentException("Null Values are not allowed");
        }

        this.type = type;
        this.instanceUrl = instanceUrl;
        this.realtimeUrl = realtimeUrl;
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

    @NonNull
    public String getRealtimeUrl() {
        return realtimeUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KreCredentials that = (KreCredentials) o;

        if (!realtimeUrl.equals(that.realtimeUrl)) return false;
        if (type != that.type) return false;
        return instanceUrl.equals(that.instanceUrl);
    }

    @Override
    public int hashCode() {
        int result = realtimeUrl.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + instanceUrl.hashCode();
        return result;
    }
}
