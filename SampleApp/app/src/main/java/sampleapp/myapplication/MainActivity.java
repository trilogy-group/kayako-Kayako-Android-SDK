package sampleapp.myapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kayako.sdk.android.k5.core.Kayako;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = MainActivity.this;
        java.util.Locale locale = Locale.ENGLISH;
        String helpCenterUrl = "http://support.kayako.com";

        // Open Help Center using the following line - Kayako
        Kayako.getInstance().openHelpCenter(this, helpCenterUrl, locale);
    }
}
