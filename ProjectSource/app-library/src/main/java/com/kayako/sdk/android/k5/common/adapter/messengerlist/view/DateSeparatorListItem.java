package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import android.support.annotation.NonNull;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;

import java.util.Map;

public class DateSeparatorListItem extends BaseListItem {

    private Long timeInMilliseconds;

    public DateSeparatorListItem(@NonNull Long time) {
        super(MessengerListType.DATE_SEPARATOR);
        timeInMilliseconds = time;
    }

    public long getTimeInMilliseconds() {
        return timeInMilliseconds;
    }

    public void setTimeInMilliseconds(long timeInMilliseconds) {
        this.timeInMilliseconds = timeInMilliseconds;
    }
}
