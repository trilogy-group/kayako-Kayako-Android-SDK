package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.SimpleMessageContinuedSelfListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.SimpleMessageSelfListItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OptimisticSendingHelper {

    private static final String MAP_KEY_CLIENT_ID = "optimistic_sending_client_id";
    private List<OptimisticMessage> optimisticMessages = new ArrayList<>();
    private String userAvatarUrl;

    public OptimisticSendingHelper(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
        if (this.userAvatarUrl == null) {
            // TODO: If null, show default avatar for customer
        }
    }

    public synchronized List<BaseListItem> getOptimisticMessages() {
        List<BaseListItem> baseListItems = new ArrayList<>();
        for (OptimisticMessage optimisticMessage : optimisticMessages) {
            baseListItems.add(optimisticMessage.getMessageListItem());
        }
        return baseListItems;
    }

    public synchronized void removeOptimisticMessage(String clientId) {
        int position = findOptimisticMessage(clientId);
        if (position != -1) {
            optimisticMessages.remove(position);
        }
    }

    public synchronized void markOptimisticMessageAsFailed(String clientId) {
        int position = findOptimisticMessage(clientId);

        OptimisticMessage messageToReplace = optimisticMessages.get(position);
        optimisticMessages.remove(position);
        optimisticMessages.add(
                position,
                generateOptimisticSendingMessage(
                        DeliveryIndicatorHelper.ClientDeliveryStatus.FAILED_TO_SEND,
                        messageToReplace.getMessage(),
                        messageToReplace.getClientId()
                )
        );
    }

    public synchronized void addOptimisticMessage(DeliveryIndicatorHelper.ClientDeliveryStatus deliveryStatus, String message, String clientId) {
        optimisticMessages.add(generateOptimisticSendingMessage(deliveryStatus, message, clientId));
    }

    public boolean isOptimisticMessage(Map<String, Object> messageDataOfBaseListItem) {
        if (messageDataOfBaseListItem != null && messageDataOfBaseListItem.containsKey(MAP_KEY_CLIENT_ID)) {
            return true;
        } else {
            return false;
        }
    }

    public String extractClientId(Map<String, Object> messageDataOfBaseListItem) {
        if (messageDataOfBaseListItem == null || !messageDataOfBaseListItem.containsKey(MAP_KEY_CLIENT_ID)) {
            throw new AssertionError("Call isOptimisticMessage() first, and if true, only then call this method");
        }

        return (String) messageDataOfBaseListItem.get(MAP_KEY_CLIENT_ID);
    }

    public OptimisticMessage getOptimisticMessage(String clientId) {
        int position = findOptimisticMessage(clientId);
        if (position != -1) {
            return optimisticMessages.get(position);
        } else {
            return null;
        }
    }

    private OptimisticMessage generateOptimisticSendingMessage(DeliveryIndicatorHelper.ClientDeliveryStatus deliveryStatus, String message, String clientId) {
        if (clientId == null || message == null) {
            throw new IllegalArgumentException("Invalid arguments!");
        }

        Map<String, Object> map = new HashMap<>();
        map.put(MAP_KEY_CLIENT_ID, clientId);

        // TODO: Attachment Types!

        if (optimisticMessages.size() == 0) {
            return new OptimisticMessage(
                    new SimpleMessageSelfListItem(
                            null,
                            message,
                            userAvatarUrl,
                            null,
                            System.currentTimeMillis(),
                            DeliveryIndicatorHelper.getDeliveryIndicator(deliveryStatus),
                            map
                    ),
                    deliveryStatus,
                    message,
                    clientId);
        } else {
            return new OptimisticMessage(
                    new SimpleMessageContinuedSelfListItem(
                            null,
                            message,
                            System.currentTimeMillis(),
                            DeliveryIndicatorHelper.getDeliveryIndicator(deliveryStatus),
                            map
                    ),
                    deliveryStatus,
                    message,
                    clientId);
        }

    }

    private int findOptimisticMessage(String clientId) {
        if (clientId == null) {
            return -1;
        }

        if (optimisticMessages.size() == 0) {
            return -1;
        }


        int position = -1;
        for (int i = 0; i < optimisticMessages.size(); i++) {
            if (optimisticMessages.get(i).getClientId().equals(clientId)) {
                position = i;
            }
        }
        return position;
    }

    public class OptimisticMessage {
        private BaseListItem messageListItem;
        private DeliveryIndicatorHelper.ClientDeliveryStatus deliveryStatus;
        private String message;
        private String clientId;

        public OptimisticMessage(BaseListItem messageListItem, DeliveryIndicatorHelper.ClientDeliveryStatus deliveryStatus, String message, String clientId) {
            this.messageListItem = messageListItem;
            this.deliveryStatus = deliveryStatus;
            this.message = message;
            this.clientId = clientId;
        }

        public BaseListItem getMessageListItem() {
            return messageListItem;
        }

        public String getClientId() {
            return clientId;
        }

        public String getMessage() {
            return message;
        }

        public DeliveryIndicatorHelper.ClientDeliveryStatus getDeliveryStatus() {
            return deliveryStatus;
        }
    }
}
