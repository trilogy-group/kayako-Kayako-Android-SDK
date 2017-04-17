package com.kayako.sdk.android.k5.kre.helpers.presence;

import com.kayako.sdk.android.k5.kre.base.KreSubscription;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

public class KrePresencePushDataTypingHelper {

    // PUBLIC so that instances can be created to save state
    public KrePresencePushDataTypingHelper() {
    }

    private final static long FIVE_SECONDS = 5 * 1000;
    private final Object mKey = new Object();

    private long mLastTimePositiveTypingEventSent = 0L;
    private boolean mAutoDisableTyping;
    private TimerTask mTimerTask;


    public void triggerTypingEvent(final KreSubscription kreSubscription, final boolean isTyping, boolean autoDisableTyping) {
        // trigger typing event (whatever it may be) - should be called whenever something is typed
        KrePresencePushDataHelper.triggerTypingEvent(kreSubscription, isTyping);

        synchronized (mKey) {
            mAutoDisableTyping = autoDisableTyping;

            // Only perform autoDisable if isTyping = true and autoDisable is enabled
            if (isTyping && autoDisableTyping) {
                runAutoDisableTypingEvent(new WeakReference<>(kreSubscription));
            } else {
                mAutoDisableTyping = false; // disable if isTyping = false
            }
        }
    }

    private void runAutoDisableTypingEvent(final WeakReference<KreSubscription> kreSubscription) {
        // Save last time when isTyping = true was sent
        mLastTimePositiveTypingEventSent = System.currentTimeMillis();

        // Set up TimerTask
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                synchronized (mKey) {
                    // Assertions
                    if (kreSubscription == null || kreSubscription.get() == null // if no longer valid, cancel thread
                            || !mAutoDisableTyping // if autoDisableTyping is disasbled
                            || !kreSubscription.get().isConnected()) { // if kreSubscription is no longer properly connected
                        cancel();
                        return;
                    }

                    // Conditions
                    if (System.currentTimeMillis() - mLastTimePositiveTypingEventSent > FIVE_SECONDS) { // if it's been more than ten seconds since a isTyping=true has been sent
                        KrePresencePushDataHelper.triggerTypingEvent(kreSubscription.get(), false);
                    }
                }
            }
        };

        // TODO: Check if this involves heavy resource usage?
        Timer timer = new Timer(true);
        timer.schedule(mTimerTask, FIVE_SECONDS);
    }
}
