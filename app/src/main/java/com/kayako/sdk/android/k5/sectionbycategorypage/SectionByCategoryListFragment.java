package com.kayako.sdk.android.k5.sectionbycategorypage;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.common.adapter.ListItemRecyclerViewAdapter;
import com.kayako.sdk.android.k5.common.data.ListItem;
import com.kayako.sdk.android.k5.common.fragments.BaseListFragment;
import com.kayako.sdk.android.k5.common.task.BackgroundTask;
import com.kayako.sdk.android.k5.welcomepage.ActivityNavigationInterface;

import java.util.List;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SectionByCategoryListFragment extends BaseListFragment implements SectionByCategoryPageContract.View, ListItemRecyclerViewAdapter.OnItemClickListener, SearchSectionAdapter.OnSearchClickListener {

    protected SectionByCategoryPageContract.Presenter mPresenter;
    protected AsyncTask mBackgroundTask;
    protected ListItemRecyclerViewAdapter listItemRecyclerViewAdapter;
    protected ActivityNavigationInterface mActivityNavigation;

    public static SectionByCategoryListFragment newInstance() {
        return new SectionByCategoryListFragment();
    }

    public SectionByCategoryListFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivityNavigation = ((ActivityNavigationInterface) getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = SectionByCategoryPageFactory.getPresenter(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.initPage();
    }

    @Override
    public void startBackgroundTask() {
        // Cancel pending tasks before starting new ones
        if (mBackgroundTask != null && !mBackgroundTask.isCancelled()) {
            mBackgroundTask.cancel(true);
        }

        mBackgroundTask = new BackgroundTask(getActivity()) {
            @Override
            protected boolean performInBackground() {
                return mPresenter.loadDataInBackground();
            }

            @Override
            protected void performOnCompletion(boolean isSuccessful) {
                mPresenter.onDataLoaded(isSuccessful);
            }

        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void openArticleListingPage(long sectionId) {
        mActivityNavigation.openNextPage(sectionId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mBackgroundTask != null && !mBackgroundTask.isCancelled()) {
            mBackgroundTask.cancel(true);
            mBackgroundTask = null;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivityNavigation = null;
    }

    @Override
    public void setUpList(final List<ListItem> items) {
        listItemRecyclerViewAdapter = new SearchSectionAdapter(items, this, this);

// TODO: Testing load more - not required for this page
//        EndlessRecyclerViewScrollAdapter.OnLoadMoreListener loadMoreListener = new EndlessRecyclerViewScrollAdapter.OnLoadMoreListener() {
//            @Override
//            public void loadMoreItems() {
//                // Show Progresss
//                listItemRecyclerViewAdapter.showLoadMoreProgress();
//
//                 Run Background Thread // TODO: Rearrange properly
//                new AsyncTask<Void, Void, Void>() {
//                    @Override
//                    protected Void doInBackground(Void... voids) {
//                        try {
//                            Thread.sleep(4000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        return null;
//                    }
//
//                    @Override
//                    protected void onPostExecute(Void aVoid) {
//                        super.onPostExecute(aVoid);
//
//                        List<ListItem> items = new ArrayList<>();
//                        items.add(new ListItem(false, "More Data 1", "Data", null));
//                        items.add(new ListItem(false, "More Data 2", "Data", null));
//                        items.add(new ListItem(false, "More Data 3", "Data", null));
//
//                        listItemRecyclerViewAdapter.hideLoadMoreProgress();
//                        listItemRecyclerViewAdapter.addData(items);
//                        // TODO: Error, items are added as duplicates - Fixed by stopping onLoadMore() after first successful load
//                    }
//                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//            }
//        };

        initList(listItemRecyclerViewAdapter, null);
    }

    @Override
    public void showOnlyListView() {
        showListViewAndHideOthers();
    }

    @Override
    public void showOnlyEmptyView() {
        showEmptyViewAndHideOthers();
    }

    @Override
    public void showOnlyErrorView() {
        showErrorViewAndHideOthers();
    }

    @Override
    public void showOnlyLoadingView() {
        showLoadingViewAndHideOthers();
    }

    @Override
    protected void reloadPage() {
        mPresenter.reloadPage();
    }

    @Override
    public void onItemClick(ListItem listItem) {
        mPresenter.onClickListItem(listItem);
    }

    @Override
    public void onClickSearch() {
        mPresenter.onClickSearch();
    }
}
