package com.kayako.sdk.android.k5.sectionbycategorypage;

import java.util.Locale;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SectionByCategoryFactory {

    static SectionByCategoryContract.Presenter mPresenter;

    public static SectionByCategoryContract.Presenter getPresenter(SectionByCategoryContract.View view) {
        if (mPresenter == null) {
            return mPresenter = new SectionByCategoryPresenter(view);
        } else {
            mPresenter.setView(view);
            return mPresenter;
        }
    }

    public static SectionByCategoryContract.Data getDataSource(String helpCenterUrl, Locale locale) {
        return new SectionByCategoryRepository(helpCenterUrl, locale);
    }
}
