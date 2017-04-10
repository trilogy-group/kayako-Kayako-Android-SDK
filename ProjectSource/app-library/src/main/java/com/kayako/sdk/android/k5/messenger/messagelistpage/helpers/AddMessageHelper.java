package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.ClientDeliveryStatus;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.UnsentMessage;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * All logic involving adding messages - ensuring that the messages are sent one after the other.
 */
public class AddMessageHelper {

    private Queue<UnsentMessage> queue = new ConcurrentLinkedQueue<UnsentMessage>();
    private OnAddNextMessageCallback callback;

    public void addMessageToSend(String message, String clientId, OnAddNextMessageCallback callback) {
        this.callback = callback;

        queue.add(new UnsentMessage(ClientDeliveryStatus.SENDING, message, clientId));
        sendNextPendingMessage();
    }

    public void onSuccessfulSendingOfMessage() {
        sendNextPendingMessage();
    }

    private void sendNextPendingMessage() {
        if (queue.size() > 0) {
            UnsentMessage unsentMessage = queue.poll();
            callback.onNextMessage(unsentMessage);
        }
    }

    public interface OnAddNextMessageCallback {
        void onNextMessage(UnsentMessage unsentMessage);
    }
}