package sampleapp.myapplication;

import android.app.Application;

import com.kayako.sdk.android.k5.core.Kayako;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class MySampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // You need to initialize Kayako with the Application context
        Kayako.initialize(this);
    }
}
