package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import android.view.View;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.view.CircleImageView;

public class SimpleMessageSelfViewHolder extends BaseDeliveryIndicatorViewHolder {

    public TextView message;
    public TextView time;
    public CircleImageView avatar;
    public CircleImageView channel;

    public SimpleMessageSelfViewHolder(View itemView) {
        super(itemView);
        avatar = (CircleImageView) itemView.findViewById(R.id.ko__avatar);
        message = (TextView) itemView.findViewById(R.id.ko__message);
        channel = (CircleImageView) itemView.findViewById(R.id.ko__channel);
        time = (TextView) itemView.findViewById(R.id.ko__time);
    }

}
