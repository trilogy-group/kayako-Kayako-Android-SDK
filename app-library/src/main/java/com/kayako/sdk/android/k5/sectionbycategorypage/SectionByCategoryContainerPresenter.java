package com.kayako.sdk.android.k5.sectionbycategorypage;

import com.kayako.sdk.android.k5.core.HelpCenterPref;
import com.kayako.sdk.android.k5.common.data.SpinnerItem;
import com.kayako.sdk.android.k5.common.utils.LocaleUtils;
import com.kayako.sdk.helpcenter.locale.Locale;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SectionByCategoryContainerPresenter implements SectionByCategoryContainerContract.Presenter {

    private SectionByCategoryContainerContract.View mView;
    private SectionByCategoryContainerContract.Data mData;
    private java.util.Locale mActiveLocale; // Ensure that during spinner setup, the page is not unnecessarily loaded due to spinner onItemSelected() being called.

    private List<SpinnerItem> mSpinnerItems;

    public SectionByCategoryContainerPresenter(SectionByCategoryContainerContract.View view) {
        mView = view;
        mData = SectionByCategoryContainerFactory.getDataSource(HelpCenterPref.getInstance().getHelpCenterUrl(), mActiveLocale = HelpCenterPref.getInstance().getLocale());
    }

    @Override
    public void initPage() {
        invalidateOldValues();
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
            }
        } else {
            mView.hideToolbarSpinner();
            mView.showToolbarTitle();
        }
    }

    @Override
    public void onSpinnerItemSelected(SpinnerItem spinnerItem) {
        Locale kayakoLocale = (Locale) spinnerItem.getResource();
        java.util.Locale selectedLocale = LocaleUtils.getLocale(kayakoLocale);
        if (!mActiveLocale.equals(selectedLocale)) { // Reload only if it's not the first time
            mView.reloadSectionsByCategory();
        }
    }

    @Override
    public void onClickContact() {
        mView.openContactPage();
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

    private void invalidateOldValues() {
        mSpinnerItems = null;
    }
}
