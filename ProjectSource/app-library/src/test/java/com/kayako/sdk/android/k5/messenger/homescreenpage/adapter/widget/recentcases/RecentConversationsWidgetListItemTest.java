package com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.recentcases;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.DiffUtilsHelper;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.ClientTypingActivity;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.ConversationViewModel;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.BaseWidgetListItem;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.recentcases.OnClickRecentConversationListener;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.recentcases.RecentConversationsWidgetListItem;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import static org.hamcrest.core.Is.is;

import static  org.hamcrest.CoreMatchers.equalTo;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class RecentConversationsWidgetListItemTest {
    private static final String TITLE = "Test title";
    private List<ConversationViewModel> conversations;
    private RecentConversationsWidgetListItem recentConversationsWidgetListItem;
    private OnClickRecentConversationListener onClickRecentConversationListener;
    private ConversationViewModel conversationViewModel;
    private BaseWidgetListItem.OnClickActionListener onClickActionListener;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    @Before
    public void setUp() {
        onClickActionListener =
                mock(BaseWidgetListItem.OnClickActionListener.class);
        onClickRecentConversationListener =
                mock(OnClickRecentConversationListener.class);
        conversations = new ArrayList<>();

        conversationViewModel = new ConversationViewModel(1L,
                "http://kayako.com/avatar.png", "Test", 10_000L, "New test", 1 ,
                new ClientTypingActivity(false));
        conversations.add(conversationViewModel);

        recentConversationsWidgetListItem = new RecentConversationsWidgetListItem(TITLE,
                "Action Button Label", onClickActionListener, conversations,
                onClickRecentConversationListener);
    }

    @Test
    public void getConversations() {
        assertThat(recentConversationsWidgetListItem.getConversations().size(),
                is(conversations.size()));
        assertThat(recentConversationsWidgetListItem.getConversations(), is(conversations));
    }

    @Test
    public void testIllegalArgumentoExceptionForConversations() {
        thrown.expect(IllegalArgumentException.class);
        List<ConversationViewModel> emptyConversations = new ArrayList<>();
        RecentConversationsWidgetListItem recentConversationsWidgetListItem =
                new RecentConversationsWidgetListItem(TITLE,
                        "Action Button Label", onClickActionListener, emptyConversations,
                        onClickRecentConversationListener);
    }

    @Test
    public void testIllegalArgumentoExceptionForOnClickRecentConversationListener() {
        thrown.expect(IllegalArgumentException.class);
        RecentConversationsWidgetListItem recentConversationsWidgetListItem =
                new RecentConversationsWidgetListItem(TITLE,
                        "Action Button Label", onClickActionListener, conversations,
                        null);
    }

    @Test
    public void getOnClickRecentConversationListener() {
        assertThat(recentConversationsWidgetListItem.getOnClickRecentConversationListener(),
                is(onClickRecentConversationListener));
    }

    @Test
    public void getContents() {
        Map<String, String> contents = recentConversationsWidgetListItem.getContents();

        collector.checkThat(contents.get("conversations"), equalTo(String.valueOf(
                conversations.size())));
        collector.checkThat(contents.get("title"), equalTo(TITLE));
        collector.checkThat(contents.get("conversations_" + conversationViewModel.getConversationId()),
                equalTo(DiffUtilsHelper.convertToString(conversationViewModel.getContents())));
    }
}
