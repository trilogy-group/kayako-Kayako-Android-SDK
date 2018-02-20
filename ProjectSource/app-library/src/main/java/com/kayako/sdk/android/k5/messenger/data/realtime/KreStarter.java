package com.kayako.sdk.android.k5.messenger.data.realtime;

public class KreStarter {

    private String realtimeUrl;
    private long currentUserId;
    private String instanceUrl;
    private String fingerprintId;

    public KreStarter(String realtimeUrl, long currentUserId, String instanceUrl, String fingerprintId) {

        if (currentUserId == 0 || instanceUrl == null || fingerprintId == null || realtimeUrl == null) {
            throw new IllegalArgumentException("Invalid Arguments. All of these are mandatory!");
        }

        this.realtimeUrl = realtimeUrl;
        this.currentUserId = currentUserId;
        this.instanceUrl = instanceUrl;
        this.fingerprintId = fingerprintId;
    }

    public String getRealtimeUrl() {
        return realtimeUrl;
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