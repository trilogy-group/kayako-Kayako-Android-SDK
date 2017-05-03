package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import android.support.annotation.NonNull;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;

public class InputFeedbackRatingListItem extends InputFieldlListItem {

    private String instructionMessage;
    private OnSelectRatingListener onSelectRatingListener;

    public InputFeedbackRatingListItem(@NonNull String instructionMessage, @NonNull OnSelectRatingListener onSelectRatingListener) {
        super(MessengerListType.INPUT_FIELD_FEEDBACK_RATING);
        this.onSelectRatingListener = onSelectRatingListener;
        this.instructionMessage = instructionMessage;

        if (onSelectRatingListener == null || instructionMessage == null) {
            throw new IllegalStateException("Invalid arguments");
        }
    }

    public InputFeedbackRatingListItem(@NonNull String instructionMessage, @NonNull String submittedRating) {
        super(MessengerListType.INPUT_FIELD_FEEDBACK_RATING, submittedRating);
        this.instructionMessage = instructionMessage;

        if (submittedRating == null || instructionMessage == null) {
            throw new IllegalStateException("Invalid arguments");
        }
    }

    public OnSelectRatingListener getOnSelectRatingListener() {
        return onSelectRatingListener;
    }

    public String getInstructionMessage() {
        return instructionMessage;
    }

    public enum RATING {
        GOOD, BAD
    }

    public interface OnSelectRatingListener {
        void onSelectRating(RATING rating);
    }
}
