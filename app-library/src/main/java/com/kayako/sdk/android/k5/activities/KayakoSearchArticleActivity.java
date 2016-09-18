package com.kayako.sdk.android.k5.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.core.HelpCenterPref;

public class KayakoSearchArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HelpCenterPref.createInstance(getApplicationContext()); // TODO: Replace with instructions for the user to add this to his/her own Application Class
        setContentView(R.layout.ko__activity_search_container);
    }
}
