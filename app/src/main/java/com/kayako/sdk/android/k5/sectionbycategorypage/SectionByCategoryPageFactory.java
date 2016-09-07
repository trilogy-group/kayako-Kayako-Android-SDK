package com.kayako.sdk.android.k5.sectionbycategorypage;

import java.util.Locale;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SectionByCategoryPageFactory {

    private static SectionByCategoryPresenter mSectionByCategoryPresenter;

    public static SectionByCategoryPageContract.Presenter getPresenter(SectionByCategoryPageContract.View view) {
        if (mSectionByCategoryPresenter == null) {
            return mSectionByCategoryPresenter = new SectionByCategoryPresenter(view);
        } else {
            mSectionByCategoryPresenter.setView(view);
            return mSectionByCategoryPresenter;
        }
    }

    public static SectionByCategoryPageContract.Data getDataSource(String helpCenterUrl, Locale locale) {
        return new SectionByCategoryRepository(helpCenterUrl, locale);
    }
}
