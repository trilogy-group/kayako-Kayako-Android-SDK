package com.kayako.sdk.android.k5.helpcenter.sectionbycategorypage;

import com.kayako.sdk.android.k5.core.HelpCenterPref;

import java.util.Locale;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SectionByCategoryFactory {

    static SectionByCategoryContract.Presenter mPresenter;
    static SectionByCategoryContract.Data mData;

    public static SectionByCategoryContract.Presenter getPresenter(SectionByCategoryContract.View view) {
        if (mData == null || !mData.doHelpCenterPreferencesMatch()) {
            mData = SectionByCategoryFactory.getDataSource(HelpCenterPref.getInstance().getHelpCenterUrl(), HelpCenterPref.getInstance().getLocale());
        }

        if (mPresenter == null) {
            return mPresenter = new SectionByCategoryPresenter(view, mData);
        } else {
            mPresenter.setView(view);
            mPresenter.setData(mData);
            return mPresenter;
        }
    }

    public static SectionByCategoryContract.Data getDataSource(String helpCenterUrl, Locale locale) {
        return new SectionByCategoryRepository(helpCenterUrl, locale);
    }
}
