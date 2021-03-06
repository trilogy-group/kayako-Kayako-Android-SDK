package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;

public class InputEmailViewHolder extends InputFieldViewHolder {

    public EditText emailEditText;
    public LinearLayout emailFieldLayout;
    public TextView messageHint;
    public Button submitButton;

    public InputEmailViewHolder(View itemView) {
        super(itemView, R.layout.ko__include_messenger_input_field_email);
        emailEditText = (EditText) itemView.findViewById(R.id.ko__email_input_field_edittext);
        emailFieldLayout = (LinearLayout) itemView.findViewById(R.id.email_field_layout);
        messageHint = (TextView) itemView.findViewById(R.id.ko__email_input_field_message_hint);
        submitButton = (Button) itemView.findViewById(R.id.ko__email_input_field_submit_button);
    }
}
