package com.kayako.sdk.android.k5.common.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.View;

import com.kayako.sdk.android.k5.R;

public class BaseMessengerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    final protected void onStart() {
        super.onStart();
        // should be called after setContentView()
        setupFloatingViewFunctionality();
    }

    private void setupFloatingViewFunctionality() {
        View backgroundLayer = findViewById(R.id.ko__messenger_background_layer);
        backgroundLayer.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                finishAllActivitiesOfTask();
                return true;
            }
        });

        backgroundLayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAllActivitiesOfTask();
            }
        });
    }

    private void finishAllActivitiesOfTask() {
        finish();

        /*if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity(); // close all activities
        } else {
            finish();
        }*/
    }
}
