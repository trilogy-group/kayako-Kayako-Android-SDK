package com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.recentcases;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.BaseWidgetViewHolder;

public class RecentConversationsWidgetViewHolder extends BaseWidgetViewHolder {

    public RecyclerView recyclerView;

    public RecentConversationsWidgetViewHolder(View itemView) {
        super(itemView, R.layout.ko__messenger_home_screen_widget_recent_cases);
        recyclerView = (RecyclerView) itemView.findViewById(R.id.ko__recent_conversation_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        // Expecting the HomeScreenListAdapter to set the adapter of this recyclerview
    }
}
