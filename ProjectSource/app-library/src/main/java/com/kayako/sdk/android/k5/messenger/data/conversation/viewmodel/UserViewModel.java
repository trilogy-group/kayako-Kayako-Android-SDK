package com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel;

import com.kayako.sdk.android.k5.common.adapter.ContentComparable;

import java.util.HashMap;
import java.util.Map;

public class UserViewModel implements ContentComparable{

    private String avatar;

    private String fullName;

    private Long lastActiveAt;

    public UserViewModel(String avatar, String fullName, Long lastActiveAt) {
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

    @Override
    public Map<String, String> getContents() {
        Map<String, String> map = new HashMap<>();
        map.put("avatar",String.valueOf(avatar));
        map.put("fullName",String.valueOf(fullName));
        map.put("lastActiveAt",String.valueOf(lastActiveAt));
        return map;
    }
}

