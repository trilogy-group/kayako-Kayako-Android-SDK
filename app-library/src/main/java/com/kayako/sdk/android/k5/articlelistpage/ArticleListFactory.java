package com.kayako.sdk.android.k5.articlelistpage;

import java.util.Locale;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class ArticleListFactory {

    private static ArticleListContract.Presenter mPresenter;

    public static ArticleListContract.Presenter getPresenter(ArticleListContract.View view) {
        if (mPresenter == null) {
            return mPresenter = new ArticleListPresenter(view);
        } else {
            mPresenter.setView(view);
            return mPresenter;
        }
    }

    public static ArticleListContract.Data getDataSource(String helpCenterUrl, Locale locale) {
        return new ArticleListRepository(helpCenterUrl, locale);
    }
}
