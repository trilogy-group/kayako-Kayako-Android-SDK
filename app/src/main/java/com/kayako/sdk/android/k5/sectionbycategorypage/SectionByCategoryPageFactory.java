package com.kayako.sdk.android.k5.sectionbycategorypage;

import java.util.Locale;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SectionByCategoryPageFactory {

    private static SectionByCategoryPresenter sSectionByCategoryPresenter;

    public static SectionByCategoryPageContract.Presenter getPresenter(SectionByCategoryPageContract.View view) {
        if (sSectionByCategoryPresenter == null) {
            return sSectionByCategoryPresenter = new SectionByCategoryPresenter(view);
        } else {
            sSectionByCategoryPresenter.setView(view);
            return sSectionByCategoryPresenter;
        }
    }

    public static SectionByCategoryPageContract.Data getDataSource(String helpCenterUrl, Locale locale) {
        return new SectionByCategoryRepository(helpCenterUrl, locale);
    }
}
