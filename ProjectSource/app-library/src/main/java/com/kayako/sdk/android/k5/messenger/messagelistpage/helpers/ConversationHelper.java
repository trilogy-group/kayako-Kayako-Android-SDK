package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;


import com.kayako.sdk.messenger.conversation.Conversation;
import com.kayako.sdk.messenger.conversation.PostConversationBodyParams;
import com.kayako.sdk.messenger.conversation.fields.status.Status;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * All logic for loading existing conversation. Saves state of conversation and conversationId.
 */
public class ConversationHelper {

    private Long mConversationId;
    private AtomicReference<Conversation> mConversation = new AtomicReference<>();
    private AtomicBoolean mIsConversationCreated = new AtomicBoolean(false); // =false for a new conversation, true for an existing conversation

    public Long getConversationId() {
        return mConversationId;
    }

    public void setConversationId(Long conversationId) {
        this.mConversationId = conversationId;
    }

    public void setConversation(Conversation conversation) {
        this.mConversation.set(conversation);
    }

    public Conversation getConversation() {
        return mConversation.get();
    }

    public boolean isConversationCreated() {
        return mIsConversationCreated.get();
    }

    public void setIsConversationCreated(boolean mIsConversationCreated) {
        this.mIsConversationCreated.set(mIsConversationCreated);
    }

    public boolean isConversationCompleted() {
        return getConversation() != null &&
                getConversation().getStatus() != null &&
                getConversation().getStatus().getType() != null &&
                getConversation().getStatus().getType() == Status.Type.COMPLETED;
    }

    public boolean isConversationClosed() {
        return getConversation() != null &&
                getConversation().getStatus() != null &&
                getConversation().getStatus().getType() != null &&
                getConversation().getStatus().getType() == Status.Type.CLOSED;
    }

    public PostConversationBodyParams getNewConversationBodyParams(String email, String message, String clientId) {
        if (email == null) {
            throw new AssertionError("If it's a new conversation and email is null, the user should not have had the chance to send a reply!");
        }

        String name = extractName(email);
        String subject = extractSubject(message);

        return new PostConversationBodyParams(name, email, subject, message, PostConversationBodyParams.SourceType.MESSENGER, clientId);
    }

    private String extractName(String email) {
        return email.substring(0, email.indexOf("@"));
    }

    private String extractSubject(String message) {
        return message; // Subject = first message sent in new conversation
    }


}