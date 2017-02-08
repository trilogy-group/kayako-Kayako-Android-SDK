package com.kayako.sdk.android.k5.common.adapter.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.loadmorelist.EndlessRecyclerViewScrollAdapter;

import java.util.List;

public class ListItemRecyclerViewAdapter extends EndlessRecyclerViewScrollAdapter {

    protected OnListItemClickListener mListItemClickListener;
    protected OnHeaderItemClickListener mHeaderItemClickListener;

    public ListItemRecyclerViewAdapter(List<BaseListItem> items) {
        super(items);
    }

    public void setListClickListener(OnListItemClickListener listener) {
        mListItemClickListener = listener;
    }

    public void setHeaderClickListener(OnHeaderItemClickListener listener) {
        mHeaderItemClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {

            case ListType.HEADER_ITEM:
                View viewHeader = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.ko__list_header, parent, false);
                return new HeaderViewHolder(viewHeader);

            case ListType.LIST_ITEM:
                View viewItem = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.ko__list_item_with_description, parent, false);
                return new ListItemViewHolder(viewItem);

            default:
                return super.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case ListType.HEADER_ITEM:
                HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;
                HeaderListItem listItem = (HeaderListItem) getData().get(position);
                headerViewHolder.mItem = listItem;
                headerViewHolder.mTitle.setText(listItem.getTitle());
                headerViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mHeaderItemClickListener != null) {
                            HeaderListItem item = (HeaderListItem) getData().get(viewHolder.getAdapterPosition());
                            mHeaderItemClickListener.onClickHeaderItem(item);
                        }
                    }
                });

                break;
            case ListType.LIST_ITEM:
                ListItemViewHolder itemViewHolder = (ListItemViewHolder) viewHolder;
                ListItem item = (ListItem) getData().get(position);
                itemViewHolder.mItem = item;
                itemViewHolder.mTitle.setText(item.getTitle());
                itemViewHolder.mSubTitle.setText(item.getSubtitle());
                itemViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mListItemClickListener != null) {
                            ListItem item = (ListItem) getData().get(viewHolder.getAdapterPosition());
                            mListItemClickListener.onClickListItem(item);
                        }
                    }
                });
                break;
            default:
                super.onBindViewHolder(viewHolder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return getData().get(position).getItemType();
    }

    public interface OnListItemClickListener {
        void onClickListItem(ListItem listItem);
    }

    public interface OnHeaderItemClickListener {
        void onClickHeaderItem(HeaderListItem listItem);
    }
}
