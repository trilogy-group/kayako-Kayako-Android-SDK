package com.kayako.sdk.android.k5.helpcenter.articlelistpage;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class ArticleListContainerFactory {

    private static volatile ArticleListContainerContract.Presenter mPresenter;

    public static ArticleListContainerContract.Presenter getPresenter(ArticleListContainerContract.View view) {
        if (mPresenter == null) {
            synchronized (ArticleListContainerFactory.class) {
                if (mPresenter == null) {
                    return mPresenter = new ArticleListContainerPresenter(view);
                } else {
                    mPresenter.setView(view);
                    return mPresenter;
                }
            }
        }
    }

}
