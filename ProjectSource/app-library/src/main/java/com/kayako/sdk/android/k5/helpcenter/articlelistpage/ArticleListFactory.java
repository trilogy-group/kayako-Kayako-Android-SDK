package com.kayako.sdk.android.k5.helpcenter.articlelistpage;

import com.kayako.sdk.android.k5.core.HelpCenterPref;

import java.util.Locale;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class ArticleListFactory {

    private static ArticleListContract.Presenter mPresenter;
    private static ArticleListContract.Data mData;

    public static ArticleListContract.Presenter getPresenter(ArticleListContract.View view) {
        if (mData == null || !mData.doesHelpCenterPreferencesMatch()) {
            mData = ArticleListFactory.getDataSource(HelpCenterPref.getInstance().getHelpCenterUrl(), HelpCenterPref.getInstance().getLocale());
        }

        if (mPresenter == null) {
            return mPresenter = new ArticleListPresenter(view, mData);
        } else {
            mPresenter.setView(view);
            mPresenter.setData(mData);
            return mPresenter;
        }
    }

    public static ArticleListContract.Data getDataSource(String helpCenterUrl, Locale locale) {
        return new ArticleListRepository(helpCenterUrl, locale);
    }
}
