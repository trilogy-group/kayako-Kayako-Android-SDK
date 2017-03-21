package com.kayako.sdk.android.k5.helpcenter.articlepage;

import com.kayako.sdk.android.k5.common.mvp.BasePresenter;
import com.kayako.sdk.android.k5.common.mvp.BaseView;
import com.kayako.sdk.helpcenter.articles.Article;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public interface ArticleContract {

    interface View extends BaseView {

        void setAuthorName(String name);

        void setAuthorAvatar(String avatarUrl);

        void setArticleTitle(String title);

        void setArticleDirectoryPath(String path);

        void setArticleContent(String htmlContent);

        void setArticleLastUpdated(String lastUpdated);

        void setArticleLastPosted(String lastPosted);

        String formatTime(long timeInMilliseconds);

        void showArticleContent();

        void hideArticleContent();

        void showLoading();

        void hideLoading();

        void hideContentScrollbarsWhileAllowingScroll();

        void openUrlIntent(String url);

        void showFailedToLoadErrorMessage();
    }

    interface Presenter extends BasePresenter<ArticleContract.View> {

        void initPage(Article article);

        void onContentLoaded();

        void onClickLinkInArticle(String url);

        void onFailureToLoadContent();
    }
}
