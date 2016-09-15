package com.kayako.sdk.android.k5.articlepage;

import com.kayako.sdk.helpcenter.articles.Article;
import com.kayako.sdk.helpcenter.category.Category;
import com.kayako.sdk.helpcenter.section.Section;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class ArticlePresenter implements ArticlePageContract.Presenter {

    private ArticlePageContract.View mView;

    public ArticlePresenter(ArticlePageContract.View view) {
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
        showOnlyLoading();
    }

    @Override
    public void onContentLoaded() {
        mView.hideContentScrollbarsWhileAllowingScroll();
        showOnlyArticleContent();
    }

    @Override
    public void setView(ArticlePageContract.View view) {
        mView = view;
    }

    private void showOnlyArticleContent(){
        mView.showArticleContent();
        mView.hideLoading();
    }

    private void showOnlyLoading(){
        mView.hideArticleContent();
        mView.showLoading();
    }
}
