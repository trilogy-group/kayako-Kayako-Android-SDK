package com.kayako.sdk.android.k5.messenger.data.conversation;

import android.content.Context;
import android.content.SharedPreferences;
import android.test.mock.MockContext;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.UserViewModel;
import static com.kayako.sdk.android.k5.messenger.data.conversationstarter.ConversationStarterHelper.convertToLastActiveAgentsData;
import static com.kayako.sdk.android.k5.messenger.data.conversationstarter.ConversationStarterHelper.getAverageResponseTimeCaption;
import static com.kayako.sdk.android.k5.messenger.data.conversationstarter.ConversationStarterHelper.getLastActiveAgentsCaption;
import static com.kayako.sdk.android.k5.messenger.data.conversationstarter.ConversationStarterHelper.getAvatarUrl;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.LastActiveAgentsData;
import com.kayako.sdk.helpcenter.user.UserMinimal;
import com.kayako.sdk.messenger.conversationstarter.ConversationStarter;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import static org.hamcrest.core.Is.is;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ConversationStarterHelperTest {
    private long averageReplyTimeInMilliseconds;
    private UserMinimal userMinimal;
    private UserViewModel user;
    private Context context;
    private static final long TIMEOUT_MINUTES = 10_000L;
    private static final String CHANEL = "chanel";

    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    @Before
    public void setUp() {
        averageReplyTimeInMilliseconds = TimeUnit.MINUTES.toMillis(TIMEOUT_MINUTES);
        userMinimal = userMinimal = new UserMinimal(1L, "TestUser",
                "http://teste.com/logo.png", TIMEOUT_MINUTES, TIMEOUT_MINUTES, CHANEL);
        user = new UserViewModel(userMinimal.getAvatarUrl(), userMinimal.getFullName(),
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

        collector.checkThat(returnedLastActiveAgentsData.getBrandName(),
                equalTo(lastActiveAgentsData.getBrandName()));
        collector.checkThat(returnedLastActiveAgentsData
                .getAverageReplyTime(), equalTo(lastActiveAgentsData.getAverageReplyTime()));
        collector.checkThat(returnedLastActiveAgentsData
                .getUser1().getAvatar(), equalTo(lastActiveAgentsData.getUser1().getAvatar()));
        collector.checkThat(returnedLastActiveAgentsData
                .getUser1().getFullName(), equalTo(lastActiveAgentsData.getUser1().getFullName()));
        collector.checkThat(returnedLastActiveAgentsData
                .getUser1().getLastActiveAt(), equalTo(lastActiveAgentsData.getUser1()
                .getLastActiveAt()));
    }

    @Test
    public void getAverageResponseTimeCaptionTest() {
        assertThat(getAverageResponseTimeCaption(-1L), is(""));
        assertThat(getAverageResponseTimeCaption(null), is(""));

        assertThat(getAverageResponseTimeCaption(60_000L), is("Usually replies in a few minutes"));
        assertThat(getAverageResponseTimeCaption(600_000L),
                is("Usually replies in under 10 minutes"));
        assertThat(getAverageResponseTimeCaption(900_000L),
                is("Usually replies in under 20 minutes"));
        assertThat(getAverageResponseTimeCaption(1_800_000L),
                is("Usually replies in under 30 minutes"));
        assertThat(getAverageResponseTimeCaption(3_600_000L), is("Usually replies within an hour"));
        assertThat(getAverageResponseTimeCaption(6_000_000L),
                is("Usually replies within a few hours"));
        assertThat(getAverageResponseTimeCaption(30_000_000L), is("Typically responds in a day"));
        assertThat(getAverageResponseTimeCaption(120_000_000L), is(""));
    }

    @Test
    public void getLastActiveAgentsCaptionTest() {
        assertThat(getLastActiveAgentsCaption(new LastActiveAgentsData("Test", 10_000L, user,
                null, null)), is("TestUser was online recently"));
    }

    @Test
    public void getAvatarUrlTest() {
        assertNull(getAvatarUrl(null));
        assertEquals(getAvatarUrl(user), user.getAvatar());
    }
}
