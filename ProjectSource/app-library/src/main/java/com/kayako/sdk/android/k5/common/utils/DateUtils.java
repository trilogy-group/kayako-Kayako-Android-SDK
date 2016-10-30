package com.kayako.sdk.android.k5.common.utils;

import android.content.Context;
import android.provider.Settings;

public class DateUtils {

    public static String formatDate(Context context, long timeInMillseconds) {
        return android.text.format.DateUtils.getRelativeTimeSpanString(timeInMillseconds, System.currentTimeMillis(), android.text.format.DateUtils.DAY_IN_MILLIS, android.text.format.DateUtils.FORMAT_SHOW_DATE).toString();
    }
}
