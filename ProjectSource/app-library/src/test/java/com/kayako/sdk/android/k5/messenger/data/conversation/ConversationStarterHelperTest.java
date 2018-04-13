package com.kayako.sdk.android.k5.messenger.data.conversation;

import android.content.Context;
import android.content.SharedPreferences;
import android.test.mock.MockContext;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.UserViewModel;
import static com.kayako.sdk.android.k5.messenger.data.conversationstarter.ConversationStarterHelper.*;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.LastActiveAgentsData;
import com.kayako.sdk.helpcenter.user.UserMinimal;
import com.kayako.sdk.messenger.conversationstarter.ConversationStarter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import static org.hamcrest.core.Is.is;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pedroveras on 12/04/18.
 */

public class ConversationStarterHelperTest {
    private long averageReplyTimeInMilliseconds;
    private UserMinimal userMinimal;
    private UserViewModel user;
    Context context;

    @Before
    public void setUp() {
        this.averageReplyTimeInMilliseconds = 10000 * 60 * 1000;
        this.userMinimal = userMinimal = new UserMinimal(1L, "TestUser",
                "http://teste.com/logo.png", 10000L, 10000L, "channel");
        this.user = new UserViewModel(userMinimal.getAvatarUrl(), userMinimal.getFullName(),
                userMinimal.getLastActiveAt());

        SharedPreferences sharedPreferences = mock(SharedPreferences.class);
        MessengerPref messengerPref = mock(MessengerPref.class);
        context = mock(Context.class);

        Context mContext = mock(Context.class);

        when(context.getSharedPreferences("kayako_help_center_info", Context.MODE_PRIVATE))
                .thenReturn(sharedPreferences);
        when(context.getSharedPreferences("kayako_messenger_info", Context.MODE_PRIVATE))
                .thenReturn(sharedPreferences);
        when(sharedPreferences.getString("KEY_BRAND_NAME", null)).thenReturn("Test");
        when(context.getApplicationContext()).thenReturn(mContext);
        when(context.getApplicationContext()).thenReturn(context);
        when(context.getApplicationContext().getString(
                R.string.ko__messenger_toolbar_subtitle_average_reply_time_in_a_few_minutes))
                .thenReturn("Usually replies in a few minutes");
        when(context.getApplicationContext().getString(
                R.string.ko__messenger_toolbar_subtitle_average_reply_time_in_ten_minutes))
                .thenReturn("Usually replies in under 10 minutes");
        when(context.getApplicationContext().getString(
                R.string.ko__messenger_toolbar_subtitle_average_reply_time_in_twenty_minutes))
                .thenReturn("Usually replies in under 20 minutes");
        when(context.getApplicationContext().getString(
                R.string.ko__messenger_toolbar_subtitle_average_reply_time_in_thirty_minutes))
                .thenReturn("Usually replies in under 30 minutes");
        when(context.getApplicationContext().getString(
                R.string.ko__messenger_toolbar_subtitle_average_reply_time_in_an_hour))
                .thenReturn("Usually replies within an hour");
        when(context.getApplicationContext().getString(
                R.string.ko__messenger_toolbar_subtitle_average_reply_time_in_few_hours))
                .thenReturn("Usually replies within a few hours");
        when(context.getApplicationContext().getString(
                R.string.ko__messenger_toolbar_subtitle_average_reply_time_in_a_day))
                .thenReturn("Typically responds in a day");

        when(context.getApplicationContext().getString(
                R.string.ko__messenger_toolbar_subtitle_assigned_agent_active))
                .thenReturn("Active");

        when(context.getApplicationContext().getString(
                R.string.ko__messenger_toolbar_avatar_caption_text_one_agent))
                .thenReturn("TestUser was online recently");

        Kayako.initialize(context);
    }

    @Test
    public void convertToLastActiveAgentsDataTest() {
        when(MessengerPref.getInstance().getBrandName()).thenReturn("Test");
        UserMinimal userMinimal = new UserMinimal(1L, "TestUser", "http://teste.com/logo.png",
                10000L, 10000L, "channel");
        List<UserMinimal> lastActiveAgents = new ArrayList<>();
        lastActiveAgents.add(userMinimal);

        try {
            convertToLastActiveAgentsData(null);
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
            assertNotNull(e.getMessage());
        }

        ConversationStarter conversationStarter = new ConversationStarter("http://teste.com", lastActiveAgents, 10000D);

        LastActiveAgentsData lastActiveAgentsData = new LastActiveAgentsData("Test",
                averageReplyTimeInMilliseconds, user, null, null);

        LastActiveAgentsData returnedLastActiveAgentsData = convertToLastActiveAgentsData(conversationStarter);

        assertEquals(lastActiveAgentsData.getBrandName(), returnedLastActiveAgentsData.getBrandName());
        assertEquals(lastActiveAgentsData.getAverageReplyTime(), returnedLastActiveAgentsData
                .getAverageReplyTime());
        assertEquals(lastActiveAgentsData.getUser1().getAvatar(), returnedLastActiveAgentsData
                .getUser1().getAvatar());
        assertEquals(lastActiveAgentsData.getUser1().getFullName(), returnedLastActiveAgentsData
                .getUser1().getFullName());
        assertEquals(lastActiveAgentsData.getUser1().getLastActiveAt(), returnedLastActiveAgentsData
                .getUser1().getLastActiveAt());
    }

    @Test
    public void getAverageResponseTimeCaptionTest() {
        assertThat(getAverageResponseTimeCaption(-1L), is(""));
        assertThat(getAverageResponseTimeCaption(null), is(""));

        assertThat(getAverageResponseTimeCaption(60000L), is("Usually replies in a few minutes"));
        assertThat(getAverageResponseTimeCaption(600000L), is("Usually replies in under 10 minutes"));
        assertThat(getAverageResponseTimeCaption(900000L), is("Usually replies in under 20 minutes"));
        assertThat(getAverageResponseTimeCaption(1800000L), is("Usually replies in under 30 minutes"));
        assertThat(getAverageResponseTimeCaption(3600000L), is("Usually replies within an hour"));
        assertThat(getAverageResponseTimeCaption(6000000L), is("Usually replies within a few hours"));
        assertThat(getAverageResponseTimeCaption(30000000L), is("Typically responds in a day"));
        assertThat(getAverageResponseTimeCaption(120000000L), is(""));
    }


    public void getLastActiveTimeCaptionTest() {
        assertThat(getLastActiveTimeCaption(true,10000L), is("Active"));
        assertThat(getLastActiveTimeCaption(false, 100L), is(""));
        assertThat(getLastActiveTimeCaption(false, 600000L), is("Active in the last 15 minutes"));
        assertThat(getLastActiveTimeCaption(false, 1500000L), is("Active in the last 30 minutes"));
        assertThat(getLastActiveTimeCaption(false, 2400000L), is("Active in the last 45 minutes"));
        assertThat(getLastActiveTimeCaption(false, 3300000L), is("Active in the last hour"));
        assertThat(getLastActiveTimeCaption(false, 36000000L), is("Active 10 hours ago"));
        assertThat(getLastActiveTimeCaption(false, 72000000L), is("Active in the last day"));
        assertThat(getLastActiveTimeCaption(false, 172800000L), is("Active 2 days ago"));
    }

    @Test
    public void getLastActiveAgentsCaptionTest() {
        assertThat(getLastActiveAgentsCaption(new LastActiveAgentsData("Test", 10000L, user,
                null, null)), is("TestUser was online recently"));
    }

    @Test
    public void getAvatarUrlTest() {
        assertNull(getAvatarUrl(null));
        assertEquals(getAvatarUrl(user), user.getAvatar());
    }
}
