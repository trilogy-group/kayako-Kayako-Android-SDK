package com.kayako.sdk.android.k5.helpcenter.articlepage;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class ArticleFactory {

    public static ArticleContract.Presenter getPresenter(ArticleContract.View view) {
        return new ArticlePresenter(view);
    }
}
