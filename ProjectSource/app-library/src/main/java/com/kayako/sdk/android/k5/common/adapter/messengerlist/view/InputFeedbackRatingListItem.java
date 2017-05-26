package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import android.support.annotation.NonNull;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;

import java.util.HashMap;
import java.util.Map;

public class InputFeedbackRatingListItem extends InputFieldlListItem {

    private String instructionMessage;
    private InputFeedback.OnSelectRatingListener onSelectRatingListener;

    public InputFeedbackRatingListItem(@NonNull String instructionMessage, @NonNull InputFeedback.OnSelectRatingListener onSelectRatingListener) {
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

    public InputFeedback.OnSelectRatingListener getOnSelectRatingListener() {
        return onSelectRatingListener;
    }

    public String getInstructionMessage() {
        return instructionMessage;
    }

    @Override
    public Map<String, String> getContents() {
        Map<String, String> map = new HashMap<>();
        map.put("instructionMessage", String.valueOf(instructionMessage));
        map.put("hasSubmittedValue", String.valueOf(hasSubmittedValue()));
        map.put("getSubmittedValue", String.valueOf(getSubmittedValue()));
        return map;
    }

}
