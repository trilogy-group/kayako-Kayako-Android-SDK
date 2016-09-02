package com.kayako.sdk.android.k5.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;

import java.util.List;

public class ListItemRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int STATE_HEADER = 0;
    private static final int STATE_ITEM = 1;

    private final List<ListItem> mValues;
    private OnItemClickListener mListener;

    public ListItemRecyclerViewAdapter(List<ListItem> items, OnItemClickListener listener) {
        mValues = items;
        mListener = listener;
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
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {

        switch (viewHolder.getItemViewType()) {
            case STATE_HEADER:
                HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;
                headerViewHolder.mItem = mValues.get(position);
                headerViewHolder.mTitle.setText(mValues.get(position).getTitle());
                break;
            case STATE_ITEM:
                ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
                itemViewHolder.mItem = mValues.get(position);
                itemViewHolder.mTitle.setText(mValues.get(position).getTitle());
                itemViewHolder.mSubTitle.setText(mValues.get(position).getSubtitle());
                break;
        }

        ViewHolder commonViewHolder = (ViewHolder) viewHolder;
        commonViewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(mValues.get(viewHolder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        ListItem item = mValues.get(position);
        if (item.isHeader()) {
            return STATE_HEADER;
        } else {
            return STATE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
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
