package com.kayako.sdk.android.k5.messenger.homescreenpage.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;

import java.util.List;

public class HomeScreenListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<BaseListItem> mItems;

    public HomeScreenListAdapter(List<BaseListItem> items) {
        this.mItems = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case HomeScreenListType.WIDGET_HEADER:
                View headerView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.ko__list_home_screen_header, parent, false);
                return new HeaderViewHolder(headerView);

            case HomeScreenListType.WIDGET_PRESENCE:
                // TODO;

            case HomeScreenListType.WIDGET_RECENT_CONVERSATIONS:
                // TODO;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case HomeScreenListType.WIDGET_HEADER:
                HeaderListItem headerListItem = (HeaderListItem) mItems.get(position);
                HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
                headerViewHolder.title.setText(headerListItem.getTitle());
                headerViewHolder.subtitle.setText(headerListItem.getSubtitle());
                break;

            // TODO:
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getItemType();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
