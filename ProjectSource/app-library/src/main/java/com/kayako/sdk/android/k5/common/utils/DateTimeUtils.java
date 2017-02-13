package com.kayako.sdk.android.k5.common.utils;

import android.content.Context;
import android.provider.Settings;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {

    private static final long oneSec = 1000;
    private static final long oneMinute = 60 * oneSec;
    private static final long oneHour = 60 * oneMinute;
    private static final long oneDay = 24 * oneHour;
    private static final long oneYear = 365 * oneDay;

    public static String formatDate(Context context, long timeInMillseconds) {
        return android.text.format.DateUtils.getRelativeTimeSpanString(timeInMillseconds, System.currentTimeMillis(), android.text.format.DateUtils.DAY_IN_MILLIS, android.text.format.DateUtils.FORMAT_SHOW_DATE).toString();
    }

    public static String formatTime(Context context, long timeInMillseconds) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a", Locale.US);
        return simpleDateFormat.format(new Date(timeInMillseconds));
    }

    public static String formatShortDateTime(long now, long timeInMilliseconds) {
        long difference = now - timeInMilliseconds;

        if (difference < oneMinute) {
            return "now";
        } else if (difference < oneHour) {
            return String.format(Locale.US, "%dm", difference / oneMinute);
        } else if (difference < oneDay) {
            return String.format(Locale.US, "%dh", difference / oneHour);
        } else if (difference < 3 * oneDay) {
            return String.format(Locale.US, "%dd", difference / oneDay);
        } else if (difference < oneYear) {
            return formatOnlyDateNotTimeThisYear(timeInMilliseconds);
        } else {
            return formatOnlyDateNotTimeBeyondYear(timeInMilliseconds);
        }
    }

    public static String formatOnlyDateNotTimeThisYear(long timeInMilliseconds) {
        return new SimpleDateFormat("d MMM", Locale.US).format(new Date(timeInMilliseconds));
    }

    public static String formatOnlyDateNotTimeBeyondYear(long timeInMilliseconds) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM yyyy", Locale.US);
        return simpleDateFormat.format(new Date(timeInMilliseconds));
    }

}
