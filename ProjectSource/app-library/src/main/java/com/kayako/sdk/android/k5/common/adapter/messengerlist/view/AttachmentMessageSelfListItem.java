package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.common.adapter.BaseDataListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.Attachment;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.ChannelDecoration;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.DeliveryIndicator;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;

import java.util.Map;

public class AttachmentMessageSelfListItem extends BaseDataListItem {

    private String avatarUrl;
    private long time;
    private ChannelDecoration channel;
    private Attachment attachment;
    private DeliveryIndicator deliveryIndicator;

    public AttachmentMessageSelfListItem(@Nullable Long id, @NonNull String avatarUrl, @Nullable ChannelDecoration channel, @Nullable DeliveryIndicator deliveryIndicator, @NonNull Attachment attachment, @Nullable long time, @Nullable Map<String, Object> data) {
        super(MessengerListType.ATTACHMENT_MESSAGE_SELF, id, data);
        this.attachment = attachment;
        this.time = time;
        this.avatarUrl = avatarUrl;
        this.channel = channel;
        this.deliveryIndicator = deliveryIndicator;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public ChannelDecoration getChannel() {
        return channel;
    }

    public void setChannel(ChannelDecoration channel) {
        this.channel = channel;
    }

    public DeliveryIndicator getDeliveryIndicator() {
        return deliveryIndicator;
    }
}
