package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.kayako.sdk.android.k5.R;

public class InputFeedbackCompletedViewHolder extends InputFieldViewHolder {

    public View goodRatingView;
    public View badRatingView;
    public TextView commentView;

    public InputFeedbackCompletedViewHolder(View itemView) {
        super(itemView, R.layout.ko__include_messenger_input_field_feedback_completed);

        goodRatingView = itemView.findViewById(R.id.ko__messenger_input_feedback_rating_good);
        badRatingView = itemView.findViewById(R.id.ko__messenger_input_feedback_rating_bad);
        commentView = (TextView) itemView.findViewById(R.id.ko__messenger_input_comment_rating_view);
    }
}
