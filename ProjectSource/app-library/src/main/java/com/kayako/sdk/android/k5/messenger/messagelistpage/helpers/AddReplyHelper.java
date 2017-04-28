package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.ClientDeliveryStatus;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.UnsentMessage;
import com.kayako.sdk.android.k5.common.utils.file.FileAttachment;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * All logic involving adding messages in order - ensuring that the messages are sent one after the other.
 * This can be used for both creation of new conversation and new message
 * <p>
 * Ensure:
 * 1. There's checking if the successful message sent is the one that was last part of the queue?
 * 2. The order is always maintained and messages are not sent more than once (multiple duplicate requests)
 * 3. Multiple new conversations should not be created when multiple replies are made for a new conversation! It should know that the first is to create new conversation, and all succeeding requests are messages to be added to the existing conversation
 * 4. Keep track of all messages that need to be sent - cache in memory
 */
public class AddReplyHelper {

    private Queue<UnsentMessage> queue = new ConcurrentLinkedQueue<UnsentMessage>();

    private AtomicBoolean isConversationCreated;
    private AtomicInteger validateCounter = new AtomicInteger(0);

    /*
    This represents the reply just sent - conversation to be created or message to be added
    This is necessary so that if a request FAILS, it should be retried.
    But if a request is IN PROCESS, it should not create duplicates
    */
    private String clientIdOfLastReplySending;

    public void setIsConversationCreated(boolean isConversationCreated) {
        if (this.isConversationCreated == null) {
            this.isConversationCreated = new AtomicBoolean(isConversationCreated);
        } else {
            this.isConversationCreated.set(isConversationCreated);
        }
    }

    public void addNewReply(String message, String clientId, OnAddReplyCallback callback) {
        validateNewConversationSpecified();

        queue.add(new UnsentMessage(message, ClientDeliveryStatus.SENDING, clientId));
        sendNext(callback);
    }

    public void addNewReply(FileAttachment attachment, String clientId, OnAddReplyCallback callback) {
        validateNewConversationSpecified();

        queue.add(new UnsentMessage(attachment, ClientDeliveryStatus.SENDING, clientId));
        sendNext(callback);
    }

    public void resendReplies(OnAddReplyCallback callback) {
        sendNext(callback);
    }

    public void onSuccessfulCreationOfConversation(String clientId, OnAddReplyCallback callback) {
        setIsConversationCreated(true);
        onSuccessfulSendingOfReply(clientId, callback);
    }

    public void onSuccessfulSendingOfMessage(String clientId, OnAddReplyCallback callback) {
        onSuccessfulSendingOfReply(clientId, callback);
    }

    public void onFailedSendingOfConversation(String clientId) {
        validateCounter.set(0);
        onFailedToSendReply(clientId);
    }

    public void onFailedSendingOfMessage(String clientId) {
        onFailedToSendReply(clientId);
    }

    private synchronized void onSuccessfulSendingOfReply(String clientId, OnAddReplyCallback callback) {
        if (clientIdOfLastReplySending == null) {
            throw new IllegalStateException("onSuccessfulSendingOfReply() should never be called BEFORE the first call to addNewReply()");
        } else if (clientIdOfLastReplySending.equals(clientId)) {

            queue.remove(); // Removes only on successful sending of last reply

            sendNext(callback); // Once removed, send the next pending reply

        } else {
            throw new IllegalStateException("Messages are not sent in a serial order! Something has gone wrong!");
        }
    }

    private synchronized void onFailedToSendReply(String clientId) {
        if (clientId == null) {
            throw new IllegalArgumentException("Can't be null");
        }

        if (clientIdOfLastReplySending != null // skip if already null
                && clientIdOfLastReplySending.equals(clientId)) {
            clientIdOfLastReplySending = null;
        }
    }

    private synchronized void sendNext(OnAddReplyCallback callback) {
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

            if (isConversationCreated.get()) {
                callback.onAddMessage(unsentMessage);
            } else {
                callback.onCreateConversation(unsentMessage);
                validateNewConversation();
            }

            clientIdOfLastReplySending = unsentMessage.getClientId();
        }
    }

    private void validateNewConversationSpecified() {
        if (isConversationCreated == null) {
            throw new IllegalStateException("setIsConversationCreated() should be called before calling any this method");
        }
    }

    private void validateNewConversation() {
        if (validateCounter.get() > 0) {
            throw new IllegalStateException("Multiple conversations should not be created from this page. Make sure to call ");
        }
        validateCounter.incrementAndGet();
    }

    public interface OnAddReplyCallback {

        void onCreateConversation(UnsentMessage unsentMessage);

        void onAddMessage(UnsentMessage unsentMessage);
    }
}