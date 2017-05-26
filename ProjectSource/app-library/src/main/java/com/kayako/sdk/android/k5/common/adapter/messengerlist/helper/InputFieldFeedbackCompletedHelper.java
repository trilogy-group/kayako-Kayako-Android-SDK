package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import android.view.View;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFeedback;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFeedbackCompletedListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFeedbackCompletedViewHolder;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFieldViewHolder;

public class InputFieldFeedbackCompletedHelper {

    private InputFieldFeedbackCompletedHelper() {
    }

    public static void configureInputFeedbackCompletedField(InputFeedbackCompletedViewHolder inputFeedbackCompletedViewHolder, InputFeedbackCompletedListItem inputFeedbackCompletedListItem) {
        InputFieldHelper.configureInputField(inputFeedbackCompletedViewHolder, inputFeedbackCompletedListItem.getInstructionMessage());
        InputFieldHelper.enableInputLayoutWithoutButton(inputFeedbackCompletedViewHolder);

        // Set up Rating View
        if (inputFeedbackCompletedListItem.getRating() == InputFeedback.RATING.GOOD) {
            inputFeedbackCompletedViewHolder.goodRatingView.setVisibility(View.VISIBLE);
            inputFeedbackCompletedViewHolder.badRatingView.setVisibility(View.GONE);
        } else {
            inputFeedbackCompletedViewHolder.badRatingView.setVisibility(View.VISIBLE);
            inputFeedbackCompletedViewHolder.goodRatingView.setVisibility(View.GONE);
        }

        // Set up Comment View
        inputFeedbackCompletedViewHolder.commentView.setText(String.format("\"%s\"", inputFeedbackCompletedListItem.getFeedback()));
    }
}
