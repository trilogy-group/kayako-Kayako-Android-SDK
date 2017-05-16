package com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.recentcases;

import android.support.annotation.NonNull;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.DiffUtilsHelper;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.ConversationViewModel;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.HomeScreenListType;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.BaseWidgetListItem;
import com.kayako.sdk.messenger.conversation.Conversation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecentConversationsWidgetListItem extends BaseWidgetListItem {

    private List<ConversationViewModel> conversations;
    private OnClickRecentConversationListener onClickRecentConversationListener;

    public RecentConversationsWidgetListItem(@NonNull String title,
                                             @NonNull String actionButtonLabel,
                                             @NonNull OnClickActionListener onClickActionListener,
                                             @NonNull List<ConversationViewModel> conversations,
                                             @NonNull OnClickRecentConversationListener onClickRecentConversationListener) {

        super(HomeScreenListType.WIDGET_RECENT_CONVERSATIONS, title, actionButtonLabel, onClickActionListener);
        this.conversations = conversations;
        this.onClickRecentConversationListener = onClickRecentConversationListener;

        if (conversations == null || conversations.size() == 0) {
            throw new IllegalArgumentException("Conversation list shouldn't be null or empty");
        }
        if (onClickRecentConversationListener == null) {
            throw new IllegalArgumentException("Click listener should be enabled");
        }
    }

    public List<ConversationViewModel> getConversations() {
        return conversations;
    }

    public OnClickRecentConversationListener getOnClickRecentConversationListener() {
        return onClickRecentConversationListener;
    }

    @Override
    public Map<String, String> getContents() {
        Map<String, String> map = new HashMap<>();
        map.put("title", String.valueOf(getTitle()));
        map.put("conversations", String.valueOf(conversations.size()));
        for (ConversationViewModel conversationViewModel : conversations) {
            map.put("conversations_" + conversationViewModel.getConversationId(), DiffUtilsHelper.convertToString(conversationViewModel.getContents()));
        }
        return map;
    }

}
