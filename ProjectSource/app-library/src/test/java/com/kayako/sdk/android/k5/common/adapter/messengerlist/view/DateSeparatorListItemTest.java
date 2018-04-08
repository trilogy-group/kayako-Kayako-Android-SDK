package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

public class DateSeparatorListItemTest {

    private Long timeInMilliseconds;
    private DateSeparatorListItem separatorListItem;

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp(){
        timeInMilliseconds = 10000L;
        separatorListItem = new DateSeparatorListItem(timeInMilliseconds);
    }

    @Test
    public void test_constructor() {
        errorCollector.checkThat(separatorListItem, notNullValue());
        errorCollector.checkThat(separatorListItem.getItemType(), is(MessengerListType.DATE_SEPARATOR));
    }

    @Test
    public void test_getTimeInMilliseconds(){
        errorCollector.checkThat(separatorListItem.getTimeInMilliseconds(), is(timeInMilliseconds.longValue()));
    }

    @Test
    public void test_setTimeInMilliseconds(){
        separatorListItem.setTimeInMilliseconds(22222L);
        errorCollector.checkThat(separatorListItem.getTimeInMilliseconds(), is(22222L));
    }

    @Test
    public void test_getContents() {
        errorCollector.checkThat(separatorListItem.getContents().size(), is(1));
        errorCollector.checkThat(separatorListItem.getContents().get("timeInMilliseconds"), is(equalTo(timeInMilliseconds.toString())));
    }

}
