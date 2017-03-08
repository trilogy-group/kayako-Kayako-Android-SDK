package com.kayako.sdk.android.k5.kre.helpers;

public class KreLogHelper {

    private static PrintLogListener sPrintLogListener;

    private KreLogHelper() {
    }

    public static void setAddLogListener(PrintLogListener listener) {
        sPrintLogListener = listener;
    }

    public static void d(String tag, String message) {
        if (sPrintLogListener == null) {
            return;
        }
        sPrintLogListener.printDebugLogs(tag, message);
    }

    public static void v(String tag, String message) {
        if (sPrintLogListener == null) {
            return;
        }

        sPrintLogListener.printVerboseLogs(tag, message);
    }

    public static void e(String tag, String message) {
        if (sPrintLogListener == null) {
            return;
        }

        sPrintLogListener.printErrorLogs(tag, message);
    }

    public static void printStackTrace(String tag, Throwable e) {
        if (sPrintLogListener == null) {
            return;
        }

        sPrintLogListener.printStackTrace(tag, e);
    }

    public static void logException(String tag, Throwable e) {
        if (sPrintLogListener == null) {
            return;
        }

        sPrintLogListener.logPotentialCrash(tag, e);
    }

    public interface PrintLogListener {
        void printDebugLogs(String tag, String message);

        void printVerboseLogs(String tag, String message);

        void printErrorLogs(String tag, String message);

        void printStackTrace(String tag, Throwable e);

        void logPotentialCrash(String tag, Throwable e);
    }
}