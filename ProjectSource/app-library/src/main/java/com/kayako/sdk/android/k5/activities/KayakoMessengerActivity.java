package com.kayako.sdk.android.k5.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.activities.BaseMessengerActivity;

public class KayakoMessengerActivity extends BaseMessengerActivity {

    public static Intent getIntent(Context context) {
        return new Intent(context, KayakoMessengerActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ko__activity_messenger_home);
    }
}
