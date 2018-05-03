package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFeedback;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFeedbackCompletedListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFeedbackCompletedViewHolder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        Html.class
})
public class InputFieldFeedbackCompleteHelperTest {
    private static final String INSTRUCTION_MESSAGE = "instructionMessage";

    @Mock
    private InputFeedbackCompletedViewHolder inputFeedbackCompletedViewHolder;

    @Mock
    private InputFeedbackCompletedListItem inputFeedbackCompletedListItem;

    @Mock
    private TextView textView;

    @Mock
    private Spanned spanned;

    @Mock
    private LinearLayout linearLayout;

    @Mock
    private View view;

    @Test
    public void givenViewHolderAndListItemWhenConfigureThenSetText(){
        //Arrange
        inputFeedbackCompletedViewHolder.commentView = textView;
        inputFeedbackCompletedViewHolder.messageInstruction = textView;
        inputFeedbackCompletedViewHolder.submittedLayout = linearLayout;
        inputFeedbackCompletedViewHolder.inputLayout = linearLayout;
        inputFeedbackCompletedViewHolder.badRatingView = view;
        inputFeedbackCompletedViewHolder.goodRatingView = view;
        when(inputFeedbackCompletedListItem.getInstructionMessage())
                .thenReturn(INSTRUCTION_MESSAGE);
        mockStatic(Html.class);
        when(Html.fromHtml(INSTRUCTION_MESSAGE)).thenReturn(spanned);

        //Act
        InputFieldFeedbackCompletedHelper
                .configureInputFeedbackCompletedField(inputFeedbackCompletedViewHolder,
                        inputFeedbackCompletedListItem);

        //Assert
        verify(textView).setText(String
                .format("\"%s\"", inputFeedbackCompletedListItem.getFeedback()));
    }

    @Test
    public void givenViewHolderAndListItemWhenConfigureRatingGoodThenSetText(){
        //Arrange
        inputFeedbackCompletedViewHolder.commentView = textView;
        inputFeedbackCompletedViewHolder.messageInstruction = textView;
        inputFeedbackCompletedViewHolder.submittedLayout = linearLayout;
        inputFeedbackCompletedViewHolder.inputLayout = linearLayout;
        inputFeedbackCompletedViewHolder.badRatingView = view;
        inputFeedbackCompletedViewHolder.goodRatingView = view;
        when(inputFeedbackCompletedListItem.getInstructionMessage())
                .thenReturn(INSTRUCTION_MESSAGE);
        when(inputFeedbackCompletedListItem.getRating())
                .thenReturn(InputFeedback.RATING.GOOD);
        mockStatic(Html.class);
        when(Html.fromHtml(INSTRUCTION_MESSAGE)).thenReturn(spanned);

        //Act
        InputFieldFeedbackCompletedHelper
                .configureInputFeedbackCompletedField(inputFeedbackCompletedViewHolder,
                        inputFeedbackCompletedListItem);

        //Assert
        verify(textView).setText(String
                .format("\"%s\"", inputFeedbackCompletedListItem.getFeedback()));
    }
}
