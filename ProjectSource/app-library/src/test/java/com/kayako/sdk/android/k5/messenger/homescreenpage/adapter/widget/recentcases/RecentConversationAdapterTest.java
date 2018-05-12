package com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.recentcases;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.utils.ImageUtils;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.ClientTypingActivity;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.ConversationViewModel;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.HomeScreenListType;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.suppress;
import static org.powermock.api.support.membermodification.MemberMatcher.methods;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

@PrepareForTest({
        LayoutInflater.class,
        Kayako.class,
        MessengerPref.class,
        ImageUtils.class
})
@RunWith(PowerMockRunner.class)
public class RecentConversationAdapterTest {

    private RecentConversationAdapter recentConversationAdapter;

    @Mock
    private OnClickRecentConversationListener onClickRecentConversationListener;

    @Mock
    private ViewGroup viewGroup;

    @Mock
    private Context context;

    @Mock
    private LayoutInflater layoutInflater;

    @Mock
    private View view;

    @Mock
    private TextView textView;

    @Mock
    private ImageView imageView;

    @Mock
    private RecentConversationViewHolder holder;

    @Mock
    private ConversationViewModel conversationViewModel;

    @Mock
    private MessengerPref messengerPref;

    @Mock
    private ClientTypingActivity clientTypingActivity;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        final List<ConversationViewModel> conversations = new ArrayList<>();
        conversations.add(conversationViewModel);
        recentConversationAdapter = new RecentConversationAdapter(conversations, onClickRecentConversationListener);
        mockStatic(LayoutInflater.class);
        when(viewGroup.getContext()).thenReturn(context);
        when(LayoutInflater.from(context)).thenReturn(layoutInflater);
        when(conversationViewModel.getLastAgentReplierTyping()).thenReturn(clientTypingActivity);
        mockStatic(Kayako.class);
        mockStatic(MessengerPref.class);
        when(Kayako.getApplicationContext()).thenReturn(context);
        when(MessengerPref.getInstance()).thenReturn(messengerPref);
        Whitebox.setInternalState(holder, "subject", textView);
        Whitebox.setInternalState(holder, "name", textView);
        Whitebox.setInternalState(holder, "unreadCounter", textView);
        Whitebox.setInternalState(holder, "time", textView);
        Whitebox.setInternalState(holder, "avatar", imageView);
        Whitebox.setInternalState(holder, "itemView", view);
        Whitebox.setInternalState(holder, "typingLoader", imageView);
        Whitebox.setInternalState(holder, "subjectLine", view);
        Whitebox.setInternalState(holder, "separator", view);
        suppress(methods(ImageUtils.class, "setAvatarImage"));
    }

    @Test
    public void whenNullParamThenException() {
        //Arrange
        final String exceptionMessage = "Can't be null";

        //Assert
        thrown.expectMessage(exceptionMessage);
        thrown.expect(IllegalArgumentException.class);

        //Act
        new RecentConversationAdapter(null, onClickRecentConversationListener);
    }

    @Test
    public void onCreateViewHolder() {
        //Arrange
        when(layoutInflater.inflate(R.layout.ko__list_messenger_home_screen_widget_conversation,
                viewGroup, false)).thenReturn(view);

        //Act
        final RecyclerView.ViewHolder  recycleViewHolder =
                recentConversationAdapter.onCreateViewHolder(viewGroup, HomeScreenListType.HEADER);

        //Assert
        assertNotNull(recycleViewHolder);
    }

    @Test
    public void onBindViewHolder() {
        //Act
        recentConversationAdapter.onBindViewHolder(holder, 0);

        //Assert
        verify(view).setVisibility(View.GONE);
    }

    @Test
    public void getItemCount() {
        //Act
        final int count = recentConversationAdapter.getItemCount();

        //Assert
        assertEquals(1, count);
    }
}
