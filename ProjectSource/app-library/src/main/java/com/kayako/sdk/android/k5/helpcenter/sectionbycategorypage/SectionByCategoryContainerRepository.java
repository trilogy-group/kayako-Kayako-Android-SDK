package com.kayako.sdk.android.k5.helpcenter.sectionbycategorypage;

import com.kayako.sdk.android.k5.core.HelpCenterPref;
import com.kayako.sdk.error.KayakoException;
import com.kayako.sdk.helpcenter.HelpCenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SectionByCategoryContainerRepository implements SectionByCategoryContainerContract.Data {

    private List<com.kayako.sdk.helpcenter.locale.Locale> mLocales = null;

    private HelpCenter mHelpCenter;
    private String mHelpCenterUrl;
    private Locale mCurrentLocale;

    public SectionByCategoryContainerRepository(String helpCenterUrl, Locale locale) {
        mHelpCenter = new HelpCenter(helpCenterUrl, locale);
        mHelpCenterUrl = helpCenterUrl;
        mCurrentLocale = locale;
    }

    @Override
    public List<com.kayako.sdk.helpcenter.locale.Locale> getPublicLocales(boolean useCacheIfAvailable) throws KayakoException{

        if (!useCacheIfAvailable || mLocales == null) {
            mLocales = mHelpCenter.getLocales();
        }

        if (mLocales == null || mLocales.size() == 0) {
            return mLocales;
        } else {
            List<com.kayako.sdk.helpcenter.locale.Locale> publicLocales = new ArrayList<>();
            for (com.kayako.sdk.helpcenter.locale.Locale locale : mLocales) {
                if (locale.isPublic()) {
                    publicLocales.add(locale);
                }
            }

            return publicLocales;
        }
    }

    public boolean isCached() {
        // Data is considered cached only if it's of the same help center url and locale
        return mLocales != null;
    }

    public boolean doHelpCenterPreferencesMatch() {
        return mHelpCenterUrl.equals(HelpCenterPref.getInstance().getHelpCenterUrl())
                && mCurrentLocale.equals(HelpCenterPref.getInstance().getLocale());
    }
}
