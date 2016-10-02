package com.kayako.sdk.android.k5.searcharticlepage;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.ListItemRecyclerViewAdapter;
import com.kayako.sdk.android.k5.common.data.ListItem;

import java.util.List;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SearchArticleAdapter extends ListItemRecyclerViewAdapter {

    public static final int STATE_SEARCH_ITEM = 2;


    public SearchArticleAdapter(List<ListItem> items, OnItemClickListener listener) {
        super(items, listener);
    }

    @Override
    public int getItemViewType(int position) {
        int selectedViewType = super.getItemViewType(position);
        if (selectedViewType == STATE_ITEM) {
            return STATE_SEARCH_ITEM;
        } else {
            return selectedViewType;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case STATE_SEARCH_ITEM:
                View viewItem = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.ko__list_item_search_result, parent, false);
                return new SearchedItemViewHolder(viewItem);
            default:
                return super.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case STATE_SEARCH_ITEM:
                SearchedItemViewHolder searchedItemViewHolder = (SearchedItemViewHolder) viewHolder;
                searchedItemViewHolder.mItem = getData().get(position);
                searchedItemViewHolder.mTitle.setText(getData().get(position).getTitle());
                searchedItemViewHolder.mSubTitle.setText(getData().get(position).getSubtitle());
                searchedItemViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mItemClickListener.onItemClick(getData().get(viewHolder.getAdapterPosition()));
                    }
                });
            default:
                super.onBindViewHolder(viewHolder, position);
        }
    }

    public class SearchedItemViewHolder extends ViewHolder {
        public final TextView mTitle;
        public final TextView mSubTitle;

        public SearchedItemViewHolder(View view) {
            super(view);
            mView = view;
            mTitle = (TextView) view.findViewById(R.id.ko__list_item_title);
            mSubTitle = (TextView) view.findViewById(R.id.ko__list_item_subtitle);
        }
    }

}