package com.kayako.sdk.android.k5.messenger.homescreenpage.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;

public class HeaderViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView subtitle;

    public HeaderViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.ko__home_screen_header_title);
        subtitle = (TextView) itemView.findViewById(R.id.ko__home_screen_header_subtitle);
    }
}
