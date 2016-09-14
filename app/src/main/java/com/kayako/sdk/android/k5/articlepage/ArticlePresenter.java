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
        mView.setArticleDirectoryPath(category.getTitle() + " > " + section.getTitle());
        mView.hideArticleContent();
        mView.setArticleContent(article.getContents());
    }

    @Override
    public void onContentLoaded() {
        mView.hideContentScrollbarsWhileAllowingScroll();
        mView.showArticleContent();
    }

    @Override
    public void setView(ArticlePageContract.View view) {
        mView = view;
    }
}
