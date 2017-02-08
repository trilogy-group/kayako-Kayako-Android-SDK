package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.view.CircleImageView;

public class SimpleMessageContinuedOtherViewHolder extends RecyclerView.ViewHolder {

    public TextView message;
    public TextView time;

    public SimpleMessageContinuedOtherViewHolder(View itemView) {
        super(itemView);
        message = (TextView) itemView.findViewById(R.id.message);
        time = (TextView) itemView.findViewById(R.id.time);
    }

}
