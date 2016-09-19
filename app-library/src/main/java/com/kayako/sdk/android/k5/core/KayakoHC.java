package com.kayako.sdk.android.k5.core;

import android.content.Context;
import android.content.Intent;

import com.kayako.sdk.android.k5.activities.KayakoHelpCenterActivity;

import java.util.Locale;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class KayakoHC {

    private Context mAppContext;
    private static KayakoHC mInstance;

    public static void initialize(Context applicationContext) {
        mInstance = new KayakoHC(applicationContext);
        HelpCenterPref.createInstance(applicationContext);
    }

    public KayakoHC(Context mAppContext) {
        this.mAppContext = mAppContext;
    }

    public static KayakoHC getInstance() {
        if (mInstance == null) {
            throw new NullPointerException("Please call KayakoHC.initialize() in your Application class");
        }
        return mInstance;
    }

    public void openHelpCenter(Context context, String helpCenterUrl, Locale defaultLocale) {
        HelpCenterPref.getInstance().setHelpCenterUrl(helpCenterUrl);
        HelpCenterPref.getInstance().setLocale(defaultLocale);
        context.startActivity(new Intent(context, KayakoHelpCenterActivity.class));
    }
}
