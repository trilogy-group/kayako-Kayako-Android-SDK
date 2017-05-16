package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.common.adapter.BaseDataListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.ChannelDecoration;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.DiffUtilsHelper;

import java.util.HashMap;
import java.util.Map;

public class TypingListItem extends BaseDataListItem {

    private String avatarUrl;

    public TypingListItem(@Nullable Long id, @NonNull String avatarUrl, @Nullable Map<String, Object> data) {
        super(MessengerListType.TYPING_FOOTER, id, data);

        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public Map<String, String> getContents() {
        Map<String, String> map = new HashMap<>();
        map.put("avatarUrl", String.valueOf(avatarUrl));
        return map;
    }

}
