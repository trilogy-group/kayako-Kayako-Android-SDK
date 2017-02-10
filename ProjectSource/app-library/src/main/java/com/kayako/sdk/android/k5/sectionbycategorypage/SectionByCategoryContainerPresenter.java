package com.kayako.sdk.android.k5.sectionbycategorypage;

import com.kayako.sdk.android.k5.common.adapter.spinnerlist.SpinnerItem;
import com.kayako.sdk.android.k5.common.utils.LocaleUtils;
import com.kayako.sdk.android.k5.core.HelpCenterPref;
import com.kayako.sdk.helpcenter.locale.Locale;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SectionByCategoryContainerPresenter implements SectionByCategoryContainerContract.Presenter {

    private SectionByCategoryContainerContract.View mView;
    private SectionByCategoryContainerContract.Data mData;

    private List<SpinnerItem> mSpinnerItems;
    private SpinnerItem mSelectedItem;
    // Ensure that during spinner setup, the page is not unnecessarily loaded due to spinner onItemSelected() being called.

    public SectionByCategoryContainerPresenter(SectionByCategoryContainerContract.View view, SectionByCategoryContainerContract.Data data) {
        mView = view;
        mData = data;
    }

    @Override
    public void initPage() {
        invalidateOldValues();
        mView.hideToolbarSpinner();
        mView.startBackgroundTask();
    }

    @Override
    public boolean loadDataInBackground() {
        try {
            List<com.kayako.sdk.helpcenter.locale.Locale> localeList = mData.getPublicLocales(true);
            if (localeList == null) {
                return false;
            }
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
                int position = getPositionOfDefaultLocale(mSpinnerItems);
                mSelectedItem = mSpinnerItems.get(position);
                mView.setToolbarSpinner(mSpinnerItems, position);
                mView.showToolbarSpinner();
                mView.hideToolbarTitle();
            }
        } else {
            mView.hideToolbarSpinner();
            mView.showToolbarTitle();
        }
    }

    private int getPositionOfDefaultLocale(List<SpinnerItem> items) {
        java.util.Locale defaultLocale = HelpCenterPref.getInstance().getLocale();

        int position = 0;
        for (SpinnerItem item : items) {
            if (LocaleUtils.areLocalesTheSame(((Locale) item.getResource()), defaultLocale)) {
                return position;
            }
            position++;
        }

        return 0;
    }

    @Override
    public void onSpinnerItemSelected(SpinnerItem spinnerItem) {
        Locale kayakoLocale = (Locale) spinnerItem.getResource();
        java.util.Locale selectedLocale = LocaleUtils.getLocale(kayakoLocale);

        HelpCenterPref.getInstance().setLocale(selectedLocale);

        if (!mSelectedItem.equals(spinnerItem)) { // Reload only if it's current locale has been changed
            mSelectedItem = spinnerItem;
            mView.reloadSectionsByCategory();
        }
    }

    @Override
    public void setData(SectionByCategoryContainerContract.Data data) {
        mData = data;
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
