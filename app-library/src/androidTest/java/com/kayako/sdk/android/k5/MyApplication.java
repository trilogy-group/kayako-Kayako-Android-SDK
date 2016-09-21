package com.kayako.sdk.android.k5;

import android.app.Application;

import com.kayako.sdk.android.k5.core.HelpCenterPref;
import com.kayako.sdk.android.k5.core.KayakoHC;

import java.util.Locale;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        KayakoHC.initialize(this);

        // Since OpenHelpCenter() is never called, set the HelpCenterPrefs before running tests
        HelpCenterPref.createInstance(this);
        HelpCenterPref.getInstance().setHelpCenterUrl("https://support.kayako.com");
        HelpCenterPref.getInstance().setLocale(Locale.ENGLISH);
    }
}
