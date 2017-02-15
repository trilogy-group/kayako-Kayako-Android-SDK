package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;

public class InputFieldViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout inputFieldLayout;
    public TextView messageInstruction;
    public TextView messageHint;
    public Button submitButton;
    public ImageView avatar;

    public InputFieldViewHolder(View itemView) {
        super(itemView);
        inputFieldLayout = (LinearLayout) itemView.findViewById(R.id.input_field_layout);
        messageInstruction = (TextView) itemView.findViewById(R.id.message_instruction);
        messageHint = (TextView) itemView.findViewById(R.id.message_hint);
        avatar = (ImageView) itemView.findViewById(R.id.avatar);
        submitButton = (Button) itemView.findViewById(R.id.submit_button);
    }
}
