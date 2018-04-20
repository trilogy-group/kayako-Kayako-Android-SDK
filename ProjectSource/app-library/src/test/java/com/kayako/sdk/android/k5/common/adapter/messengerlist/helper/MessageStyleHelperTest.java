package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import android.content.Context;
import android.content.res.Resources;
import android.widget.TextView;
import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.core.Kayako;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@PrepareForTest({Kayako.class})
@RunWith(PowerMockRunner.class)
public class MessageStyleHelperTest {

    @Mock
    private TextView textView;

    @Mock
    private Context mockContext;

    @Mock
    private Resources resources;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(Kayako.class);
        PowerMockito.when(Kayako.getApplicationContext()).thenReturn(mockContext);
        PowerMockito.when(mockContext.getResources()).thenReturn(resources);
        PowerMockito.when(resources.getColor(ArgumentMatchers.anyInt())).thenReturn(1);
    }

    @Test
    public void setMessage() {
        //Arrange
        final String content = "content";

        //Act
        MessageStyleHelper.setMessage(textView, content);

        //Assert
        verify(textView, times(1)).setText(content);
    }

    @Test
    public void setSelfMessageStyle() {
        //Arrange
        final boolean isSelf = Boolean.TRUE;
        final boolean isFadedBackground = Boolean.TRUE;
        final String message = "emoticon";

        //Act
        MessageStyleHelper.setMessageStyle(isSelf, isFadedBackground, textView, message);

        //Assert
        verify(textView, times(1)).setAlpha(0.3f);
    }

    @Test
    public void setOtherMessageStyle() {
        //Arrange
        final boolean isSelf = Boolean.FALSE;
        final boolean isFadedBackground = Boolean.FALSE;
        final String message = "emoticon";

        //Act
        MessageStyleHelper.setMessageStyle(isSelf, isFadedBackground, textView, message);

        //Assert
        verify(textView, times(1)).setBackgroundResource(
                R.drawable.ko__speech_bubble_other);
    }

    @Test
    public void setEmoteMessageStyle() {
        //Arrange
        final boolean isSelf = Boolean.FALSE;
        final boolean isFadedBackground = Boolean.FALSE;
        final String message = "\uD83D\uDE02";

        //Act
        MessageStyleHelper.setMessageStyle(isSelf, isFadedBackground, textView, message);

        //Assert
        verify(textView, times(1)).setBackgroundResource(0);
    }
}
