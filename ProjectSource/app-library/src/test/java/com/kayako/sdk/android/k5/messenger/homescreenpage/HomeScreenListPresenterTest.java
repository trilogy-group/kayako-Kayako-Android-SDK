package com.kayako.sdk.android.k5.messenger.homescreenpage;

import android.content.Context;
import android.content.res.Resources;
import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.utils.FailsafePollingHelper;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.ClientTypingActivity;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.ConversationViewModel;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.ConversationViewModelHelper;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.UserViewModel;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.presence.PresenceWidgetListItem;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.recentcases.RecentConversationsWidgetListItem;
import com.kayako.sdk.messenger.conversation.Conversation;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import java.util.List;

@PrepareForTest({
        MessengerPref.class,
        Kayako.class
})
@RunWith(PowerMockRunner.class)
public class HomeScreenListPresenterTest {

    private static final long CONVERSATION_ID = 1_111L;
    private HomeScreenListPresenter homeScreenListPresenter;

    @Mock
    private Context context;

    @Mock
    private HomeScreenListContract.View view;

    @Mock
    private FailsafePollingHelper failsafePollingHelper;

    @Mock
    private MessengerPref messengerPref;

    @Mock
    private RecentConversationsWidgetListItem recentConversationsWidgetListItem;

    @Mock
    private PresenceWidgetListItem presenceWidgetListItem;

    @Mock
    private ConversationViewModelHelper conversationViewModelHelper;

    @Mock
    private Conversation conversation;

    @Mock
    private Resources resources;

    @Mock
    private List<ConversationViewModel> conversationList;

    @Mock
    private UserViewModel userViewModel;

    @Captor
    private ArgumentCaptor<List<BaseListItem>> listArgumentCaptor;

    @Before
    public void setUp() {
        homeScreenListPresenter = new HomeScreenListPresenter(view);
        mockStatic(MessengerPref.class);
        when(MessengerPref.getInstance()).thenReturn(messengerPref);
        Whitebox.setInternalState(homeScreenListPresenter, "mConversationViewModelHelper", conversationViewModelHelper);
        Whitebox.setInternalState(homeScreenListPresenter, "mRecentCasesWidgetListItem", recentConversationsWidgetListItem);
        mockStatic(Kayako.class);
        when(Kayako.getApplicationContext()).thenReturn(context);
        when(context.getResources()).thenReturn(resources);
        when(resources.getString(R.string.ko__messenger_home_screen_widget_recent_cases_title)).thenReturn("stringOne");
        when(resources.getString(R.string.ko__messenger_home_screen_widget_recent_cases_action_button_label)).thenReturn("stringTwo");
        when(messengerPref.getTitle()).thenReturn("title");
        when(messengerPref.getDescription()).thenReturn("description");
        when(conversationViewModelHelper.getConversationList()).thenReturn(conversationList);
        when(conversationViewModelHelper.exists(CONVERSATION_ID)).thenReturn(true);
        when(conversationList.size()).thenReturn(5);
    }

    @Test
    public void initPage() {
        //Arrange
        Whitebox.setInternalState(homeScreenListPresenter, "mPresenceWidgetListItem", presenceWidgetListItem);
        Whitebox.setInternalState(homeScreenListPresenter, "mFailsafePollingHelper", failsafePollingHelper);

        //Act
        homeScreenListPresenter.initPage();
        verify(view).setupList(listArgumentCaptor.capture());

        //Assert
        assertEquals(4, listArgumentCaptor.getValue().size());
    }

    @Test
    public void onChange() {
        //Arrange
        when(conversation.getId()).thenReturn(CONVERSATION_ID);
        when(conversationViewModelHelper.updateConversationProperty(CONVERSATION_ID, conversation)).thenReturn(true);

        //Act
        homeScreenListPresenter.onChange(conversation);
        verify(view).setupList(listArgumentCaptor.capture());

        //Assert
        assertEquals(3, listArgumentCaptor.getValue().size());
    }

    @Test
    public void onTyping() {
        //Assert
        final boolean isTyping = Boolean.TRUE;
        when(conversationViewModelHelper.updateRealtimeProperty(ArgumentMatchers.eq(CONVERSATION_ID),
                ArgumentMatchers.any(ClientTypingActivity.class))).thenReturn(true);

        //Act
        homeScreenListPresenter.onTyping(CONVERSATION_ID, userViewModel, isTyping);
        verify(view).setupList(listArgumentCaptor.capture());

        //Assert
        assertEquals(3, listArgumentCaptor.getValue().size());
    }
}
