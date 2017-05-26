package com.kayako.sdk.android.k5.helpcenter.sectionbycategorypage;

import com.kayako.sdk.android.k5.common.adapter.spinnerlist.SpinnerItem;
import com.kayako.sdk.android.k5.common.mvp.BaseData;
import com.kayako.sdk.android.k5.common.mvp.BasePresenter;
import com.kayako.sdk.android.k5.common.mvp.BaseView;
import com.kayako.sdk.error.KayakoException;
import com.kayako.sdk.helpcenter.locale.Locale;

import java.util.List;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public interface SectionByCategoryContainerContract {

    interface Data extends BaseData {
        List<Locale> getPublicLocales(boolean useCache) throws KayakoException;

        boolean isCached();

        boolean doHelpCenterPreferencesMatch();
    }

    interface View extends BaseView {
        void setToolbarSpinner(List<SpinnerItem> items, int defaultPosition);

        void showToolbarSpinner();

        void hideToolbarSpinner();

        void showToolbarTitle();

        void hideToolbarTitle();

        void setToolbarTitle(String title);

        void reloadSectionsByCategory();

        void startBackgroundTask();

    }

    interface Presenter extends BasePresenter<SectionByCategoryContainerContract.View> {

        void setData(SectionByCategoryContainerContract.Data data);

        void initPage();

        boolean loadDataInBackground();

        void onDataLoaded(boolean isSuccessful);

        void onSpinnerItemSelected(SpinnerItem spinnerItem);

    }
}
