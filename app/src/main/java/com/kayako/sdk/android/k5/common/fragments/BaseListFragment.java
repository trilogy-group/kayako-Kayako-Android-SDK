package com.kayako.sdk.android.k5.common.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.EndlessRecyclerViewScrollAdapter;
import com.kayako.sdk.android.k5.common.adapter.EndlessRecyclerViewScrollListener;
import com.kayako.sdk.android.k5.common.data.ListItem;

import java.util.List;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public abstract class BaseListFragment extends Fragment {

    protected View mRoot;
    protected RecyclerView mRecyclerView;
    private ViewStub mEmptyStubView;
    private ViewStub mErrorStubView;
    private ViewStub mLoadingStubView;
    private EndlessRecyclerViewScrollAdapter<ListItem> mAdapter;
    private EndlessRecyclerViewScrollListener mScrollListener;

    @Override
    final public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.ko__fragment_item_list, container, false);
        mEmptyStubView = (ViewStub) mRoot.findViewById(R.id.ko__stub_empty_state);
        mLoadingStubView = (ViewStub) mRoot.findViewById(R.id.ko__stub_loading_state);
        mErrorStubView = (ViewStub) mRoot.findViewById(R.id.ko__stub_error_state);
        mRecyclerView = (RecyclerView) mRoot.findViewById(R.id.ko__list);
        return mRoot;
    }

    protected abstract void reloadPage();

    protected void showListView() {
        View view = mRoot.findViewById(R.id.ko__list);
        view.setVisibility(View.VISIBLE);
    }

    protected void hideListView() {
        View view = mRoot.findViewById(R.id.ko__list);
        view.setVisibility(View.GONE);
    }

    protected void showEmptyView() {
        if (mEmptyStubView != null) {
            // After the stub is inflated, the stub is removed from the view hierarchy.
            mEmptyStubView.setVisibility(View.VISIBLE);
        }
        mRoot.findViewById(R.id.ko__view_state_empty).setVisibility(View.VISIBLE);
    }

    protected void hideEmptyView() {
        if (mEmptyStubView != null) {
            mEmptyStubView.setVisibility(View.GONE);
        }
        View inflatedView = mRoot.findViewById(R.id.ko__view_state_empty);
        if (inflatedView != null) {
            inflatedView.setVisibility(View.GONE);
        }
    }

    protected void showErrorView() {
        if (mErrorStubView != null) {
            mErrorStubView.setVisibility(View.VISIBLE);
        }
        mRoot.findViewById(R.id.ko__view_state_error).setVisibility(View.VISIBLE);
        Button retryButton = (Button) (mRoot.findViewById(R.id.ko__error_retry_button));
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reloadPage();
            }
        });
    }

    protected void hideErrorView() {
        if (mErrorStubView != null) {
            mErrorStubView.setVisibility(View.GONE);
        }

        View inflatedView = mRoot.findViewById(R.id.ko__view_state_error);
        if (inflatedView != null) {
            inflatedView.setVisibility(View.GONE);
        }
    }

    protected void showLoadingView() {
        if (mLoadingStubView != null) {
            mLoadingStubView.setVisibility(View.VISIBLE);
        }
        mRoot.findViewById(R.id.ko__view_state_loading).setVisibility(View.VISIBLE);
    }

    protected void hideLoadingView() {
        if (mLoadingStubView != null) {
            mLoadingStubView.setVisibility(View.GONE);
        }

        View inflatedView = mRoot.findViewById(R.id.ko__view_state_loading);
        if (inflatedView != null) {
            inflatedView.setVisibility(View.GONE);
        }
    }

    protected void showEmptyViewAndHideOthers() {
        showEmptyView();
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

    protected void showErrorViewAndHideOthers() {
        showErrorView();
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
     * <p>
     * If loadMoreListener is set as null, it implies that there is no more items to load when user scrolls to bottom of list (disable load more listener)
     *
     * @param adapter
     * @param loadMoreListener
     */
    protected void initList(final EndlessRecyclerViewScrollAdapter adapter, final EndlessRecyclerViewScrollAdapter.OnLoadMoreListener loadMoreListener) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mRoot.getContext());
        mAdapter = adapter;

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);
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
//                    adapter.setHasMoreItems(false); // TODO: TESTING
            }
        };

        if (loadMoreListener != null) { // only enable scroll listener if needed
            adapter.setHasMoreItems(true);
            mRecyclerView.addOnScrollListener(mScrollListener);
        }
    }

    protected void addToList(List<ListItem> items) {
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
