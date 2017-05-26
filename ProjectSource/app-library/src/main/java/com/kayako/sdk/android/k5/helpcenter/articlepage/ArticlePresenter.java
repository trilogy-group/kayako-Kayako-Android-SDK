package com.kayako.sdk.android.k5.helpcenter.articlepage;

import com.kayako.sdk.helpcenter.articles.Article;
import com.kayako.sdk.helpcenter.category.Category;
import com.kayako.sdk.helpcenter.section.Section;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class ArticlePresenter implements ArticleContract.Presenter {

    private ArticleContract.View mView;

    public ArticlePresenter(ArticleContract.View view) {
        mView = view;
    }

    @Override
    public void initPage(Article article) {
        Section section = article.getSection();
        Category category = section.getCategory();

        mView.setAuthorName(article.getAuthor().getFullName());
        mView.setAuthorAvatar(article.getAuthor().getAvatarUrl());
        mView.setArticleTitle(article.getTitle());
        mView.setArticleDirectoryPath(String.format("%s > %s", category.getTitle(), section.getTitle()));
        mView.setArticleContent(article.getContents());
        mView.setArticleLastUpdated(String.format("Updated %s", mView.formatTime(article.getLastUpdated())));
        mView.setArticleLastPosted(String.format("Posted %s", mView.formatTime(article.getLastPosted())));
        showOnlyLoading();
    }

    @Override
    public void onContentLoaded() {
        mView.hideContentScrollbarsWhileAllowingScroll();
        showOnlyArticleContent();
    }

    @Override
    public void onClickLinkInArticle(String url) {
        mView.openUrlIntent(url);
    }

    @Override
    public void onFailureToLoadContent() {
        mView.showFailedToLoadErrorMessage();
    }

    @Override
    public void setView(ArticleContract.View view) {
        mView = view;
    }

    private void showOnlyArticleContent() {
        mView.showArticleContent();
        mView.hideLoading();
    }

    private void showOnlyLoading() {
        mView.hideArticleContent();
        mView.showLoading();
    }
}
