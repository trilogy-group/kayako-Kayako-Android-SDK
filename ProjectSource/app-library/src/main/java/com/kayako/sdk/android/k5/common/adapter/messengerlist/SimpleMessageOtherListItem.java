package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;

import java.util.Map;

public class SimpleMessageOtherListItem extends BaseListItem {

    private String message;
    private String avatarUrl;
    private long time;
    private ChannelDecoration channel;
    private Map<String, Object> data;

    public SimpleMessageOtherListItem(@NonNull String message, @NonNull String avatarUrl, @Nullable ChannelDecoration channel, @Nullable long time, @Nullable Map<String, Object> data) {
        super(MessengerListType.SIMPLE_MESSAGE_OTHER);

        this.message = message;
        this.avatarUrl = avatarUrl;
        this.time = time;
        this.channel = channel;
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

    public ChannelDecoration getChannel() {
        return channel;
    }

    public void setChannel(ChannelDecoration channel) {
        this.channel = channel;
    }
}
