package com.kayako.sdk.android.k5.kre.helpers.presence;

import com.kayako.sdk.android.k5.kre.base.KreSubscription;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class KrePresencePushDataTypingHelper {

    // PUBLIC so that instances can be created to save state
    public KrePresencePushDataTypingHelper() {
    }

    private final static long TWO_SECONDS = 2 * 1000;
    private final static long FIVE_SECONDS = 5 * 1000;

    private AtomicLong mLastTimeTypingEventSent = new AtomicLong(0);
    private AtomicBoolean mLastTypingEventSent = new AtomicBoolean(false);

    private boolean mAutoDisableTyping;
    private AtomicLong mLastTimePositiveAutoDisableTypingEventTriggeredByClient = new AtomicLong(0); // measures client triggers - not events sent over socket
    private final Object mKey = new Object();
    private TimerTask mTimerTask;
    private Timer mTimer;

    public void triggerTypingEvent(final KreSubscription kreSubscription, final boolean isTyping, boolean autoDisableTyping) {
        // trigger typing event (whatever it may be) - should be called whenever something is typed
        triggerTypingEventConservatively(kreSubscription, isTyping);

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
        // Cancel running tasks when a new event is triggered
        if (mTimerTask != null && mTimer != null) {
            mTimer.cancel();
            mTimerTask.cancel();
        }

        // The last time when the calling class calls the triggerTypingEvent() with isTyping = true
        mLastTimePositiveAutoDisableTypingEventTriggeredByClient.set(System.currentTimeMillis());

        // Set up TimerTask
        mTimer = new Timer(true);
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
                    if (System.currentTimeMillis() - mLastTimePositiveAutoDisableTypingEventTriggeredByClient.get() > FIVE_SECONDS) { // if it's been more than FIVE_SECONDS since a isTyping=true has been sent
                        triggerTypingEventConservatively(kreSubscription.get(), false);
                    }
                }
            }
        };

        mTimer.schedule(mTimerTask, FIVE_SECONDS);
    }

    /**
     * Typing events are allowed to be triggered if
     * - the typing state has changed from the previously triggered event
     * - an event hasn't been sent in over 2 seconds
     * <p>
     * Reason:
     * 1. This is done to prevent multiple calls for the same event.
     * This is done to prevent multiple trigger threads getting executed leading to java.util.concurrent.RejectedExecutionException
     * <p>
     * 2. However, it should also ensure the events are sent regularly.
     * Since we're relying on sockets, an agent or customer may open the relevant page at any time. Therefore, regular sending of events are required so that new subscribers will also receive events.
     *
     * @param isTyping
     * @return
     */
    private boolean hasSameEventBeenSentRecently(final boolean isTyping) {
        return mLastTypingEventSent.get() == isTyping && // Same event
                System.currentTimeMillis() - mLastTimeTypingEventSent.get() <= TWO_SECONDS; // sent within 2 seconds
    }

    private boolean triggerTypingEventConservatively(KreSubscription kreSubscription, boolean isTyping) {
        boolean triggerEvent = !hasSameEventBeenSentRecently(isTyping);

        if (triggerEvent) {
            KrePresencePushDataHelper.triggerTypingEvent(kreSubscription, isTyping);

            mLastTypingEventSent.set(isTyping);
            mLastTimeTypingEventSent.set(System.currentTimeMillis());
        }

        return triggerEvent;
    }
}
