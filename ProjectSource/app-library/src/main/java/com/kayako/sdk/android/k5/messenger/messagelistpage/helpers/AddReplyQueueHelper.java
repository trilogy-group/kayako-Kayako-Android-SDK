package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.UnsentMessage;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;

public class AddReplyQueueHelper implements AddReplyQueueInterface {

    private Queue<UnsentMessage> queue = new ConcurrentLinkedQueue<UnsentMessage>();
    private AtomicReference<String> clientIdOfLastReplySending = new AtomicReference<>(null); // This represents the reply just sent - conversation to be created or message to be added

    @Override
    public void addNewReply(UnsentMessage unsentMessage, String clientId, AddReplyListener callback) {
        queue.add(unsentMessage);
        sendNext(callback);
    }

    @Override
    public void onSuccessfulSendingOfReply(String clientId, AddReplyListener callback) {
        if (clientIdOfLastReplySending.get() == null) {
            throw new IllegalStateException("onSuccessfulSendingOfReply() should never be called BEFORE the first call to addNewReply()");

        } else if (clientIdOfLastReplySending.get().equals(clientId)) {
            queue.remove(); // Removes only on successful sending of last reply
            resetLastSendingClientId(clientId);

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

        resetLastSendingClientId(clientId);
    }

    @Override
    public void sendNext(AddReplyListener callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Callback can not be null!");
        }

        if (queue.size() > 0) {
            UnsentMessage unsentMessage = queue.peek();

            if (clientIdOfLastReplySending.get() != null // skip if not available
                    && clientIdOfLastReplySending.get().equals(unsentMessage.getClientId())) { // If the last reply has not been sent yet, then ignore making a new request and wait for it to be successful
                // The new reply would already be added and will get its chance later once its preceding elements are sent!
                // However, the oldest reply that fails to send may delay or halt all the new replies added - SAD but necessary for New Conversation > New Message + messages in order flow
                return;
            }

            clientIdOfLastReplySending.set(unsentMessage.getClientId());

            callback.onAddReply(unsentMessage); // callback should be done LAST (Order is important)
        }
    }

    @Override
    public String getLastSentReplyClientId() {
        return clientIdOfLastReplySending.get();
    }

    private void resetLastSendingClientId(String clientId) {
        if (clientIdOfLastReplySending.get() != null // skip if already null
                && clientIdOfLastReplySending.get().equals(clientId)) {
            clientIdOfLastReplySending.set(null);
        } else {
            throw new IllegalStateException("Can not reset unless the lastClientId matches - ensuring order of messages being processed");
        }
    }

}
