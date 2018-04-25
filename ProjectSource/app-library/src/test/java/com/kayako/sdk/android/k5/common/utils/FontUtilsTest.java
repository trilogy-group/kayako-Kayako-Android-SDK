package com.kayako.sdk.android.k5.common.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertNull;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        Typeface.class
})
public class FontUtilsTest {
    @Mock
    private TextView textView;

    @Mock
    private Context context;

    @Test
    public void givenFontStyleWhenSetTypeFaceThenSetFont() {
        //Arrange
        mockStatic(Typeface.class);
        when(Typeface.createFromAsset(context.getAssets()
                , "SEMIBOLD"))
                .thenReturn(Typeface.DEFAULT_BOLD);

        //Act
        FontUtils.applyFont(context, textView, FontUtils.FontStyle.SEMIBOLD);

        //Assert
        assertNull(textView.getTypeface());
    }
}
