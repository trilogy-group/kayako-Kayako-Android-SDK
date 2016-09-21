package com.kayako.sdk.android.k5.articlelistpage;

import com.kayako.sdk.android.k5.common.data.ListItem;
import com.kayako.sdk.android.k5.common.mvp.BaseData;
import com.kayako.sdk.android.k5.common.mvp.BasePresenter;
import com.kayako.sdk.android.k5.common.mvp.BaseView;
import com.kayako.sdk.helpcenter.articles.Article;
import com.kayako.sdk.helpcenter.section.Section;

import java.util.List;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public interface ArticleListContract {

    interface Data extends BaseData {
        List<Article> getArticles(long sectionId, int offset, int limit, boolean useCache);

        boolean isCached(long sectionId);

        boolean doesHelpCenterPreferencesMatch();
    }

    interface View extends BaseView {

        void setUpList(List<ListItem> items, String title, String description);

        void showOnlyListView();

        void showOnlyEmptyView();

        void showOnlyErrorView();

        void showOnlyLoadingView();

        void startBackgroundTaskToLoadData();

        void startBackgroundTaskToLoadMoreData();

        void addItemsToList(List<ListItem> items);

        void showLoadingMoreItemsProgress();

        void hideLoadingMoreItemsProgress();

        void setListHasMoreItems(boolean hasMoreItems);

        void openArticleActivity(Article article);
    }

    interface Presenter extends BasePresenter<ArticleListContract.View> {

        void setData(Data data);

        void initPage(long sectionId);

        void initPage(Section section);

        boolean fetchDataInBackground();

        void onDataLoaded(boolean isSuccessful);

        boolean fetchMoreDataInBackground();

        void onMoreDataLoaded(boolean isSuccessful);

        void onClickListItem(ListItem listItem);

        void reloadPage();

        void loadMoreData();
    }

}
