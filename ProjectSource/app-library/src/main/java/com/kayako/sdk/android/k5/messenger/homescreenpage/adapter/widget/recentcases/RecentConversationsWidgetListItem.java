package com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.recentcases;

import android.support.annotation.NonNull;

import com.kayako.sdk.android.k5.messenger.data.conversationstarter.RecentConversation;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.HomeScreenListType;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.BaseWidgetListItem;

import java.util.List;

public class RecentConversationsWidgetListItem extends BaseWidgetListItem {

    private List<RecentConversation> conversations;
    private OnClickRecentConversationListener onClickRecentConversationListener;

    public RecentConversationsWidgetListItem(@NonNull String title,
                                             @NonNull String actionButtonLabel,
                                             @NonNull OnClickActionListener onClickActionListener,
                                             @NonNull List<RecentConversation> conversations,
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

    public List<RecentConversation> getConversations() {
        return conversations;
    }

    public OnClickRecentConversationListener getOnClickRecentConversationListener() {
        return onClickRecentConversationListener;
    }
}
