package com.kayako.sdk.android.k5.common.adapter.searchlist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.loadmorelist.EndlessRecyclerViewScrollAdapter;

import java.util.List;


/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SearchListAdapter extends EndlessRecyclerViewScrollAdapter {

    private OnSearchedArticleItemClickListener mListener;

    public SearchListAdapter(List<BaseListItem> items, OnSearchedArticleItemClickListener listener) {
        super(items);
        mListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return getData().get(position).getItemType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case SearchListType.SEARCH_ITEM:
                View viewItem = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.ko__list_item_search_result, parent, false);
                return new SearchItemViewHolder(viewItem);
            default:
                return super.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case SearchListType.SEARCH_ITEM:
                SearchItemViewHolder searchItemViewHolder = (SearchItemViewHolder) viewHolder;
                SearchListItem item = (SearchListItem) getData().get(position);
                searchItemViewHolder.mTitle.setText(item.getTitle());
                searchItemViewHolder.mSubTitle.setText(item.getSubtitle());
                searchItemViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SearchListItem item = (SearchListItem) getData().get(viewHolder.getAdapterPosition());
                        mListener.onClickSearchedArticle(item);
                    }
                });
            default:
                super.onBindViewHolder(viewHolder, position);
        }
    }

    public interface OnSearchedArticleItemClickListener {
        void onClickSearchedArticle(SearchListItem listItem);
    }

}