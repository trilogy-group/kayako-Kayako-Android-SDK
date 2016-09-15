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
    private boolean mShouldReloadSectionsByCategory; // Ensure that during spinner setup, the page is not unnecessarily loaded due to spinner onItemSelected() being called.

    public SectionByCategoryContainerPresenter(SectionByCategoryContainerContract.View view) {
        mView = view;
        mData = SectionByCategoryContainerFactory.getDataSource(HelpCenterPref.getInstance().getHelpCenterUrl(), HelpCenterPref.getInstance().getLocale());
    }

    @Override
    public void initPage() {
        mView.showToolbarTitle(); // TODO: Remove title idea. BAD.
        mView.hideToolbarSpinner();
        mView.startBackgroundTask();
    }

    @Override
    public boolean loadDataInBackground() {
        try {
            List<com.kayako.sdk.helpcenter.locale.Locale> localeList = mData.getPublicLocales(true);
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
                mShouldReloadSectionsByCategory = true;
            }
        } else {
            mView.hideToolbarSpinner();
            mView.showToolbarTitle();
        }
    }

    @Override
    public void onSpinnerItemSelected(SpinnerItem spinnerItem) {
        if(mShouldReloadSectionsByCategory) { // Reload only if it's not the first time.
            mView.reloadSectionsByCategory();
        }
    }

    @Override
    public void setView(SectionByCategoryContainerContract.View view) {
        mView = view;
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
