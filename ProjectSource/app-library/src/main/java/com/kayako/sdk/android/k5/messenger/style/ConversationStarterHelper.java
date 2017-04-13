package com.kayako.sdk.android.k5.messenger.style;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.utils.ImageUtils;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.android.k5.messenger.data.conversation.ConversationViewModel;
import com.kayako.sdk.android.k5.messenger.data.conversation.ConversationViewModelHelper;
import com.kayako.sdk.android.k5.messenger.data.conversation.UserViewModel;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.LastActiveAgentsData;
import com.kayako.sdk.helpcenter.user.UserMinimal;
import com.kayako.sdk.messenger.conversation.Conversation;
import com.kayako.sdk.messenger.conversationstarter.ConversationStarter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConversationStarterHelper {

    private static ConversationViewModelHelper mConversationViewModelHelper = new ConversationViewModelHelper();

    private ConversationStarterHelper() {
    }

    public static LastActiveAgentsData convertToLastActiveAgentsData(ConversationStarter conversationStarter) {
        String brand = MessengerPref.getInstance().getBrandName();
        long averageReplyTimeInMilliseconds;
        UserViewModel user1 = null;
        UserViewModel user2 = null;
        UserViewModel user3 = null;

        if (conversationStarter == null) {
            throw new IllegalArgumentException("Null unacceptable!");
        }

        if (conversationStarter.getAverageReplyTime() != null) {
            averageReplyTimeInMilliseconds = convert(conversationStarter.getAverageReplyTime());
        } else {
            averageReplyTimeInMilliseconds = -1L;
        }

        if (conversationStarter.getLastActiveAgents() != null) {
            final int size = conversationStarter.getLastActiveAgents().size();
            switch (size) {
                case 3:
                    user3 = convert(conversationStarter.getLastActiveAgents().get(2));
                case 2:
                    user2 = convert(conversationStarter.getLastActiveAgents().get(1));
                case 1:
                    user1 = convert(conversationStarter.getLastActiveAgents().get(0));
                    break;
            }
        }

        return new LastActiveAgentsData(
                brand,
                averageReplyTimeInMilliseconds,
                user1,
                user2,
                user3
        );

    }

    public static List<ConversationViewModel> convertToRecentConversation(ConversationStarter conversationStarter) {
        List<Conversation> conversationList = conversationStarter.getActiveConversations();

        if (conversationList == null || conversationList.size() == 0) {
            return Collections.emptyList();
        }

        for (Conversation conversation : conversationList) {
            mConversationViewModelHelper.addOrUpdateElement(conversation, null);
        }

        if (mConversationViewModelHelper.getSize() > 3) {
            removeOldConversations(conversationList);
        }

        return mConversationViewModelHelper.getConversationList();
    }

    public static String getAverageResponseTimeCaption(Long averageReplyTimeInMilliseconds) {
        final long ONE_HOUR_IN_MILLISECONDS = 60 * 60 * 1000;
        final long ONE_DAY_IN_MILLISECONDS = 24 * ONE_HOUR_IN_MILLISECONDS;

        String subtitle;
        if (averageReplyTimeInMilliseconds == null || averageReplyTimeInMilliseconds == -1L || averageReplyTimeInMilliseconds == 0L) { // Get default subtitle
            // TODO: Check if correct DEFAULT to show?
            subtitle = null; // Show nothing if unknown
        } else if (Math.round(averageReplyTimeInMilliseconds / ONE_DAY_IN_MILLISECONDS) > 1) { // replies in more than one day
            subtitle = String.format(Kayako.getApplicationContext().getString(R.string.ko__messenger_toolbar_subtitle_average_reply_time_in_days), Math.round(averageReplyTimeInMilliseconds / ONE_DAY_IN_MILLISECONDS));
        } else if (Math.round(averageReplyTimeInMilliseconds / ONE_DAY_IN_MILLISECONDS) == 1) { // replies in about 1.4 days time (1.5 rounds off to 2)
            subtitle = Kayako.getApplicationContext().getString(R.string.ko__messenger_toolbar_subtitle_average_reply_time_in_a_day);
        } else {
            subtitle = String.format(Kayako.getApplicationContext().getString(R.string.ko__messenger_toolbar_subtitle_average_reply_time_in_hours), Math.round(averageReplyTimeInMilliseconds / ONE_HOUR_IN_MILLISECONDS));
        }

        return subtitle;
    }

    public static String getLastActiveAgentsCaption(LastActiveAgentsData lastActiveAgentsData) {
        UserViewModel user1 = lastActiveAgentsData.getUser1();
        UserViewModel user2 = lastActiveAgentsData.getUser2();
        UserViewModel user3 = lastActiveAgentsData.getUser3();

        // TODO: Check the lastActiveAt and label it as "Jamie Edwards was online about 1 hour ago"
        // TODO: According to Harminder - Find the shortest LastActiveAt time of the 3 agents and set as online that much time ago.

        String captionText;
        if (user1 != null && user1.getFullName() != null &&
                user2 != null && user2.getFullName() != null &&
                user3 != null && user3.getFullName() != null) {
            captionText = String.format(Kayako.getApplicationContext().getString(R.string.ko__messenger_toolbar_avatar_caption_text_three_agent),
                    extractFirstName(user1.getFullName()),
                    extractFirstName(user2.getFullName()),
                    extractFirstName(user3.getFullName()));
        } else if (user1 != null && user1.getFullName() != null &&
                user2 != null && user2.getFullName() != null) {
            captionText = String.format(Kayako.getApplicationContext().getString(R.string.ko__messenger_toolbar_avatar_caption_text_two_agent),
                    extractFirstName(user1.getFullName()),
                    extractFirstName(user2.getFullName()));
        } else if (user1 != null && user1.getFullName() != null) {
            captionText = String.format(Kayako.getApplicationContext().getString(R.string.ko__messenger_toolbar_avatar_caption_text_one_agent),
                    extractFirstName(user1.getFullName()));
        } else {
            captionText = null;
        }

        return captionText;
    }

    public static String getAvatarUrl(UserViewModel user) {
        if (user == null) {
            return null;
        }
        return user.getAvatar();
    }

    public static void setAgentAvatar(ImageView imageView, UserViewModel user) {
        if (user == null) {
            imageView.setVisibility(View.GONE);
        } else {
            String avatarUrl = user.getAvatar();
            setAgentAvatar(imageView, avatarUrl);
        }
    }

    public static void setAgentAvatar(ImageView imageView, String avatarUrl) {
        if (avatarUrl != null) {
            ImageUtils.setAvatarImage(Kayako.getApplicationContext(), imageView, avatarUrl);
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }
    }

    public static void setCaptionText(String captionText, TextView textView) {
        if (captionText == null) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(captionText);
        }
    }

    private static void removeOldConversations(List<Conversation> newConversations) {
        List<Long> ids = new ArrayList<>();
        for (Conversation newConversation : newConversations) {
            ids.add(newConversation.getId());
        }

        for (ConversationViewModel conversationViewModel : mConversationViewModelHelper.getConversationList()) {
            if (!ids.contains(conversationViewModel.getConversationId())) {
                mConversationViewModelHelper.removeElement(conversationViewModel.getConversationId());
            }
        }
    }

    private static String extractFirstName(String fullName) {
        if (fullName == null) {
            return null;
        }

        // trim extra spaces
        fullName = fullName.trim();

        // break up name by spaces
        String[] parts = fullName.split(" ");
        if (parts.length > 0) {
            return parts[0];
        } else {
            return fullName;
        }
    }

    private static long convert(double minutes) {
        long seconds = (long) (minutes * 60 * 1000);
        return seconds;
    }

    private static UserViewModel convert(UserMinimal userMinimal) {
        return new UserViewModel(
                userMinimal.getAvatarUrl(),
                userMinimal.getFullName(),
                userMinimal.getLastActiveAt()
        );
    }
}
