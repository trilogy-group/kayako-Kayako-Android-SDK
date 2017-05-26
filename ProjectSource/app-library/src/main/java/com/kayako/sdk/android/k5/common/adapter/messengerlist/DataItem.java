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
    private DeliveryIndicator deliveryIndicator;
    private boolean isRead;
    private List<Attachment> attachments;

    public DataItem(Long id, Map<String, Object> data, UserDecoration userDecoration, ChannelDecoration channelDecoration, DeliveryIndicator deliveryIndicator, String message, Long timeInMilliseconds, List<Attachment> attachments, boolean isRead) {
        assert userDecoration != null;
        assert message != null;
        assert timeInMilliseconds != null;

        this.id = id;
        this.data = data;
        this.userDecoration = userDecoration;
        this.channelDecoration = channelDecoration;
        this.deliveryIndicator = deliveryIndicator;
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

    public UserDecoration getUserDecoration() {
        return userDecoration;
    }

    public ChannelDecoration getChannelDecoration() {
        return channelDecoration;
    }

    public boolean isRead() {
        return isRead;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public DeliveryIndicator getDeliveryIndicator() {
        return deliveryIndicator;
    }
}
