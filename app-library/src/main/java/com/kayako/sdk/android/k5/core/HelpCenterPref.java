package com.kayako.sdk.android.k5.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.Locale;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class HelpCenterPref {
    final private String PREF_NAME = "kayako_help_center_info";
    final private String KEY_HELP_CENTER_URL = "help_center_url";
    final private String KEY_LOCALE = "locale";

    private static HelpCenterPref mInstance;
    private static SharedPreferences mPrefs;
    private Context mContext;

    public static void createInstance(Context context) {
        mInstance = new HelpCenterPref(context);
    }

    public static HelpCenterPref getInstance() {
        return mInstance;
        // TODO: Throw error?
    }

    private HelpCenterPref(Context context) {
        mContext = context.getApplicationContext();
        if (mPrefs == null) {
            mPrefs = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
    }

    // TODO: Validate URL provided by user?
    public void setHelpCenterUrl(String url) {
        mPrefs.edit().putString(KEY_HELP_CENTER_URL, url).apply();
    }

    public String getHelpCenterUrl() {
        // TODO: Testing
        return "https://support.kayako.com";
//        return mPrefs.getString(KEY_HELP_CENTER_URL, null);
        // TODO: Retrieve default help center url from, say, Android Manifest meta tag?
    }

    public void setLocale(Locale locale) {
        mPrefs.edit().putString(KEY_LOCALE, locale.getISO3Language()).apply();
    }

    public Locale getLocale() {
        String selectedLanguage = mPrefs.getString(KEY_LOCALE, null);

        if (TextUtils.isEmpty(selectedLanguage)) {
            return mContext.getResources().getConfiguration().locale;
        } else {
            return new Locale(selectedLanguage);
        }
    }
}
