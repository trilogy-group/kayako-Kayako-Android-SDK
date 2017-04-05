package com.kayako.sdk.android.k5.common.adapter.conversationlist;

import android.view.View;
import android.widget.TextView;

import com.kayako.sdk.android.k5.common.utils.DateTimeUtils;

public class ConversationListItemHelper {

    private ConversationListItemHelper() {
    }

    public static void setUnreadCounter(TextView unreadCounterView, int unreadCount) {
        if (unreadCount == 0) {
            unreadCounterView.setVisibility(View.GONE);
        } else if (unreadCount > 9) {
            unreadCounterView.setVisibility(View.VISIBLE);
            unreadCounterView.setText("+9"); // Not adding to strings.xml because assuming numbers stay the same across languages
        } else {
            unreadCounterView.setVisibility(View.VISIBLE);
            unreadCounterView.setText(String.valueOf(unreadCount));
        }
    }

    public static void setFormattedTime(TextView timeView, long timeInMilliseconds) {
        timeView.setText(DateTimeUtils.formatShortDateTime(System.currentTimeMillis(), timeInMilliseconds));
    }
}
