package com.kayako.sdk.android.k5.common.utils;

import android.content.Context;
import android.provider.Settings;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {

    public static String formatDate(Context context, long timeInMillseconds) {
        return android.text.format.DateUtils.getRelativeTimeSpanString(timeInMillseconds, System.currentTimeMillis(), android.text.format.DateUtils.DAY_IN_MILLIS, android.text.format.DateUtils.FORMAT_SHOW_DATE).toString();
    }

    public static String formatTime(Context context, long timeInMillseconds) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a", Locale.US);
        return simpleDateFormat.format(new Date(timeInMillseconds));
    }

}
