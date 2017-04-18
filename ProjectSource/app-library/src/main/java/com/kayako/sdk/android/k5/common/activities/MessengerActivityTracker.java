package com.kayako.sdk.android.k5.common.activities;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MessengerActivityTracker {

    // TODO: Create a condition - "Tap again outside to close Messenger" so that accidental clicks won't trigger a close
    private final static List<WeakReference<BaseMessengerActivity>> openMessengerActivities = new ArrayList<>();
    private final static Set<OnCloseMessengerListener> onCloseMessengerListenerSet = new HashSet<>();

    public static void addActivity(final BaseMessengerActivity activity) {
        addToMessageQueue(new Runnable() {
            @Override
            public void run() {
                synchronized (openMessengerActivities) {
                    if (activity == null) {
                        return;
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
                    for (WeakReference weakReference : openMessengerActivities) {
                        if (weakReference == null  // Null list item
                                || weakReference.get() == null  // Null activity
                                || ((AppCompatActivity) weakReference.get()).isFinishing()) { // If the activity is finishing
                            openMessengerActivities.remove(weakReference);
                        }
                    }

                    callOnCloseMessengerListenerIfClosed();
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
                            AppCompatActivity appCompatActivity = (AppCompatActivity) weakReference.get();
                            if (!appCompatActivity.isFinishing()) {
                                appCompatActivity.finish();
                            }
                        }
                    }

                    openMessengerActivities.clear();

                    callOnCloseMessengerListenerIfClosed();
                }
            }
        });
    }

    /**
     * Use this to release resources on Messenger close
     *
     * @param listener
     */
    public static void addOnCloseMessengerListener(OnCloseMessengerListener listener) {
        synchronized (onCloseMessengerListenerSet) {
            onCloseMessengerListenerSet.add(listener);
        }
    }

    public static void removeOnCloseMessengerListener(OnCloseMessengerListener listener) {
        synchronized (onCloseMessengerListenerSet) {
            onCloseMessengerListenerSet.remove(listener);
        }
    }

    /**
     * This method is used so that the UI thread isn't blocked giving: java.lang.RuntimeException: Unable to destroy activity
     * Also, ensure every other function is synchronized!
     */
    private static void addToMessageQueue(final Runnable runnable) {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                runnable.run();
            }
        });
    }

    private static void callOnCloseMessengerListenerIfClosed() {
        synchronized (onCloseMessengerListenerSet) {
            if (openMessengerActivities.size() == 0) {
                for (OnCloseMessengerListener onCloseMessengerListener : onCloseMessengerListenerSet) {
                    onCloseMessengerListener.onCloseMessenger();
                }
            }
        }
    }

    public interface OnCloseMessengerListener {
        void onCloseMessenger();
    }
}
