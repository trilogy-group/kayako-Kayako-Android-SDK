package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.ClientDeliveryStatus;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.UnsentMessage;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * All logic involving adding messages in order - ensuring that the messages are sent one after the other.
 * This can be used for both creation of new conversation and new message
 * <p>
 * Ensure:
 * 1. There's checking if the successful message sent is the one that was last part of the queue?
 * 2. The order is always maintained and messages are not sent more than once (multiple duplicate requests)
 * 3. Multiple new conversations should not be created when multiple replies are made for a new conversation! It should know that the first is to create new conversation, and all succeeding requests are messages to be added to the existing conversation
 */
public class AddReplyHelper {

    private Queue<UnsentMessage> queue = new ConcurrentLinkedQueue<UnsentMessage>();
    private String clientIdOfLastReplySent; // Reply sent - conversation to be created or message to be added

    private AtomicBoolean isConversationCreated;
    private int validateCounter = 0;

    public void setIsConversationCreated(boolean isConversationCreated) {
        if (this.isConversationCreated == null) {
            this.isConversationCreated = new AtomicBoolean(isConversationCreated);
        } else {
            this.isConversationCreated.set(isConversationCreated);
        }
    }

    public void addNewReply(String message, String clientId, OnAddReplyCallback callback) {
        validateNewConversationSpecified();

        queue.add(new UnsentMessage(ClientDeliveryStatus.SENDING, message, clientId));
        sendNext(callback);
    }

    public void onSuccessfulCreationOfConversation(String clientId, OnAddReplyCallback callback) {
        setIsConversationCreated(true);
        onSuccessfulSendingOfReply(clientId, callback);
    }

    public void onSuccessfulSendingOfMessage(String clientId, OnAddReplyCallback callback) {
        onSuccessfulSendingOfReply(clientId, callback);
    }

    private synchronized void onSuccessfulSendingOfReply(String clientId, OnAddReplyCallback callback) {
        if (clientIdOfLastReplySent == null) {
            throw new IllegalStateException("onSuccessfulSendingOfReply() should never be called BEFORE the first call to addNewReply()");
        } else if (clientIdOfLastReplySent.equals(clientId)) {

            queue.remove(); // Removes only on successful sending of last reply

            sendNext(callback); // Once removed, send the next pending reply

        } else {
            throw new IllegalStateException("Messages are not sent in a serial order! Something has gone wrong!");
        }
    }

    private synchronized void sendNext(OnAddReplyCallback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Callback can not be null!");
        }

        if (queue.size() > 0) {
            UnsentMessage unsentMessage = queue.peek();

            if (clientIdOfLastReplySent != null // If first time, continue
                    && clientIdOfLastReplySent.equals(unsentMessage.getClientId())) { // If the last reply has not been sent yet, then ignore making a new request and wait for it to be successful
                // The new reply would already be added and will get its chance later once its preceding elements are sent!
                // However, the oldest reply that fails to send may delay or halt all the new replies added - SAD but necessary for New Conversation > New Message + messages in order flow
                return;
            }

            if (isConversationCreated.get()) {
                callback.onAddMessage(unsentMessage);
            } else {
                callback.onCreateConversation(unsentMessage);
                validateNewConversation();
            }

            clientIdOfLastReplySent = unsentMessage.getClientId();
        }
    }

    private void validateNewConversationSpecified() {
        if (isConversationCreated == null) {
            throw new IllegalStateException("setIsConversationCreated() should be called before calling any this method");
        }
    }

    private void validateNewConversation() {
        if (validateCounter > 0) {
            throw new IllegalStateException("Multiple conversations should not be created from this page. Make sure to call ");
        }
        validateCounter++;
    }

    public interface OnAddReplyCallback {

        void onCreateConversation(UnsentMessage unsentMessage);

        void onAddMessage(UnsentMessage unsentMessage);
    }
}