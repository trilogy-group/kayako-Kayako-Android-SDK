package com.kayako.sdk.android.k5.common.utils.file;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileFormatUtilTest {
    public static final Long TEST_FILE_SIZE_BYTES = 13_245_678L;
    public static final String EXPECTED_OUTPUT = "12 MB";
    public static final String EXPECTED_OUTPUT_ON_NULL = "-";
    public static final String EXPECTED_OUTPUT_ON_SMALL_SIZE = "0 KB";

    @Test
    public void verifyIfGettingCorrectSizeConversion() {
        assertEquals(EXPECTED_OUTPUT, FileFormatUtil.formatFileSize(TEST_FILE_SIZE_BYTES));
    }

    @Test
    public void verifyIfGettingSlashOnNullParameter() {
        assertEquals(EXPECTED_OUTPUT_ON_NULL, FileFormatUtil.formatFileSize(null));
    }

    @Test
    public void verifyGettingZeroOnLessThan1024Bytes() {
        assertEquals(EXPECTED_OUTPUT_ON_SMALL_SIZE, FileFormatUtil.formatFileSize(12L));
    }
}
