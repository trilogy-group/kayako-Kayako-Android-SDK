package com.kayako.sdk.android.k5.common.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.View;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.core.KayakoLogHelper;
import com.kayako.sdk.android.k5.messenger.data.realtime.RealtimeConversationHelper;
import com.kayako.sdk.android.k5.messenger.data.realtime.RealtimeCurrentUserTrackerHelper;

public class BaseMessengerActivity extends AppCompatActivity {

    private static final String TAG = "BaseMessengerActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadRelevantStaticClasses();
        MessengerActivityTracker.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MessengerActivityTracker.refreshList();
    }

    @Override
    final protected void onStart() {
        super.onStart();
        // should be called after setContentView()
        setupFloatingViewFunctionality();
    }

    private void setupFloatingViewFunctionality() {
        findViewById(R.id.ko__messenger_custom_foreground).setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                finishAllActivitiesOfTask();
                return true;
            }
        });

        // TODO: Causing problems with the toolbar
        findViewById(R.id.ko__space_to_close_messenger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAllActivitiesOfTask();
            }
        });
    }

    private void finishAllActivitiesOfTask() {
        MessengerActivityTracker.finishAllActivities();
    }

    private void loadRelevantStaticClasses() {
        // Force following static classes to initialize so that the code in the static block is called before MessengerActivityTracker is called
        try {
            Class.forName(RealtimeCurrentUserTrackerHelper.class.getName());
        } catch (ClassNotFoundException e) {
            KayakoLogHelper.printStackTrace(TAG, e);
        }

        try {
            Class.forName(RealtimeConversationHelper.class.getName());
        } catch (ClassNotFoundException e) {
            KayakoLogHelper.printStackTrace(TAG, e);
        }

    }
}
