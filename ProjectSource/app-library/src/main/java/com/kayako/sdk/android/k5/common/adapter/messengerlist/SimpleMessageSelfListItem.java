package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;

import java.util.Map;

public class SimpleMessageSelfListItem extends BaseListItem {

    private String message;
    private String avatarUrl;
    private long time;
    private Map<String, Object> data;

    public SimpleMessageSelfListItem(String message, String avatarUrl, long time, Map<String, Object> data) {
        super(MessengerListType.SIMPLE_MESSAGE_SELF);

        this.message = message;
        this.avatarUrl = avatarUrl;
        this.time = time;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
