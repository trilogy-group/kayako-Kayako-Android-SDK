package com.kayako.sdk.android.k5.articlepage;

import com.kayako.sdk.android.k5.common.mvp.BasePresenter;
import com.kayako.sdk.android.k5.common.mvp.BaseView;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public interface ArticlePageContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<ArticlePageContract.View> {

    }
}
