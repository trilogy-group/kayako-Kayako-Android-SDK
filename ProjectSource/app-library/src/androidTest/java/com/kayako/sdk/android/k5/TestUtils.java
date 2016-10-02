package com.kayako.sdk.android.k5;

import android.app.Activity;
import android.content.pm.ActivityInfo;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class TestUtils {

    public static void changeOrientationToLandscapeMode(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public static void changeOrientationToPortraiteMode(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

}
