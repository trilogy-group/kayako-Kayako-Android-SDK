package com.kayako.sdk.android.k5.kre.helpers.presence;

import com.kayako.sdk.android.k5.kre.base.KreSubscription;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class KrePresencePushDataConservativelyHelper {

    private PerformTriggerOperationCallback mPerformTriggerOperationCallback;

    // PUBLIC so that instances can be created to save state
    public KrePresencePushDataConservativelyHelper(PerformTriggerOperationCallback performTriggerOperationCallback) {
        mPerformTriggerOperationCallback = performTriggerOperationCallback;
    }

    private final static long TWO_SECONDS = 2 * 1000;
    private final static long FIVE_SECONDS = 5 * 1000;

    private AtomicLong mLastTimeEventSent = new AtomicLong(0);
    private AtomicBoolean mLastStateSent = new AtomicBoolean(false);

    private boolean mAutoDisableEvent;
    private AtomicLong mLastTimePositiveAutoDisableEventTriggeredByClient = new AtomicLong(0); // measures client triggers - not events sent over socket
    private final Object mKey = new Object();
    private TimerTask mTimerTask;
    private Timer mTimer;

    public void triggerOperation(final KreSubscription kreSubscription, final boolean state, boolean autoDisableTriggerState) {
        // trigger state event (whatever it may be, eg: typing event) - should be called whenever the state is changed (eg: typing by user)
        triggerEventConservatively(kreSubscription, state);

        synchronized (mKey) {
            mAutoDisableEvent = autoDisableTriggerState;

            // Only perform autoDisable if state = true and autoDisable is enabled
            if (state && autoDisableTriggerState) {
                runAutoDisableStateEvent(new WeakReference<>(kreSubscription));
            } else {
                mAutoDisableEvent = false; // disable if state = false
            }
        }
    }

    private void runAutoDisableStateEvent(final WeakReference<KreSubscription> kreSubscription) {
        // Cancel running tasks when a new event is triggered
        if (mTimerTask != null && mTimer != null) {
            mTimer.cancel();
            mTimerTask.cancel();
        }

        // The last time when the calling class calls the triggerEvent() with state (eg: isTyping) = true
        mLastTimePositiveAutoDisableEventTriggeredByClient.set(System.currentTimeMillis());

        // Set up TimerTask
        mTimer = new Timer(true);
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                synchronized (mKey) {
                    // Assertions
                    if (kreSubscription == null || kreSubscription.get() == null // if no longer valid, cancel thread
                            || !mAutoDisableEvent // if autoDisableEvent is disasbled
                            || !kreSubscription.get().isConnected()) { // if kreSubscription is no longer properly connected
                        cancel();
                        return;
                    }

                    // Conditions
                    if (System.currentTimeMillis() - mLastTimePositiveAutoDisableEventTriggeredByClient.get() > FIVE_SECONDS) { // if it's been more than FIVE_SECONDS since a state (eg: isTyping) =true has been sent
                        triggerEventConservatively(kreSubscription.get(), false);
                    }
                }
            }
        };

        mTimer.schedule(mTimerTask, FIVE_SECONDS);
    }

    /**
     * This method has been added to handle state events like Typing events.
     * <p>
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
     * @param state
     * @return
     */
    private boolean hasSameEventBeenSentRecently(final boolean state) {
        return mLastStateSent.get() == state && // Same event
                System.currentTimeMillis() - mLastTimeEventSent.get() <= TWO_SECONDS; // sent within 2 seconds
    }

    private boolean triggerEventConservatively(KreSubscription kreSubscription, boolean state) {
        boolean triggerEvent = !hasSameEventBeenSentRecently(state);

        if (triggerEvent) {
            mPerformTriggerOperationCallback.performTriggerOperation(kreSubscription, state);

            mLastStateSent.set(state);
            mLastTimeEventSent.set(System.currentTimeMillis());
        }

        return triggerEvent;
    }

    public interface PerformTriggerOperationCallback {
        void performTriggerOperation(KreSubscription kreSubscription, boolean state);
    }
}
