package com.kayako.sdk.android.k5.articlelistpage;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.common.adapter.EndlessRecyclerViewScrollAdapter;
import com.kayako.sdk.android.k5.common.adapter.ListItemRecyclerViewAdapter;
import com.kayako.sdk.android.k5.common.data.ListItem;
import com.kayako.sdk.android.k5.common.fragments.BaseListFragment;

import java.util.List;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class ArticleListFragment extends BaseListFragment implements ArticleListContract.View, EndlessRecyclerViewScrollAdapter.OnLoadMoreListener, ListItemRecyclerViewAdapter.OnItemClickListener {

    public static final String ARG_SECTION_ID = "section-id";

    public static ArticleListFragment newInstance(long sectionId) {
        Bundle bundle = new Bundle();
        bundle.putLong(ARG_SECTION_ID, sectionId);

        ArticleListFragment articleListFragment = new ArticleListFragment();
        articleListFragment.setArguments(bundle);
        return articleListFragment;
    }

    public ArticleListFragment() {
    }

    ArticleListContract.Presenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = ArticleListFactory.getPresenter(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(ARG_SECTION_ID)) {
            mPresenter.initPage(getArguments().getLong(ARG_SECTION_ID));
        } else {
            // TODO: Throw Exception or close activity???
        }
    }

    @Override
    public void setUpList(List<ListItem> items) {
        ArticleListAdapter articleListAdapter = new ArticleListAdapter(items, this);
        initList(articleListAdapter, this);
    }

    @Override
    protected void reloadPage() {
        mPresenter.reloadPage();
    }

    @Override
    public void startBackgroundTask() {
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {
                return mPresenter.loadDataInBackground();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                mPresenter.onDataLoaded(aBoolean);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

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
    public void loadMoreItems() {
        // TODO
    }

    @Override
    public void onItemClick(ListItem listItem) {
        // TODO: Open Article List Page
    }
}
