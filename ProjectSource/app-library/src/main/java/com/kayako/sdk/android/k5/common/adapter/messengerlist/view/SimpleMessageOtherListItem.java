package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.common.adapter.BaseDataListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.ChannelDecoration;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.DiffUtilsHelper;

import java.util.HashMap;
import java.util.Map;

public class SimpleMessageOtherListItem extends BaseDataListItem {

    private String message;
    private String avatarUrl;
    private long time;
    private ChannelDecoration channel;

    public SimpleMessageOtherListItem(@Nullable Long id, @NonNull String message, @NonNull String avatarUrl, @Nullable ChannelDecoration channel, @Nullable long time, @Nullable Map<String, Object> data) {
        super(MessengerListType.SIMPLE_MESSAGE_OTHER, id, data);

        this.message = message;
        this.avatarUrl = avatarUrl;
        this.time = time;
        this.channel = channel;
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

    @Override
    public Map<String, String> getContents() {
        Map<String, String> map = new HashMap<>();
        map.put("message", String.valueOf(message));
        map.put("avatarUrl", String.valueOf(avatarUrl));
        map.put("time", String.valueOf(time));
        if(channel!=null){
            map.putAll(channel.getContents());
        }
        return map;
    }

}
