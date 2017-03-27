package com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.helper;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.LastActiveAgentsData;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.PresenceWidgetListItem;
import com.kayako.sdk.android.k5.messenger.style.ConversationStarterHelper;
import com.kayako.sdk.messenger.conversationstarter.ConversationStarter;

public class WidgetFactory {

    private WidgetFactory() {
    }

    public static PresenceWidgetListItem generatePresenceWidgetListItem(ConversationStarter conversationStarter) {
        if (conversationStarter == null) {
            throw new IllegalArgumentException("Null value received");
        }

        String title = Kayako.getApplicationContext().getResources().getString(R.string.ko__messenger_home_screen_widget_presence_title);

        LastActiveAgentsData activeAgentsData = ConversationStarterHelper.convert(conversationStarter);
        return new PresenceWidgetListItem(
                title,
                ConversationStarterHelper.getLastActiveAgentsCaption(activeAgentsData),
                ConversationStarterHelper.getAvatarUrl(activeAgentsData.getUser1()),
                ConversationStarterHelper.getAvatarUrl(activeAgentsData.getUser2()),
                ConversationStarterHelper.getAvatarUrl(activeAgentsData.getUser3())
        );
    }

}
