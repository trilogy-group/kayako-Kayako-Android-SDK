package com.kayako.sdk.android.k5.common.utils;

import android.test.mock.MockContext;
import android.text.format.DateUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.times;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DateUtils.class})
public class DateTimeUtilsTest {

    private static final String JUST_NOW = "Just now";
    private static final String NOW = "now";
    private static final String ONE_MINUTE_AGO = "1m ago";
    private static final String ONE_HOUR_AGO = "1h ago";
    private static final String ONE_DAY_AGO = "1d ago";
    private static final String FORMAT_PATTERN = "h:mm a";
    private static final String FORMATTED_DATE = "12/12/2018";

    private static final long ONE_SECOND_IN_MILLIS = 1_000L;
    private static final long ONE_MINUTE_IN_MILLIS = 60 * ONE_SECOND_IN_MILLIS;
    private static final long ONE_HOUR_IN_MILLIS = 60 * ONE_MINUTE_IN_MILLIS;
    private static final long ONE_DAY_IN_MILLIS = 24 * ONE_HOUR_IN_MILLIS;
    private static final long ONE_YEAR_IN_MILLIS = 365 * ONE_DAY_IN_MILLIS;
    private static final long NOW_IN_MILLIS = System.currentTimeMillis();


    private long timeInMilliseconds = NOW_IN_MILLIS;

    private MockContext mockContext;

    @Before
    public void setUp() {
        mockStatic(android.text.format.DateUtils.class);
        mockContext = new MockContext();
        timeInMilliseconds = NOW_IN_MILLIS;
    }

    @Test
    public void formatDate() {
        when(android.text.format.DateUtils.getRelativeTimeSpanString(anyLong(), anyLong(), anyLong(), anyInt())).thenReturn(FORMATTED_DATE);
        String dateStr = DateTimeUtils.formatDate(mockContext, timeInMilliseconds);
        assertThat(dateStr, is(FORMATTED_DATE));
        verifyStatic(android.text.format.DateUtils.class, times(1));
    }

    @Test
    public void formatTimeTest() {
        String dateStr = DateTimeUtils.formatTime(mockContext, timeInMilliseconds);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_PATTERN, Locale.US);
        assertThat(simpleDateFormat.format(new Date(timeInMilliseconds)), is(equalTo(dateStr)));
    }

    @Test
    public void testFormatMessengerDateTimeWhenDifferenceLessThanAndEqualFourtyFour() {
        timeInMilliseconds = NOW_IN_MILLIS - 44 * ONE_SECOND_IN_MILLIS;
        String dateStr = DateTimeUtils.formatMessengerDateTime(NOW_IN_MILLIS, timeInMilliseconds);
        assertThat(dateStr, is(equalTo(JUST_NOW)));
    }

    @Test
    public void testFormatMessengerDateTimeWhenDifferenceLessThanAndEqualEightyNineSecond() {
        timeInMilliseconds = NOW_IN_MILLIS - 89 * ONE_SECOND_IN_MILLIS;
        String dateStr = DateTimeUtils.formatMessengerDateTime(NOW_IN_MILLIS, timeInMilliseconds);
        assertThat(dateStr, is(equalTo(ONE_MINUTE_AGO)));
    }


    @Test
    public void testFormatMessengerDateTimeWhenDifferenceLessThanAndEqualFourtyFourMinute() {
        timeInMilliseconds = NOW_IN_MILLIS - 44 * ONE_MINUTE_IN_MILLIS;
        String dateStr = DateTimeUtils.formatMessengerDateTime(NOW_IN_MILLIS, timeInMilliseconds);
        assertThat(dateStr, is(equalTo(String.format(Locale.US, "%dm ago", 44))));
    }

    @Test
    public void testFormatMessengerDateTimeWhenDifferenceLessThanAndEqualEightyNineMinutes() {
        timeInMilliseconds = NOW_IN_MILLIS - 89 * ONE_MINUTE_IN_MILLIS;
        String dateStr = DateTimeUtils.formatMessengerDateTime(NOW_IN_MILLIS, timeInMilliseconds);
        assertThat(dateStr, is(equalTo(ONE_HOUR_AGO)));
    }

    @Test
    public void testFormatMessengerDateTimeWhenDifferenceLessThanAndEqualTwentyOneHours() {
        timeInMilliseconds = NOW_IN_MILLIS - 21 * ONE_HOUR_IN_MILLIS;
        String dateStr = DateTimeUtils.formatMessengerDateTime(NOW_IN_MILLIS, timeInMilliseconds);
        assertThat(dateStr, is(equalTo(String.format(Locale.US, "%dh ago", 21))));
    }

    @Test
    public void testFormatMessengerDateTimeWhenDifferenceLessThanAndEqualOneHundredThirtyTwoHours() {
        timeInMilliseconds = NOW_IN_MILLIS - 132 * ONE_HOUR_IN_MILLIS;
        String dateStr = DateTimeUtils.formatMessengerDateTime(NOW_IN_MILLIS, timeInMilliseconds);
        assertThat(dateStr, is(equalTo(String.format(Locale.US, "%dd ago", 132 / 24))));
    }

    @Test
    public void testFormatMessengerDateTimeElseCase() {
        timeInMilliseconds = NOW_IN_MILLIS - 150 * ONE_HOUR_IN_MILLIS;
        String dateStr = DateTimeUtils.formatMessengerDateTime(NOW_IN_MILLIS, timeInMilliseconds);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        String expected = simpleDateFormat.format(new Date(timeInMilliseconds));
        assertThat(dateStr, is(equalTo(expected)));
    }


    @Test
    public void testFormatMessengerDateTimeWhenDifferenceLessThanAndEqualThirtyFiveHours() {
        timeInMilliseconds = NOW_IN_MILLIS - 35 * ONE_HOUR_IN_MILLIS;
        String dateStr = DateTimeUtils.formatMessengerDateTime(NOW_IN_MILLIS, timeInMilliseconds);
        assertThat(dateStr, is(equalTo(ONE_DAY_AGO)));
    }

    @Test
    public void formatShortDateTimeTestWhenDiffLessThanOneMinute() {
        timeInMilliseconds = NOW_IN_MILLIS - 50 * ONE_SECOND_IN_MILLIS;
        String dateStr = DateTimeUtils.formatShortDateTime(NOW_IN_MILLIS, timeInMilliseconds);
        assertThat(dateStr, is(equalTo(NOW)));
    }

    @Test
    public void formatShortDateTimeTestWhenDiffLessThanOneHour() {
        timeInMilliseconds = NOW_IN_MILLIS - 50 * ONE_MINUTE_IN_MILLIS;
        String dateStr = DateTimeUtils.formatShortDateTime(NOW_IN_MILLIS, timeInMilliseconds);
        assertThat(dateStr, is(equalTo(String.format(Locale.US, "%dm", 50))));
    }

    @Test
    public void formatShortDateTimeTestWhenDiffLessThanOneDay() {
        timeInMilliseconds = NOW_IN_MILLIS - 23 * ONE_HOUR_IN_MILLIS;
        String dateStr = DateTimeUtils.formatShortDateTime(NOW_IN_MILLIS, timeInMilliseconds);
        assertThat(dateStr, is(equalTo(String.format(Locale.US, "%dh", 23))));
    }

    @Test
    public void formatShortDateTimeTestWhenDiffLessThanThreeDay() {
        timeInMilliseconds = NOW_IN_MILLIS - 2 * ONE_DAY_IN_MILLIS;
        String dateStr = DateTimeUtils.formatShortDateTime(NOW_IN_MILLIS, timeInMilliseconds);
        assertThat(dateStr, is(equalTo(String.format(Locale.US, "%dd", 2))));
    }

    @Test
    public void formatShortDateTimeTestWhenDiffLessThanOneYear() {
        timeInMilliseconds = NOW_IN_MILLIS - 360 * ONE_DAY_IN_MILLIS;
        String dateStr = DateTimeUtils.formatShortDateTime(NOW_IN_MILLIS, timeInMilliseconds);
        assertThat(dateStr, is(equalTo(new SimpleDateFormat("d MMM", Locale.US).format(new Date(timeInMilliseconds)))));
    }

    @Test
    public void formatShortDateTimeTestElseCase() {
        timeInMilliseconds = NOW_IN_MILLIS - 2 * ONE_YEAR_IN_MILLIS;
        String dateStr = DateTimeUtils.formatShortDateTime(NOW_IN_MILLIS, timeInMilliseconds);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM yyyy", Locale.US);
        assertThat(dateStr, is(equalTo(simpleDateFormat.format(new Date(timeInMilliseconds)))));
    }


    @Test
    public void formatOnlyDateNotTimeThisYearTest() {
        timeInMilliseconds = NOW_IN_MILLIS - ONE_YEAR_IN_MILLIS;
        String dateStr = DateTimeUtils.formatOnlyDateNotTimeThisYear(timeInMilliseconds);
        assertThat(dateStr, is(equalTo(new SimpleDateFormat("d MMM", Locale.US).format(new Date(timeInMilliseconds)))));
    }

    @Test
    public void formatOnlyDateNotTimeBeyondYearTest() {
        timeInMilliseconds = NOW_IN_MILLIS - 2 * ONE_YEAR_IN_MILLIS;
        String dateStr = DateTimeUtils.formatOnlyDateNotTimeBeyondYear(timeInMilliseconds);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM yyyy", Locale.US);
        assertThat(dateStr, is(equalTo(simpleDateFormat.format(new Date(timeInMilliseconds)))));
    }
}
