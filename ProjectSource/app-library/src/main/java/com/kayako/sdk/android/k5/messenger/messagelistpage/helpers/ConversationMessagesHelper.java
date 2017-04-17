package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;


import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.UniqueSortedUpdatableResourceList;
import com.kayako.sdk.messenger.message.Message;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * All logic involving retrieving messages of an existing conversation which includes pagination.
 */
public class ConversationMessagesHelper {

    private UniqueSortedUpdatableResourceList<Message> messages = new UniqueSortedUpdatableResourceList<>();
    private final int LIMIT = 30;
    private AtomicInteger lastSuccessfulOffset = new AtomicInteger(-1);
    private AtomicBoolean hasMoreMessages = new AtomicBoolean(true);
    private AtomicBoolean hasLoadedMessagesBefore = new AtomicBoolean(false);

    public boolean hasLoadedMessagesBefore() {
        return hasLoadedMessagesBefore.get();
    }

    public int getLimit() {
        return LIMIT;
    }

    public int getLastSuccessfulOffset() {
        return lastSuccessfulOffset.get();
    }

    public boolean exists(long messageId) {
        return messages.exists(messageId);
    }

    public List<Message> getMessages() {
        return messages.getList();
    }

    public boolean hasMoreMessages() {
        return hasMoreMessages.get();
    }

    public void onLoadNextMessages(List<Message> newMessages, int offset) {
        // Check if hasMore
        if (newMessages.size() == 0) {
            hasMoreMessages.set(false);
        } else if (newMessages.size() < LIMIT) {
            hasMoreMessages.set(false);
        } else if (newMessages.size() == LIMIT) {
            hasMoreMessages.set(true);
        } else {
            // new messages should never be more than the limit. API BUG!
        }

        // Set last successful offset if it's greater than the current one
        if (offset > lastSuccessfulOffset.get()) {
            lastSuccessfulOffset.set(offset);
        }

        // Add new messages to messageList
        for (Message newMessage : newMessages) {
            messages.addElement(newMessage.getId(), newMessage);
        }

        hasLoadedMessagesBefore.set(true);
    }
}