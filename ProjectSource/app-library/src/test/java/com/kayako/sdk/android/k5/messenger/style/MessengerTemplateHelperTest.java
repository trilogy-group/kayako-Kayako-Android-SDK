package com.kayako.sdk.android.k5.messenger.style;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.android.k5.core.MessengerStylePref;
import com.kayako.sdk.android.k5.messenger.style.type.Background;
import com.kayako.sdk.android.k5.messenger.style.type.Foreground;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MessengerStylePref.class,
                 Kayako.class})
public class MessengerTemplateHelperTest {

    @Mock
    private View view;

    @Mock
    private TextView textView;

    @Mock
    private ImageView imageView;

    @Mock
    private MessengerStylePref messengerStylePref;

    @Mock
    private Context mockContext;

    @Mock
    private Drawable drawable;

    @Mock
    private Resources resources;

    @Mock
    private Background background;

    @Mock
    private Foreground foreground;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        mockStatic(MessengerStylePref.class);
        mockStatic(Kayako.class);
        when(Kayako.getApplicationContext()).thenReturn(mockContext);
        when(mockContext.getResources()).thenReturn(resources);
        when(resources.getDrawable(ArgumentMatchers.anyInt())).thenReturn(drawable);
        when(MessengerStylePref.getInstance()).thenReturn(messengerStylePref);
        when(messengerStylePref.getForeground()).thenReturn(foreground);
        when(view.findViewById(ArgumentMatchers.anyInt())).thenReturn(view);
    }

    @Test
    public void whenNullViewThenIllegalArgumentException() {
        //Arrange
        final String exceptionMessage = "Invalid argument. Can't be null";
        final View view = null;

        //Assert
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(exceptionMessage);

        //Act
        MessengerTemplateHelper.applyBackgroundTheme(view);
    }

    @Test
    public void whenNullTextViewThenIllegalArgumentException() {
        //Arrange
        final String exceptionMessage = "Can't be null!";
        final TextView textView = null;

        //Assert
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(exceptionMessage);

        //Act
        MessengerTemplateHelper.applyTextColor(textView);
    }

    @Test
    public void whenNullApplyBackgroundColorThrowException() {
        //Arrange
        final String exceptionMessage = "Can't be null!";
        final View view = null;

        //Assert
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(exceptionMessage);

        //Act
        MessengerTemplateHelper.applyBackgroundColor(view);
    }

    @Test
    public void applyBackgroundThemeWithDefaultBackgroundAndForeground() {
        //Act
        MessengerTemplateHelper.applyBackgroundTheme(view);

        //Assert
        verify(view, times(1)).setBackgroundDrawable(drawable);
        verify(view, times(1)).setBackgroundDrawable(drawable);
    }

    @Test
    public void applyBackgroundThemeWithDarkBackground() {
        //Arrange
        when(messengerStylePref.getBackground()).thenReturn(background);
        when(background.isDarkBackground()).thenReturn(false);
        when(background.getBackgroundDrawable()).thenReturn(drawable);

        //Act
        MessengerTemplateHelper.applyBackgroundTheme(view, background, foreground);

        //Assert
        verify(view, times(1)).setBackgroundDrawable(drawable);
        verify(view, times(1)).setBackgroundDrawable(drawable);
    }


    @Test
    public void applyBackgroundThemeWithNullBackGround() {
        //Arrange
        final Background backgroundLocal = null;

        //Act
        MessengerTemplateHelper.applyBackgroundTheme(view, backgroundLocal, foreground);

        //Assert
        verify(view, times(1)).setBackgroundDrawable(drawable);
        verify(view, times(1)).setBackgroundDrawable(drawable);
    }

    @Test
    public void applyTextColorWhenDarkBackground() {
        //Arrange
        when(resources.getColor(R.color.ko__messenger_default_light_text_color)).thenReturn(R.color.ko__messenger_default_light_text_color);

        //Act
        MessengerTemplateHelper.applyTextColor(textView);

        //Assert
        verify(textView, times(1)).setTextColor(R.color.ko__messenger_default_light_text_color);
    }

    @Test
    public void applyTextColorWhenLightBackground() {
        //Arrange
        when(resources.getColor(R.color.ko__messenger_default_dark_text_color)).thenReturn(R.color.ko__messenger_default_dark_text_color);
        when(messengerStylePref.getBackground()).thenReturn(background);
        when(background.isDarkBackground()).thenReturn(false);

        //Act
        MessengerTemplateHelper.applyTextColor(textView);

        //Assert
        verify(textView, times(1)).setTextColor(R.color.ko__messenger_default_dark_text_color);
    }

    @Test
    public void applyBackgroundColorWhenDarkBackground() {
        //Act
        MessengerTemplateHelper.applyBackgroundColor(view);

        //Assert
        verify(view, times(1)).setBackgroundResource(R.color.ko__messenger_default_light_view_background_color);
    }

    @Test
    public void applyBackgroundColorWhenLightBackground() {
        //Arrange
        when(messengerStylePref.getBackground()).thenReturn(background);
        when(background.isDarkBackground()).thenReturn(false);

        //Act
        MessengerTemplateHelper.applyBackgroundColor(view);

        //Assert
        verify(view, times(1)).setBackgroundResource(R.color.ko__messenger_default_dark_view_background_color);
    }

    @Test
    public void applyBackButtonColorWhenDarkBackground() {
        //Act
        MessengerTemplateHelper.applyBackButtonColor(imageView);

        //Assert
        verify(imageView, times(1)).setImageResource(R.drawable.ko__ic_messenger_back_light);
    }

    @Test
    public void applyBackButtonColorWhenLightBackground() {
        //Arrange
        when(messengerStylePref.getBackground()).thenReturn(background);
        when(background.isDarkBackground()).thenReturn(false);

        //Act
        MessengerTemplateHelper.applyBackButtonColor(imageView);

        //Assert
        verify(imageView, times(1)).setImageResource(R.drawable.ko__ic_messenger_back_dark);
    }
}
