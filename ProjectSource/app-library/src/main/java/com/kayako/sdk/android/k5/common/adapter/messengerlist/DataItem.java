package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import java.util.List;
import java.util.Map;

public class DataItem {

    /* Fields describing the resource*/
    private Long id;
    private Map<String, Object> data;

    /* Fields needed to generate the views */

    /* Mandatory fields (needed to generate views) */
    private UserDecoration userDecoration;
    private String message;
    private Long timeInMilliseconds;

    /* Not Mandatory fields (needed to generate views) */
    private ChannelDecoration channelDecoration;
    private boolean isRead;
    private List<Attachment> attachments;

    // TODO: Unfurled content model?
    // TODO: Activity Message? isActivity <-- checks should be added before isSelf
    // TODO: Delivery Indicators? Blue Tick, Double Tick...

    public DataItem(Long id, Map<String, Object> data, UserDecoration userDecoration, ChannelDecoration channelDecoration, String message, Long timeInMilliseconds, List<Attachment> attachments, boolean isRead) {
        assert userDecoration != null;
        assert message != null;
        assert timeInMilliseconds != null;

        this.id = id;
        this.data = data;
        this.userDecoration = userDecoration;
        this.channelDecoration = channelDecoration;
        this.message = message;
        this.timeInMilliseconds = timeInMilliseconds;
        this.attachments = attachments;
        this.isRead = isRead;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimeInMilliseconds() {
        return timeInMilliseconds;
    }

    public void setTimeInMilliseconds(Long timeInMilliseconds) {
        this.timeInMilliseconds = timeInMilliseconds;
    }

    public UserDecoration getUserDecoration() {
        return userDecoration;
    }

    public void setUserDecoration(UserDecoration userDecoration) {
        this.userDecoration = userDecoration;
    }

    public ChannelDecoration getChannelDecoration() {
        return channelDecoration;
    }

    public void setChannelDecoration(ChannelDecoration channelDecoration) {
        this.channelDecoration = channelDecoration;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
}
