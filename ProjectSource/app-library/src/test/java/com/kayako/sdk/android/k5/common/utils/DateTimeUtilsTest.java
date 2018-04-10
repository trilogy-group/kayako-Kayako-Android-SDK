package com.kayako.sdk.android.k5.common.utils;

import android.test.mock.MockContext;
import android.text.format.DateUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
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
    private static final String TIME_FORMAT_PATTERN = "h:mm a";
    private static final String DATE_FORMAT_PATTERN = "dd/MM/yyyy";
    private static final String DATE_FORMAT_PATTERN_WITH_MONTH_AND_YEAR = "MMM yyyy";
    private static final String DATE_FORMAT_PATTERN_WITH_DAY_AND_MONTH = "d MMM";
    private static final String FORMATTED_DATE = "12/12/2018";

    private static final long ONE_SECOND_IN_MILLIS = TimeUnit.SECONDS.toMillis(1);
    private static final long ONE_MINUTE_IN_MILLIS = TimeUnit.MINUTES.toMillis(1);
    private static final long ONE_HOUR_IN_MILLIS = TimeUnit.HOURS.toMillis(1);
    private static final long ONE_DAY_IN_MILLIS = TimeUnit.DAYS.toMillis(1);
    private static final long ONE_YEAR_IN_MILLIS = 365 * ONE_DAY_IN_MILLIS;
    private static final long NOW_IN_MILLIS = System.currentTimeMillis();

    private long timeInMilliseconds = NOW_IN_MILLIS;

    private MockContext mockContext;
    private DateFormat dateFormat;

    @Before
    public void setUp() {
        mockStatic(DateUtils.class);
        mockContext = new MockContext();
        timeInMilliseconds = NOW_IN_MILLIS;
        dateFormat = new SimpleDateFormat(TIME_FORMAT_PATTERN, Locale.US);
    }

    @Test
    public void formatDate() {
        // Arrange
        //i should use anyLong() here because System.currentTimeMillis()  will be different result after one ms
        when(DateUtils.getRelativeTimeSpanString(eq(timeInMilliseconds), anyLong(),
                eq(DateUtils.DAY_IN_MILLIS), eq(DateUtils.FORMAT_SHOW_DATE)))
                .thenReturn(FORMATTED_DATE);

        //Act
        String dateStr = DateTimeUtils.formatDate(mockContext, timeInMilliseconds);

        //Assert
        assertThat(dateStr, is(FORMATTED_DATE));
        verifyStatic(DateUtils.class, times(1));
    }

    @Test
    public void formatTimeTest() {
        //Act
        String dateStr = DateTimeUtils.formatTime(mockContext, timeInMilliseconds);

        //Assert
        assertThat(dateFormat.format(new Date(timeInMilliseconds)), is(equalTo(dateStr)));
    }

    @Test
    public void testFormatMessengerDateTimeWhenDifferenceLessThanAndEqualFourtyFour() {
        //Arrange
        timeInMilliseconds = NOW_IN_MILLIS - 44 * ONE_SECOND_IN_MILLIS;

        //Act
        String dateStr = DateTimeUtils.formatMessengerDateTime(NOW_IN_MILLIS, timeInMilliseconds);

        //Assert
        assertThat(dateStr, is(equalTo(JUST_NOW)));
    }

    @Test
    public void testFormatMessengerDateTimeWhenDifferenceLessThanAndEqualEightyNineSecond() {
        //Arrange
        timeInMilliseconds = NOW_IN_MILLIS - 89 * ONE_SECOND_IN_MILLIS;

        //Act
        String dateStr = DateTimeUtils.formatMessengerDateTime(NOW_IN_MILLIS, timeInMilliseconds);

        //Assert
        assertThat(dateStr, is(equalTo(ONE_MINUTE_AGO)));
    }

    @Test
    public void testFormatMessengerDateTimeWhenDifferenceLessThanAndEqualFourtyFourMinute() {
        //Arrange
        timeInMilliseconds = NOW_IN_MILLIS - 44 * ONE_MINUTE_IN_MILLIS;

        //Act
        String dateStr = DateTimeUtils.formatMessengerDateTime(NOW_IN_MILLIS, timeInMilliseconds);

        //Assert
        assertThat(dateStr, is(equalTo(String.format(Locale.US, "%dm ago", 44))));
    }

    @Test
    public void testFormatMessengerDateTimeWhenDifferenceLessThanAndEqualEightyNineMinutes() {
        //Arrange
        timeInMilliseconds = NOW_IN_MILLIS - 89 * ONE_MINUTE_IN_MILLIS;

        //Act
        String dateStr = DateTimeUtils.formatMessengerDateTime(NOW_IN_MILLIS, timeInMilliseconds);

        //Assert
        assertThat(dateStr, is(equalTo(ONE_HOUR_AGO)));
    }

    @Test
    public void testFormatMessengerDateTimeWhenDifferenceLessThanAndEqualTwentyOneHours() {
        //Arrange
        timeInMilliseconds = NOW_IN_MILLIS - 21 * ONE_HOUR_IN_MILLIS;

        //Act
        String dateStr = DateTimeUtils.formatMessengerDateTime(NOW_IN_MILLIS, timeInMilliseconds);

        //Assert
        assertThat(dateStr, is(equalTo(String.format(Locale.US, "%dh ago", 21))));
    }

    @Test
    public void testFormatMessengerDateTimeWhenDifferenceLessThanAndEqualOneHundredThirtyTwoHours() {
        //Arrange
        timeInMilliseconds = NOW_IN_MILLIS - 132 * ONE_HOUR_IN_MILLIS;

        //Act
        String dateStr = DateTimeUtils.formatMessengerDateTime(NOW_IN_MILLIS, timeInMilliseconds);

        //Assert
        assertThat(dateStr, is(equalTo(String.format(Locale.US, "%dd ago", 132 / 24))));
    }

    @Test
    public void testFormatMessengerDateTimeElseCase() {
        //Arrange
        timeInMilliseconds = NOW_IN_MILLIS - 150 * ONE_HOUR_IN_MILLIS;
        dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.US);

        //Act
        String dateStr = DateTimeUtils.formatMessengerDateTime(NOW_IN_MILLIS, timeInMilliseconds);
        String expected = dateFormat.format(new Date(timeInMilliseconds));

        //Assert
        assertThat(dateStr, is(equalTo(expected)));
    }

    @Test
    public void testFormatMessengerDateTimeWhenDifferenceLessThanAndEqualThirtyFiveHours() {
        //Arrange
        timeInMilliseconds = NOW_IN_MILLIS - 35 * ONE_HOUR_IN_MILLIS;

        //Act
        String dateStr = DateTimeUtils.formatMessengerDateTime(NOW_IN_MILLIS, timeInMilliseconds);

        //Assert
        assertThat(dateStr, is(equalTo(ONE_DAY_AGO)));
    }

    @Test
    public void formatShortDateTimeTestWhenDiffLessThanOneMinute() {
        //Arrange
        timeInMilliseconds = NOW_IN_MILLIS - 50 * ONE_SECOND_IN_MILLIS;

        //Act
        String dateStr = DateTimeUtils.formatShortDateTime(NOW_IN_MILLIS, timeInMilliseconds);

        //Assert
        assertThat(dateStr, is(equalTo(NOW)));
    }

    @Test
    public void formatShortDateTimeTestWhenDiffLessThanOneHour() {
        //Arrange
        timeInMilliseconds = NOW_IN_MILLIS - 50 * ONE_MINUTE_IN_MILLIS;

        //Act
        String dateStr = DateTimeUtils.formatShortDateTime(NOW_IN_MILLIS, timeInMilliseconds);

        //Assert
        assertThat(dateStr, is(equalTo(String.format(Locale.US, "%dm", 50))));
    }

    @Test
    public void formatShortDateTimeTestWhenDiffLessThanOneDay() {
        //Arrange
        timeInMilliseconds = NOW_IN_MILLIS - 23 * ONE_HOUR_IN_MILLIS;

        //Act
        String dateStr = DateTimeUtils.formatShortDateTime(NOW_IN_MILLIS, timeInMilliseconds);

        //Assert
        assertThat(dateStr, is(equalTo(String.format(Locale.US, "%dh", 23))));
    }

    @Test
    public void formatShortDateTimeTestWhenDiffLessThanThreeDay() {
        //Arrange
        timeInMilliseconds = NOW_IN_MILLIS - 2 * ONE_DAY_IN_MILLIS;

        //Act
        String dateStr = DateTimeUtils.formatShortDateTime(NOW_IN_MILLIS, timeInMilliseconds);

        //Assert
        assertThat(dateStr, is(equalTo(String.format(Locale.US, "%dd", 2))));
    }

    @Test
    public void formatShortDateTimeTestWhenDiffLessThanOneYear() {
        //Arrange
        timeInMilliseconds = NOW_IN_MILLIS - 360 * ONE_DAY_IN_MILLIS;

        //Act
        String dateStr = DateTimeUtils.formatShortDateTime(NOW_IN_MILLIS, timeInMilliseconds);

        //Assert
        assertThat(dateStr, is(equalTo(new SimpleDateFormat(DATE_FORMAT_PATTERN_WITH_DAY_AND_MONTH, Locale.US)
                .format(new Date(timeInMilliseconds)))));
    }

    @Test
    public void formatShortDateTimeTestElseCase() {
        //Arrange
        timeInMilliseconds = NOW_IN_MILLIS - 2 * ONE_YEAR_IN_MILLIS;

        //Act
        String dateStr = DateTimeUtils.formatShortDateTime(NOW_IN_MILLIS, timeInMilliseconds);

        //Assert
        assertThat(dateStr, is(equalTo(new SimpleDateFormat(DATE_FORMAT_PATTERN_WITH_MONTH_AND_YEAR,
                Locale.US).format(new Date(timeInMilliseconds)))));
    }

    @Test
    public void formatOnlyDateNotTimeThisYearTest() {
        //Arrange
        timeInMilliseconds = NOW_IN_MILLIS - ONE_YEAR_IN_MILLIS;

        //Act
        String dateStr = DateTimeUtils.formatOnlyDateNotTimeThisYear(timeInMilliseconds);

        //Assert
        assertThat(dateStr, is(equalTo(new SimpleDateFormat(DATE_FORMAT_PATTERN_WITH_DAY_AND_MONTH, Locale.US)
                .format(new Date(timeInMilliseconds)))));
    }

    @Test
    public void formatOnlyDateNotTimeBeyondYearTest() {
        //Arrange
        timeInMilliseconds = NOW_IN_MILLIS - 2 * ONE_YEAR_IN_MILLIS;

        //Act
        String dateStr = DateTimeUtils.formatOnlyDateNotTimeBeyondYear(timeInMilliseconds);

        //Assert
        assertThat(dateStr, is(equalTo(new SimpleDateFormat(DATE_FORMAT_PATTERN_WITH_MONTH_AND_YEAR, Locale.US)
                .format(new Date(timeInMilliseconds)))));
    }
}

