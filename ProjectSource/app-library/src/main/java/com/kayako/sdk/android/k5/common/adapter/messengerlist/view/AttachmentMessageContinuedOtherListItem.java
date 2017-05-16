package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.common.adapter.BaseDataListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.Attachment;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;

import java.util.HashMap;
import java.util.Map;

public class AttachmentMessageContinuedOtherListItem extends BaseDataListItem {

    private long time;
    private Attachment attachment;

    public AttachmentMessageContinuedOtherListItem(@Nullable Long id, @NonNull Attachment attachment, @Nullable long time, @Nullable Map<String, Object> data) {
        super(MessengerListType.ATTACHMENT_MESSAGE_CONTINUED_OTHER, id, data);
        this.attachment = attachment;
        this.time = time;
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

    @Override
    public Map<String, String> getContents() {
        Map<String, String> map = new HashMap<>();
        map.put("time", String.valueOf(time));
        if (attachment != null) {
            map.putAll(attachment.getContents());
        }
        return map;
    }
}
