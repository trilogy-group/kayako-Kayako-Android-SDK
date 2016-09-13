package com.kayako.sdk.android.k5.sectionbycategorypage;

import com.kayako.sdk.android.k5.common.core.HelpCenterPref;
import com.kayako.sdk.android.k5.common.data.SpinnerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SectionByCategoryContainerPresenter implements SectionByCategoryContainerContract.Presenter {

    private SectionByCategoryContainerContract.View mView;
    private SectionByCategoryContainerContract.Data mData;

    private List<SpinnerItem> mSpinnerItems;

    public SectionByCategoryContainerPresenter(SectionByCategoryContainerContract.View view) {
        mView = view;
        mData = SectionByCategoryContainerFactory.getDataSource(HelpCenterPref.getInstance().getHelpCenterUrl(), HelpCenterPref.getInstance().getLocale());
    }

    @Override
    public void initPage() {
        mView.showToolbarTitle();
        mView.hideToolbarSpinner();
        mView.startBackgroundTask();
    }

    @Override
    public boolean loadDataInBackground() {
        try {
            List<com.kayako.sdk.helpcenter.locale.Locale> localeList = mData.getPublicLocales();
            mSpinnerItems = convertToSpinnerItems(localeList);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void onDataLoaded(boolean isSuccessful) {
        if (isSuccessful) {
            if (mSpinnerItems.size() == 0) {
                mView.hideToolbarSpinner();
                mView.showToolbarTitle();
            } else {
                mView.setToolbarSpinner(mSpinnerItems);
                mView.showToolbarSpinner();
                mView.hideToolbarTitle();
            }
        } else {
            mView.hideToolbarSpinner();
            mView.showToolbarTitle();
        }
    }

    @Override
    public void onSpinnerItemSelected(SpinnerItem spinnerItem) {

    }

    @Override
    public void setView(SectionByCategoryContainerContract.View view) {

    }

    private List<SpinnerItem> convertToSpinnerItems(List<com.kayako.sdk.helpcenter.locale.Locale> locales) {
        List<SpinnerItem> items = new ArrayList<>();
        for (com.kayako.sdk.helpcenter.locale.Locale locale : locales) {
            SpinnerItem item = new SpinnerItem();
            item.setLabel(locale.getNativeName());
            item.setResource(locale);
            items.add(item);
        }
        return items;
    }
}
