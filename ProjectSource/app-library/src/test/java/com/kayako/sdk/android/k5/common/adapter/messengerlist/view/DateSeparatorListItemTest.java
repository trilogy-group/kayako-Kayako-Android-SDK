package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.is;

public class DateSeparatorListItemTest {

    private long timeInMilliseconds;
    private DateSeparatorListItem separatorListItem;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        timeInMilliseconds = 10_000L;
        separatorListItem = new DateSeparatorListItem(timeInMilliseconds);
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        errorCollector.checkThat(separatorListItem.getTimeInMilliseconds(), is(timeInMilliseconds));
        errorCollector.checkThat(separatorListItem.getItemType(), is(MessengerListType.DATE_SEPARATOR));
    }

    @Test
    public void setTimeInMilliseconds() {
        final long newTime = 22_222L;
        separatorListItem.setTimeInMilliseconds(newTime);
        errorCollector.checkThat(separatorListItem.getTimeInMilliseconds(), is(newTime));
    }

    @Test
    public void getContents() {
        errorCollector.checkThat(separatorListItem.getContents().size(), is(1));
        errorCollector.checkThat(separatorListItem.getContents().get("timeInMilliseconds"),
                                is(String.valueOf(timeInMilliseconds)));
    }
}
