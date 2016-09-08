package com.kayako.sdk.android.k5.searcharticlepage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.kayako.sdk.android.k5.common.data.ListItem;
import com.kayako.sdk.android.k5.common.fragments.BaseListFragment;

import java.util.List;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SearchArticleResultFragment extends BaseListFragment implements SearchArticleContract.View {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showLoadingView();
    }

    @Override
    protected void reloadPage() {

    }

    @Override
    public void showOnlyListView() {

    }

    @Override
    public void showOnlyEmptyView() {

    }

    @Override
    public void showOnlyErrorView() {

    }

    @Override
    public void showOnlyLoadingView() {

    }

    @Override
    public void startBackgroundTask(String searchQuery) {

    }

    @Override
    public void cancelBackgroundTask() {

    }

    @Override
    public void setUpList(List<ListItem> items) {

    }

    @Override
    public void addItemsToList(List<ListItem> items) {

    }

    @Override
    public void showLoadingMoreItemsProgress() {

    }

    @Override
    public void hideLoadingMoreItemsProgress() {

    }

    @Override
    public void setListHasMoreItems(boolean hasMoreItems) {

    }
}
