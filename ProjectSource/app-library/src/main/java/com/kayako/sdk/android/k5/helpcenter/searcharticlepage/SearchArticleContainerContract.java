package com.kayako.sdk.android.k5.helpcenter.searcharticlepage;

import com.kayako.sdk.android.k5.common.mvp.BasePresenter;
import com.kayako.sdk.android.k5.common.mvp.BaseView;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public interface SearchArticleContainerContract {

    interface View extends BaseView {

        void showLessCharactersTypedErrorMessage();

        void showSearchResults(String query);

        void clearSearchResults();
    }

    interface Presenter extends BasePresenter<SearchArticleContainerContract.View> {
        void onTextEntered(String query);

        void onEnterPressed(String query);
    }

}
