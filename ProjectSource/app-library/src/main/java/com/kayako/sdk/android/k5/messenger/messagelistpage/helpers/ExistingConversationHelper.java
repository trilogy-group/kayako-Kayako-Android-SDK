package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;


import com.kayako.sdk.messenger.conversation.Conversation;

import java.util.concurrent.atomic.AtomicReference;

/**
 * All logic for loading existing conversation. Saves state of conversation and conversationId.
 *
 * * // TODO: Combine with NewConversationHelper?
 */
public class ExistingConversationHelper {

    private Long mConversationId;
    private AtomicReference<Conversation> mConversation = new AtomicReference<>();

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

}