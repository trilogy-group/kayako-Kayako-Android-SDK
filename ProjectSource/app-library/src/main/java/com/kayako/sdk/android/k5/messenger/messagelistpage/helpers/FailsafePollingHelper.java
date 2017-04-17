package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

public class FailsafePollingHelper {

    private static final long TIME_INTERVAL = 60 * 1000; // 1 minute in milliseconds
    private Timer mTimer;
    private Handler mHandler;

    public FailsafePollingHelper() {
    }

    public void startPolling(final PollingListener listener) {
        stopPolling();
        mTimer = new Timer();
        mHandler = new Handler();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                // Runs on UI Thread
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onPoll();
                    }
                });
            }
        };

        mTimer.schedule(timerTask, TIME_INTERVAL, TIME_INTERVAL);
    }

    public void stopPolling() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
            mHandler = null;
        }
    }

    public interface PollingListener {
        void onPoll();
    }
}
