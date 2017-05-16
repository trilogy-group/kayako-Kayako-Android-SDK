package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;

import java.util.HashMap;
import java.util.Map;

public class EmptyListItem extends BaseListItem {

    public EmptyListItem() {
        super(MessengerListType.EMPTY_SEPARATOR);
    }

    @Override
    public Map<String, String> getContents() {
        Map<String, String> map = new HashMap<>();
        return map;
    }
}
