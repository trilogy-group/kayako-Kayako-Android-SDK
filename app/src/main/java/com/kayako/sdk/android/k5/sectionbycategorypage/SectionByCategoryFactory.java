package com.kayako.sdk.android.k5.sectionbycategorypage;

import java.util.Locale;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SectionByCategoryFactory {

    public static SectionByCategoryContract.Presenter getPresenter(SectionByCategoryContract.View view) {
        return new SectionByCategoryPresenter(view);
    }

    public static SectionByCategoryContract.Data getDataSource(String helpCenterUrl, Locale locale) {
        return new SectionByCategoryRepository(helpCenterUrl, locale);
    }
}
