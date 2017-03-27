package com.kayako.sdk.android.k5.messenger.data.conversationstarter;

public class ActiveUser {

    private String avatar;

    private String fullName;

    private Long lastActiveAt;

    public ActiveUser(String avatar, String fullName, Long lastActiveAt) {
        this.avatar = avatar;
        this.fullName = fullName;
        this.lastActiveAt = lastActiveAt;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getFullName() {
        return fullName;
    }

    public Long getLastActiveAt() {
        return lastActiveAt;
    }
}

