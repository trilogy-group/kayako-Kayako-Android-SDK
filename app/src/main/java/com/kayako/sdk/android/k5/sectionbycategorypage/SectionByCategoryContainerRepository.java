package com.kayako.sdk.android.k5.sectionbycategorypage;

import com.kayako.sdk.helpcenter.HelpCenter;
import com.kayako.sdk.helpcenter.category.Category;
import com.kayako.sdk.helpcenter.section.Section;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SectionByCategoryContainerRepository implements SectionByCategoryContainerContract.Data {

    private List<com.kayako.sdk.helpcenter.locale.Locale> mLocales = null;

    private HelpCenter mHelpCenter;

    public SectionByCategoryContainerRepository(String helpCenterUrl, Locale locale) {
        mHelpCenter = new HelpCenter(helpCenterUrl, locale);
    }

    // TODO: Force Refresh? Caching?

    @Override
    public List<com.kayako.sdk.helpcenter.locale.Locale> getPublicLocales() {
        List<com.kayako.sdk.helpcenter.locale.Locale> locales = mHelpCenter.getLocales();
        if (locales == null || locales.size() == 0) {
            return locales;
        } else {
            List<com.kayako.sdk.helpcenter.locale.Locale> publicLocales = new ArrayList<>();
            for (com.kayako.sdk.helpcenter.locale.Locale locale : locales) {
                if (locale.isPublic()) {
                    publicLocales.add(locale);
                }
            }

            return publicLocales;
        }
    }
}
