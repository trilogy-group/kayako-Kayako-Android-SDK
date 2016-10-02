package com.kayako.sdk.android.k5.common.utils;

import com.kayako.sdk.helpcenter.locale.Locale;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class LocaleUtils {

    public static java.util.Locale getLocale(Locale kayakoLocale) {
        String language = getLanguage(kayakoLocale);
        String region = kayakoLocale.getRegion();

        if (region == null) {
            return new java.util.Locale(language);
        } else {
            return new java.util.Locale(language, region);
        }
    }

    private static String getLanguage(Locale kayakoLocale) {
        String locale = kayakoLocale.getLocale();

        String[] separators = new String[]{"_", "-"};
        for (String separator : separators) {
            if (locale.contains(separator)) {
                String[] parts = locale.split(separator);
                if (parts.length > 0) {
                    return parts[0];
                }
            }
        }
        return kayakoLocale.getLocale();
    }

    public static boolean areLocalesTheSame(Locale kayakoLocale, java.util.Locale secondLocale) {
        java.util.Locale firstLocale = getLocale(kayakoLocale);
        return firstLocale.equals(secondLocale);
    }
}
