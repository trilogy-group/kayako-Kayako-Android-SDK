package com.kayako.sdk.android.k5.common.adapter.searchlist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.loadmorelist.EndlessRecyclerViewScrollAdapter;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;

import java.util.List;

import static com.kayako.sdk.android.k5.common.adapter.ListType.SEARCH_ITEM;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SearchArticleAdapter extends EndlessRecyclerViewScrollAdapter {

    private OnSearchedArticleItemClickListener mListener;

    public SearchArticleAdapter(List<BaseListItem> items, OnSearchedArticleItemClickListener listener) {
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
            case SEARCH_ITEM:
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
            case SEARCH_ITEM:
                SearchedItemViewHolder searchedItemViewHolder = (SearchedItemViewHolder) viewHolder;
                SearchListItem item = (SearchListItem) getData().get(position);
                searchedItemViewHolder.mTitle.setText(item.getTitle());
                searchedItemViewHolder.mSubTitle.setText(item.getSubtitle());
                searchedItemViewHolder.mView.setOnClickListener(new View.OnClickListener() {
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