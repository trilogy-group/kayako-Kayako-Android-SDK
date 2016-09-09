package com.kayako.sdk.android.k5.searcharticlepage;

import com.kayako.sdk.android.k5.common.data.ListItem;
import com.kayako.sdk.android.k5.common.mvp.BaseData;
import com.kayako.sdk.android.k5.common.mvp.BasePresenter;
import com.kayako.sdk.android.k5.common.mvp.BaseView;
import com.kayako.sdk.helpcenter.articles.Article;
import com.kayako.sdk.helpcenter.search.SearchArticle;

import java.util.List;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public interface SearchArticleContract {

    interface Data extends BaseData {
        List<SearchArticle> searchArticles(String query, int offset, int limit);
    }

    interface View extends BaseView {

        // change state methods

        void showBlankView();

        void showOnlyListView();

        void showOnlyEmptyView();

        void showOnlyErrorView();

        void showOnlyLoadingView();

        // show content methods

        void startSearchTask();

        void startLoadMoreTask();

        void cancelBackgroundTasks();

        void setUpList(List<ListItem> items);

        // Load more methods

        void addItemsToList(List<ListItem> items);

        void showLoadingMoreItemsProgress();

        void hideLoadingMoreItemsProgress();

        void setListHasMoreItems(boolean hasMoreItems);

    }

    interface Presenter extends BasePresenter<SearchArticleContract.View> {

        void initPage();

        void searchArticles(String query);

        boolean loadDataInBackground();

        void onDataLoaded(boolean isSuccessful);

        boolean loadMoreDataInBackground();

        void onMoreDataLoaded(boolean isSuccessful);

        void onClickListItem(ListItem listItem);

        void reloadPage();

        void loadMoreData();
    }
}
