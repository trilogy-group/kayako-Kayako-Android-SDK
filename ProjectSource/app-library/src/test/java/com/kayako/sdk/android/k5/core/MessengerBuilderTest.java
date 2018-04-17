package com.kayako.sdk.android.k5.core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.webkit.URLUtil;
import com.kayako.sdk.android.k5.activities.KayakoMessengerActivity;
import com.kayako.sdk.android.k5.messenger.style.type.Background;
import com.kayako.sdk.android.k5.messenger.style.type.Foreground;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(PowerMockRunner.class)
@PrepareForTest({URLUtil.class, Patterns.class, Kayako.class,
MessengerPref.class, MessengerStylePref.class, KayakoMessengerActivity.class})
public class MessengerBuilderTest {

    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String BRAND_NAME = "brand_Name";
    private static final String URL = "https://url.a";
    private static final String FINGERPRINT_ID = "123ABC";
    private static final String USER_EMAIL = "aaa.bbb@zzz.com";
    private static final String USER_NAME = "test user";
    private static final int STRING_RES_ID = 0;
    private final MessengerBuilder builder = new MessengerBuilder();
    private MessengerPref messengerPref;
    private MessengerStylePref messengerStylePref;

    @Mock
    private Background background;

    @Mock
    private Foreground foreground;

    @Mock
    private Context mockContext;

    @Mock
    private Resources resources;

    @Mock
    private Drawable drawable;

    @Mock
    private Activity activity;

    @Mock
    private Intent intent;

    @Mock
    private AppCompatActivity compatActivity;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private Pattern pattern;

    @Mock
    private Matcher matcher;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        mockStatic(Kayako.class);
        mockStatic(URLUtil.class);
        mockStatic(MessengerPref.class);
        mockStatic(MessengerStylePref.class);
        mockStatic(Patterns.class);
        messengerPref = mock(MessengerPref.class);
        messengerStylePref = mock(MessengerStylePref.class);
        when(MessengerPref.getInstance()).thenReturn(messengerPref);
        when(MessengerStylePref.getInstance()).thenReturn(messengerStylePref);
        when(Kayako.getApplicationContext()).thenReturn(mockContext);
        when(mockContext.getResources()).thenReturn(resources);
        when(resources.getString(STRING_RES_ID)).thenReturn(BRAND_NAME, TITLE, DESCRIPTION);
        when(URLUtil.isNetworkUrl(URL)).thenReturn(true);
        when(background.getBackgroundDrawable()).thenReturn(drawable);
        when(foreground.getDrawableForDarkBackground()).thenReturn(drawable);
        when(foreground.getType()).thenReturn(Foreground.ForegroundType.TEXTURE);
        when(foreground.getDrawableForLightBackground()).thenReturn(drawable);
        Whitebox.setInternalState(Patterns.class, "EMAIL_ADDRESS", pattern);
        when(pattern.matcher(USER_EMAIL)).thenReturn(matcher);
        when(matcher.matches()).thenReturn(true);
    }

    @Test
    public void whenInvalidUrlThenIllegalArgumentException() {
        final String exceptionMessage = "Invalid Network Url!";
        final String instanceUrl = "/url";
        when(URLUtil.isNetworkUrl(instanceUrl)).thenReturn(false);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(exceptionMessage);
        builder.setUrl(instanceUrl);
    }

    @Test
    public void whenNullFingerprintIdThenIllegalArgumentException() {
        final String exceptionMessage = "Invalid Fingerprint Id";
        final String newFingerprintId = null;
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(exceptionMessage);
        builder.setFingerprintId(newFingerprintId);
    }

    @Test
    public void whenInvalidEmailThenIllegalArgumentException() {
        final String exceptionMessage = "Invalid Email Provided";
        final String newEmail = "abc";
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(exceptionMessage);
        builder.setUserEmail(newEmail);
    }

    @Test
    public void whenNullUserNameThenIllegalArgumentException() {
        final String exceptionMessage = "Invalid User Name Provided";
        final String newUserName = null;
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(exceptionMessage);
        builder.setUserName(newUserName);
    }

    @Test
    public void whenNullBackgroundThenIllegalArgumentException() {
        final String exceptionMessage = "Invalid Background";
        final Background newBackground = null;
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(exceptionMessage);
        builder.setBackground(newBackground);
    }

    @Test
    public void whenNullForegroundThenIllegalArgumentException() {
        final String exceptionMessage = "Invalid Foreground";
        final Foreground newForeground = null;
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(exceptionMessage);
        builder.setForeground(newForeground);
    }

    @Test
    public void whenInvalidStringResIdThenIllegalArgumentException() {
        final String exceptionMessage = "Invalid String resource for Brand Name";
        final int stringResId = 0;
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(exceptionMessage);
        when(resources.getString(stringResId)).thenThrow(Resources.NotFoundException.class);
        builder.setBrandName(stringResId);
    }

    @Test
    public void whenNullBrandNameThenIllegalArgumentException() {
        final String exceptionMessage = "Invalid String";
        final String newBrandName = null;
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(exceptionMessage);
        builder.setBrandName(newBrandName);
    }

    @Test
    public void whenUrlNullThenIllegalArgumentException() {
        final String exceptionMessage = "URL is mandatory. Please call setUrl()";
        final MessengerBuilder builderLocal = new MessengerBuilder();
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(exceptionMessage);
        builderLocal.open(activity);
    }

    @Test
    public void whenBrandNameNullThenIllegalArgumentException() {
        final String exceptionMessage = "BrandName is mandatory. Please call setBrandName()";
        final MessengerBuilder builderLocal = new MessengerBuilder();
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(exceptionMessage);
        builderLocal.setUrl(URL);
        builderLocal.open(activity);
    }

    @Test
    public void whenTitleNullThenIllegalArgumentException() {
        final String exceptionMessage = "Title is mandatory. Please call setTitle()";
        final MessengerBuilder builderLocal = new MessengerBuilder();
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(exceptionMessage);
        builderLocal.setUrl(URL);
        builderLocal.setBrandName(BRAND_NAME);
        builderLocal.open(activity);
    }

    @Test
    public void whenDescriptionNullThenIllegalArgumentException() {
        final String exceptionMessage = "Description is mandatory. Please call setDescription()";
        final MessengerBuilder builderLocal = new MessengerBuilder();
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(exceptionMessage);
        builderLocal.setUrl(URL);
        builderLocal.setBrandName(BRAND_NAME);
        builderLocal.setTitle(TITLE);
        builderLocal.open(activity);
    }

    @Test
    public void openActivity() throws Exception{
        //Arrange
        whenNew(Intent.class).withArguments(activity, KayakoMessengerActivity.class).thenReturn(intent);
        builder.setBrandName(STRING_RES_ID);
        builder.setTitle(STRING_RES_ID);
        builder.setDescription(STRING_RES_ID);
        builder.setUrl(URL);
        builder.setFingerprintId(FINGERPRINT_ID);
        builder.setUserName(USER_NAME);
        builder.setBackground(background);
        builder.setForeground(foreground);

        //Act
        builder.open(activity);

        //Assert
        verify(messengerPref, times(1)).setBrandName(BRAND_NAME);
        verify(messengerPref, times(1)).setTitle(TITLE);
        verify(messengerPref, times(1)).setDescription(DESCRIPTION);
        verify(messengerPref, times(1)).setUrl(URL);
        verify(messengerPref, times(1)).setFingerprintId(FINGERPRINT_ID);
        verify(messengerPref, times(1)).setUserName(USER_NAME);
        verify(messengerStylePref, times(1)).setBackground(background);
        verify(messengerStylePref, times(1)).setForeground(foreground);
    }

    @Test
    public void openAppCompatActivity() throws Exception{
        //Arrange
        whenNew(Intent.class).withArguments(compatActivity, KayakoMessengerActivity.class).thenReturn(intent);
        builder.setBrandName(STRING_RES_ID);
        builder.setTitle(STRING_RES_ID);
        builder.setDescription(STRING_RES_ID);
        builder.setUrl(URL);
        builder.setUserName(USER_NAME);

        //Act
        builder.open(compatActivity);

        //Assert
        verify(messengerPref, times(1)).setBrandName(BRAND_NAME);
        verify(messengerPref, times(1)).setTitle(TITLE);
        verify(messengerPref, times(1)).setDescription(DESCRIPTION);
        verify(messengerPref, times(1)).setUrl(URL);
        verify(messengerPref, times(1)).setUserName(USER_NAME);
        verify(messengerStylePref, times(0)).setBackground(background);
        verify(messengerStylePref, times(0)).setForeground(foreground);
    }
}
