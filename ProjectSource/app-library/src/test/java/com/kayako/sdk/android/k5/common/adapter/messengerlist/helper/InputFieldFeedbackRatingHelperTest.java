package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFeedback;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFeedbackRatingListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFeedbackRatingViewHolder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        Html.class
})
public class InputFieldFeedbackRatingHelperTest {
    private static final String INSTRUCTION_MESSAGE = "instructionMessage";

    @Mock
    private InputFeedbackRatingViewHolder inputFeedbackRatingViewHolder;

    @Mock
    private InputFeedbackRatingListItem inputFeedbackRatingListItem;

    @Mock
    private TextView textView;

    @Mock
    private LinearLayout linearLayout;

    @Mock
    private ViewSwitcher viewSwitcher;

    @Mock
    private View view;

    @Mock
    private View differentView;

    @Mock
    private Spanned spanned;

    @Test
    public void givenViewHolderListItemWhenConfigureInputFeedbackThenSetRating(){
        //Arrange
        inputFeedbackRatingViewHolder.messageInstruction = textView;
        inputFeedbackRatingViewHolder.submittedLayout = linearLayout;
        inputFeedbackRatingViewHolder.inputLayout = linearLayout;
        inputFeedbackRatingViewHolder.badRatingViewSwitcher = viewSwitcher;
        inputFeedbackRatingViewHolder.goodRatingViewSwitcher = viewSwitcher;
        when(inputFeedbackRatingListItem.getInstructionMessage())
                .thenReturn(INSTRUCTION_MESSAGE);
        mockStatic(Html.class);
        when(Html.fromHtml(INSTRUCTION_MESSAGE)).thenReturn(spanned);
        when(viewSwitcher.getCurrentView()).thenReturn(view);
        inputFeedbackRatingViewHolder.selectedBadRatingView = view;
        inputFeedbackRatingViewHolder.selectedGoodRatingView = view;

        //Act
        InputFieldFeedbackRatingHelper.configureInputFeedbackField(inputFeedbackRatingViewHolder, inputFeedbackRatingListItem);

        //Assert
        verify(viewSwitcher, times(2)).showNext();
    }

    @Test
    public void givenViewHolderListItemWhenConfigureInputFeedbackThenEnableSubmittedLayout(){
        //Arrange
        inputFeedbackRatingViewHolder.messageInstruction = textView;
        inputFeedbackRatingViewHolder.submittedLayout = linearLayout;
        inputFeedbackRatingViewHolder.inputLayout = linearLayout;
        inputFeedbackRatingViewHolder.submittedAnswer = textView;
        when(inputFeedbackRatingListItem.getInstructionMessage())
                .thenReturn(INSTRUCTION_MESSAGE);
        when(inputFeedbackRatingListItem.hasSubmittedValue())
                .thenReturn(Boolean.TRUE);
        mockStatic(Html.class);
        when(Html.fromHtml(INSTRUCTION_MESSAGE)).thenReturn(spanned);

        //Act
        InputFieldFeedbackRatingHelper.configureInputFeedbackField(inputFeedbackRatingViewHolder, inputFeedbackRatingListItem);

        //Assert
        verify(textView).setText(spanned);
    }

    @Test
    public void givenRatingViewHolderWhenSetRatingViewThenShowNext(){
        //Arrange
        inputFeedbackRatingViewHolder.badRatingViewSwitcher = viewSwitcher;
        inputFeedbackRatingViewHolder.goodRatingViewSwitcher = viewSwitcher;
        when(viewSwitcher.getCurrentView()).thenReturn(view);
        inputFeedbackRatingViewHolder.selectedBadRatingView = view;
        inputFeedbackRatingViewHolder.selectedGoodRatingView = differentView;

        //Act
        InputFieldFeedbackRatingHelper.setRatingView(InputFeedback.RATING.GOOD, inputFeedbackRatingViewHolder);

        //Assert
        verify(viewSwitcher, times(2)).showNext();
    }

    @Test
    public void givenRatingViewHolderWhenBadSetRatingViewThenShowNext(){
        //Arrange
        inputFeedbackRatingViewHolder.badRatingViewSwitcher = viewSwitcher;
        inputFeedbackRatingViewHolder.goodRatingViewSwitcher = viewSwitcher;
        when(viewSwitcher.getCurrentView()).thenReturn(view);
        inputFeedbackRatingViewHolder.selectedBadRatingView = differentView;
        inputFeedbackRatingViewHolder.selectedGoodRatingView = view;

        //Act
        InputFieldFeedbackRatingHelper.setRatingView(InputFeedback.RATING.BAD, inputFeedbackRatingViewHolder);

        //Assert
        verify(viewSwitcher, times(2)).showNext();
    }

    @Test
    public void givenViewHolderWhenGetRatingThenReturnInputFeedback() throws Exception {
        //Arrange
        spy(InputFieldFeedbackRatingHelper.class);
        Method privateMethod = InputFieldFeedbackRatingHelper.class.getDeclaredMethod("getRating", InputFeedbackRatingViewHolder.class);
        privateMethod.setAccessible(true);

        //Act
        InputFeedback.RATING returnedValue = (InputFeedback.RATING)privateMethod.invoke(inputFeedbackRatingViewHolder);

        //Assert
        assertEquals(InputFeedback.RATING.GOOD, returnedValue);
    }
}
