package com.kayako.sdk.android.k5.articlelistpage;

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
public class SectionInfoAdapter extends ListItemRecyclerViewAdapter {

    protected static final int STATE_SECTION_INFO = 2;
    protected String mTitle;
    protected String mDescription;

    public SectionInfoAdapter(List<ListItem> items, OnItemClickListener listener, String title, String description) {
        super(items, listener);
        items.add(0, null);
        mTitle = title;
        mDescription = description;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case STATE_SECTION_INFO:
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
            case STATE_SECTION_INFO:
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
            return STATE_SECTION_INFO;
        }
        return super.getItemViewType(position);
    }

    public class SectionInfoViewHolder extends ViewHolder {
        public TextView title;
        public TextView description;


        public SectionInfoViewHolder(View view) {
            super(view);
            mView = view;
            mItem = null;
            title = (TextView) view.findViewById(R.id.ko__section_title);
            description = (TextView) view.findViewById(R.id.ko__section_description);
        }
    }
}
