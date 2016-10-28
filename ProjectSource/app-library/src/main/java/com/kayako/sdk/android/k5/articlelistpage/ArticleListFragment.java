package com.kayako.sdk.android.k5.articlelistpage;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.activities.KayakoArticleActivity;
import com.kayako.sdk.android.k5.common.adapter.loadmorelist.EndlessRecyclerViewScrollAdapter;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.list.ListItemRecyclerViewAdapter;
import com.kayako.sdk.android.k5.common.adapter.list.ListItem;
import com.kayako.sdk.android.k5.common.adapter.sectioninfolist.SectionInfoAdapter;
import com.kayako.sdk.android.k5.common.fragments.BaseListFragment;
import com.kayako.sdk.android.k5.common.task.BackgroundTask;
import com.kayako.sdk.android.k5.common.utils.ViewUtils;
import com.kayako.sdk.helpcenter.articles.Article;
import com.kayako.sdk.helpcenter.section.Section;

import java.util.List;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class ArticleListFragment extends BaseListFragment implements ArticleListContract.View, EndlessRecyclerViewScrollAdapter.OnLoadMoreListener, ListItemRecyclerViewAdapter.OnListItemClickListener {

    public static final String ARG_SECTION = "section";

    private BackgroundTask mTaskToLoadData;
    private BackgroundTask mTaskToLoadMoreData;

    public static ArticleListFragment newInstance(Section section) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_SECTION, section);

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
        if (getArguments() != null && getArguments().containsKey(ARG_SECTION)) {
            mPresenter.initPage((Section) getArguments().getSerializable(ARG_SECTION));
        } else {
            ViewUtils.showToastMessage(getContext(), "Invalid arguments!", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cancelBackgroundTasks();
        mTaskToLoadData = null;
        mTaskToLoadMoreData = null;
    }

    @Override
    public void setUpList(List<BaseListItem> items, String title, String description) {
        SectionInfoAdapter sectionInfoAdapter = new SectionInfoAdapter(items, this, title, description);
        initList(sectionInfoAdapter, this);
    }

    @Override
    public void addItemsToList(List<BaseListItem> items) {
        super.addToList(items);
    }

    @Override
    public void showLoadingMoreItemsProgress() {
        super.showLoadMoreProgress();
    }

    @Override
    public void hideLoadingMoreItemsProgress() {
        super.hideLoadMoreProgress();
    }

    @Override
    public void setListHasMoreItems(boolean hasMoreItems) {
        super.setHasMoreItems(hasMoreItems);
    }

    protected void reloadPage() {
        mPresenter.reloadPage();
    }

    @Override
    public void startBackgroundTaskToLoadData() {
        cancelTask(mTaskToLoadData);

        mTaskToLoadData = (BackgroundTask) new BackgroundTask(getActivity()) {
            @Override
            protected boolean performInBackground() {
                return mPresenter.fetchDataInBackground();
            }

            @Override
            protected void performOnCompletion(boolean isSuccessful) {
                mPresenter.onDataLoaded(isSuccessful);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    @Override
    public void startBackgroundTaskToLoadMoreData() {
        mTaskToLoadMoreData = (BackgroundTask) new BackgroundTask(getActivity()) {
            @Override
            protected boolean performInBackground() {
                return mPresenter.fetchMoreDataInBackground();
            }

            @Override
            protected void performOnCompletion(boolean isSuccessful) {
                mPresenter.onMoreDataLoaded(isSuccessful);
            }
        }.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    @Override
    public void cancelBackgroundTasks() {
        cancelTask(mTaskToLoadData);
        cancelTask(mTaskToLoadMoreData);
    }


    @Override
    public void showOnlyListView() {
        super.showListViewAndHideOthers();
    }

    @Override
    public void showOnlyEmptyView() {
        super.showEmptyViewAndHideOthers(null, null);
    }

    @Override
    public void showOnlyErrorView() {
        super.showErrorViewAndHideOthers(null, null, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reloadPage();
            }
        });
    }

    @Override
    public void showOnlyLoadingView() {
        super.showLoadingViewAndHideOthers();
    }

    @Override
    public void loadMoreItems() {
        mPresenter.loadMoreData();
    }

    @Override
    public void onClickListItem(ListItem listItem) {
        mPresenter.onClickListItem(listItem);
    }

    @Override
    public void openArticleActivity(Article article) {
        startActivity(KayakoArticleActivity.getIntent(getContext(), article));
    }

    @Override
    public void showLoadMoreErrorMessage() {
        ViewUtils.showSnackBar(mRoot, getString(R.string.ko__msg_error_unable_to_load_more_items));
    }

    private void cancelTask(BackgroundTask task) {
        if (task != null) {
            task.cancelTask();
        }
    }

}
