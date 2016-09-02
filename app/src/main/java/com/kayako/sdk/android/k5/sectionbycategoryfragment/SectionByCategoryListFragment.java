package com.kayako.sdk.android.k5.sectionbycategoryfragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.ui.BaseListFragment;
import com.kayako.sdk.android.k5.ui.ListItem;
import com.kayako.sdk.android.k5.ui.ListItemRecyclerViewAdapter;

import java.util.List;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SectionByCategoryListFragment extends BaseListFragment implements SectionByCategoryPageContract.View {

    protected SectionByCategoryPageContract.Presenter mPresenter;
    protected AsyncTask mBackgroundTask;

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
        mBackgroundTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mPresenter.loadDataInBackground();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mPresenter.onDataLoaded();
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
        initList(new ListItemRecyclerViewAdapter(items, new ListItemRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ListItem listItem) {
                mPresenter.onClickListItem(listItem);
            }
        }));
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
