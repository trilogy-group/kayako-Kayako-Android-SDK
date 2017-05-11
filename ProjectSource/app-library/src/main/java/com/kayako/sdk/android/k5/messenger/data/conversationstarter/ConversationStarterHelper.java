package com.kayako.sdk.android.k5.messenger.data.conversationstarter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.utils.ImageUtils;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.UserViewModel;
import com.kayako.sdk.helpcenter.user.UserMinimal;
import com.kayako.sdk.messenger.conversationstarter.ConversationStarter;

public class ConversationStarterHelper {

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

    public static String getAverageResponseTimeCaption(Long averageReplyTimeInMilliseconds) {
        final long ONE_MINUTE_IN_MILLISECONDS = 60 * 1000;

        long numberOfMinutes = averageReplyTimeInMilliseconds / ONE_MINUTE_IN_MILLISECONDS;

        if (numberOfMinutes < 5) {
            return Kayako.getApplicationContext().getString(R.string.ko__messenger_toolbar_subtitle_average_reply_time_in_a_few_minutes);
        } else if (numberOfMinutes < 15) {
            return Kayako.getApplicationContext().getString(R.string.ko__messenger_toolbar_subtitle_average_reply_time_in_ten_minutes);
        } else if (numberOfMinutes < 25) {
            return Kayako.getApplicationContext().getString(R.string.ko__messenger_toolbar_subtitle_average_reply_time_in_twenty_minutes);
        } else if (numberOfMinutes < 40) {
            return Kayako.getApplicationContext().getString(R.string.ko__messenger_toolbar_subtitle_average_reply_time_in_thirty_minutes);
        } else if (numberOfMinutes < 80) { // 1 HOUR & 20 MINUTES
            return Kayako.getApplicationContext().getString(R.string.ko__messenger_toolbar_subtitle_average_reply_time_in_an_hour);
        } else if (numberOfMinutes < 240) { // 4 HOURS
            return Kayako.getApplicationContext().getString(R.string.ko__messenger_toolbar_subtitle_average_reply_time_in_few_hours);
        } else if (numberOfMinutes < 1800) { // 30 HOURS = 1 DAY 8 HOURS
            return Kayako.getApplicationContext().getString(R.string.ko__messenger_toolbar_subtitle_average_reply_time_in_a_day);
        } else {
            // SHOW NOTHING if it takes more than a day
            return "";
        }
    }

    public static String getLastActiveTimeCaption(boolean isActive, Long averageReplyTimeInMilliseconds) {
        final long ONE_MINUTE_IN_MILLISECONDS = 60 * 1000;

        long numberOfMinutes = (System.currentTimeMillis() - averageReplyTimeInMilliseconds) / ONE_MINUTE_IN_MILLISECONDS;

        // Assertion due to invalid time on machine
        if (numberOfMinutes < 0) {
            // if the time on the machine is behind, the current time will be less than the time the user was active last, leading to a negative value
            return "";
        }

        if (isActive) {
            return Kayako.getApplicationContext().getString(R.string.ko__messenger_toolbar_subtitle_assigned_agent_active);
        } else if (numberOfMinutes <= 15) {
            return String.format(
                    Kayako.getApplicationContext().getString(R.string.ko__messenger_toolbar_subtitle_assigned_agent_active_in_x_minutes),
                    15);

        } else if (numberOfMinutes <= 37) {
            return String.format(
                    Kayako.getApplicationContext().getString(R.string.ko__messenger_toolbar_subtitle_assigned_agent_active_in_x_minutes),
                    30);

        } else if (numberOfMinutes <= 52) {
            return String.format(
                    Kayako.getApplicationContext().getString(R.string.ko__messenger_toolbar_subtitle_assigned_agent_active_in_x_minutes),
                    45);

        } else if (numberOfMinutes <= 90) {
            return Kayako.getApplicationContext().getString(R.string.ko__messenger_toolbar_subtitle_assigned_agent_active_in_one_hour);

        } else if (numberOfMinutes < 1410) { // 23.5 HOURS
            return String.format(
                    Kayako.getApplicationContext().getString(R.string.ko__messenger_toolbar_subtitle_assigned_agent_active_in_x_hours),
                    Math.round((numberOfMinutes - 1) / 60));

        } else if (numberOfMinutes < 1470) { // 24.5 HOURS
            return Kayako.getApplicationContext().getString(R.string.ko__messenger_toolbar_subtitle_assigned_agent_active_in_one_day);

        } else if (numberOfMinutes < 5760) { // ~ 4 DAYS
            return String.format(
                    Kayako.getApplicationContext().getString(R.string.ko__messenger_toolbar_subtitle_assigned_agent_active_in_x_days),
                    Math.round((numberOfMinutes - 1) / (60 * 24)));
        } else {
            return ""; // SHOW NOTHING if user was active for more than 4 days
        }
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
