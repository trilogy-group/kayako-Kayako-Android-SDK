package com.kayako.sdk.android.k5.common.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.loadmorelist.EndlessRecyclerViewScrollAdapter;
import com.kayako.sdk.android.k5.common.viewhelpers.CustomStateViewHelper;
import com.kayako.sdk.android.k5.common.viewhelpers.DefaultStateViewHelper;

import java.util.List;

public class TestBaseListFragment extends BaseListFragment {

    protected boolean isMethodSuccessfullyRun = false;

    public TestBaseListFragment(){
    }

    @Override
    public void setupView(View view) {
        super.setupView(view);
        isMethodSuccessfullyRun = Boolean.TRUE;
    }

    @Override
    public void showListView() {
        super.showListView();
        isMethodSuccessfullyRun = Boolean.TRUE;
    }

    @Override
    public void hideListView() {
        super.hideListView();
        isMethodSuccessfullyRun = Boolean.TRUE;
    }

    @Override
    public void showEmptyViewAndHideOthers() {
        super.showEmptyViewAndHideOthers();
        isMethodSuccessfullyRun = Boolean.TRUE;
    }

    @Override
    public void showLoadingViewAndHideOthers() {
        super.showLoadingViewAndHideOthers();
        isMethodSuccessfullyRun = Boolean.TRUE;
    }

    @Override
    public void showErrorViewAndHideOthers() {
        super.showErrorViewAndHideOthers();
        isMethodSuccessfullyRun = Boolean.TRUE;
    }

    @Override
    public void showListViewAndHideOthers(){
        super.showListViewAndHideOthers();
        isMethodSuccessfullyRun = Boolean.TRUE;
    }

    @Override
    public void hideAll() {
        super.hideAll();
        isMethodSuccessfullyRun = Boolean.TRUE;
    }

    @Override
    public CustomStateViewHelper getCustomStateViewHelper() {
        return super.getCustomStateViewHelper();
    }

    @Override
    public DefaultStateViewHelper getDefaultStateViewHelper() {
        return super.getDefaultStateViewHelper();
    }

    @Override
    public void initList(final EndlessRecyclerViewScrollAdapter adapter) {
        super.initList(adapter);
        isMethodSuccessfullyRun = Boolean.TRUE;
    }

    @Override
    public void init(final EndlessRecyclerViewScrollAdapter adapter, final LinearLayoutManager layoutManager) {
        super.init(adapter, layoutManager);
        isMethodSuccessfullyRun = Boolean.TRUE;
    }

    @Override
    public void addItemsToEndOfList(List<BaseListItem> items) {
        super.addItemsToEndOfList(items);
        isMethodSuccessfullyRun = Boolean.TRUE;
    }

    @Override
    public void addItemsToBeginningOfList(List<BaseListItem> items) {
        super.addItemsToBeginningOfList(items);
        isMethodSuccessfullyRun = Boolean.TRUE;
    }

    @Override
    public void addItem(BaseListItem item, int position) {
        super.addItem(item, position);
        isMethodSuccessfullyRun = Boolean.TRUE;
    }

    @Override
    public void removeItem(int position) {
        super.removeItem(position);
        isMethodSuccessfullyRun = Boolean.TRUE;
    }

    @Override
    public void replaceItem(BaseListItem item, int position) {
        super.replaceItem(item, position);
        isMethodSuccessfullyRun = Boolean.TRUE;
    }

    public void scrollToEndOfList() {
        super.scrollToEndOfList();
        isMethodSuccessfullyRun = Boolean.TRUE;
    }

    @Override
    public void scrollToBeginningOfList() {
        super.scrollToBeginningOfList();
        isMethodSuccessfullyRun = Boolean.TRUE;
    }

    @Override
    public void scrollToPosition(final int position) {
        super.scrollToPosition(position);
        isMethodSuccessfullyRun = Boolean.TRUE;
    }

    @Override
    public int findFirstVisibleItemPosition() {
        return super.findFirstVisibleItemPosition();
    }

    @Override
    public int findLastVisibleItemPosition() {
        return super.findLastVisibleItemPosition();
    }

    @Override
    public int getSizeOfData() {
        return super.getSizeOfData();
    }

    @Override
    public void setScrollListener(RecyclerView.OnScrollListener onScrollListener) {
        super.setScrollListener(onScrollListener);
        isMethodSuccessfullyRun = Boolean.TRUE;
    }

    @Override
    public void removeScrollListener(RecyclerView.OnScrollListener onScrollListener) {
        super.removeScrollListener(onScrollListener);
        isMethodSuccessfullyRun = Boolean.TRUE;
    }

    @Override
    public void setLoadMoreListener(final EndlessRecyclerViewScrollAdapter.OnLoadMoreListener loadMoreListener) {
        super.setLoadMoreListener(loadMoreListener);
        isMethodSuccessfullyRun = Boolean.TRUE;
    }

    @Override
    public void removeLoadMoreListener() {
        super.removeLoadMoreListener();
        isMethodSuccessfullyRun = Boolean.TRUE;
    }

    @Override
    public void showLoadMoreProgress() {
        super.showLoadMoreProgress();
        isMethodSuccessfullyRun = Boolean.TRUE;
    }

    @Override
    public void hideLoadMoreProgress() {
        super.hideLoadMoreProgress();
        isMethodSuccessfullyRun = Boolean.TRUE;
    }

    @Override
    public void setHasMoreItems(boolean hasMoreItems) {
        super.setHasMoreItems(hasMoreItems);
        isMethodSuccessfullyRun = Boolean.TRUE;
    }

    @Override
    public boolean hasMoreItems(){
        return super.hasMoreItems();
    }

    @Override
    public void setOnListPageChangeStateListener(OnListPageStateChangeListener listPageChangeStateListener) {
        super.setOnListPageChangeStateListener(listPageChangeStateListener);
        isMethodSuccessfullyRun = Boolean.TRUE;
    }

    @Override
    public OnListPageStateChangeListener getOnListPageChangeStateListener() {
        return super.getOnListPageChangeStateListener();
    }
}
