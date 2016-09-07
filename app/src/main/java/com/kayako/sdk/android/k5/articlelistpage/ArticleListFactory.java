package com.kayako.sdk.android.k5.articlelistpage;

import java.util.Locale;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class ArticleListFactory {

    private static ArticleListContract.Presenter sPresenter;

    public static ArticleListContract.Presenter getPresenter(ArticleListContract.View view) {
        if (sPresenter == null) {
            return sPresenter = new ArticleListPresenter(view);
        } else {
            sPresenter.setView(view);
            return sPresenter;
        }
    }

    public static ArticleListContract.Data getDataSource(String helpCenterUrl, Locale locale) {
        return new ArticleListRepository(helpCenterUrl, locale);
    }
}
