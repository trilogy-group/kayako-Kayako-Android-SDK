package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import android.support.annotation.NonNull;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;

import java.util.HashMap;
import java.util.Map;

public class InputFeedbackCompletedListItem extends InputFieldlListItem {

    private String instructionMessage;
    private InputFeedback.RATING rating;
    private String feedback;

    public InputFeedbackCompletedListItem(@NonNull String instructionMessage, @NonNull InputFeedback.RATING rating, @NonNull String feedback) {
        super(MessengerListType.INPUT_FIELD_FEEDBACK_COMPLETED);
        this.instructionMessage = instructionMessage;
        this.rating = rating;
        this.feedback = feedback;

        if (instructionMessage == null || rating == null || feedback == null) {
            throw new IllegalArgumentException("Invalid arguments");
        }
    }

    public String getInstructionMessage() {
        return instructionMessage;
    }

    public InputFeedback.RATING getRating() {
        return rating;
    }

    public String getFeedback() {
        return feedback;
    }

    @Override
    public Map<String, String> getContents() {
        Map<String, String> map = new HashMap<>();
        map.put("instructionMessage", String.valueOf(instructionMessage));
        map.put("rating", String.valueOf(rating));
        map.put("feedback", String.valueOf(feedback));
        map.put("hasSubmittedValue", String.valueOf(hasSubmittedValue()));
        map.put("getSubmittedValue", String.valueOf(getSubmittedValue()));
        return map;
    }

}
