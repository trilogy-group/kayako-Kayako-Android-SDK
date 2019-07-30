package com.kayako.sdk.android.k5.helpcenter.articlepage;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class ArticleContainerFactory {

    private static volatile ArticleContainerContract.Presenter mPresenter;

    public static ArticleContainerContract.Presenter getPresenter(ArticleContainerContract.View view) {
        if (mPresenter == null) {
            synchronized (ArticleContainerFactory.class) {
                if (mPresenter == null) {
                    return mPresenter = new ArticleContainerPresenter(view);
                } else {
                    mPresenter.setView(view);
                    return mPresenter;
                }
            }
        }
    }
}
