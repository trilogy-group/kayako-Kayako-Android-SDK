package com.kayako.sdk.android.k5.common.adapter.conversationlist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;

public class ConversationItemViewHolder extends RecyclerView.ViewHolder {

    public ImageView avatar;
    public TextView name;
    public TextView time;
    public TextView subject;
    public TextView status;
    public RelativeLayout layout;
    public View itemView;

    public ConversationItemViewHolder(View v) {
        super(v);
        itemView = v;
        avatar = (ImageView) v.findViewById(R.id.avatar);
        name = (TextView) v.findViewById(R.id.name);
        time = (TextView) v.findViewById(R.id.time);
        subject = (TextView) v.findViewById(R.id.subject);
        status = (TextView) v.findViewById(R.id.status);
        layout = (RelativeLayout) v.findViewById(R.id.layout);
    }
}
