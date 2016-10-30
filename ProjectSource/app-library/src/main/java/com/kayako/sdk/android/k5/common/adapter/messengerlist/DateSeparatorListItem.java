package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;

import java.util.Map;

public class DateSeparatorListItem extends BaseListItem {

    public long timeInMilliseconds;

    public DateSeparatorListItem(long time) {
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
