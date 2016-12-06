package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;

public class EmptyListItem extends BaseListItem {

    public EmptyListItem() {
        super(MessengerListType.EMPTY_SEPARATOR);
    }
}
