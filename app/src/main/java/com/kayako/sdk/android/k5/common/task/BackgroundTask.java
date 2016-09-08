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
    final protected Boolean doInBackground(Void... voids) {
        return performInBackground();
    }

    @Override
    final protected void onPostExecute(Boolean aBoolean) {
        if (aBoolean == null) {
            aBoolean = false;
        }

        if (mActivityReference.get() != null) {
            performOnCompletion(aBoolean);
        }

        mActivityReference = null;
    }

    protected abstract boolean performInBackground();

    protected abstract void performOnCompletion(boolean isSuccessful);
}

