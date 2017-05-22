package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.UnsentMessage;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AddReplyQueueHelper implements AddReplyQueueInterface {

    private Queue<UnsentMessage> queue = new ConcurrentLinkedQueue<UnsentMessage>();
    private String clientIdOfLastReplySending; // This represents the reply just sent - conversation to be created or message to be added

    @Override
    public void addNewReply(UnsentMessage unsentMessage, String clientId, AddReplyListener callback) {
        queue.add(unsentMessage);
        sendNext(callback);
    }

    @Override
    public void onSuccessfulSendingOfReply(String clientId, AddReplyListener callback) {
        if (clientIdOfLastReplySending == null) {
            throw new IllegalStateException("onSuccessfulSendingOfReply() should never be called BEFORE the first call to addNewReply()");

        } else if (clientIdOfLastReplySending.equals(clientId)) {
            queue.remove(); // Removes only on successful sending of last reply
            sendNext(callback); // Once removed, send the next pending reply

        } else {
            throw new IllegalStateException("Messages are not sent in a serial order! Something has gone wrong!");
        }
    }

    @Override
    public synchronized void onFailedToSendReply(String clientId) {
        if (clientId == null) {
            throw new IllegalArgumentException("Can't be null");
        }

        if (clientIdOfLastReplySending != null // skip if already null
                && clientIdOfLastReplySending.equals(clientId)) {
            clientIdOfLastReplySending = null;
        }
    }

    @Override
    public void sendNext(AddReplyListener callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Callback can not be null!");
        }

        if (queue.size() > 0) {
            UnsentMessage unsentMessage = queue.peek();

            if (clientIdOfLastReplySending != null // skip if not available
                    && clientIdOfLastReplySending.equals(unsentMessage.getClientId())) { // If the last reply has not been sent yet, then ignore making a new request and wait for it to be successful
                // The new reply would already be added and will get its chance later once its preceding elements are sent!
                // However, the oldest reply that fails to send may delay or halt all the new replies added - SAD but necessary for New Conversation > New Message + messages in order flow
                return;
            }

            callback.onAddReply(unsentMessage);

            clientIdOfLastReplySending = unsentMessage.getClientId();
        }
    }

    @Override
    public String getLastSentReplyClientId() {
        return clientIdOfLastReplySending;
    }

}
