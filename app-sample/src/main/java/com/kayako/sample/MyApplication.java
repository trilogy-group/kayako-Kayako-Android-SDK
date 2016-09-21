package com.kayako.sample;

import android.app.Application;

import com.kayako.sdk.android.k5.core.Kayako;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Kayako.initialize(this);
    }
}
