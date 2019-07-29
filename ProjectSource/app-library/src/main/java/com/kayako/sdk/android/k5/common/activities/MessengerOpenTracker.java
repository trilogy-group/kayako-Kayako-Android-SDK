package com.kayako.sdk.android.k5.common.activities;

import android.os.Handler;

import java.util.HashSet;
import java.util.Set;

public class MessengerOpenTracker {

    private static final Set<OnCloseMessengerListener> onCloseMessengerListenerSet = new HashSet<>();
    private static final Set<OnOpenMessengerListener> onOpenMessengerListeners = new HashSet<>();

    /**
     * Use this to release resources on Messenger close
     *
     * @param listener
     */
    public static void addOnCloseMessengerListener(final OnCloseMessengerListener listener) {
        addToMessageQueue(new Runnable() {
            @Override
            public void run() {
                synchronized (onCloseMessengerListenerSet) {
                    onCloseMessengerListenerSet.add(listener);
                }
            }
        });
    }

    public static void removeOnCloseMessengerListener(final OnCloseMessengerListener listener) {
        addToMessageQueue(new Runnable() {
            @Override
            public void run() {
                synchronized (onCloseMessengerListenerSet) {
                    onCloseMessengerListenerSet.remove(listener);
                }
            }
        });
    }

    public static void addOnOpenMessengerListener(final OnOpenMessengerListener listener) {
        addToMessageQueue(new Runnable() {
            @Override
            public void run() {
                synchronized (onOpenMessengerListeners) {
                    onOpenMessengerListeners.add(listener);
                }
            }
        });
    }

    public static void removeOnOpenMessengerListener(final OnOpenMessengerListener listener) {
        addToMessageQueue(new Runnable() {
            @Override
            public void run() {
                synchronized (onOpenMessengerListeners) {
                    onOpenMessengerListeners.remove(listener);
                }
            }
        });
    }

    static void callOnCloseMessengerListener() {
        synchronized (onCloseMessengerListenerSet) {

            for (OnCloseMessengerListener onCloseMessengerListener : onCloseMessengerListenerSet) {
                onCloseMessengerListener.onCloseMessenger();
            }
        }
    }

    static void callOnOpenMessengerListeners() {
        synchronized (onOpenMessengerListeners) {
            for (OnOpenMessengerListener onOpenMessengerListener : onOpenMessengerListeners) {
                onOpenMessengerListener.onOpenMessenger();
            }
        }
    }


    /**
     * This method is used so that the UI thread isn't blocked giving: java.lang.RuntimeException: Unable to destroy activity
     * Also, ensure every other function is synchronized!
     */
    private static void addToMessageQueue(final Runnable runnable) {
        Handler handler = new Handler();
        handler.post(runnable);
    }

    public interface OnOpenMessengerListener {
        void onOpenMessenger();
    }

    public interface OnCloseMessengerListener {
        void onCloseMessenger();
    }

}
