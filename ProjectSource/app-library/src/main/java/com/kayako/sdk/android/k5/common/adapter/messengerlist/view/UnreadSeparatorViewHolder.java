package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;

public class UnreadSeparatorViewHolder extends RecyclerView.ViewHolder {

    public TextView unreadText;

    public UnreadSeparatorViewHolder(View itemView) {
        super(itemView);
        unreadText = (TextView) itemView.findViewById(R.id.ko__unread_label);
    }
}
