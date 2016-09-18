package com.kayako.sdk.android.k5.articlelistpage;

import java.util.Locale;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class ArticleListContainerFactory {

    private static ArticleListContainerContract.Presenter mPresenter;

    public static ArticleListContainerContract.Presenter getPresenter(ArticleListContainerContract.View view) {
        if (mPresenter == null) {
            return mPresenter = new ArticleListContainerPresenter(view);
        } else {
            mPresenter.setView(view);
            return mPresenter;
        }
    }

}
