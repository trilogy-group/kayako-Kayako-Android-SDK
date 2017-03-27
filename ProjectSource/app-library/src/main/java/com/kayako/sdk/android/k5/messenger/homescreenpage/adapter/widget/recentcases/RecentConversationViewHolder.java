package com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.recentcases;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;

public class RecentConversationViewHolder extends RecyclerView.ViewHolder {

    public ImageView avatar;
    public TextView name;
    public TextView time;
    public TextView subject;
    public View itemView;

    public RecentConversationViewHolder(View v) {
        super(v);
        itemView = v;
        avatar = (ImageView) v.findViewById(R.id.avatar);
        name = (TextView) v.findViewById(R.id.name);
        time = (TextView) v.findViewById(R.id.time);
        subject = (TextView) v.findViewById(R.id.subject);
    }
}
