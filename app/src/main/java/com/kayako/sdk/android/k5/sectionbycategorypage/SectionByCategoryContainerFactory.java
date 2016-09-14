package com.kayako.sdk.android.k5.sectionbycategorypage;

import java.util.Locale;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SectionByCategoryContainerFactory {


    public static SectionByCategoryContainerContract.Presenter getPresenter(SectionByCategoryContainerContract.View view) {
        return new SectionByCategoryContainerPresenter(view);
    }

    public static SectionByCategoryContainerContract.Data getDataSource(String helpCenterUrl, Locale locale) {
        return new SectionByCategoryContainerRepository(helpCenterUrl, locale);
    }
}
