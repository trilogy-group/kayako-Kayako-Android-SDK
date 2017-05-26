package com.kayako.sdk.android.k5.common.adapter.messengerlist;

public class ViewBehaviour {

    public Boolean showTime;
    public Boolean showAvatar;
    public Boolean showChannel;
    public Boolean showAsSelf;
    public Boolean showDeliveryIndicator;

    public MessageType messageType;

    public ViewBehaviour(boolean showTime, boolean showAvatar, boolean showChannel, boolean showAsSelf, boolean showDeliveryIndicator, MessageType messageType) {
        this.showTime = showTime;
        this.showAvatar = showAvatar;
        this.showChannel = showChannel;
        this.showAsSelf = showAsSelf;
        this.messageType = messageType;
        this.showDeliveryIndicator = showDeliveryIndicator;
    }

    public enum MessageType {
        SIMPLE, ATTACHMENT
    }
}
