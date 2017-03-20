package com.kayako.sdk.android.k5.common.activities;

import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MessengerActivityTracker {

    // TODO: Create a condition - "Tap again outside to close Messenger" so that accidental clicks won't trigger a close

    private static List<WeakReference<BaseMessengerActivity>> openMessengerActivities = new ArrayList<>();

    public static synchronized void addActivity(BaseMessengerActivity activity) {
        openMessengerActivities.add(new WeakReference<>(activity));
        refreshList();
    }

    public static synchronized void refreshList() {
        for (WeakReference weakReference : openMessengerActivities) {
            if (weakReference == null  // Null list item
                    || weakReference.get() == null  // Null activity
                    || ((AppCompatActivity) weakReference.get()).isFinishing()) { // If the activity is finishing
                openMessengerActivities.remove(weakReference);
            }
        }
    }

    public static synchronized void finishAllActivities() {
        for (WeakReference weakReference : openMessengerActivities) {
            if (weakReference != null && weakReference.get() != null) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) weakReference.get();
                if (!appCompatActivity.isFinishing()) {
                    appCompatActivity.finish();
                }
            }
        }
        openMessengerActivities.clear();
    }
}
