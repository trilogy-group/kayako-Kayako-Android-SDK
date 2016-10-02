package com.kayako.sdk.android.k5.articlepage;

import com.kayako.sdk.android.k5.common.mvp.BasePresenter;
import com.kayako.sdk.android.k5.common.mvp.BaseView;
import com.kayako.sdk.helpcenter.articles.Article;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public interface ArticleContainerContract {

    interface View extends BaseView {

        void openSearchActivity();
    }

    interface Presenter extends BasePresenter<ArticleContainerContract.View> {

        void onClickSearch();
    }
}
