package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;

import java.util.HashMap;
import java.util.Map;

public class SystemMessageListItem extends BaseListItem {

    private String message;

    public SystemMessageListItem(@NonNull String message) {
        super(MessengerListType.SYSTEM_MESSAGE);
        this.message = message;

        if (message == null || message.length() == 0) {
            throw new IllegalArgumentException();
        }
    }

    public String getMessage() {
        return message;
    }

    @Override
    public Map<String, String> getContents() {
        Map<String, String> map = new HashMap<>();
        map.put("message", String.valueOf(message));
        return map;
    }
}
