package com.kayako.sdk.android.k5.helpcenter.searcharticlepage;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.mvp.BaseData;
import com.kayako.sdk.android.k5.common.mvp.BasePresenter;
import com.kayako.sdk.android.k5.common.mvp.BaseView;
import com.kayako.sdk.error.KayakoException;
import com.kayako.sdk.helpcenter.articles.Article;
import com.kayako.sdk.helpcenter.search.SearchArticle;

import java.util.List;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public interface SearchArticleContract {

    interface Data extends BaseData {
        List<SearchArticle> searchArticles(String query, int offset, int limit) throws KayakoException;
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

        void setUpList(List<BaseListItem> items);

        // Load more methods

        void addItemsToList(List<BaseListItem> items);

        void showLoadingMoreItemsProgress();

        void hideLoadingMoreItemsProgress();

        void setListHasMoreItems(boolean hasMoreItems);

        void showErrorToLoadMoreMessage();

        void openArticleActivity(Article article);
    }

    interface Presenter extends BasePresenter<SearchArticleContract.View> {

        void initPage();

        void searchArticles(String query);

        void clearSearchResults();

        boolean loadDataInBackground();

        void onDataLoaded(boolean isSuccessful);

        boolean loadMoreDataInBackground();

        void onMoreDataLoaded(boolean isSuccessful);

        void onClickListItem(BaseListItem listItem);

        void reloadPage();

        void loadMoreData();
    }
}
