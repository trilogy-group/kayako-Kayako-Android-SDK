package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;

import java.util.Map;

public class SimpleMessageOtherListItem extends BaseListItem {

    private String message;
    private String avatarUrl;
    private Map<String, Object> data;

    public SimpleMessageOtherListItem(String message, String avatarUrl, Map<String, Object> data) {
        super(MessengerListType.SIMPLE_MESSAGE_OTHER);

        this.message = message;
        this.avatarUrl = avatarUrl;
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
}
