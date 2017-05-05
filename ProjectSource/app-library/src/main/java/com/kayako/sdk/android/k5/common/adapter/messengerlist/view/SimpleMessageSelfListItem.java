package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.common.adapter.BaseDataListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.ChannelDecoration;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.DeliveryIndicator;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;

import java.util.Map;

public class SimpleMessageSelfListItem extends BaseDataListItem {

    // Same arguments as SimpleMessageContinuedSelfListItem but padding is different

    private String message;
    private long time;
    private DeliveryIndicator deliveryIndicator;
    private boolean fadeBackground;

    public SimpleMessageSelfListItem(@Nullable Long id, @NonNull String message, @Nullable long time, @Nullable DeliveryIndicator deliveryIndicator, boolean fadeBackground, @Nullable Map<String, Object> data) {
        super(MessengerListType.SIMPLE_MESSAGE_SELF, id, data);

        this.message = message;
        this.time = time;
        this.deliveryIndicator = deliveryIndicator;
        this.fadeBackground = fadeBackground;
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

    public DeliveryIndicator getDeliveryIndicator() {
        return deliveryIndicator;
    }

    public boolean isFadeBackground() {
        return fadeBackground;
    }
}
