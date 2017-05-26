package com.kayako.sdk.android.k5.core;

import java.util.HashSet;
import java.util.Set;

public class KayakoLogHelper {

    private static Set<PrintLogListener> sPrintLogListeners = new HashSet<>();

    private KayakoLogHelper() {
    }

    public static void addLogListener(PrintLogListener listener) {
        sPrintLogListeners.add(listener);
    }

    public static void d(String tag, String message) {
        if (sPrintLogListeners == null || sPrintLogListeners.size() == 0) {
            return;
        }

        for (PrintLogListener logListener : sPrintLogListeners) {
            logListener.printDebugLogs(tag, message);
        }
    }

    public static void v(String tag, String message) {
        if (sPrintLogListeners == null || sPrintLogListeners.size() == 0) {
            return;
        }

        for (PrintLogListener logListener : sPrintLogListeners) {
            logListener.printVerboseLogs(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (sPrintLogListeners == null || sPrintLogListeners.size() == 0) {
            return;
        }

        for (PrintLogListener logListener : sPrintLogListeners) {
            logListener.printErrorLogs(tag, message);
        }
    }

    public static void printStackTrace(String tag, Throwable e) {
        if (sPrintLogListeners == null || sPrintLogListeners.size() == 0) {
            return;
        }

        for (PrintLogListener logListener : sPrintLogListeners) {
            logListener.printStackTrace(tag, e);
        }
    }

    public static void logException(String tag, Throwable e) {
        if (sPrintLogListeners == null || sPrintLogListeners.size() == 0) {
            return;
        }

        for (PrintLogListener logListener : sPrintLogListeners) {
            logListener.logPotentialCrash(tag, e);
        }
    }

    public interface PrintLogListener {
        void printDebugLogs(String tag, String message);

        void printVerboseLogs(String tag, String message);

        void printErrorLogs(String tag, String message);

        void printStackTrace(String tag, Throwable e);

        void logPotentialCrash(String tag, Throwable e);
    }
}
