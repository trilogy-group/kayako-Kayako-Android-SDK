package com.kayako.sdk.android.k5.common.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.loadmorelist.EndlessRecyclerViewScrollAdapter;
import com.kayako.sdk.android.k5.common.adapter.loadmorelist.EndlessRecyclerViewScrollListener;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;

import java.util.List;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public abstract class BaseListFragment extends BaseStateFragment {

    protected View mRoot;
    protected RecyclerView mRecyclerView;
    private EndlessRecyclerViewScrollAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private EndlessRecyclerViewScrollListener mLoadMoreListener;

    @Override
    final public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.ko__fragment_list, container, false);
        mRecyclerView = (RecyclerView) mRoot.findViewById(R.id.ko__list);
        super.initStateViews(mRoot);
        return mRoot;
    }

    protected void showListView() {
        View view = mRoot.findViewById(R.id.ko__list);
        view.setVisibility(View.VISIBLE);
    }

    protected void hideListView() {
        View view = mRoot.findViewById(R.id.ko__list);
        view.setVisibility(View.GONE);
    }

    protected void showEmptyViewAndHideOthers(@Nullable String title, @Nullable String description) {
        showEmptyView(title, description);
        hideErrorView();
        hideLoadingView();
        hideListView();
    }

    protected void showLoadingViewAndHideOthers() {
        showLoadingView();
        hideEmptyView();
        hideErrorView();
        hideListView();
    }

    protected void showErrorViewAndHideOthers(@Nullable String title, @Nullable String description, @NonNull View.OnClickListener onClickRetryListener) {
        showErrorView(title, description, onClickRetryListener);
        hideEmptyView();
        hideLoadingView();
        hideListView();
    }

    protected void showListViewAndHideOthers() {
        showListView();
        hideEmptyView();
        hideErrorView();
        hideLoadingView();
    }

    /**
     * Initialize the RecyclerView, Adapter and ScrollListener.
     * <p/>
     * If loadMoreListener is set as null, it implies that there is no more items to load when user scrolls to bottom of list (disable load more listener)
     *
     * @param adapter
     */
    protected void initList(final EndlessRecyclerViewScrollAdapter adapter) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mRoot.getContext());
        init(adapter, linearLayoutManager);
    }

    protected void init(final EndlessRecyclerViewScrollAdapter adapter, final LinearLayoutManager layoutManager) {
        assert adapter != null;
        assert layoutManager != null;

        mAdapter = adapter;
        mLayoutManager = layoutManager;

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true); // assuming the layout size of recyclerview does not change
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    protected void addItemsToEndOfList(List<BaseListItem> items) {
        mAdapter.addLoadMoreData(items);
        mAdapter.hideLoadMoreProgress();
    }

    protected void addItemsToBeginningOfList(List<BaseListItem> items) {
        mAdapter.addItems(items, 0);
        mAdapter.hideLoadMoreProgress();
    }

    protected void addItem(BaseListItem item, int position) {
        mAdapter.addItem(item, position);
    }

    protected void removeItem(int position) {
        mAdapter.removeItem(position);
    }

    protected void replaceItem(BaseListItem item, int position) {
        mAdapter.replaceItem(item, position);
    }

    /**
     * Smooth scroll to the end of list
     */
    protected void scrollToEndOfList() {
        assert mAdapter.getData().size() > 0;

        mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount());
    }

    /**
     * Smooth scroll to the beginning of list
     */
    protected void scrollToBeginningOfList() {
        assert mAdapter.getData().size() > 0;

        mRecyclerView.smoothScrollToPosition(0);
    }


    /**
     * Set OnScrollListener
     *
     * @param onScrollListener
     */
    protected void setScrollListener(RecyclerView.OnScrollListener onScrollListener) {
        assert mRecyclerView != null;
        assert onScrollListener != null;

        mRecyclerView.addOnScrollListener(onScrollListener);
    }

    /**
     * Add OnScrollListener
     *
     * @param onScrollListener
     */
    protected void removeScrollListener(RecyclerView.OnScrollListener onScrollListener) {
        assert mRecyclerView != null;

        mRecyclerView.removeOnScrollListener(onScrollListener);
    }

    protected void setLoadMoreListener(final EndlessRecyclerViewScrollAdapter.OnLoadMoreListener loadMoreListener) {
        assert mLayoutManager != null;
        assert mAdapter != null;
        assert mRecyclerView != null;
        assert loadMoreListener != null;

        mLoadMoreListener = new EndlessRecyclerViewScrollListener(mLayoutManager, mAdapter) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Using a handler because it will cause IllegalStateException: Scroll callbacks should not be used to change the structure of the RecyclerView or the adapter contents.
                Handler loadMoreHandler = new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message inputMessage) {
                        synchronized (this) {
                            loadMoreListener.loadMoreItems();
                        }
                    }
                };

                loadMoreHandler.sendEmptyMessage(0);
            }
        };

        mAdapter.setHasMoreItems(true);
        mRecyclerView.addOnScrollListener(mLoadMoreListener);
    }

    protected void removeLoadMoreListener() {
        assert mLoadMoreListener != null;
        assert mRecyclerView != null;
        assert mAdapter != null;

        mAdapter.setHasMoreItems(false);
        mRecyclerView.removeOnScrollListener(mLoadMoreListener);
    }


    protected void showLoadMoreProgress() {
        mAdapter.showLoadMoreProgress();
    }

    protected void hideLoadMoreProgress() {
        mAdapter.hideLoadMoreProgress();
    }

    protected void setHasMoreItems(boolean hasMoreItems) {
        mAdapter.setHasMoreItems(hasMoreItems);
    }
}
