package com.kayako.sdk.android.k5.sectionbycategorypage;

import java.util.Locale;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SectionByCategoryContainerFactory {

    static SectionByCategoryContainerContract.Presenter mPresenter;

    public static SectionByCategoryContainerContract.Presenter getPresenter(SectionByCategoryContainerContract.View view) {
        if (mPresenter == null) {
            return mPresenter = new SectionByCategoryContainerPresenter(view);
        } else {
            mPresenter.setView(view);
            return mPresenter;
        }
    }

    public static SectionByCategoryContainerContract.Data getDataSource(String helpCenterUrl, Locale locale) {
        return new SectionByCategoryContainerRepository(helpCenterUrl, locale);
    }
}
