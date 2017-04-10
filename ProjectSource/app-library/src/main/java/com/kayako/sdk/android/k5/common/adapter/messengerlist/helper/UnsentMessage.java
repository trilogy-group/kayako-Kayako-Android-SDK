package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

/**
 * This object is used to represent an unsent message.
 *
 * An unsent has the following:
 * 1. Client Id - to identify the message
 * 2. Message - content of the message
 * 3. Delivery Status - status of the unsent message
 *
 *
 * It should never contain API specific values - such as conversationId. Especially since it can be used for New Conversations (which doesn't have ids)
 */
public class UnsentMessage {
    private ClientDeliveryStatus deliveryStatus;
    private String message;
    private String clientId;

    public UnsentMessage(ClientDeliveryStatus deliveryStatus, String message, String clientId) {
        if (deliveryStatus == null || message == null || clientId == null) {
            throw new IllegalArgumentException("Invalid Arguments");
        }

        this.deliveryStatus = deliveryStatus;
        this.message = message;
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
}
