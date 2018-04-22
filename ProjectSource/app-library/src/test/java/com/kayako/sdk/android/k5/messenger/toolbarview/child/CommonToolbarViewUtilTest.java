package com.kayako.sdk.android.k5.messenger.toolbarview.child;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.UserViewModel;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.AssignedAgentData;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.ConversationStarterHelper;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.LastActiveAgentsData;
import com.kayako.sdk.android.k5.messenger.style.MessengerTemplateHelper;
import com.kayako.sdk.android.k5.messenger.style.type.Background;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.anyInt;
import org.mockito.Answers;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@PrepareForTest({ConversationStarterHelper.class,
                 TextUtils.class,
                 MessengerTemplateHelper.class})
@RunWith(PowerMockRunner.class)
public class CommonToolbarViewUtilTest {

    @Mock
    private View view;

    @Mock
    private TextView textView;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private AssignedAgentData assignedAgentData;

    @Mock
    private LastActiveAgentsData lastActiveAgentsData;

    @Mock
    private UserViewModel userViewModel;

    @Mock
    private ImageView imageView;

    @Mock
    private Background background;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void setUnreadCountWhenLessThanNine() {
        //Arrange
        final int unreadCount = 5;
        when(view.findViewById(anyInt())).thenReturn(textView);

        //Act
        CommonToolbarViewUtil.setUnreadCount(view, unreadCount);

        //Assert
        verify(textView, times(1)).setVisibility(View.VISIBLE);
        verify(textView, times(1)).setText(String.valueOf(unreadCount));
    }

    @Test
    public void setUnreadCountWhenGreaterThanNine() {
        //Arrange
        final int unreadCount = 10;
        when(view.findViewById(anyInt())).thenReturn(textView);

        //Act
        CommonToolbarViewUtil.setUnreadCount(view, unreadCount);

        //Assert
        verify(textView, times(1)).setVisibility(View.VISIBLE);
        verify(textView, times(1)).setText("+9");
    }

    @Test
    public void setUnreadCountWhenZero() {
        //Arrange
        final int unreadCount = 0;
        when(view.findViewById(anyInt())).thenReturn(textView);

        //Act
        CommonToolbarViewUtil.setUnreadCount(view, unreadCount);

        //Assert
        verify(textView, times(1)).setVisibility(View.GONE);
    }

    @Test
    public void WhenBrandNameNullThenIllegalArgumentException() {
        //Arrange
        final String exceptionMessage = "Brand Name can not be null!";
        final String brandName = null;

        //Assert
        thrown.expectMessage(exceptionMessage);
        thrown.expect(IllegalArgumentException.class);

        //Act
        CommonToolbarViewUtil.setTitle(view, brandName);
    }

    @Test
    public void setTitle() {
        //Arrange
        final String brandName = "brand_name";
        when(view.findViewById(anyInt())).thenReturn(textView);

        //Act
        CommonToolbarViewUtil.setTitle(view, brandName);

        //Assert
        verify(textView, times(1)).setText(brandName);
    }

    @Test
    public void setSubtitleForAverageResponseTimeWhenSubtitleEmpty() {
        //Arrange
        final long averageReplyTimeInMilliseconds = 1_000L;
        mockStatic(ConversationStarterHelper.class);
        mockStatic(TextUtils.class);
        when(ConversationStarterHelper.getAverageResponseTimeCaption(
                averageReplyTimeInMilliseconds)).thenReturn("");
        when(TextUtils.isEmpty("")).thenReturn(true);
        when(view.findViewById(anyInt())).thenReturn(textView);

        //Act
        CommonToolbarViewUtil.setSubtitleForAverageResponseTime(view, averageReplyTimeInMilliseconds);

        //Assert
        verify(textView, times(1)).setVisibility(View.GONE);
    }

    @Test
    public void setSubtitleForAverageResponseTimeWhenSubtitleNotEmpty() {
        //Arrange
        final long averageReplyTimeInMilliseconds = 1_000L;
        mockStatic(ConversationStarterHelper.class);
        mockStatic(TextUtils.class);
        when(ConversationStarterHelper.getAverageResponseTimeCaption(
                averageReplyTimeInMilliseconds)).thenReturn("text");
        when(TextUtils.isEmpty("text")).thenReturn(false);
        when(view.findViewById(anyInt())).thenReturn(textView);

        //Act
        CommonToolbarViewUtil.setSubtitleForAverageResponseTime(view, averageReplyTimeInMilliseconds);

        //Assert
        verify(textView, times(1)).setVisibility(View.VISIBLE);
    }

    @Test
    public void setSubtitleForUserLastActiveTimeWhenSubtitleEmpty() {
        //Arrange
        mockStatic(ConversationStarterHelper.class);
        mockStatic(TextUtils.class);
        when(assignedAgentData.isActive()).thenReturn(true);
        when(assignedAgentData.getUser().getLastActiveAt()).thenReturn(1_000L);
        when(ConversationStarterHelper.getLastActiveTimeCaption(assignedAgentData.isActive(),
                assignedAgentData.getUser().getLastActiveAt())).thenReturn("");
        when(TextUtils.isEmpty("")).thenReturn(true);
        when(view.findViewById(anyInt())).thenReturn(textView);

        //Act
        CommonToolbarViewUtil.setSubtitleForUserLastActiveTime(view, assignedAgentData);

        //Assert
        verify(textView, times(1)).setVisibility(View.GONE);
    }

    @Test
    public void setSubtitleForUserLastActiveTimeWhenSubtitleNotEmpty() {
        //Arrange
        mockStatic(ConversationStarterHelper.class);
        mockStatic(TextUtils.class);
        when(assignedAgentData.isActive()).thenReturn(true);
        when(assignedAgentData.getUser().getLastActiveAt()).thenReturn(1_000L);
        when(ConversationStarterHelper.getLastActiveTimeCaption(assignedAgentData.isActive(),
                assignedAgentData.getUser().getLastActiveAt())).thenReturn("text");
        when(TextUtils.isEmpty("text")).thenReturn(false);
        when(view.findViewById(anyInt())).thenReturn(textView);

        //Act
        CommonToolbarViewUtil.setSubtitleForUserLastActiveTime(view, assignedAgentData);

        //Assert
        verify(textView, times(1)).setVisibility(View.VISIBLE);
    }

    @Test
    public void setLastActiveAgentAvatars() {
        //Arrange
        when(view.findViewById(anyInt())).thenReturn(imageView);
        when(view.findViewById(R.id.ko__messenger_toolbar_avatar_caption_text)).thenReturn(textView);
        when(lastActiveAgentsData.getUser1()).thenReturn(userViewModel);
        when(lastActiveAgentsData.getUser2()).thenReturn(userViewModel);
        when(lastActiveAgentsData.getUser3()).thenReturn(userViewModel);

        //Act
        CommonToolbarViewUtil.setLastActiveAgentAvatars(view, lastActiveAgentsData);

        //Assert
        verify(view, times(4)).findViewById(anyInt());
    }

    @Test
    public void setAssignedAgentAvatar() {
        //Arrange
        when(view.findViewById(R.id.ko__messenger_toolbar_avatar2)).thenReturn(imageView);

        //Act
        CommonToolbarViewUtil.setAssignedAgentAvatar(view, assignedAgentData);

        //Assert
        verify(view, times(1)).findViewById(anyInt());
    }

    @Test
    public void customizeColorsToMatchMessengerStyle() {
        //Arrange
        when(view.findViewById(R.id.ko__messenger_toolbar_subtitle)).thenReturn(textView);
        when(view.findViewById(R.id.ko__messenger_toolbar_title)).thenReturn(textView);
        when(view.findViewById(R.id.ko__messenger_toolbar_back_button)).thenReturn(imageView);
        mockStatic(MessengerTemplateHelper.class);
        when(MessengerTemplateHelper.getSelectedBackground()).thenReturn(background);

        //Act
        CommonToolbarViewUtil.customizeColorsToMatchMessengerStyle(view);

        //Arrange
        verify(view, times(3)).findViewById(anyInt());
    }

    @Test
    public void customizeColorsToMatchMessengerStyleForExpandedToolbar() {
        //Arrange
        when(view.findViewById(R.id.ko__messenger_toolbar_avatar_caption_text)).thenReturn(textView);
        when(view.findViewById(R.id.ko__messenger_toolbar_avatar_separator)).thenReturn(view);
        when(view.findViewById(R.id.ko__messenger_toolbar_back_button)).thenReturn(imageView);
        mockStatic(MessengerTemplateHelper.class);
        when(MessengerTemplateHelper.getSelectedBackground()).thenReturn(background);

        //Act
        CommonToolbarViewUtil.customizeColorsToMatchMessengerStyleForExpandedToolbar(view);

        //Assert
        verify(view, times(3)).findViewById(anyInt());
    }

    @Test
    public void setOnlyTitleNotCollapsedView() {
        //Arrange
        final String title = "title";
        final String exceptionMessage =
                "This method should only be called on a collapsed view!";
        when(view.findViewById(R.id.ko__messenger_toolbar_avatar_caption_text)).thenReturn(view);

        //Assert
        thrown.expectMessage(exceptionMessage);
        thrown.expect(IllegalStateException.class);

        //Act
        CommonToolbarViewUtil.setOnlyTitle(view, title);
    }

    @Test
    public void setOnlyTitleOnCollapsedView() {
        //Arrange
        final String title = "title";
        when(view.findViewById(R.id.ko__messenger_toolbar_title)).thenReturn(textView);
        when(view.findViewById(R.id.ko__messenger_toolbar_subtitle)).thenReturn(textView);
        when(view.findViewById(R.id.ko__messenger_toolbar_avatars)).thenReturn(textView);

        //Act
        CommonToolbarViewUtil.setOnlyTitle(view, title);

        //Assert
        verify(view, times(4)).findViewById(anyInt());
    }
}
