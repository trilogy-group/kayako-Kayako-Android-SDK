package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;

public class AttachmentMessageContinuedSelfViewHolder extends RecyclerView.ViewHolder {

    public TextView message;
    public TextView time;
    public ImageView attachmentPlaceholder;
    public ImageView attachmentThumbnail;

    public AttachmentMessageContinuedSelfViewHolder(View itemView) {
        super(itemView);
        message = (TextView) itemView.findViewById(R.id.message);
        attachmentPlaceholder = (ImageView) itemView.findViewById(R.id.attachment_placeholder);
        attachmentThumbnail = (ImageView) itemView.findViewById(R.id.attachment_image);
        time = (TextView) itemView.findViewById(R.id.time);
    }

}
