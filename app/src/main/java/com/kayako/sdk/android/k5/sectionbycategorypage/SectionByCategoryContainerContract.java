package com.kayako.sdk.android.k5.sectionbycategorypage;

import com.kayako.sdk.android.k5.common.data.SpinnerItem;
import com.kayako.sdk.android.k5.common.mvp.BaseData;
import com.kayako.sdk.android.k5.common.mvp.BasePresenter;
import com.kayako.sdk.android.k5.common.mvp.BaseView;
import com.kayako.sdk.helpcenter.locale.Locale;

import java.util.List;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public interface SectionByCategoryContainerContract {

    interface Data extends BaseData {
        List<Locale> getPublicLocales(boolean useCache);
    }

    interface View extends BaseView {
        void setToolbarSpinner(List<SpinnerItem> items);

        void showToolbarSpinner();

        void hideToolbarSpinner();

        void showToolbarTitle();

        void hideToolbarTitle();

        void setToolbarTitle(String title);

        void reloadSectionsByCategory();

        void startBackgroundTask();
    }

    interface Presenter extends BasePresenter<SectionByCategoryContainerContract.View> {
        void initPage();

        boolean loadDataInBackground();

        void onDataLoaded(boolean isSuccessful);

        void onSpinnerItemSelected(SpinnerItem spinnerItem);
    }
}
