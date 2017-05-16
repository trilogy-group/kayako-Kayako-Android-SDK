package com.kayako.sdk.android.k5.common.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.View;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.core.KayakoLogHelper;
import com.kayako.sdk.android.k5.messenger.data.realtime.RealtimeConversationHelper;
import com.kayako.sdk.android.k5.messenger.data.realtime.RealtimeCurrentUserTrackerHelper;

public abstract class BaseMessengerActivity extends AppCompatActivity {

    private static final String TAG = "BaseMessengerActivity";

    private static final String FRAGMENT_TAG = "fragment_tag";
    private Fragment mRetainedFragment;

    @Override
    final protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ko__activity_messenger_default);

        loadRelevantStaticClasses();
        MessengerActivityTracker.addActivity(this);

        mRetainedFragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (mRetainedFragment == null) {
            Fragment fragment = getContainerFragment();
            if (fragment == null) {
                throw new IllegalStateException("Invalid fragment returned in abstract method getContainerFragment()");
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(
                            R.id.ko__fragment_container,
                            fragment,
                            FRAGMENT_TAG)
                    .commitAllowingStateLoss();
        }
    }


    @Override
    final protected void onStart() {
        super.onStart();
        // should be called after setContentView()
        setupFloatingViewFunctionality();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (isFinishing()) {
            // we will not need this fragment anymore, this may also be a good place to signal to the retained fragment object to perform its own cleanup.
            if (mRetainedFragment != null) {
                getSupportFragmentManager().beginTransaction().remove(mRetainedFragment).commit();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MessengerActivityTracker.refreshList();
    }


    private void setupFloatingViewFunctionality() {
        findViewById(R.id.ko__space_to_close_messenger).setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                finishAllActivitiesOfTask();
                return true;
            }
        });

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

    protected abstract Fragment getContainerFragment();

    @Override
    public void finish() {
        super.finish();
        overridePendingTransitionExit(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransitionExit(this);
    }


    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransitionEnter(this);
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected static void overridePendingTransitionEnter(AppCompatActivity activity) {
        activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected static void overridePendingTransitionEnter(Activity activity) {
        activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    protected static void overridePendingTransitionExit(AppCompatActivity activity) {
        activity.overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

}
