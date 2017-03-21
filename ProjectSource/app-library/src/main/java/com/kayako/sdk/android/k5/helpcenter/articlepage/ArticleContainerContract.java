package com.kayako.sdk.android.k5.helpcenter.articlepage;

import com.kayako.sdk.android.k5.common.mvp.BasePresenter;
import com.kayako.sdk.android.k5.common.mvp.BaseView;

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
