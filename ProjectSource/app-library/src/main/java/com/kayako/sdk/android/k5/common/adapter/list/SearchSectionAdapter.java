package com.kayako.sdk.android.k5.common.adapter.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.ListType;
import com.kayako.sdk.android.k5.common.adapter.list.ListItemRecyclerViewAdapter;

import java.util.List;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SearchSectionAdapter extends ListItemRecyclerViewAdapter {

    protected OnSearchClickListener mSearchClickListener;

    public SearchSectionAdapter(List<BaseListItem> items, OnListItemClickListener itemClickListener, OnSearchClickListener searchClickListener) {
        super(items);
        items.add(0, null);
        mSearchClickListener = searchClickListener;
        setListClickListener(itemClickListener);
        setHeaderClickListener(null);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ListType.SEARCH_SECTION_ITEM:
                View viewSearchSection = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.ko__list_search_section, parent, false);
                return new SearchSectionViewHolder(viewSearchSection);
            default:
                return super.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case ListType.SEARCH_SECTION_ITEM:
                SearchSectionViewHolder searchViewHolder = (SearchSectionViewHolder) viewHolder;
                searchViewHolder.mSearchEditText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mSearchClickListener.onClickSearch();
                    }
                });
                break;
            default:
                super.onBindViewHolder(viewHolder, position);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ListType.SEARCH_SECTION_ITEM;
        }
        return super.getItemViewType(position);
    }

    public interface OnSearchClickListener {
        void onClickSearch();
    }

}
