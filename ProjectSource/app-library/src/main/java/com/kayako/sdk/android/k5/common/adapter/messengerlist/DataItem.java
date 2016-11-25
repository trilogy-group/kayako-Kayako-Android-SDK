package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import java.util.List;
import java.util.Map;

public class DataItem {

    /* Fields describing the resource*/
    private Long id;
    private Map<String, Object> data;

    /* Fields needed to generate the views */

    /* Mandatory fields (needed to generate views) */
    private String avatarUrl;
    private String message;
    private Long timeInMilliseconds;
    private Long userId;

    /* Not Mandatory fields (needed to generate views) */
    private ChannelDecoration channelDecoration;
    private boolean isRead;
    private boolean isSelf;
    private List<Attachment> attachments;

    // TODO: Unfurled content model?
    // TODO: Activity Message? isActivity <-- checks should be added before isSelf
    // TODO: Delivery Indicators? Blue Tick, Double Tick...

    public DataItem(Long id, Map<String, Object> data, String avatarUrl, boolean isSelf, long userId, ChannelDecoration channelDecoration, String message, Long timeInMilliseconds, List<Attachment> attachments, boolean isRead) {
        assert avatarUrl != null;
        assert message != null;
        assert timeInMilliseconds != null;

        this.id = id;
        this.data = data;
        this.avatarUrl = avatarUrl;
        this.userId = userId;
        this.isSelf = isSelf;
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isSelf() {
        return isSelf;
    }

    public void setSelf(boolean self) {
        isSelf = self;
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
