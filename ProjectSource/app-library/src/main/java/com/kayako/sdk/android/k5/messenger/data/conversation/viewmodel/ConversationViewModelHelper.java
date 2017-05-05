package com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.UniqueSortedUpdatableResourceList;
import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.messenger.conversation.Conversation;

import java.util.Collections;
import java.util.List;

import static com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.BotMessageHelper.getDefaultDrawableForConversation;

/**
 * Responsibilities of this class:
 * 1. It should associate conversations with their realtime client activity (isTyping)
 * 2. It should maintain a list of conversation viewmodels that are relevant to a page/instance
 * 3. It should allow updating of both conversation data and realtime behaviour at any time - without affecting existing items
 * 4. It should allow classes using an instance of this class to check if a conversation is relevant
 * <p>
 * Other responsibilities:
 * 1. newly added conversations are set to isTyping = false by default
 * 2. updated conversations should replace existing isTyping value IF a valid value is available, or, retain original value
 */
public class ConversationViewModelHelper {

    private UniqueSortedUpdatableResourceList<ConversationViewModel> conversations = new UniqueSortedUpdatableResourceList<>();

    public ConversationViewModelHelper() {
    }

    public void addOrUpdateElement(@NonNull Conversation conversation, @Nullable ClientTypingActivity clientTypingActivity) {
        if (conversation == null) {
            throw new IllegalArgumentException("Invalid values");
        }

        if (conversations.exists(conversation.getId())) { // Existing item - should retain original realtime activity
            updateConversationProperty(conversation.getId(), conversation);
            if (clientTypingActivity != null) {
                updateRealtimeProperty(conversation.getId(), clientTypingActivity);
            }
        } else { // New Item - set default realtime activity
            conversations.addElement(conversation.getId(), convert(conversation, clientTypingActivity));
        }
    }

    /**
     * @param conversationId
     * @param conversation
     * @return whether or not the value was updated - calls to this method with no new changes return false
     */
    public boolean updateConversationProperty(long conversationId, @NonNull Conversation conversation) {
        if (conversation == null) {
            throw new IllegalArgumentException("Invalid values");
        }

        ConversationViewModel conversationViewModel = conversations.getElement(conversationId);

        if (conversationViewModel == null) {
            return false; // Skip if not found - can't update something that doesn't exist
        } else {
            ConversationViewModel updatedConversationViewModel = convert(conversation, conversationViewModel.getLastAgentReplierTyping());
            if (updatedConversationViewModel.equals(conversationViewModel)) {
                return false;
            } else {
                conversations.addElement(conversationId, updatedConversationViewModel);
                return true;
            }
        }
    }

    /**
     * @param conversationId
     * @param clientTypingActivity
     * @return whether or not the value was updated - calls to this method with no new changes return false
     */
    public boolean updateRealtimeProperty(long conversationId, @NonNull ClientTypingActivity clientTypingActivity) {
        if (clientTypingActivity == null) {
            throw new IllegalArgumentException("Invalid values");
        }

        ConversationViewModel originalConversation = conversations.getElement(conversationId);
        if (originalConversation == null) {
            return false; // Skip if not found - can't update something that doesn't exist
        } else if (originalConversation.getLastAgentReplierTyping().equals(clientTypingActivity)) {
            return false;
        } else {
            conversations.addElement(conversationId, convert(originalConversation, clientTypingActivity));
            return true;
        }
    }

    /**
     * Remove an item from the list
     *
     * @param conversationId
     */
    public void removeElement(long conversationId) {
        conversations.removeElement(conversationId);
    }

    /**
     * Returns list of conversations in descending order of conversation ids
     *
     * @return
     */
    public List<ConversationViewModel> getConversationList() {
        List<ConversationViewModel> list = conversations.getList();
        Collections.reverse(list);
        return list;
    }

    /**
     * @return size of the list
     */
    public int getSize() {
        return conversations.getSize();
    }

    /**
     * @param id
     * @return true if the item exists
     */
    public boolean exists(long id) {
        return conversations.exists(id);
    }

    private static ConversationViewModel convert(@NonNull ConversationViewModel conversationViewModel, @Nullable ClientTypingActivity clientTypingActivity) {
        if (conversationViewModel == null) {
            return null;
        }

        return new ConversationViewModel(
                conversationViewModel.getConversationId(),
                conversationViewModel.getAvatarUrl(),
                conversationViewModel.getName(),
                conversationViewModel.getTimeInMilleseconds(),
                conversationViewModel.getSubject(),
                conversationViewModel.getUnreadCount(),
                clientTypingActivity == null ? new ClientTypingActivity(false) : clientTypingActivity
        );
    }

    private static ConversationViewModel convert(@NonNull Conversation conversation, @Nullable ClientTypingActivity clientTypingActivity) {
        if (conversation == null) {
            return null;
        }

        return new ConversationViewModel(
                conversation.getId(),
                conversation.getLastAgentReplier() == null
                        ? null
                        : conversation.getLastAgentReplier().getAvatarUrl(),
                conversation.getLastAgentReplier() == null
                        ? null
                        : conversation.getLastAgentReplier().getFullName(),
                conversation.getUpdatedAt(),
                conversation.getLastMessagePreview(),
                conversation.getReadMarker() == null
                        ? 0
                        : conversation.getReadMarker().getUnreadCount() == null
                        ? 0
                        : conversation.getReadMarker().getUnreadCount(),
                clientTypingActivity == null
                        ? new ClientTypingActivity(false)
                        : clientTypingActivity
        );
    }

}
