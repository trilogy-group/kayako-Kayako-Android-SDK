package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.view.CircleImageView;

public class AttachmentMessageSelfViewHolder extends BaseDeliveryIndicatorViewHolder {

    public TextView message;
    public TextView time;
    public CircleImageView avatar;
    public CircleImageView channel;
    public ImageView attachmentPlaceholder;
    public ImageView attachmentThumbnail;

    public AttachmentMessageSelfViewHolder(View itemView) {
        super(itemView);
        message = (TextView) itemView.findViewById(R.id.ko__message);
        avatar = (CircleImageView) itemView.findViewById(R.id.ko__avatar);
        attachmentPlaceholder = (ImageView) itemView.findViewById(R.id.ko__attachment_placeholder);
        attachmentThumbnail = (ImageView) itemView.findViewById(R.id.ko__attachment_image);
        channel = (CircleImageView) itemView.findViewById(R.id.ko__channel);
        time = (TextView) itemView.findViewById(R.id.ko__time);
    }

}
