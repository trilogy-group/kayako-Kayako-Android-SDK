package com.kayako.sdk.android.k5.common.adapter.conversationlist;

import android.test.mock.MockContext;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.BotMessageHelper;
import com.kayako.sdk.android.k5.common.utils.DateTimeUtils;
import com.kayako.sdk.android.k5.common.utils.ImageUtils;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.android.k5.core.MessengerPref;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.doNothing;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        Kayako.class,
        ImageUtils.class,
        MessengerPref.class
})
public class ConversationViewItemViewHelperTest {

    protected int unreadCount;

    private MockContext mockContext;

    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    @Mock
    private TextView textView;

    @Mock
    private ImageView imageView;

    @Mock
    private View subjectLine;

    @Before
    public void setUp() {
        mockContext = new MockContext();
    }

    @Test
    public void shouldCallSetVisibilityWhenZeroUnreadCount() {
        //Arrange
        unreadCount = 0;

        //Act
        ConversationViewItemViewHelper.setUnreadCounter(textView, unreadCount);

        //Assert
        verify(textView, times(1)).setVisibility(View.GONE);
        assertThat(textView.getVisibility(), notNullValue());
    }

    @Test
    public void shouldCallSetVisibilityWhenUnreadCountGreaterThanNine() {
        //Arrange
        unreadCount = 10;

        //Act
        ConversationViewItemViewHelper.setUnreadCounter(textView, unreadCount);

        //Assert
        verify(textView, times(1)).setVisibility(View.VISIBLE);
        verify(textView, times(1)).setText("+9");
        assertThat(textView.getVisibility(), notNullValue());
    }

    @Test
    public void shouldCallSetVisibilityWhenUnreadCountNotEqualZeroAndLessThanNine() {
        //Arrange
        unreadCount = 5;

        //Act
        ConversationViewItemViewHelper.setUnreadCounter(textView, unreadCount);

        //Assert
        verify(textView, times(1)).setVisibility(View.VISIBLE);
        verify(textView, times(1)).setText(String.valueOf(unreadCount));
        assertThat(textView.getVisibility(), is(View.VISIBLE));
    }

    @Test
    public void setFormattedTimeTest() {
        //Arrange
        long timeInMilliseconds = System.currentTimeMillis();

        //Act
        ConversationViewItemViewHelper.setFormattedTime(textView, timeInMilliseconds);

        //Assert
        verify(textView, times(1)).setText(DateTimeUtils.
                formatMessengerDateTime(System.currentTimeMillis(), timeInMilliseconds));
    }

    @Test
    public void setTypingIndicatorWhenIsTypingIsTrue() throws Exception {
        //Arrange
        boolean isTyping = Boolean.TRUE;
        mockStatic(Kayako.class);
        mockStatic(ImageUtils.class);
        when(Kayako.getApplicationContext()).thenReturn(mockContext);
        doNothing()
                .when(ImageUtils.class, "setImage", eq(mockContext), eq(imageView),
                        eq(null), eq(R.drawable.ko__img_loading_dots));

        //Act
        ConversationViewItemViewHelper.setTypingIndicator(imageView, subjectLine, isTyping);

        //Assert
        verifyStatic(ImageUtils.class, times(1));
        ImageUtils.setImage(mockContext, imageView, null, R.drawable.ko__img_loading_dots);
    }

    @Test
    public void setTypingIndicatorWhenIsTypingisFalse() {
        //Arrange
        final boolean isTyping = Boolean.FALSE;

        //Act
        ConversationViewItemViewHelper.setTypingIndicator(imageView, subjectLine, isTyping);

        //Assert
        verify(imageView, times(1)).setVisibility(View.GONE);
        verify(subjectLine, times(1)).setVisibility(View.VISIBLE);
        collector.checkThat(imageView.getVisibility(), notNullValue());
        collector.checkThat(subjectLine.getVisibility(), notNullValue());
    }

    @Test
    public void setAvatarWhenAvatarUrlIsNull() throws Exception {
        //Arrange
        mockStatic(ImageUtils.class);
        doNothing().when(ImageUtils.class, "setAvatarImage", eq(mockContext),
                eq(imageView), eq(null));

        //Act
        ConversationViewItemViewHelper.setAvatar(mockContext, imageView, null);

        //Assert
        verifyStatic(ImageUtils.class, times(1));
        ImageUtils.setAvatarImage(mockContext, imageView, BotMessageHelper
                .getDefaultDrawableForConversation());
    }

    @Test
    public void setAvatarWhenAvatarUrlIsNotNull() throws Exception {
        //Arrange
        final String avatarUrl = "testUrl";
        mockStatic(ImageUtils.class);
        doNothing().when(ImageUtils.class, "setAvatarImage", eq(mockContext),
                eq(imageView), eq(avatarUrl));

        //Act
        ConversationViewItemViewHelper.setAvatar(mockContext, imageView, avatarUrl);

        //Assert
        verifyStatic(ImageUtils.class, times(1));
        ImageUtils.setAvatarImage(mockContext, imageView, avatarUrl);
    }

    @Test
    public void setNameWhenNameIsNull() {
        //Arrange
        final String name = null;
        mockStatic(MessengerPref.class);
        when(MessengerPref.getInstance()).thenReturn(mock(MessengerPref.class));

        //Act
        ConversationViewItemViewHelper.setName(textView, name);

        //Assert
        verify(textView).setText(MessengerPref.getInstance().getBrandName());
    }

    @Test
    public void setNameWhenNameIsNotNull() {
        //Arrange
        final String name = "";

        //Act
        ConversationViewItemViewHelper.setName(textView, name);

        //Assert
        verify(textView).setText(name);
    }
}

