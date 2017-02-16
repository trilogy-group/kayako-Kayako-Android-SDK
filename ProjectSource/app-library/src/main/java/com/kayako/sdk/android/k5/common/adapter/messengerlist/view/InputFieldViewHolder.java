package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.view.CircleImageView;

public class InputFieldViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout inputLayout;
    public LinearLayout submittedLayout;

    public TextView messageInstruction;
    public TextView submittedAnswer;
    public CircleImageView avatar;

    public InputFieldViewHolder(View itemView, @LayoutRes int inputLayoutId) {
        super(itemView);

        messageInstruction = (TextView) itemView.findViewById(R.id.message_instruction);
        submittedAnswer = (TextView) itemView.findViewById(R.id.submitted_value);
        avatar = (CircleImageView) itemView.findViewById(R.id.avatar);

        inputLayout = (LinearLayout) itemView.findViewById(R.id.input_layout);
        submittedLayout = (LinearLayout) itemView.findViewById(R.id.submitted_layout);

        View inflatedView = LayoutInflater.from(itemView.getContext()).inflate(inputLayoutId, inputLayout, false);
        inputLayout.removeAllViews();
        inputLayout.addView(inflatedView);
    }
}
