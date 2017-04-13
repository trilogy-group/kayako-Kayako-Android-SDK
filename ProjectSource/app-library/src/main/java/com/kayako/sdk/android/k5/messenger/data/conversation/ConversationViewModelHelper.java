package com.kayako.sdk.android.k5.messenger.data.conversation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.UniqueSortedUpdatableResourceList;
import com.kayako.sdk.messenger.conversation.Conversation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Need a way to ensure that:
 * 1. newly added conversations are set to isTyping = false by default
 * 2. updated conversations should replace existing isTyping value IF a valid value is available, or, retain original value
 */
public class ConversationViewModelHelper {

    private UniqueSortedUpdatableResourceList<ConversationViewModel> conversations = new UniqueSortedUpdatableResourceList<>();

    // TODO: Set views as well in:
    // 1. RecentConversationAdapter
    // 2. ConversationListAdapter

    // TODO: Remove those conversations not mentioned?

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

    public boolean updateConversationProperty(long conversationId, @NonNull Conversation conversation) {
        if (conversation == null) {
            throw new IllegalArgumentException("Invalid values");
        }

        ConversationViewModel conversationViewModel = conversations.getElement(conversationId);

        if (conversationViewModel == null) {
            return false; // Skip if not found - can't update something that doesn't exist
        } else {
            conversations.addElement(conversationId, convert(conversation, conversationViewModel.getLastAgentReplierTyping()));
            return true;
        }
    }

    public boolean updateRealtimeProperty(long conversationId, @NonNull ClientTypingActivity clientTypingActivity) {
        if (clientTypingActivity == null) {
            throw new IllegalArgumentException("Invalid values");
        }

        ConversationViewModel originalConversation = conversations.getElement(conversationId);
        if (originalConversation == null) {
            return false; // Skip if not found - can't update something that doesn't exist
        } else {
            conversations.addElement(conversationId, convert(originalConversation, clientTypingActivity));
            return true;
        }
    }

    /**
     * Returns list of conversations in descending order
     *
     * @return
     */
    public List<ConversationViewModel> getConversationList() {
        List<ConversationViewModel> list = conversations.getList();
        Collections.reverse(list);
        return list;
    }

    /**
     * Select only the conversations relevant
     *
     * @param conversationIds
     * @return
     */
    public List<ConversationViewModel> getConversationList(List<Long> conversationIds) {
        List<ConversationViewModel> list = new ArrayList<>();
        for (Long conversationId : conversationIds) {
            if (conversations.exists(conversationId)) {
                list.add(conversations.getElement(conversationId));
            }
        }

        Collections.reverse(list);
        return list;
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
                conversation.getLastReplier().getAvatarUrl(),
                conversation.getLastReplier().getFullName(),
                conversation.getUpdatedAt(),
                conversation.getLastMessagePreview(),
                conversation.getReadMarker() == null ? 0 :
                        conversation.getReadMarker().getUnreadCount() == null ? 0 : conversation.getReadMarker().getUnreadCount(),
                clientTypingActivity == null ? new ClientTypingActivity(false) : clientTypingActivity
        );


        // TODO: Add to ConversationListItem - realtime events - istyping
        // TODO: Which photo to show? The agent? - LastAgentReplier
        // TODO: Whose name? The agent?
            /*
            realtimeConversation.getConversation().getCreator().getAvatarUrl(),
            realtimeConversation.getConversation().getCreator().getFullName(),
            realtimeConversation.getConversation().getUpdatedAt(),
            realtimeConversation.getConversation().getLastMessagePreview(),
            realtimeConversation.getConversation().getReadMarker() == null ? 0 : realtimeConversation.getConversation().getReadMarker().getUnreadCount() == null ? 0 : realtimeConversation.getConversation().getReadMarker().getUnreadCount(),
             */
    }
}
