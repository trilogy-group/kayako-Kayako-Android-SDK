package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import android.test.mock.MockContext;
import android.text.Html;
import android.text.Spanned;
import android.widget.Button;
import android.widget.TextView;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputEmailViewHolder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        InputFieldCommonStateHelper.class,
        Html.class
})
public class InputFieldEmailHelperTest {

    private static final String DUMMY_STRING = "Sorry, that email looks invalid";

    private InputFieldEmailHelper inputFieldEmailHelper;

    private MockContext mockContext;

    @Before
    public void setUp() throws Exception {
        try {
            Constructor<InputFieldEmailHelper> c = InputFieldEmailHelper.class.getDeclaredConstructor();
            c.setAccessible(true);
            inputFieldEmailHelper = c.newInstance();
            mockContext = new MockContext();
        } catch (Exception e) {
            System.out.println("Before");
            e.printStackTrace();

        }
    }

    @Test
    public void setErrorFieldState() throws Exception {
        //Arrange
        mockStatic(InputFieldCommonStateHelper.class, Html.class);
        InputEmailViewHolder viewHolder = mock(InputEmailViewHolder.class);
        TextView textView = mock(TextView.class);
        Button button = mock(Button.class);
        viewHolder.submitButton = button;
        viewHolder.messageHint = textView;
        when(Html.fromHtml(DUMMY_STRING)).thenReturn(mock(Spanned.class));
        doNothing().when(InputFieldCommonStateHelper.class);
        InputFieldCommonStateHelper.setErrorFieldState(mockContext, viewHolder.emailFieldLayout, viewHolder.messageHint, true);

        //Act
        Method method = inputFieldEmailHelper.getClass().getDeclaredMethod("setErrorFieldState", InputEmailViewHolder.class);
        method.invoke(inputFieldEmailHelper, viewHolder);

        //Assert


    }

    @Test
    public void configureInputEmailField() {
    }
}