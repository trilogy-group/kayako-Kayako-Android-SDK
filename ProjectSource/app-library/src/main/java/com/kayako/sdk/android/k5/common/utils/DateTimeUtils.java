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

    /**
     * Format date in the following manner:
     * <p>
     * 0 to 44 seconds: "Just now"
     * 45 to 89 seconds: "1m ago"
     * 90 seconds to 44 minutes: "2m ago" ... "44m ago"
     * 45 to 89 minutes: "1h ago"
     * 90 minutes to 21 hours: "2h ago" ... "21h ago"
     * 22 to 35 hours: "1d ago"
     * 36 hours to 132 hours: "2d ago" ... "5d ago"
     * dd/mm/yyyy
     */
    public static String formatMessengerDateTime(long now, long timeInMilliseconds) {
        long difference = now - timeInMilliseconds;
        if (difference <= 44 * oneSec) {
            return "Just now";
        } else if (difference <= 89 * oneSec || difference / oneMinute == 0) {
            return "1m ago";
        } else if (difference <= 44 * oneMinute) {
            long minutes = difference / oneMinute;
            return String.format(Locale.US, "%dm ago", minutes);
        } else if (difference <= 89 * oneMinute || difference / oneHour == 0) {
            return "1h ago";
        } else if (difference <= 21 * oneHour) {
            long hours = difference / oneHour;
            return String.format(Locale.US, "%dh ago", hours);
        } else if (difference <= 35 * oneHour || difference / oneDay == 0) {
            return "1d ago";
        } else if (difference <= 132 * oneHour) {
            long days = difference / oneDay;
            return String.format(Locale.US, "%dd ago", days);
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            return simpleDateFormat.format(new Date(timeInMilliseconds));
        }
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
