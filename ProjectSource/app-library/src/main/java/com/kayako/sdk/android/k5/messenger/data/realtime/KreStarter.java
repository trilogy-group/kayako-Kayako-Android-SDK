package com.kayako.sdk.android.k5.messenger.data.realtime;

public class KreStarter {

    private long currentUserId;
    private String instanceUrl;
    private String fingerprintId;

    public KreStarter(long currentUserId, String instanceUrl, String fingerprintId) {

        if (currentUserId == 0 || instanceUrl == null || fingerprintId == null) {
            throw new IllegalArgumentException("Invalid Arguments. All of these are mandatory!");
        }

        this.currentUserId = currentUserId;
        this.instanceUrl = instanceUrl;
        this.fingerprintId = fingerprintId;
    }

    public long getCurrentUserId() {
        return currentUserId;
    }

    public String getInstanceUrl() {
        return instanceUrl;
    }

    public String getFingerprintId() {
        return fingerprintId;
    }
}