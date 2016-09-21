package com.kayako.sdk.android.k5.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kayako.sdk.android.k5.R;

public class KayakoSearchArticleActivity extends AppCompatActivity {

    public static Intent getIntent(Context context) {
        return new Intent(context, KayakoSearchArticleActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ko__activity_search_container);
    }
}
