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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.doNothing;



@RunWith(PowerMockRunner.class)
@PrepareForTest({Kayako.class, ImageUtils.class, MessengerPref.class})
public class ConversationViewItemViewHelperTest {

    @Mock
    TextView textView;

    @Mock
    ImageView imageView;

    @Mock
    View subjectLine;


    private MockContext mockContext;

    private int UNREAD_COUNT;

    @Before
    public void setUp() throws Exception {
        mockContext = new MockContext();
    }

    @After
    public void tearDown() throws Exception {
        validateMockitoUsage();
    }

    @Test
    public void shouldCallSetVisibilityWhenZeroUnreadCount() {
        UNREAD_COUNT = 0;
        ConversationViewItemViewHelper.setUnreadCounter(textView, UNREAD_COUNT);
        verify(textView, times(1)).setVisibility(View.GONE);
    }

    @Test
    public void shouldCallSetVisibilityWhenUnreadCountGreaterThanNine() {
        UNREAD_COUNT = 10;
        ConversationViewItemViewHelper.setUnreadCounter(textView, UNREAD_COUNT);
        verify(textView, times(1)).setVisibility(View.VISIBLE);
        verify(textView, times(1)).setText("+9");
    }

    @Test
    public void shouldCallSetVisibilityWhenUnreadCountNotEqualZeroAndLessThanNine() {
        UNREAD_COUNT = 5;
        ConversationViewItemViewHelper.setUnreadCounter(textView, UNREAD_COUNT);
        verify(textView, times(1)).setVisibility(View.VISIBLE);
        verify(textView, times(1)).setText(String.valueOf(UNREAD_COUNT));
    }

    @Test
    public void setFormattedTimeTest() {
        long timeInMilliseconds = System.currentTimeMillis();
        ConversationViewItemViewHelper.setFormattedTime(textView, timeInMilliseconds);
        verify(textView, times(1)).setText(DateTimeUtils.formatMessengerDateTime(System.currentTimeMillis(), timeInMilliseconds));
    }

    @Test
    public void testSetTypingIndicatorWhenIsTypingIsTrue() throws Exception {
        boolean isTyping = Boolean.TRUE;
        mockStatic(Kayako.class);
        mockStatic(ImageUtils.class);

        when(Kayako.getApplicationContext()).thenReturn(mockContext);
        doNothing()
                .when(ImageUtils.class, "setImage", eq(mockContext), eq(imageView), anyString(), anyInt());

        ConversationViewItemViewHelper.setTypingIndicator(imageView, subjectLine, isTyping);

        verifyStatic(ImageUtils.class, times(1));
        ImageUtils.setImage(mockContext, imageView, null, R.drawable.ko__img_loading_dots);
    }

    @Test
    public void testSetTypingIndicatorWhenIsTypingisFalse() throws Exception {
        boolean isTyping = Boolean.FALSE;
        ConversationViewItemViewHelper.setTypingIndicator(imageView, subjectLine, isTyping);
        verify(imageView, times(1)).setVisibility(View.GONE);
        verify(subjectLine, times(1)).setVisibility(View.VISIBLE);
    }

    @Test
    public void testSetAvatarWhenAvatarUrlIsNull() throws Exception {
        String avatarUrl = null;
        mockStatic(ImageUtils.class);
        doNothing().when(ImageUtils.class, "setAvatarImage", eq(mockContext), eq(imageView), eq(null));
        ConversationViewItemViewHelper.setAvatar(mockContext, imageView, avatarUrl);

        verifyStatic(ImageUtils.class, times(1));
        ImageUtils.setAvatarImage(mockContext, imageView, BotMessageHelper.getDefaultDrawableForConversation());
    }

    @Test
    public void testSetAvatarWhenAvatarUrlIsNotNull() throws Exception {
        String avatarUrl = "testUrl";
        mockStatic(ImageUtils.class);
        doNothing().when(ImageUtils.class, "setAvatarImage", eq(mockContext), eq(imageView), anyString());
        ConversationViewItemViewHelper.setAvatar(mockContext, imageView, avatarUrl);
        verifyStatic(ImageUtils.class, times(1));
        ImageUtils.setAvatarImage(mockContext, imageView, avatarUrl);

    }

    @Test
    public void testSetNameWhenNameisNull() {
        String name = null;
        mockStatic(MessengerPref.class);
        when(MessengerPref.getInstance()).thenReturn(mock(MessengerPref.class));
        ConversationViewItemViewHelper.setName(textView, name);
        verify(textView).setText(MessengerPref.getInstance().getBrandName());
    }

    @Test
    public void testSetNameWhenNameisNotNull() {
        String name = "";
        ConversationViewItemViewHelper.setName(textView, name);
        verify(textView).setText(name);
    }
}