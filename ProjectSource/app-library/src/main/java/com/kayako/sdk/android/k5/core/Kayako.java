package com.kayako.sdk.android.k5.core;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.URLUtil;

import com.kayako.sdk.android.k5.activities.KayakoConversationListActivity;
import com.kayako.sdk.android.k5.activities.KayakoHelpCenterActivity;

import java.util.Locale;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class Kayako {

    private static Kayako mInstance;
    private Context mAppContext;

    private Kayako(Context mAppContext) {
        this.mAppContext = mAppContext;
    }

    public static void initialize(Context applicationContext) {
        mInstance = new Kayako(applicationContext);
        HelpCenterPref.createInstance(applicationContext);
        MessengerPref.createInstance(applicationContext);
    }

    public static Kayako getInstance() {
        if (mInstance == null) {
            throw new NullPointerException("Please call Kayako.initialize() in your Application class");
        }
        return mInstance;
    }

    public static Context getApplicationContext() {
        return getInstance().mAppContext;
    }

    public void openHelpCenter(Context context, String helpCenterUrl, Locale defaultLocale) {
        setUpCommonCredentials(helpCenterUrl, defaultLocale);
        context.startActivity(KayakoHelpCenterActivity.getIntent(context));
    }

    public void openMessenger(Context context, String helpCenterUrl, Locale defaultLocale) {
        setUpCommonCredentials(helpCenterUrl, defaultLocale);
        context.startActivity(KayakoConversationListActivity.getIntent(context));
    }

    private void setUpCommonCredentials(String helpCenterUrl, Locale defaultLocale) {
        if (!URLUtil.isValidUrl(helpCenterUrl)) {
            throw new IllegalArgumentException("Help Center Url provided is not valid");
        }

        if (TextUtils.isEmpty(helpCenterUrl) || defaultLocale == null) {
            throw new IllegalArgumentException("helpCenterUrl and defaultLocale are mandatory non-null arguments and need to be provided.");
        }

        HelpCenterPref.getInstance().setHelpCenterUrl(helpCenterUrl);
        HelpCenterPref.getInstance().setLocale(defaultLocale);
    }

}
