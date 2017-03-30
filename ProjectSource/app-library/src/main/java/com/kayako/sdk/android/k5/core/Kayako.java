package com.kayako.sdk.android.k5.core;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.webkit.URLUtil;

import com.kayako.sdk.android.k5.activities.KayakoHelpCenterActivity;
import com.kayako.sdk.android.k5.activities.KayakoMessengerActivity;

import java.lang.ref.WeakReference;
import java.util.Locale;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class Kayako {

    private static Kayako mInstance;
    private WeakReference<Context> mAppContext; // when the application is closed, this class will no longer be used

    private Kayako(Context mAppContext) {
        this.mAppContext = new WeakReference<>(mAppContext);
    }

    public static void initialize(Context applicationContext) {
        mInstance = new Kayako(applicationContext);
        HelpCenterPref.createInstance(applicationContext);
        MessengerPref.createInstance(applicationContext);
        MessengerStylePref.createInstance(applicationContext);
        MessengerUserPref.createInstance(applicationContext);
    }

    public static void clearCache() {
        HelpCenterPref.getInstance().clearAll();
        MessengerPref.getInstance().clearAll();
        MessengerStylePref.getInstance().clearAll();
        MessengerUserPref.getInstance().clearAll();
    }

    public static Kayako getInstance() {
        if (mInstance == null) {
            throw new NullPointerException("Please call Kayako.initialize() in your Application class");
        }
        return mInstance;
    }

    public static Context getApplicationContext() {
        return getInstance().mAppContext.get(); // assuming that when application is closed, this class will no longer be used
    }

    public void openHelpCenter(Context context, String helpCenterUrl, Locale defaultLocale) {
        setUpCommonCredentials(helpCenterUrl, defaultLocale);
        Intent intent = KayakoHelpCenterActivity.getIntent(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public MessengerBuilder getMessenger() {
        return new MessengerBuilder();
    }

    // TODO: Create a Messenger Builder - Builder pattern here
    // default visibility scope
    void openMessenger(Context context, String helpCenterUrl, Locale defaultLocale) {
        setUpCommonCredentials(helpCenterUrl, defaultLocale);
        Intent intent = KayakoMessengerActivity.getIntent(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    // TODO: Redo this to separate Messenger and Helpcenter
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
