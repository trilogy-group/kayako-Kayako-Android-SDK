package com.kayako.sdk.android.k5.common.view;

import android.content.Context;
import android.util.AttributeSet;
import com.kayako.sdk.android.k5.common.utils.FontUtils;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.validateMockitoUsage;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        FontUtils.class
})
public class ItalicsTextViewTest {
    private static final int DEF_STYLE_ATTR = 0;
    private static final int DEF_STYLE_RES = 0;

    @Mock
    private Context context;

    @Mock
    private AttributeSet attributeSet;

    @Test
    public void givenContextWhenConstructClassThenApplyFont() {
        //Arrange
        mockStatic(FontUtils.class);

        //Act
        ItalicsTextView italicsTextView = new ItalicsTextView(context);

        //Assert
        verifyStatic(FontUtils.class);
        FontUtils.applyFont(context, italicsTextView, FontUtils.FontStyle.ITALICS);
    }

    @Test
    public void givenContextAttributesSetWhenConstructClassThenApplyFont() {
        //Arrange
        mockStatic(FontUtils.class);

        //Act
        ItalicsTextView italicsTextView = new ItalicsTextView(context, attributeSet);

        //Assert
        verifyStatic(FontUtils.class);
        FontUtils.applyFont(context, italicsTextView, FontUtils.FontStyle.ITALICS);
    }

    @Test
    public void givenContextAttributesSetDefAttrWhenConstructClassThenApplyFont() {
        //Arrange
        mockStatic(FontUtils.class);

        //Act
        ItalicsTextView italicsTextView =
                new ItalicsTextView(context, attributeSet, DEF_STYLE_ATTR);

        //Assert
        verifyStatic(FontUtils.class);
        FontUtils.applyFont(context, italicsTextView, FontUtils.FontStyle.ITALICS);
    }

    @Test
    public void givenContextAttributesSetDefAttrDefResWhenConstructClassThenApplyFont() {
        //Arrange
        mockStatic(FontUtils.class);

        //Act
        ItalicsTextView italicsTextView =
                new ItalicsTextView(context, attributeSet, DEF_STYLE_ATTR, DEF_STYLE_RES);

        //Assert
        verifyStatic(FontUtils.class);
        FontUtils.applyFont(context, italicsTextView, FontUtils.FontStyle.ITALICS);
    }

    @After
    public void validate() {
        validateMockitoUsage();
    }

}
