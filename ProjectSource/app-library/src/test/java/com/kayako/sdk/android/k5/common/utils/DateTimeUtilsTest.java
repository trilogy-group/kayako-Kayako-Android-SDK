package com.kayako.sdk.android.k5.common.utils;

import android.test.mock.MockContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.times;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({android.text.format.DateUtils.class})
public class DateTimeUtilsTest {

    private final String JUST_NOW = "Just now";
    private final String NOW_ = "now";
    private final String ONE_MINUTE_AGO = "1m ago";
    private final String ONE_HOUR_AGO = "1h ago";
    private final String ONE_DAY_AGO = "1d ago";
    private final long ONE_SECOND = 1000;
    private final long ONE_MINUTE = 60 * ONE_SECOND;
    private final long ONE_HOUR = 60 * ONE_MINUTE;
    private final long ONE_DAY = 24 * ONE_HOUR;
    private final long ONE_YEAR = 365 * ONE_DAY;

    final String FORMAT_PATTERN = "h:mm a";

    private MockContext mockContext;

    private long TIME_IN_MILLISECONDS;
    private long NOW;
    private String FORMATTED_DATE;


    @Before
    public void setUp() {
        mockStatic(android.text.format.DateUtils.class);
        mockContext = new MockContext();
        NOW = System.currentTimeMillis();
    }


    @Test
    public void formatDate() {
        TIME_IN_MILLISECONDS = NOW;
        FORMATTED_DATE = "12/12/2018";
        when(android.text.format.DateUtils.getRelativeTimeSpanString(anyLong(), anyLong(), anyLong(), anyInt())).thenReturn(FORMATTED_DATE);
        String dateStr = DateTimeUtils.formatDate(mockContext, TIME_IN_MILLISECONDS);
        assertThat(dateStr, is(FORMATTED_DATE));
        verifyStatic(android.text.format.DateUtils.class, times(1));
    }

    @Test
    public void formatTimeTest() {
        TIME_IN_MILLISECONDS = NOW;
        String dateStr = DateTimeUtils.formatTime(mockContext, TIME_IN_MILLISECONDS);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_PATTERN, Locale.US);
        assertThat(simpleDateFormat.format(new Date(TIME_IN_MILLISECONDS)), is(equalTo(dateStr)));
    }

    @Test
    public void testFormatMessengerDateTimeWhenDifferenceLessThanAndEqualFourtyFour() {
        TIME_IN_MILLISECONDS = NOW - 44 * ONE_SECOND;
        String dateStr = DateTimeUtils.formatMessengerDateTime(NOW, TIME_IN_MILLISECONDS);
        assertThat(dateStr, is(equalTo(JUST_NOW)));
    }

    @Test
    public void testFormatMessengerDateTimeWhenDifferenceLessThanAndEqualEightyNineSecond() {
        TIME_IN_MILLISECONDS = NOW - 89 * ONE_SECOND;
        String dateStr = DateTimeUtils.formatMessengerDateTime(NOW, TIME_IN_MILLISECONDS);
        assertThat(dateStr, is(equalTo(ONE_MINUTE_AGO)));
    }


    @Test
    public void testFormatMessengerDateTimeWhenDifferenceLessThanAndEqualFourtyFourMinute() {
        TIME_IN_MILLISECONDS = NOW - 44 * ONE_MINUTE;
        String dateStr = DateTimeUtils.formatMessengerDateTime(NOW, TIME_IN_MILLISECONDS);
        assertThat(dateStr, is(equalTo(String.format(Locale.US, "%dm ago", 44))));
    }

    @Test
    public void testFormatMessengerDateTimeWhenDifferenceLessThanAndEqualEightyNineMinutes() {
        TIME_IN_MILLISECONDS = NOW - 89 * ONE_MINUTE;
        String dateStr = DateTimeUtils.formatMessengerDateTime(NOW, TIME_IN_MILLISECONDS);
        assertThat(dateStr, is(equalTo(ONE_HOUR_AGO)));
    }

    @Test
    public void testFormatMessengerDateTimeWhenDifferenceLessThanAndEqualTwentyOneHours() {
        TIME_IN_MILLISECONDS = NOW - 21 * ONE_HOUR;
        String dateStr = DateTimeUtils.formatMessengerDateTime(NOW, TIME_IN_MILLISECONDS);
        assertThat(dateStr, is(equalTo(String.format(Locale.US, "%dh ago", 21))));
    }

    @Test
    public void testFormatMessengerDateTimeWhenDifferenceLessThanAndEqualOneHundredThirtyTwoHours() {
        TIME_IN_MILLISECONDS = NOW - 132 * ONE_HOUR;
        String dateStr = DateTimeUtils.formatMessengerDateTime(NOW, TIME_IN_MILLISECONDS);
        assertThat(dateStr, is(equalTo(String.format(Locale.US, "%dd ago", 132 / 24))));
    }

    @Test
    public void testFormatMessengerDateTimeElseCase() {
        TIME_IN_MILLISECONDS = NOW - 150 * ONE_HOUR;
        String dateStr = DateTimeUtils.formatMessengerDateTime(NOW, TIME_IN_MILLISECONDS);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        String expected = simpleDateFormat.format(new Date(TIME_IN_MILLISECONDS));
        assertThat(dateStr, is(equalTo(expected)));
    }


    @Test
    public void testFormatMessengerDateTimeWhenDifferenceLessThanAndEqualThirtyFiveHours() {
        TIME_IN_MILLISECONDS = NOW - 35 * ONE_HOUR;
        String dateStr = DateTimeUtils.formatMessengerDateTime(NOW, TIME_IN_MILLISECONDS);
        assertThat(dateStr, is(equalTo(ONE_DAY_AGO)));
    }


    @Test
    public void formatShortDateTimeTestWhenDiffLessThanOneMinute() {
        TIME_IN_MILLISECONDS = NOW - 50 * ONE_SECOND;
        String dateStr = DateTimeUtils.formatShortDateTime(NOW, TIME_IN_MILLISECONDS);
        assertThat(dateStr, is(equalTo(NOW_)));
    }

    @Test
    public void formatShortDateTimeTestWhenDiffLessThanOneHour() {
        TIME_IN_MILLISECONDS = NOW - 50 * ONE_MINUTE;
        String dateStr = DateTimeUtils.formatShortDateTime(NOW, TIME_IN_MILLISECONDS);
        assertThat(dateStr, is(equalTo(String.format(Locale.US, "%dm", 50))));
    }

    @Test
    public void formatShortDateTimeTestWhenDiffLessThanOneDay() {
        TIME_IN_MILLISECONDS = NOW - 23 * ONE_HOUR;
        String dateStr = DateTimeUtils.formatShortDateTime(NOW, TIME_IN_MILLISECONDS);
        assertThat(dateStr, is(equalTo(String.format(Locale.US, "%dh", 23))));
    }

    @Test
    public void formatShortDateTimeTestWhenDiffLessThanThreeDay() {
        TIME_IN_MILLISECONDS = NOW - 2 * ONE_DAY;
        String dateStr = DateTimeUtils.formatShortDateTime(NOW, TIME_IN_MILLISECONDS);
        assertThat(dateStr, is(equalTo(String.format(Locale.US, "%dd", 2))));
    }

    @Test
    public void formatShortDateTimeTestWhenDiffLessThanOneYear() {
        TIME_IN_MILLISECONDS = NOW - 360 * ONE_DAY;
        String dateStr = DateTimeUtils.formatShortDateTime(NOW, TIME_IN_MILLISECONDS);
        assertThat(dateStr, is(equalTo(new SimpleDateFormat("d MMM", Locale.US).format(new Date(TIME_IN_MILLISECONDS)))));
    }

    @Test
    public void formatShortDateTimeTestElseCase() {
        TIME_IN_MILLISECONDS = NOW - 2 * ONE_YEAR;
        String dateStr = DateTimeUtils.formatShortDateTime(NOW, TIME_IN_MILLISECONDS);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM yyyy", Locale.US);
        assertThat(dateStr, is(equalTo(simpleDateFormat.format(new Date(TIME_IN_MILLISECONDS)))));
    }


    @Test
    public void formatOnlyDateNotTimeThisYearTest() {
        TIME_IN_MILLISECONDS = NOW - ONE_YEAR;
        String dateStr = DateTimeUtils.formatOnlyDateNotTimeThisYear(TIME_IN_MILLISECONDS);
        assertThat(dateStr, is(equalTo(new SimpleDateFormat("d MMM", Locale.US).format(new Date(TIME_IN_MILLISECONDS)))));
    }

    @Test
    public void formatOnlyDateNotTimeBeyondYearTest() {
        TIME_IN_MILLISECONDS = NOW - 2 * ONE_YEAR;
        String dateStr = DateTimeUtils.formatOnlyDateNotTimeBeyondYear(TIME_IN_MILLISECONDS);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM yyyy", Locale.US);
        assertThat(dateStr, is(equalTo(simpleDateFormat.format(new Date(TIME_IN_MILLISECONDS)))));

    }
}