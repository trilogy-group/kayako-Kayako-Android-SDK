package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;

public class BaseDeliveryIndicatorViewHolder extends RecyclerView.ViewHolder {

    public ImageView deliveryIndicatorIcon;
    public TextView deliveryIndicatorText;
    public TextView deliveryIndicatorTime;
    public View dotSeparator;
    public View deliveryIndicatorView;

    public BaseDeliveryIndicatorViewHolder(View itemView) {
        super(itemView);
        deliveryIndicatorIcon = (ImageView) itemView.findViewById(R.id.ko__delivery_status_image);
        deliveryIndicatorText = (TextView) itemView.findViewById(R.id.ko__delivery_status);
        deliveryIndicatorTime = (TextView) itemView.findViewById(R.id.ko__time);
        dotSeparator = itemView.findViewById(R.id.dot_separator);
        deliveryIndicatorView = itemView.findViewById(R.id.ko__delivery_indicator_row);
    }
}
