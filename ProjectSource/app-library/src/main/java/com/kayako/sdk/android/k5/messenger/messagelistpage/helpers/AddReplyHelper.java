package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.ClientDeliveryStatus;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.UnsentMessage;
import com.kayako.sdk.android.k5.common.utils.file.FileAttachment;
import com.kayako.sdk.android.k5.core.KayakoLogHelper;

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
 * 5. Attachments and Messages can be sent independent of each other. This allows messages to send without waiting for large attachment to send.
 */
public class AddReplyHelper {

    private AtomicInteger mValidateOneNewConversationCounter = new AtomicInteger(0); // Ensure only one conversation is created
    private AtomicBoolean mIsConversationCreated;

    private AddReplyQueueHelper mAddMessageHelper = new AddReplyQueueHelper();
    private AddReplyQueueHelper mAddAttachmentHelper = new AddReplyQueueHelper();

    private OnAddReplyCallback mCallback;

    public void setIsConversationCreated(boolean isConversationCreated) {
        if (this.mIsConversationCreated == null) {
            this.mIsConversationCreated = new AtomicBoolean(isConversationCreated);
        } else {
            this.mIsConversationCreated.set(isConversationCreated);
        }
    }

    public void setCallback(OnAddReplyCallback onAddReplyCallback) {
        this.mCallback = onAddReplyCallback;
    }

    public void addNewReply(String message, String clientId) {
        validateNewConversationSpecified();
        validateCallback();

        mAddMessageHelper.addNewReply(
                new UnsentMessage(message, ClientDeliveryStatus.SENDING, clientId),
                clientId,
                addReplyListener
        );
    }

    public void addNewReply(FileAttachment attachment, String clientId) {
        validateNewConversationSpecified();
        validateCallback();

        mAddAttachmentHelper.addNewReply(
                new UnsentMessage(attachment, ClientDeliveryStatus.SENDING, clientId),
                clientId,
                addReplyListener
        );
    }

    public void resendReplies() {
        validateCallback();
        mAddMessageHelper.sendNext(addReplyListener);
        mAddAttachmentHelper.sendNext(addReplyListener);
    }

    public void onSuccessfulCreationOfConversation(String clientId) {
        validateCallback();
        setIsConversationCreated(true);

        if (mAddMessageHelper.getLastSentReplyClientId() != null && mAddMessageHelper.getLastSentReplyClientId().equals(clientId)) {
            mAddMessageHelper.onSuccessfulSendingOfReply(clientId, addReplyListener);
        } else if (mAddAttachmentHelper.getLastSentReplyClientId() != null && mAddAttachmentHelper.getLastSentReplyClientId().equals(clientId)) {
            mAddAttachmentHelper.onSuccessfulSendingOfReply(clientId, addReplyListener);
        } else {
            throwInvalidClientIdStateException();
        }
    }

    public void onSuccessfulSendingOfMessage(String clientId) {
        validateCallback();

        if (mAddMessageHelper.getLastSentReplyClientId() != null && mAddMessageHelper.getLastSentReplyClientId().equals(clientId)) {
            mAddMessageHelper.onSuccessfulSendingOfReply(clientId, addReplyListener);
        } else if (mAddAttachmentHelper.getLastSentReplyClientId() != null && mAddAttachmentHelper.getLastSentReplyClientId().equals(clientId)) {
            mAddAttachmentHelper.onSuccessfulSendingOfReply(clientId, addReplyListener);
        } else {
            throwInvalidClientIdStateException();
        }
    }

    public void onFailedCreationOfConversation(String clientId) {
        KayakoLogHelper.e("onFailedCreationOfConversation", clientId);

        mValidateOneNewConversationCounter.set(0);

        if (mAddMessageHelper.getLastSentReplyClientId() != null && mAddMessageHelper.getLastSentReplyClientId().equals(clientId)) {
            mAddMessageHelper.onFailedToSendReply(clientId);
        } else if (mAddAttachmentHelper.getLastSentReplyClientId() != null && mAddAttachmentHelper.getLastSentReplyClientId().equals(clientId)) {
            mAddAttachmentHelper.onFailedToSendReply(clientId);
        } else {
            throwInvalidClientIdStateException();
        }
    }

    public void onFailedSendingOfMessage(String clientId) {
        KayakoLogHelper.e("onFailedSendingOfMessage", clientId);
        if (mAddMessageHelper.getLastSentReplyClientId() != null && mAddMessageHelper.getLastSentReplyClientId().equals(clientId)) {
            mAddMessageHelper.onFailedToSendReply(clientId);
        } else if (mAddAttachmentHelper.getLastSentReplyClientId() != null && mAddAttachmentHelper.getLastSentReplyClientId().equals(clientId)) {
            mAddAttachmentHelper.onFailedToSendReply(clientId);
        } else {
            throwInvalidClientIdStateException();
        }
    }

    private void throwInvalidClientIdStateException() {
        // If a clientId is received which is not known by any of the Queue Helpers, then an invalid state has occured and an exception should be thrown
        // throw new IllegalStateException("Invalid State. Last sent client id does not match!");

        // However, the failed to send callbacks happen multiple times for the same clientId. Which means, although the clientId was processed correctly by the QueueHelpers, when the same clientId is received again, it fails
        // Therefore, if such behaviour can happen, an exception should not be thrown and the illegal state should be silently logged
        KayakoLogHelper.e(getClass().getName(), ("Invalid State. Last sent client id does not match!"));
    }

    private AddReplyQueueInterface.AddReplyListener addReplyListener = new AddReplyQueueInterface.AddReplyListener() {
        @Override
        public void onAddReply(UnsentMessage unsentMessage) {
            if (mIsConversationCreated.get()) {
                mCallback.onAddMessage(unsentMessage);
            } else {
                mCallback.onCreateConversation(unsentMessage);
                validateNewConversation();
            }
        }
    };

    private void validateNewConversationSpecified() {
        if (mIsConversationCreated == null) {
            throw new IllegalStateException("setIsConversationCreated() should be called before calling any this method");
        }
    }

    private void validateNewConversation() {
        if (mValidateOneNewConversationCounter.get() > 0) {
            throw new IllegalStateException("Multiple conversations should not be created from this page. Make sure to call ");
        }
        mValidateOneNewConversationCounter.incrementAndGet();
    }

    private void validateCallback() {
        if (mCallback == null) {
            throw new IllegalStateException("OnAddReplyCallback callback needs to be set before you can call this method!");
        }
    }

    public interface OnAddReplyCallback {

        void onCreateConversation(UnsentMessage unsentMessage);

        void onAddMessage(UnsentMessage unsentMessage);
    }

}