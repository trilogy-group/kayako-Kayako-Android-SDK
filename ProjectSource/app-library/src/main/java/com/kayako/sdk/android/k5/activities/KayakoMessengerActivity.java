package com.kayako.sdk.android.k5.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.activities.BaseMessengerActivity;
import com.kayako.sdk.android.k5.messenger.homescreenpage.HomeScreenContainerFragment;

public class KayakoMessengerActivity extends BaseMessengerActivity {

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, KayakoMessengerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        activity.startActivity(intent);
        overridePendingTransitionEnter(activity);
    }

    public static void startActivity(AppCompatActivity activity) {
        Intent intent = new Intent(activity, KayakoMessengerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        activity.startActivity(new Intent(activity, KayakoMessengerActivity.class));
        overridePendingTransitionEnter(activity);
    }

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
    protected Fragment getContainerFragment() {
        return new HomeScreenContainerFragment();
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected static void overridePendingTransitionEnter(AppCompatActivity activity) {
        activity.overridePendingTransition(R.anim.slide_from_top, R.anim.slide_to_bottom);
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected static void overridePendingTransitionEnter(Activity activity) {
        activity.overridePendingTransition(R.anim.slide_from_top, R.anim.slide_to_bottom);
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    protected static void overridePendingTransitionExit(AppCompatActivity activity) {
        activity.overridePendingTransition(R.anim.slide_from_bottom, R.anim.slide_to_top);
    }


}
