package com.kayako.sdk.android.k5.articlepage;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class ArticleFactory {

    public static ArticlePageContract.Presenter getPresenter(ArticlePageContract.View view) {
        return new ArticlePresenter(view);
    }
}
