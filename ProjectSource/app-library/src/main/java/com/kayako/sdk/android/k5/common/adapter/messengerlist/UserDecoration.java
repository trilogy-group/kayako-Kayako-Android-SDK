package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import android.support.annotation.NonNull;

import com.kayako.sdk.android.k5.common.adapter.ContentComparable;
import com.kayako.sdk.messenger.message.Message;

import java.util.HashMap;
import java.util.Map;

public class UserDecoration implements ContentComparable {
    private String name;
    private Long userId;
    private String avatarUrl;
    private boolean isSelf;

    public UserDecoration(@NonNull String avatarUrl, @NonNull Long userId, boolean isSelf) {
        this.userId = userId;
        this.avatarUrl = avatarUrl;
        this.isSelf = isSelf;
    }

    public UserDecoration(@NonNull String name, @NonNull String avatarUrl, @NonNull Long userId, boolean isSelf) {
        this.name = name;
        this.userId = userId;
        this.avatarUrl = avatarUrl;
        this.isSelf = isSelf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public boolean isSelf() {
        return isSelf;
    }

    public void setSelf(boolean self) {
        isSelf = self;
    }

    @Override
    public Map<String, String> getContents() {
        Map<String, String> map = new HashMap<>();
        map.put("name", String.valueOf(name));
        map.put("userId", String.valueOf(userId));
        map.put("avatarUrl", String.valueOf(avatarUrl));
        map.put("isSelf", String.valueOf(isSelf));
        return map;
    }
}
