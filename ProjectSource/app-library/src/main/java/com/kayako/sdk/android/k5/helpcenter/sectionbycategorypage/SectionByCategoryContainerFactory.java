package com.kayako.sdk.android.k5.helpcenter.sectionbycategorypage;

import com.kayako.sdk.android.k5.core.HelpCenterPref;

import java.util.Locale;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SectionByCategoryContainerFactory {

    static volatile SectionByCategoryContainerContract.Presenter mPresenter;
    static SectionByCategoryContainerContract.Data mData;

    public static SectionByCategoryContainerContract.Presenter getPresenter(SectionByCategoryContainerContract.View view) {
        if (mData == null || !mData.doHelpCenterPreferencesMatch()) {
            mData = SectionByCategoryContainerFactory.getDataSource(HelpCenterPref.getInstance().getHelpCenterUrl(), HelpCenterPref.getInstance().getLocale());
        }
        if (mPresenter == null) {
            synchronized (SectionByCategoryContainerFactory.class) {
                if (mPresenter == null) {
                    return mPresenter = new SectionByCategoryContainerPresenter(view, mData);
                } else {
                    mPresenter.setView(view);
                    mPresenter.setData(mData);
                    return mPresenter;
                }
            }
        }
    }

    public static SectionByCategoryContainerContract.Data getDataSource(String helpCenterUrl, Locale locale) {
        return new SectionByCategoryContainerRepository(helpCenterUrl, locale);
    }
}
