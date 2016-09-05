package com.kayako.sdk.android.k5.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.ui.data.ListItem;

import java.util.List;

public class ListItemRecyclerViewAdapter extends EndlessRecyclerViewScrollAdapter<ListItem> {

    protected static final int STATE_HEADER = 0;
    protected static final int STATE_ITEM = 1;

    private OnItemClickListener mItemClickListener;

    public ListItemRecyclerViewAdapter(List<ListItem> items, OnItemClickListener listener) {
        super(items);
        mItemClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case STATE_HEADER:
                View viewHeader = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.ko__list_header, parent, false);
                return new HeaderViewHolder(viewHeader);
            case STATE_ITEM:
                View viewItem = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.ko__list_item_with_description, parent, false);
                return new ItemViewHolder(viewItem);
            default:
                return super.onCreateViewHolder(parent, viewType);
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case STATE_HEADER:
                HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;
                headerViewHolder.mItem = getData().get(position);
                headerViewHolder.mTitle.setText(getData().get(position).getTitle());
                headerViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mItemClickListener.onItemClick(getData().get(viewHolder.getAdapterPosition()));
                    }
                });

                break;
            case STATE_ITEM:
                ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
                itemViewHolder.mItem = getData().get(position);
                itemViewHolder.mTitle.setText(getData().get(position).getTitle());
                itemViewHolder.mSubTitle.setText(getData().get(position).getSubtitle());
                itemViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mItemClickListener.onItemClick(getData().get(viewHolder.getAdapterPosition()));
                    }
                });
                break;
            default:
                super.onBindViewHolder(viewHolder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        ListItem item = getData().get(position);
        if (item.isLoading()) {
            return super.getItemViewType(position);
        } else if (item.isHeader()) {
            return STATE_HEADER;
        } else {
            return STATE_ITEM;
        }
    }

    @Override
    public ListItem getLoadingFooterItem() {
        ListItem item = new ListItem(false, null, null, null);
        item.setLoading(true);
        return item;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public ListItem mItem;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class HeaderViewHolder extends ViewHolder {
        public final TextView mTitle;

        public HeaderViewHolder(View view) {
            super(view);
            mView = view;
            mTitle = (TextView) view.findViewById(R.id.ko__list_header_title);
        }
    }

    public class ItemViewHolder extends ViewHolder {
        public final TextView mTitle;
        public final TextView mSubTitle;

        public ItemViewHolder(View view) {
            super(view);
            mView = view;
            mTitle = (TextView) view.findViewById(R.id.ko__list_item_title);
            mSubTitle = (TextView) view.findViewById(R.id.ko__list_item_subtitle);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(ListItem listItem);
    }
}
