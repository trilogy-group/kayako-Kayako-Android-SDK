package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.view.CircleImageView;

public class SimpleMessageOtherViewHolder extends RecyclerView.ViewHolder {

    public TextView message;
    public TextView time;
    public CircleImageView avatar;
    public CircleImageView channel;

    public SimpleMessageOtherViewHolder(View itemView) {
        super(itemView);
        avatar = (CircleImageView) itemView.findViewById(R.id.avatar);
        channel = (CircleImageView) itemView.findViewById(R.id.channel);
        message = (TextView) itemView.findViewById(R.id.message);
        time = (TextView) itemView.findViewById(R.id.time);
    }

}
