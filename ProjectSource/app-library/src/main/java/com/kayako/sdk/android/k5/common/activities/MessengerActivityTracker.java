package com.kayako.sdk.android.k5.common.activities;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MessengerActivityTracker {

    // TODO?: Create a condition - "Tap again outside to close Messenger" so that accidental clicks won't trigger a close
    private final static List<WeakReference<BaseMessengerActivity>> openMessengerActivities = new ArrayList<>();

    public static void addActivity(final BaseMessengerActivity activity) {
        addToMessageQueue(new Runnable() {
            @Override
            public void run() {
                synchronized (openMessengerActivities) {
                    if (activity == null) {
                        return;
                    }

                    if (openMessengerActivities.size() == 0) { // if a new activity is added for first time (in synchronized block)
                        MessengerOpenTracker.callOnOpenMessengerListeners();
                    }

                    openMessengerActivities.add(new WeakReference<>(activity));
                    refreshList();
                }
            }
        });
    }

    public static void refreshList() {
        addToMessageQueue(new Runnable() {
            @Override
            public void run() {
                synchronized (openMessengerActivities) {

                    List<WeakReference<BaseMessengerActivity>> activitiesToRemove = new ArrayList<WeakReference<BaseMessengerActivity>>();

                    for (int i = 0; i < openMessengerActivities.size(); i++) {
                        if (openMessengerActivities.get(i) == null  // Null list item
                                || openMessengerActivities.
                                get(i).get() == null  // Null activity
                                || (openMessengerActivities.get(i).get()).isFinishing()) { // If the activity is finishing
                            activitiesToRemove.add(openMessengerActivities.get(i));
                        }
                    }

                    openMessengerActivities.removeAll(activitiesToRemove);

                    if (openMessengerActivities.size() == 0) { // no messenger pages are open - therefore closed
                        MessengerOpenTracker.callOnCloseMessengerListener();
                    }
                }
            }
        });
    }

    public static void finishAllActivities() {
        addToMessageQueue(new Runnable() {
            @Override
            public void run() {
                synchronized (openMessengerActivities) {
                    for (WeakReference weakReference : openMessengerActivities) {
                        if (weakReference != null && weakReference.get() != null) {
                            BaseMessengerActivity baseMessengerActivity = (BaseMessengerActivity) weakReference.get();
                            if (!baseMessengerActivity.isFinishing()) {
                                baseMessengerActivity.finishFinal();
                            }
                        }
                    }

                    openMessengerActivities.clear();

                    if (openMessengerActivities.size() == 0) { // no messenger pages are open - therefore closed
                        MessengerOpenTracker.callOnCloseMessengerListener();
                    }
                }
            }
        });
    }

    /**
     * This method is used so that the UI thread isn't blocked giving: java.lang.RuntimeException: Unable to destroy activity
     * Also, ensure every other function is synchronized!
     */
    private static void addToMessageQueue(final Runnable runnable) {
        Handler handler = new Handler();
        handler.post(runnable);
    }
}
