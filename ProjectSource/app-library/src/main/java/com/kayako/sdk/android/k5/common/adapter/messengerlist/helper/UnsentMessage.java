package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import android.support.annotation.NonNull;

import com.kayako.sdk.android.k5.common.utils.file.FileAttachment;
import com.kayako.sdk.base.requester.AttachmentFile;

/**
 * This object is used to represent an unsent message.
 * <p>
 * An unsent has the following:
 * 1. Client Id - to identify the message
 * 2. Message - content of the message
 * 3. Delivery Status - status of the unsent message
 * <p>
 * <p>
 * It should never contain API specific values - such as conversationId. Especially since it can be used for New Conversations (which doesn't have ids)
 */
public class UnsentMessage {

    @NonNull
    private ClientDeliveryStatus deliveryStatus;

    @NonNull
    private String clientId;

    private String message;

    private FileAttachment attachment;

    public UnsentMessage(@NonNull String message, @NonNull ClientDeliveryStatus deliveryStatus, @NonNull String clientId) {
        if (deliveryStatus == null || message == null || clientId == null) {
            throw new IllegalArgumentException("Invalid Arguments");
        }

        this.deliveryStatus = deliveryStatus;
        this.message = message;
        this.clientId = clientId;
    }

    public UnsentMessage(@NonNull FileAttachment attachment, @NonNull ClientDeliveryStatus deliveryStatus, @NonNull String clientId) {
        if (deliveryStatus == null || clientId == null || attachment == null) {
            throw new IllegalArgumentException("Invalid Arguments");
        }

        this.attachment = attachment;
        this.deliveryStatus = deliveryStatus;
        this.clientId = clientId;
    }

    public ClientDeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public String getMessage() {
        return message;
    }

    public String getClientId() {
        return clientId;
    }

    public FileAttachment getAttachment() {
        return attachment;
    }
}
