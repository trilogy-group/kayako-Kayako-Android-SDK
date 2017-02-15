package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import android.view.View;
import android.widget.EditText;

import com.kayako.sdk.android.k5.R;

public class InputEmailViewHolder extends InputFieldViewHolder {

    public EditText emailEditText;

    public InputEmailViewHolder(View itemView) {
        super(itemView);
        emailEditText = (EditText) itemView.findViewById(R.id.input_email_edittext);
    }
}
