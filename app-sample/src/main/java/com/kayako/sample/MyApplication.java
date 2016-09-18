package com.kayako.sample;

import android.app.Application;

import com.kayako.sdk.android.k5.core.KayakoHC;

import java.util.Locale;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        KayakoHC.initialize(this, "https://support.kayakostage.net", new Locale("en", "us"));
    }
}
