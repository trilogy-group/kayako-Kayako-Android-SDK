package com.kayako.sdk.android.k5.sectionbycategorypage;

import java.util.Locale;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SectionByCategoryContainerFactory {

    private static SectionByCategoryContainerPresenter sSectionByCategoryContainerPresenter;

    public static SectionByCategoryContainerContract.Presenter getPresenter(SectionByCategoryContainerContract.View view) {
        if (sSectionByCategoryContainerPresenter == null) {
            return sSectionByCategoryContainerPresenter = new SectionByCategoryContainerPresenter(view);
        } else {
            sSectionByCategoryContainerPresenter.setView(view);
            return sSectionByCategoryContainerPresenter;
        }
    }

    public static SectionByCategoryContainerContract.Data getDataSource(String helpCenterUrl, Locale locale) {
        return new SectionByCategoryContainerRepository(helpCenterUrl, locale);
    }
}
