package com.kayako.sdk.android.k5.common.adapter.loadmorelist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.ListType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public abstract class EndlessRecyclerViewScrollAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<BaseListItem> mValues;
    private LoadingItem mProgressItem = getLoadingFooterItem();
    private boolean mHasMoreItems = false;

    public EndlessRecyclerViewScrollAdapter(List<BaseListItem> items) {
        mValues = items;
    }

    public List<BaseListItem> getData() {
        return mValues;
    }

    public void setData(List<BaseListItem> newData) {
        if (newData == null) {
            newData = new ArrayList<>();
        }
        notifyItemRangeRemoved(0, mValues.size());
        mValues = newData;
        notifyItemRangeInserted(0, mValues.size());

    }

    public void addData(List<BaseListItem> moreData) {
        int originalSize = mValues.size();
        mValues.addAll(moreData);
        int newSize = mValues.size();

        notifyItemRangeInserted(originalSize, newSize);
    }

    private LoadingItem getLoadingFooterItem() {
        return new LoadingItem();
    }

    public void showLoadMoreProgress() {
        if (!mValues.contains(mProgressItem)) {
            mValues.add(mProgressItem);
            notifyItemInserted(mValues.size() - 1);
        }
    }

    public void hideLoadMoreProgress() {
        int position = mValues.indexOf(mProgressItem);
        if (position != -1) {
            mValues.remove(mProgressItem);
            notifyItemRemoved(position);
        }
    }

    /**
     * Use this function to enable/disable the progressbar at the bottom of the RecyclerView list.
     *
     * Once all items have been loaded, ensure that the
     *
     * @param hasMoreItems mark as true if it has more items
     */
    public void setHasMoreItems(boolean hasMoreItems) {
        mHasMoreItems = hasMoreItems;
    }

    public boolean hasMoreItems() {
        return mHasMoreItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ListType.LOADING_ITEM:
                View viewHeader = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.ko__list_item_loading, parent, false);
                return new LoadingViewHolder(viewHeader);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        // Nothing to bind here
    }

    @Override
    public int getItemViewType(int position) {
        return mValues.get(position).getItemType();
    }

    @Override
    public int getItemCount() {
        return getData().size();
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar mProgressBar;

        public LoadingViewHolder(View view) {
            super(view);
            mProgressBar = (ProgressBar) view.findViewById(R.id.ko__view_loading);
        }
    }

    public interface OnLoadMoreListener {
        void loadMoreItems();
    }
}
