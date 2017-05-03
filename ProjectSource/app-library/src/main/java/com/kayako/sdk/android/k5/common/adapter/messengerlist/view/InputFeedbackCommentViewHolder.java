package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;

public class InputFeedbackCommentViewHolder extends InputFieldViewHolder {

    public EditText commentEditText;
    public LinearLayout feedbackFieldLayout;
    public TextView messageHint;
    public Button submitButton;

    public InputFeedbackCommentViewHolder(View itemView) {
        super(itemView, R.layout.ko__include_messenger_input_field_feedback_comment);
        commentEditText = (EditText) itemView.findViewById(R.id.ko__feedback_input_field_edittext);
        feedbackFieldLayout = (LinearLayout) itemView.findViewById(R.id.ko__feedback_input_field_layout);
        messageHint = (TextView) itemView.findViewById(R.id.ko__feedback_input_field_message_hint);
        submitButton = (Button) itemView.findViewById(R.id.ko__feedback_input_field_submit_button);
    }
}
