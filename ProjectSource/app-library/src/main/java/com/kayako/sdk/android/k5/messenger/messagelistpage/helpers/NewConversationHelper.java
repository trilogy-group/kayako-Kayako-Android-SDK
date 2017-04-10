package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import com.kayako.sdk.messenger.conversation.PostConversationBodyParams;

/**
 * All logic for handling a new conversation
 */
public class NewConversationHelper {

    private boolean mIsConversationCreated;

    public NewConversationHelper() {
    }

    public boolean isConversationCreated() {
        return mIsConversationCreated;
    }

    public void setIsConversationCreated(boolean mIsConversationCreated) {
        this.mIsConversationCreated = mIsConversationCreated;
    }

    public PostConversationBodyParams getNewConversationBodyParams(String email, String message, String clientId) {
        if (email == null) {
            throw new AssertionError("If it's a new conversation and email is null, the user should not have had the chance to send a reply!");
        }

        String name = extractName(email);
        String subject = extractSubject(message);

        return new PostConversationBodyParams(name, email, subject, message, clientId);
    }

    private String extractName(String email) {
        return email.substring(0, email.indexOf("@"));
    }

    private String extractSubject(String message) {
        return message; // Subject = first message sent in new conversation
    }
}