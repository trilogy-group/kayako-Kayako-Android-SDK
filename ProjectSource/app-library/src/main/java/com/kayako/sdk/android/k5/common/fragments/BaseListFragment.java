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

    protected RecyclerView mRecyclerView;
    protected View mRoot;
    private EndlessRecyclerViewScrollAdapter mAdapter;
    private EndlessRecyclerViewScrollListener mScrollListener;

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
     * @param loadMoreListener
     */
    protected void initList(final EndlessRecyclerViewScrollAdapter adapter, final EndlessRecyclerViewScrollAdapter.OnLoadMoreListener loadMoreListener) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mRoot.getContext());
        mAdapter = adapter;

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true); // assuming the layout size of recyclerview does not change
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mScrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager, adapter) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Using a handler because it will cause IllegalStateException: Scroll callbacks should not be used to change the structure of the RecyclerView or the adapter contents.
                Handler loadMoreHandler = new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message inputMessage) {
                        loadMoreListener.loadMoreItems();
                    }
                };

                loadMoreHandler.sendEmptyMessage(0);
            }
        };

        if (loadMoreListener != null) { // only enable scroll listener if needed
            adapter.setHasMoreItems(true);
            mRecyclerView.addOnScrollListener(mScrollListener);
        }
    }

    protected void addToList(List<BaseListItem> items) {
        mAdapter.addData(items);
        mAdapter.hideLoadMoreProgress();
    }

    protected void showLoadMoreProgress() {
        mAdapter.showLoadMoreProgress();
    }

    protected void hideLoadMoreProgress() {
        mAdapter.hideLoadMoreProgress();
    }

    protected void setHasMoreItems(boolean hasMoreItems) {
        if (!hasMoreItems) {
            mRecyclerView.clearOnScrollListeners();
        }

        mAdapter.setHasMoreItems(hasMoreItems);
    }
}
