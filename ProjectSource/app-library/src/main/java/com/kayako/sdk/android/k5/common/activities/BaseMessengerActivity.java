package com.kayako.sdk.android.k5.common.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ko__activity_messenger_default);

        /*
        Do not mark onCreate() as final as later versions of Android may generate code for it causing conflicts when this library is integrated into other projects
        More Info: https://commonsware.com/blog/2015/10/14/linkageerror-and-your-android-code.html
        */

        // Ensure the screen is always in portrait mode for Messenger - choosing to do this programmatically so that developers can't change the behaviour
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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

        setAnimationDuration();
    }


    @Override
    protected void onStart() {
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

    final public void finishFinal() {
        super.finish();
        overrideFinalPendingTransitionExit(this);
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent, getAnimation(this).toBundle());
    }

    protected static ActivityOptionsCompat getAnimation(Activity activity) {
        View backgroundView = activity.findViewById(R.id.ko__messenger_custom_background);
        View backButton = activity.findViewById(R.id.ko__messenger_toolbar_back_button);

        if (backgroundView != null && backButton == null) { // From Home Screen that has no back button
            // TODO: Commented out animation is the most meaningful transition - but it's looking odd. Not done right. Till I figure it out, let it fade
            // Pair<View, String> pairBackground = new Pair<>(backgroundView, "ko__messenger_background");
            //;, pairBackground);
            return ActivityOptionsCompat.makeSceneTransitionAnimation(activity);

        } else if (backgroundView != null) { // From Screens with Toolbars
            Pair<View, String> pairBackground = new Pair<>(backgroundView, "ko__messenger_background");
            Pair<View, String> pairBackButton = new Pair<>(backButton, "ko__messenger_toolbar_back_button");
            return ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairBackground, pairBackButton);

        } else { // Others
            return ActivityOptionsCompat.makeSceneTransitionAnimation(activity);
        }
    }

    protected void setAnimationDuration() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            this.getWindow().setSharedElementEnterTransition(new ChangeBounds().setDuration(200));
        }
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected static void overrideFinalPendingTransitionEnter(AppCompatActivity activity) {
        activity.overridePendingTransition(R.anim.slide_from_top, R.anim.slide_to_bottom);
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected static void overrideFinalPendingTransitionEnter(Activity activity) {
        activity.overridePendingTransition(R.anim.slide_from_top, R.anim.slide_to_bottom);
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    protected static void overrideFinalPendingTransitionExit(AppCompatActivity activity) {
        activity.overridePendingTransition(R.anim.slide_from_bottom, R.anim.slide_to_top);
    }

}
