package com.kayako.sdk.android.k5.common.task;

import android.app.Activity;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public abstract class BackgroundTask extends AsyncTask<Void, Void, Boolean> {

    WeakReference<Activity> mActivityReference;

    public BackgroundTask(Activity activity) {
        mActivityReference = new WeakReference<Activity>(activity);
    }

    @Override
    protected final Boolean doInBackground(Void... voids) {
        return performInBackground();
    }

    /**
     * Ensures that status is a boolean value (never null) and UI tasks will not run if the reference to the Activity is null
     * @param aBoolean
     */
    @Override
    protected final void onPostExecute(Boolean aBoolean) {
        if (aBoolean == null) {
            aBoolean = false;
        }

        if (mActivityReference.get() != null) {
            performOnCompletion(aBoolean);
        }

        mActivityReference = null;
    }

    /**
     * Performs the necessary checks ensuring the task is cancelled only if it can be cancelled
     */
    public void cancelTask() {
        if (!isCancelled() && getStatus() != Status.FINISHED) {
            cancel(true);
        }
    }

    protected abstract boolean performInBackground();

    protected abstract void performOnCompletion(boolean isSuccessful);
}

