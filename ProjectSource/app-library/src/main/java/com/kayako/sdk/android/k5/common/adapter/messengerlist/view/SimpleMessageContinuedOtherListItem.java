package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.common.adapter.BaseDataListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;

import java.util.HashMap;
import java.util.Map;

public class SimpleMessageContinuedOtherListItem extends BaseDataListItem {

    private String message;
    private long time;

    public SimpleMessageContinuedOtherListItem(@Nullable Long id, @NonNull String message, @Nullable long time, @Nullable Map<String, Object> data) {
        super(MessengerListType.SIMPLE_MESSAGE_CONTINUED_OTHER, id, data);
        this.message = message;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public Map<String, String> getContents() {
        Map<String, String> map = new HashMap<>();
        map.put("time", String.valueOf(time));
        map.put("message", String.valueOf(message));
        return map;
    }

}
