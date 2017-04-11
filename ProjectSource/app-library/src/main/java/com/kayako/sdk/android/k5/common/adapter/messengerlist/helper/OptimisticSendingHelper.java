package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.SimpleMessageContinuedSelfListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.SimpleMessageSelfListItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OptimisticSendingHelper {

    private static final String MAP_KEY_CLIENT_ID = "optimistic_sending_client_id";
    private static final String MAP_KEY_CLIENT_DELIVERY_INDICATOR_TYPE = "client_delivery_indicator_type";
    private List<UnsentMessage> unsentMessageList = new ArrayList<>();
    private String userAvatarUrl;

    public OptimisticSendingHelper(@Nullable String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
        if (this.userAvatarUrl == null) {
            // TODO: If null, show default avatar for customer
        }
    }

    public List<UnsentMessage> getUnsentMessages() {
        return new ArrayList<>(unsentMessageList); // A copy is sent back
    }

    public synchronized List<BaseListItem> getMessageViews() {
        // Views are generated when required
        List<BaseListItem> baseListItems = new ArrayList<>();
        for (int i = 0; i < unsentMessageList.size(); i++) {
            POSITION position = getPosition(i, unsentMessageList.size());
            baseListItems.add(generateOptimisticSendingViewMessage(position, unsentMessageList.get(i)));
        }
        return baseListItems;
    }

    public synchronized void removeMessage(String clientId) {
        int position = findOptimisticMessage(clientId);
        if (position != -1) {
            unsentMessageList.remove(position);
        }
    }

    public void markAllAsFailed() {
        for (int i = 0; i < unsentMessageList.size(); i++) {
            markAsFailed(unsentMessageList.get(i).getClientId());
        }
    }

    public void markAllAsSending() {
        for (int i = 0; i < unsentMessageList.size(); i++) {
            markAsSending(unsentMessageList.get(i).getClientId());
        }
    }

    public synchronized void addMessage(UnsentMessage unsentMessage) {
        unsentMessageList.add(unsentMessage);
    }

    public boolean isOptimisticMessage(Map<String, Object> messageDataOfBaseListItem) {
        if (messageDataOfBaseListItem != null && messageDataOfBaseListItem.containsKey(MAP_KEY_CLIENT_ID)) {
            return true;
        } else {
            return false;
        }
    }

    public ClientDeliveryStatus getDeliveryStatus(Map<String, Object> messageDataOfBaseListItem) {
        return (ClientDeliveryStatus) messageDataOfBaseListItem.get(MAP_KEY_CLIENT_DELIVERY_INDICATOR_TYPE);
    }

    private synchronized void markAsFailed(String clientId) {
        int position = findOptimisticMessage(clientId);

        UnsentMessage messageToReplace = unsentMessageList.get(position);
        unsentMessageList.remove(position);
        unsentMessageList.add(
                position,
                new UnsentMessage(
                        ClientDeliveryStatus.FAILED_TO_SEND,
                        messageToReplace.getMessage(),
                        messageToReplace.getClientId()
                )
        );
    }

    public void markAsSending(String clientId) {
        int position = findOptimisticMessage(clientId);

        UnsentMessage messageToReplace = unsentMessageList.get(position);
        unsentMessageList.remove(position);
        unsentMessageList.add(
                position,
                new UnsentMessage(
                        ClientDeliveryStatus.SENDING,
                        messageToReplace.getMessage(),
                        messageToReplace.getClientId()
                )
        );
    }

    private BaseListItem generateOptimisticSendingViewMessage(POSITION position, UnsentMessage unsentMessage) {
        if (position == null || unsentMessage == null) {
            throw new IllegalArgumentException("Invalid arguments!");
        }

        Map<String, Object> map = new HashMap<>();
        map.put(MAP_KEY_CLIENT_ID, unsentMessage.getClientId());
        map.put(MAP_KEY_CLIENT_DELIVERY_INDICATOR_TYPE, unsentMessage.getDeliveryStatus());

        // TODO: Attachment Types!

        // Show time & delivery indicator only for last element
        long time;
        ClientDeliveryStatus clientDeliveryStatus;
        if (position == POSITION.END || position == POSITION.ONLY_ELEMENT) {
            time = System.currentTimeMillis();
            clientDeliveryStatus = unsentMessage.getDeliveryStatus();
        } else {
            time = 0;
            clientDeliveryStatus = null;
        }

        // Group with first element with avatar, and others continued
        if (position == POSITION.TOP || position == POSITION.ONLY_ELEMENT) {
            return new SimpleMessageSelfListItem(
                    null,
                    unsentMessage.getMessage(),
                    userAvatarUrl,
                    null,
                    time,
                    DeliveryIndicatorHelper.getDeliveryIndicator(clientDeliveryStatus),
                    map
            );
        } else {
            return new SimpleMessageContinuedSelfListItem(
                    null,
                    unsentMessage.getMessage(),
                    time,
                    DeliveryIndicatorHelper.getDeliveryIndicator(clientDeliveryStatus),
                    map
            );
        }
    }

    private int findOptimisticMessage(String clientId) {
        if (clientId == null) {
            return -1;
        }

        if (unsentMessageList.size() == 0) {
            return -1;
        }


        int position = -1;
        for (int i = 0; i < unsentMessageList.size(); i++) {
            if (unsentMessageList.get(i).getClientId().equals(clientId)) {
                position = i;
            }
        }
        return position;
    }

    private POSITION getPosition(int i, int size) {
        POSITION position;

        if (i == 0 && unsentMessageList.size() == 1) {
            position = POSITION.ONLY_ELEMENT;
        } else if (i == 0) {
            position = POSITION.TOP;
        } else if (i == unsentMessageList.size() - 1) {
            position = POSITION.END;
        } else {
            position = POSITION.MIDDLE;
        }
        return position;
    }

    private enum POSITION {
        TOP, MIDDLE, END, ONLY_ELEMENT
    }
}
