package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.common.adapter.BaseDataListItem;

import java.util.Map;

public class AttachmentMessageSelfListItem extends BaseDataListItem {

    private String avatarUrl;
    private String attachmentThumbnailUrl;
    private String message;
    private long time;
    private ChannelDecoration channel;

    public AttachmentMessageSelfListItem(@NonNull String avatarUrl, @Nullable ChannelDecoration channel, @Nullable String attachmentThumbnailUrl, @Nullable String message, @Nullable long time, @Nullable Map<String, Object> data) {
        super(MessengerListType.ATTACHMENT_MESSAGE_SELF, data);
        this.message = message;
        this.attachmentThumbnailUrl = attachmentThumbnailUrl;
        this.time = time;
        this.avatarUrl = avatarUrl;
        this.channel = channel;
    }

    public String getAttachmentThumbnailUrl() {
        return attachmentThumbnailUrl;
    }

    public void setAttachmentThumbnailUrl(String attachmentThumbnailUrl) {
        this.attachmentThumbnailUrl = attachmentThumbnailUrl;
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
}
