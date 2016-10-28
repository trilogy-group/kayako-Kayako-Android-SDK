package com.kayako.sdk.android.k5.common.adapter.sectioninfolist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.list.ListItemRecyclerViewAdapter;

import java.util.List;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SectionInfoAdapter extends ListItemRecyclerViewAdapter {

    protected String mTitle;
    protected String mDescription;

    public SectionInfoAdapter(List<BaseListItem> items, OnListItemClickListener listener, String title, String description) {
        super(items);
        items.add(0, null);
        mTitle = title;
        mDescription = description;
        setListClickListener(listener);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case SectionInfoListType.SECTION_INFO_ITEM:
                View viewSearchSection = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.ko__list_section_info, parent, false);
                return new SectionInfoViewHolder(viewSearchSection);
            default:
                return super.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case SectionInfoListType.SECTION_INFO_ITEM:
                SectionInfoViewHolder sectionInfoViewHolder = (SectionInfoViewHolder) viewHolder;
                sectionInfoViewHolder.title.setText(mTitle);
                sectionInfoViewHolder.description.setText(mDescription);
                break;
            default:
                super.onBindViewHolder(viewHolder, position);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return SectionInfoListType.SECTION_INFO_ITEM;
        }
        return super.getItemViewType(position);
    }
}
