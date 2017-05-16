package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import android.support.annotation.NonNull;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;

import java.util.HashMap;
import java.util.Map;

public class InputFeedbackCommentListItem extends InputFieldlListItem {

    private String instructionMessage;
    private InputFeedback.RATING rating;
    private OnAddFeedbackCommentCallback onAddFeedbackComment;

    public InputFeedbackCommentListItem(@NonNull String instructionMessage, @NonNull InputFeedback.RATING rating, @NonNull OnAddFeedbackCommentCallback onAddFeedbackComment) {
        super(MessengerListType.INPUT_FIELD_FEEDBACK_COMMENT);
        this.instructionMessage = instructionMessage;
        this.rating = rating;
        this.onAddFeedbackComment = onAddFeedbackComment;

        if (instructionMessage == null || rating == null || onAddFeedbackComment == null) {
            throw new IllegalArgumentException("Invalid arguments");
        }
    }

    public InputFeedbackCommentListItem(@NonNull String instructionMessage, @NonNull String submittedComment) {
        super(MessengerListType.INPUT_FIELD_FEEDBACK_COMMENT, submittedComment);
        this.instructionMessage = instructionMessage;

        if (submittedComment == null || instructionMessage == null) {
            throw new IllegalArgumentException("Invalid arguments");
        }
    }

    public String getInstructionMessage() {
        return instructionMessage;
    }

    public InputFeedback.RATING getRating() {
        return rating;
    }

    public OnAddFeedbackCommentCallback getOnAddFeedbackComment() {
        return onAddFeedbackComment;
    }

    public interface OnAddFeedbackCommentCallback {

        void onChangeFeedbackRating(InputFeedback.RATING rating);

        void onAddFeedbackComment(InputFeedback.RATING rating, String feedback);
    }

    @Override
    public Map<String, String> getContents() {
        Map<String, String> map = new HashMap<>();
        map.put("instructionMessage", String.valueOf(instructionMessage));
        map.put("rating", String.valueOf(rating));
        map.put("hasSubmittedValue", String.valueOf(hasSubmittedValue()));
        map.put("getSubmittedValue", String.valueOf(getSubmittedValue()));
        return map;
    }

}
