package com.kayako.sdk.android.k5.common.utils.file;

import java.util.Locale;

public class FileFormatUtil {

    public static final long ONE_KB_IN_BYTES = 1024;
    public static final long ONE_MB_IN_BYTES = 1024 * ONE_KB_IN_BYTES;

    public static String formatFileSize(Long fileSizeInBytes) {
        if (fileSizeInBytes == null) {
            return "-";
        } else if (fileSizeInBytes < ONE_KB_IN_BYTES) {
            return "0 KB";
        } else if (fileSizeInBytes < ONE_MB_IN_BYTES) {
            return String.format(Locale.US, "%d %s", Math.round(fileSizeInBytes / ONE_KB_IN_BYTES), "KB");
        } else {
            return String.format(Locale.US, "%d %s", Math.round(fileSizeInBytes / ONE_MB_IN_BYTES), "MB");
        }
    }

}
