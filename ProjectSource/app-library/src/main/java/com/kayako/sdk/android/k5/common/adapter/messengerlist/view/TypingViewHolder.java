package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.view.CircleImageView;

public class TypingViewHolder extends RecyclerView.ViewHolder {

    public CircleImageView avatar;
    public ImageView animatedTypingImage;

    public TypingViewHolder(View itemView) {
        super(itemView);
        avatar = (CircleImageView) itemView.findViewById(R.id.ko__avatar);
        animatedTypingImage = (ImageView) itemView.findViewById(R.id.ko__typing_progress_loader);
    }
}
