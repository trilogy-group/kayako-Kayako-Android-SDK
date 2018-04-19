package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import com.kayako.sdk.android.k5.R;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;

@RunWith(MockitoJUnitRunner.class)
public class InputFieldCommonStateHelperTest {

    @Mock(answer = RETURNS_DEEP_STUBS)
    private Context mockContext;

    @Mock
    private ViewGroup viewGroup;

    @Mock
    private TextView textView;

    @Before
    public void setUp() {
        when(mockContext.getResources().getColor(anyInt())).thenReturn(1);
    }

    @Test
    public void setErrorFieldStateWithRoundedTop() {
        //Arrange
        final boolean isRoundedTop = Boolean.TRUE;

        //Act
        InputFieldCommonStateHelper.setErrorFieldState(mockContext, viewGroup, textView, isRoundedTop);

        //Assert
        verify(viewGroup, times(1)).setBackgroundResource(
                R.drawable.ko__speech_bubble_input_field_rounded_top_background_red);
        verify(textView, times(1)).setTextColor(1);
    }

    @Test
    public void setErrorFieldStateWithoutRoundedTop() {
        //Arrange
        final boolean isRoundedTop = Boolean.FALSE;

        //Act
        InputFieldCommonStateHelper.setErrorFieldState(mockContext, viewGroup, textView, isRoundedTop);

        //Assert
        verify(viewGroup, times(1)).setBackgroundResource(
                R.drawable.ko__speech_bubble_input_field_background_red);
        verify(textView, times(1)).setTextColor(1);
    }

    @Test
    public void setFocusedFieldStateWithRoundedTop() {
        //Arrange
        final boolean isRoundedTop = Boolean.TRUE;

        //Act
        InputFieldCommonStateHelper.setFocusedFieldState(mockContext, viewGroup, textView, isRoundedTop);

        //Assert
        verify(viewGroup, times(1)).setBackgroundResource(
                R.drawable.ko__speech_bubble_input_field_rounded_top_background);
        verify(textView, times(1)).setTextColor(1);
    }

    @Test
    public void setFocusedFieldStateWithoutRoundedTop() {
        //Arrange
        final boolean isRoundedTop = Boolean.FALSE;

        //Act
        InputFieldCommonStateHelper.setFocusedFieldState(mockContext, viewGroup, textView, isRoundedTop);

        //Assert
        verify(viewGroup, times(1)).setBackgroundResource(
                R.drawable.ko__speech_bubble_input_field_background);
        verify(textView, times(1)).setTextColor(1);
    }

    @Test
    public void setUnfocusedFieldStateWithRoundedTop() {
        //Arrange
        final boolean isRoundedTop = Boolean.TRUE;

        //Act
        InputFieldCommonStateHelper.setUnfocusedFieldState(mockContext, viewGroup, textView, isRoundedTop);

        //Assert
        verify(viewGroup, times(1)).setBackgroundResource(
                R.drawable.ko__speech_bubble_input_field_rounded_top_background_white);
        verify(textView, times(1)).setTextColor(1);
    }

    @Test
    public void setUnfocusedFieldStateWithoutRoundedTop() {
        //Arrange
        final boolean isRoundedTop = Boolean.FALSE;

        //Act
        InputFieldCommonStateHelper.setUnfocusedFieldState(mockContext, viewGroup, textView, isRoundedTop);

        //Assert
        verify(viewGroup, times(1)).setBackgroundResource(
                R.drawable.ko__speech_bubble_input_field_background_white);
        verify(textView, times(1)).setTextColor(1);
    }
}
