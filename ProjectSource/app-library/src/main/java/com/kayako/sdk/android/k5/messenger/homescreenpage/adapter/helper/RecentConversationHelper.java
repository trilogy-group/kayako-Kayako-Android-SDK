package com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.helper;

import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.ConversationViewModel;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.ConversationViewModelHelper;
import com.kayako.sdk.messenger.conversation.Conversation;
import com.kayako.sdk.messenger.conversationstarter.ConversationStarter;

import java.util.ArrayList;
import java.util.List;

public class RecentConversationHelper {

    private RecentConversationHelper() {
    }

    public static void updateRecentConversations(ConversationViewModelHelper conversationViewModelHelper, ConversationStarter conversationStarter, @Nullable OnRemoveUnusedConversationListener onRemoveUnusedConversationListener) {
        List<Conversation> conversationList = conversationStarter.getActiveConversations();

        if (conversationList == null || conversationList.size() == 0) {
            return;
        }

        for (Conversation conversation : conversationList) {
            conversationViewModelHelper.addOrUpdateElement(conversation, null);
        }

        if (conversationViewModelHelper.getSize() > 3) {
            removeOldConversations(conversationViewModelHelper, conversationList, onRemoveUnusedConversationListener);
        }
    }

    private static void removeOldConversations(ConversationViewModelHelper conversationViewModelHelper, List<Conversation> newConversations, @Nullable OnRemoveUnusedConversationListener onRemoveUnusedConversationListener) {
        List<Long> ids = new ArrayList<>();
        for (Conversation newConversation : newConversations) {
            ids.add(newConversation.getId());
        }

        for (ConversationViewModel conversationViewModel : conversationViewModelHelper.getConversationList()) {
            if (!ids.contains(conversationViewModel.getConversationId())) {

                if (onRemoveUnusedConversationListener != null) {
                    onRemoveUnusedConversationListener.onRemoveUnusedConversation(
                            newConversations.get(
                                    ids.indexOf(conversationViewModel.getConversationId())));
                }
                conversationViewModelHelper.removeElement(conversationViewModel.getConversationId());
            }
        }
    }

    public interface OnRemoveUnusedConversationListener {
        void onRemoveUnusedConversation(Conversation conversation);
    }

}
