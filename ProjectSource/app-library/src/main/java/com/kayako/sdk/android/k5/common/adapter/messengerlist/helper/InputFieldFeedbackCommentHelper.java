package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFeedbackCommentListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFeedbackCommentViewHolder;
import com.kayako.sdk.android.k5.core.Kayako;

public class InputFieldFeedbackCommentHelper {

    private InputFieldFeedbackCommentHelper() {
    }

    private static void setErrorFieldState(InputFeedbackCommentViewHolder viewHolder) {
        Context context = Kayako.getApplicationContext();

        InputFieldCommonStateHelper.setErrorFieldState(context, viewHolder.feedbackFieldLayout, viewHolder.messageHint);
        viewHolder.messageHint.setText(Html.fromHtml(context.getString(R.string.ko__messenger_input_feedback_comment_message_hint_error)));
        viewHolder.submitButton.setText(context.getResources().getString(R.string.ko__label_submit));
    }

    private static void setFocusedFieldState(InputFeedbackCommentViewHolder viewHolder) {
        Context context = Kayako.getApplicationContext();
        InputFieldCommonStateHelper.setFocusedFieldState(context, viewHolder.feedbackFieldLayout, viewHolder.messageHint);
        viewHolder.messageHint.setText(Html.fromHtml(context.getString(R.string.ko__messenger_input_feedback_comment_message_hint_default)));
        viewHolder.submitButton.setText(context.getResources().getString(R.string.ko__label_submit));
    }

    private static void setUnfocusedFieldState(InputFeedbackCommentViewHolder viewHolder) {
        Context context = Kayako.getApplicationContext();
        InputFieldCommonStateHelper.setUnfocusedFieldState(context, viewHolder.feedbackFieldLayout, viewHolder.messageHint);
        viewHolder.messageHint.setText(Html.fromHtml(context.getString(R.string.ko__messenger_input_feedback_comment_message_hint_default)));
        viewHolder.submitButton.setText(context.getResources().getString(R.string.ko__label_submit));
    }

    private static boolean isValid(String comment) {
        return !TextUtils.isEmpty(comment);
    }

    private static boolean isFocused(View view) {
        return view.isFocused(); // TODO: Check if this is working when there is more than one focusable entity
    }

    private static void setFeedbackFieldState(InputFeedbackCommentViewHolder viewHolder, boolean validateField) {
        String enteredEmail = viewHolder.commentEditText.getText().toString();
        if (validateField && !isValid(enteredEmail)) {
            setErrorFieldState(viewHolder);
        } else if (isFocused(viewHolder.commentEditText)) {
            setFocusedFieldState(viewHolder);
        } else {
            setUnfocusedFieldState(viewHolder);
        }
    }


    public static void configureInputFeedbackField(final InputFeedbackCommentViewHolder viewHolder, final InputFeedbackCommentListItem listItem) {
        InputFieldHelper.configureInputField(viewHolder, BotMessageHelper.getBotDrawable(), listItem.getInstructionMessage());

        // Set up Input field
        if (listItem.hasSubmittedValue()) {
            InputFieldHelper.enableSubmittedLayout(viewHolder, listItem.getSubmittedValue());
        } else {
            InputFieldHelper.enableInputLayout(viewHolder);

            // Set up  field
            setFocusedFieldState(viewHolder);
            viewHolder.feedbackFieldLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    setFeedbackFieldState(viewHolder, false);
                }
            });

            // Submit button
            viewHolder.submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFeedbackFieldState(viewHolder, true);

                    String feedback = viewHolder.commentEditText.getText().toString();
                    if (isValid(feedback)) {
                        InputFieldHelper.enableSubmittedLayout(viewHolder, feedback);
                        listItem.getOnAddFeedbackComment().onAddFeedbackComment(listItem.getRating(), feedback);
                    }
                }
            });
        }
    }
}
