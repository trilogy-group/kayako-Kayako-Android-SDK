package com.kayako.sdk.android.k5.articlelistpage;

import java.util.Locale;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class ArticleListFactory {

    public static ArticleListContract.Presenter getPresenter(ArticleListContract.View view) {
        return new ArticleListPresenter(view);
    }

    public static ArticleListContract.Data getDataSource(String helpCenterUrl, Locale locale) {
        return new ArticleListRepository(helpCenterUrl, locale);
    }
}
