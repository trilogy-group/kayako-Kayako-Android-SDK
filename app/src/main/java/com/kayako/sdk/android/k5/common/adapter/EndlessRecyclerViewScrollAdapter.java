package com.kayako.sdk.android.k5.common.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.data.LoadingItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public abstract class EndlessRecyclerViewScrollAdapter<T extends LoadingItem> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int STATE_LOAD_MORE = -10;

    protected List<T> mValues;
    private T mProgressItem = getLoadingFooterItem();
    private boolean mHasMoreItems = false;

    public EndlessRecyclerViewScrollAdapter(List<T> items) {
        mValues = items;
    }

    public List<T> getData() {
        return mValues;
    }

    public void setData(List<T> newData) {
        // TODO: Test
        if (newData == null) {
            newData = new ArrayList<>();
        }
        notifyItemRangeRemoved(0, mValues.size());
        mValues = newData;
        notifyItemRangeInserted(0, mValues.size());

    }

    public void addData(List<T> moreData) {
        int originalSize = mValues.size();
        mValues.addAll(moreData);
        int newSize = mValues.size();

        notifyItemRangeInserted(originalSize, newSize);
    }

    public abstract T getLoadingFooterItem();

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
     * @param hasMoreItems
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
            case STATE_LOAD_MORE:
                View viewHeader = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.ko__list_item_loading, parent, false);
                return new LoadingViewHolder(viewHeader);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case STATE_LOAD_MORE:
//                LoadingViewHolder headerViewHolder = (LoadingViewHolder) viewHolder;
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        LoadingItem item = mValues.get(position);
        if (item.isLoading()) {
            return STATE_LOAD_MORE;
        } else {
            return 0;
        }
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
