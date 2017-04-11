package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.ClientDeliveryStatus;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.UnsentMessage;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * All logic involving adding messages in order - ensuring that the messages are sent one after the other.
 */
public class AddMessageHelper {

    private Queue<UnsentMessage> queue = new ConcurrentLinkedQueue<UnsentMessage>();

    public void addMessageToSend(String message, String clientId, OnAddNextMessageCallback callback) {
        queue.add(new UnsentMessage(ClientDeliveryStatus.SENDING, message, clientId));
        sendNextPendingMessage(callback);
    }

    public void onSuccessfulSendingOfMessage(OnAddNextMessageCallback callback) {
        sendNextPendingMessage(callback);
    }

    private void sendNextPendingMessage(OnAddNextMessageCallback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Callback can not be null!");
        }

        if (queue.size() > 0) {
            UnsentMessage unsentMessage = queue.poll();
            callback.onNextMessage(unsentMessage);
        }
    }

    public interface OnAddNextMessageCallback {
        void onNextMessage(UnsentMessage unsentMessage);
    }
}