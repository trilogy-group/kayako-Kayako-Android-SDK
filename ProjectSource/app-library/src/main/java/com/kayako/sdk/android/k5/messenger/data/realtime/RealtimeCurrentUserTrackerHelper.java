package com.kayako.sdk.android.k5.messenger.data.realtime;

import com.kayako.sdk.android.k5.common.activities.MessengerOpenTracker;
import com.kayako.sdk.android.k5.core.MessengerUserPref;

import java.util.concurrent.atomic.AtomicBoolean;

public class RealtimeCurrentUserTrackerHelper {

    private static final AtomicBoolean sIsTrackingUser = new AtomicBoolean(false);

    private static RealtimeUserHelper.UserPresenceListener userPresenceListener = new RealtimeUserHelper.UserPresenceListener() {
        @Override
        public void onUserOnline(long userId) {
        }

        @Override
        public void onUserOffline(long userId) {
        }
    };

    private static MessengerOpenTracker.OnOpenMessengerListener sOnOpenMessengerListener = new MessengerOpenTracker.OnOpenMessengerListener() {
        @Override
        public void onOpenMessenger() {
            trackCurrentUserIfNotTrackedAlready();
        }
    };

    private static MessengerOpenTracker.OnCloseMessengerListener onCloseMessengerListener = new MessengerOpenTracker.OnCloseMessengerListener() {
        @Override
        public void onCloseMessenger() {
            closeAll();
        }
    };

    static {
        MessengerOpenTracker.addOnCloseMessengerListener(onCloseMessengerListener);
        MessengerOpenTracker.addOnOpenMessengerListener(sOnOpenMessengerListener);
    }

    private static void closeAll() {
        untrackCurrentUser();
        // DO NOT Remove OnOpenListener and OnCloseListener - it should continue tracking, even after multiple closes and opens of messenger
    }

    /**
     * Call this method wherever the current user is created for first time - eg: in New Conversation
     */
    public static void trackCurrentUserIfNotTrackedAlready() {
        synchronized (sIsTrackingUser) { // Ensure multiple calls don't cause race conditions
            if (!sIsTrackingUser.get()) {  // Ensure tracking is done only once

                if (MessengerUserPref.getInstance().getPresenceChannel() != null
                        && MessengerUserPref.getInstance().getUserId() != null) { // Ensure Current User info is available

                    RealtimeUserHelper.trackUser( // Track user
                            MessengerUserPref.getInstance().getPresenceChannel(),
                            MessengerUserPref.getInstance().getUserId(),
                            userPresenceListener);

                    sIsTrackingUser.set(true);
                }
            }
        }
    }

    public static void untrackCurrentUser(){
        RealtimeUserHelper.untrackUser(userPresenceListener);
        sIsTrackingUser.set(false);
    }
}
