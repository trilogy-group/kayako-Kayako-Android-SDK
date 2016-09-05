package com.kayako.sdk.android.k5.sectionbycategoryfragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.ui.fragments.BaseListFragment;
import com.kayako.sdk.android.k5.ui.adapter.EndlessRecyclerViewScrollAdapter;
import com.kayako.sdk.android.k5.ui.data.ListItem;
import com.kayako.sdk.android.k5.ui.adapter.ListItemRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SectionByCategoryListFragment extends BaseListFragment implements SectionByCategoryPageContract.View {

    protected SectionByCategoryPageContract.Presenter mPresenter;
    protected AsyncTask mBackgroundTask;
    protected ListItemRecyclerViewAdapter listItemRecyclerViewAdapter;

    public static SectionByCategoryListFragment newInstance() {
        SectionByCategoryListFragment fragment = new SectionByCategoryListFragment();
        return fragment;
    }

    public SectionByCategoryListFragment() {
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
        mBackgroundTask = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                return mPresenter.loadDataInBackground();
            }

            @Override
            protected void onPostExecute(Boolean isSuccessful) {
                super.onPostExecute(isSuccessful);
                if (isSuccessful == null) {
                    isSuccessful = false;
                }
                mPresenter.onDataLoaded(isSuccessful);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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
    public void setUpList(final List<ListItem> items) {
        listItemRecyclerViewAdapter = new SearchSectionAdapter(items, new ListItemRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ListItem listItem) {
                mPresenter.onClickListItem(listItem);
            }
        }, new SearchSectionAdapter.OnSearchClickListener() {
            @Override
            public void onClickSearch() {
                mPresenter.onClickSearch();
            }
        });

        EndlessRecyclerViewScrollAdapter.OnLoadMoreListener loadMoreListener = new EndlessRecyclerViewScrollAdapter.OnLoadMoreListener() {
            @Override
            public void loadMoreItems() {
                // Show Progresss
                listItemRecyclerViewAdapter.showLoadMoreProgress();

//                 Run Background Thread // TODO: Rearrange properly
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);

                        List<ListItem> items = new ArrayList<>();
                        items.add(new ListItem(false, "More Data 1", "Data", null));
                        items.add(new ListItem(false, "More Data 2", "Data", null));
                        items.add(new ListItem(false, "More Data 3", "Data", null));

                        listItemRecyclerViewAdapter.hideLoadMoreProgress();
                        listItemRecyclerViewAdapter.addData(items);
                        // TODO: Error, items are added as duplicates
                    }
                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        };

        initList(listItemRecyclerViewAdapter, loadMoreListener);
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
}
