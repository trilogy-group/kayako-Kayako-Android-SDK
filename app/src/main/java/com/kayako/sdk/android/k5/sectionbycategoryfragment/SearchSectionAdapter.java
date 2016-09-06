package com.kayako.sdk.android.k5.sectionbycategoryfragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.ListItemRecyclerViewAdapter;
import com.kayako.sdk.android.k5.common.data.ListItem;

import java.util.List;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SearchSectionAdapter extends ListItemRecyclerViewAdapter {

    protected static final int STATE_SEARCH_SECTION = 3;

    protected OnSearchClickListener mSearchClickListener;

    public SearchSectionAdapter(List<ListItem> items, OnItemClickListener itemClickListener, OnSearchClickListener searchClickListener) {
        super(items, itemClickListener);
        items.add(0, null);
        mSearchClickListener = searchClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case STATE_SEARCH_SECTION:
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
            case STATE_SEARCH_SECTION:
                SearchSectionViewHolder searchViewHolder = (SearchSectionViewHolder) viewHolder;
                searchViewHolder.searchEditText.setOnClickListener(new View.OnClickListener() {
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
            return STATE_SEARCH_SECTION;
        }
        return super.getItemViewType(position);
    }

    public class SearchSectionViewHolder extends ViewHolder {
        public EditText searchEditText;

        public SearchSectionViewHolder(View view) {
            super(view);
            mView = view;
            mItem = null;
            searchEditText = (EditText) view.findViewById(R.id.ko__search_edittext);
        }
    }

    public interface OnSearchClickListener {
        void onClickSearch();
    }

}
