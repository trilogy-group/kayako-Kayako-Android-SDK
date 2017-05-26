package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.DiffUtilsCallback;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.DiffUtilsHelper;

import java.util.HashMap;
import java.util.Map;

public class UnreadSeparatorListItem extends BaseListItem {

    private String text;

    /**
     * Constructor to use for default text
     */
    public UnreadSeparatorListItem() {
        super(MessengerListType.UNREAD_SEPARATOR);
    }

    /**
     * Constructor to set text to UnreadSeparatorListItem
     *
     * @param text
     */
    public UnreadSeparatorListItem(@Nullable String text) {
        super(MessengerListType.UNREAD_SEPARATOR);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public Map<String, String> getContents() {
        Map<String, String> map = new HashMap<>();
        map.put("text", String.valueOf(text));
        return map;
    }
}
