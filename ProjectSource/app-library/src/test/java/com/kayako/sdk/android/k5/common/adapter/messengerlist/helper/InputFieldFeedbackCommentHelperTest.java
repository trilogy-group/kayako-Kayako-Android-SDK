package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import android.content.Context;
import android.content.res.Resources;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFeedback;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFeedbackCommentListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFeedbackCommentViewHolder;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFeedbackRatingViewHolder;
import com.kayako.sdk.android.k5.core.Kayako;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import java.lang.reflect.Method;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        Html.class,
        Kayako.class,
        TextUtils.class,
        InputFieldFeedbackCommentHelper.class
})
public class InputFieldFeedbackCommentHelperTest {
    private static final String INSTRUCTION_MESSAGE = "instructionMessage";

    @Mock
    private InputFeedbackCommentViewHolder inputFeedbackCommentViewHolder;
    
    @Mock
    private InputFeedbackCommentListItem inputFeedbackCommentListItem;

    @Mock
    private InputFeedbackRatingViewHolder inputFeedbackRatingViewHolder;

    @Mock
    private TextView textView;

    @Mock
    private Button button;

    @Mock
    private Spanned spanned;

    @Mock
    private LinearLayout linearLayout;

    @Mock
    private EditText editText;

    @Mock
    private ViewSwitcher viewSwitcher;

    @Mock
    private View view;

    @Mock
    private Context context;

    @Mock
    private Resources resources;

    @Mock
    private Editable editable;

    @Test
    public void givenViewFolderListItemWhenConfigureInputFieldThenSetText(){
        //Arrange
        inputFeedbackCommentViewHolder.messageInstruction = textView;
        inputFeedbackCommentViewHolder.messageHint = textView;
        inputFeedbackCommentViewHolder.submittedLayout = linearLayout;
        inputFeedbackCommentViewHolder.inputLayout = linearLayout;
        inputFeedbackCommentViewHolder.commentEditText = editText;
        inputFeedbackCommentViewHolder.submitButton = button;
        when(inputFeedbackCommentListItem.getInstructionMessage())
                .thenReturn(INSTRUCTION_MESSAGE);
        mockStatic(Html.class);
        when(Html.fromHtml(INSTRUCTION_MESSAGE)).thenReturn(spanned);
        when(inputFeedbackCommentListItem.getRating()).thenReturn(InputFeedback.RATING.GOOD);
        inputFeedbackRatingViewHolder.badRatingViewSwitcher = viewSwitcher;
        inputFeedbackRatingViewHolder.goodRatingViewSwitcher = viewSwitcher;
        when(viewSwitcher.getCurrentView()).thenReturn(view);
        inputFeedbackRatingViewHolder.selectedBadRatingView = view;
        inputFeedbackRatingViewHolder.selectedGoodRatingView = view;
        inputFeedbackCommentViewHolder.ratingView = inputFeedbackRatingViewHolder;
        mockStatic(Kayako.class);
        when(Kayako.getApplicationContext()).thenReturn(context);
        inputFeedbackCommentViewHolder.feedbackFieldLayout = linearLayout;
        when(context.getResources()).thenReturn(resources);
        mockStatic(TextUtils.class);
        when(editText.getText()).thenReturn(editable);
        when(TextUtils.isEmpty(inputFeedbackCommentViewHolder.commentEditText.getText()))
                .thenReturn(Boolean.FALSE);
        
        //Act
        InputFieldFeedbackCommentHelper.configureInputFeedbackField
                (inputFeedbackCommentViewHolder, inputFeedbackCommentListItem);

        //Assert
        verify(editText).setText(null);
    }

    @Test
    public void givenViewHolderWhenSetErrorFieldStateThenSetText()
            throws Exception {
        //Arrange
        inputFeedbackCommentViewHolder.messageHint = textView;
        inputFeedbackCommentViewHolder.submitButton = button;
        inputFeedbackCommentViewHolder.commentEditText = editText;
        inputFeedbackCommentViewHolder.feedbackFieldLayout = linearLayout;
        spy(InputFieldFeedbackCommentHelper.class);
        Method privateMethod = InputFieldFeedbackCommentHelper.class
                .getDeclaredMethod("setFeedbackFieldState",
                        InputFeedbackCommentViewHolder.class, boolean.class);
        privateMethod.setAccessible(true);
        mockStatic(TextUtils.class);
        when(TextUtils.isEmpty(INSTRUCTION_MESSAGE)).thenReturn(Boolean.TRUE);
        when(editText.getText()).thenReturn(editable);
        when(editable.toString()).thenReturn(INSTRUCTION_MESSAGE);
        mockStatic(Kayako.class);
        when(Kayako.getApplicationContext()).thenReturn(context);
        when(context.getResources()).thenReturn(resources);
        mockStatic(Html.class);
        when(Html.fromHtml(INSTRUCTION_MESSAGE)).thenReturn(spanned);

        //Act
        privateMethod.invoke(privateMethod, inputFeedbackCommentViewHolder, Boolean.TRUE);

        //Assert
        verify(button).setText(resources.getString(R.string.ko__label_submit));
    }

    @Test
    public void givenViewHolderWhenSetFocusedFieldStateThenSetText()
            throws Exception {
        //Arrange
        inputFeedbackCommentViewHolder.messageHint = textView;
        inputFeedbackCommentViewHolder.submitButton = button;
        inputFeedbackCommentViewHolder.commentEditText = editText;
        inputFeedbackCommentViewHolder.feedbackFieldLayout = linearLayout;
        spy(InputFieldFeedbackCommentHelper.class);
        Method privateMethod = InputFieldFeedbackCommentHelper.class
                .getDeclaredMethod("setFeedbackFieldState",
                        InputFeedbackCommentViewHolder.class, boolean.class);
        privateMethod.setAccessible(true);
        mockStatic(TextUtils.class);
        when(TextUtils.isEmpty(INSTRUCTION_MESSAGE)).thenReturn(Boolean.FALSE);
        when(editText.getText()).thenReturn(editable);
        when(editable.toString()).thenReturn(INSTRUCTION_MESSAGE);
        mockStatic(Kayako.class);
        when(Kayako.getApplicationContext()).thenReturn(context);
        when(context.getResources()).thenReturn(resources);
        mockStatic(Html.class);
        when(Html.fromHtml(INSTRUCTION_MESSAGE)).thenReturn(spanned);
        when(editText.isFocused()).thenReturn(Boolean.TRUE);

        //Act
        privateMethod.invoke(privateMethod, inputFeedbackCommentViewHolder, Boolean.TRUE);

        //Assert
        verify(button).setText(resources.getString(R.string.ko__label_submit));
    }

    @Test
    public void givenViewHolderWhenSetUnfocusedFieldStateThenSetText()
            throws Exception {
        //Arrange
        inputFeedbackCommentViewHolder.messageHint = textView;
        inputFeedbackCommentViewHolder.submitButton = button;
        inputFeedbackCommentViewHolder.commentEditText = editText;
        inputFeedbackCommentViewHolder.feedbackFieldLayout = linearLayout;
        spy(InputFieldFeedbackCommentHelper.class);
        Method privateMethod = InputFieldFeedbackCommentHelper.class
                .getDeclaredMethod("setFeedbackFieldState",
                        InputFeedbackCommentViewHolder.class, boolean.class);
        privateMethod.setAccessible(true);
        mockStatic(TextUtils.class);
        when(TextUtils.isEmpty(INSTRUCTION_MESSAGE)).thenReturn(Boolean.FALSE);
        when(editText.getText()).thenReturn(editable);
        when(editable.toString()).thenReturn(INSTRUCTION_MESSAGE);
        mockStatic(Kayako.class);
        when(Kayako.getApplicationContext()).thenReturn(context);
        when(context.getResources()).thenReturn(resources);
        mockStatic(Html.class);
        when(Html.fromHtml(INSTRUCTION_MESSAGE)).thenReturn(spanned);

        //Act
        privateMethod.invoke(privateMethod, inputFeedbackCommentViewHolder, Boolean.TRUE);

        //Assert
        verify(button).setText(resources.getString(R.string.ko__label_submit));
    }
}
