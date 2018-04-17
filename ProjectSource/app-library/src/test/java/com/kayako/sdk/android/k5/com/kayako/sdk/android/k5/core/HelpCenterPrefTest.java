package com.kayako.sdk.android.k5.com.kayako.sdk.android.k5.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.kayako.sdk.android.k5.core.HelpCenterPref;
import com.kayako.sdk.android.k5.core.Kayako;

import static org.hamcrest.core.Is.is;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Locale;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TextUtils.class, HelpCenterPref.class})
public class HelpCenterPrefTest {
    private Context context;
    private static final String LANGUAGE = "EN";
    private static final String REGION = "US";

    @Before
    public void setUp() {
        context = mock(Context.class);
        Context mContext = mock(Context.class);
        when(context.getApplicationContext()).thenReturn(mContext);
        SharedPreferences sharedPreferences = mock(SharedPreferences.class);
        when(mContext.getSharedPreferences("kayako_help_center_info", Context.MODE_PRIVATE))
                .thenReturn(sharedPreferences);
        when(sharedPreferences.getString("help_center_url", null)).thenReturn("http://kayako.com");
        when(sharedPreferences.getString("locale_language", null)).thenReturn("EN");
        when(sharedPreferences.getString("locale_region", null)).thenReturn("US");
        Kayako.initialize(context);
        PowerMockito.mockStatic(TextUtils.class);
        PowerMockito.when(TextUtils.isEmpty(LANGUAGE)).thenReturn(false);
    }

    @Test
    public void getHelpCenterUrl() {
        assertThat(HelpCenterPref.getInstance().getHelpCenterUrl(), is("http://kayako.com"));
    }

    @Test
    public void getLocale() {
        Locale locale = new Locale(LANGUAGE, REGION);
        assertThat(HelpCenterPref.getInstance().getLocale(), is(locale));
    }
}
