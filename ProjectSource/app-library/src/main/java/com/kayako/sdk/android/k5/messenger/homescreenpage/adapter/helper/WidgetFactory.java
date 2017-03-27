package com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.helper;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.LastActiveAgentsData;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.RecentConversation;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.BaseWidgetListItem;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.presence.PresenceWidgetListItem;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.recentcases.OnClickRecentConversationListener;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.recentcases.RecentConversationsWidgetListItem;
import com.kayako.sdk.android.k5.messenger.style.ConversationStarterHelper;
import com.kayako.sdk.messenger.conversationstarter.ConversationStarter;

import java.util.List;

public class WidgetFactory {

    private WidgetFactory() {
    }

    public static PresenceWidgetListItem generatePresenceWidgetListItem(ConversationStarter conversationStarter) throws IllegalArgumentException {
        if (conversationStarter == null) {
            throw new IllegalArgumentException("Null value received");
        }

        String title = Kayako.getApplicationContext().getResources().getString(R.string.ko__messenger_home_screen_widget_presence_title);

        LastActiveAgentsData activeAgentsData = ConversationStarterHelper.convertToLastActiveAgentsData(conversationStarter);
        return new PresenceWidgetListItem(
                title,
                ConversationStarterHelper.getLastActiveAgentsCaption(activeAgentsData),
                ConversationStarterHelper.getAvatarUrl(activeAgentsData.getUser1()),
                ConversationStarterHelper.getAvatarUrl(activeAgentsData.getUser2()),
                ConversationStarterHelper.getAvatarUrl(activeAgentsData.getUser3())
        );
    }

    public static RecentConversationsWidgetListItem generateRecentCasesWidgetListItem(ConversationStarter conversationStarter,
                                                                                      BaseWidgetListItem.OnClickActionListener onClickActionListener,
                                                                                      OnClickRecentConversationListener onClickRecentConversationListener) throws IllegalArgumentException {
        if (conversationStarter == null || onClickActionListener == null
                || onClickRecentConversationListener == null) {
            throw new IllegalArgumentException("Null value received");
        }

        if (conversationStarter.getActiveConversations() == null || conversationStarter.getActiveConversations().size() == 0) {
            throw new IllegalArgumentException("No conversations to show! Can not generate this widget!");
        }

        String title = Kayako.getApplicationContext().getResources().getString(R.string.ko__messenger_home_screen_widget_recent_cases_title);
        String actionText = Kayako.getApplicationContext().getResources().getString(R.string.ko__messenger_home_screen_widget_recent_cases_action_button_label);

        List<RecentConversation> conversationList = ConversationStarterHelper.convertToRecentConversation(conversationStarter);

        return new RecentConversationsWidgetListItem(
                title,
                actionText,
                onClickActionListener,
                conversationList,
                onClickRecentConversationListener
        );
    }

}
