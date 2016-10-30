package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.view.CircleImageView;

public class AttachmentMessageSelfViewHolder extends RecyclerView.ViewHolder {

    public TextView message;
    public TextView time;
    public CircleImageView avatar;
    public ImageView attachmentPlaceholder;
    public ImageView attachmentThumbnail;

    public AttachmentMessageSelfViewHolder(View itemView) {
        super(itemView);
        message = (TextView) itemView.findViewById(R.id.message);
        avatar = (CircleImageView) itemView.findViewById(R.id.avatar);
        attachmentPlaceholder = (ImageView) itemView.findViewById(R.id.attachment_placeholder);
        attachmentThumbnail = (ImageView) itemView.findViewById(R.id.attachment_image);
        time = (TextView) itemView.findViewById(R.id.time);
    }

}
