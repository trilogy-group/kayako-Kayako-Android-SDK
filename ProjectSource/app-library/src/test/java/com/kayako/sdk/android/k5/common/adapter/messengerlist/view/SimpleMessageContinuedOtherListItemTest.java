package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import java.util.HashMap;
import java.util.Map;

public class SimpleMessageContinuedOtherListItemTest {

    private Long id;
    private final static String MESSAGE = "message for testing";
    private long time;
    private Map<String, Object> data;
    private SimpleMessageContinuedOtherListItem continuedOtherListItem;

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp(){
        id = 2L;
        time = 9876L;
        data = new HashMap<>();
        continuedOtherListItem = new SimpleMessageContinuedOtherListItem(id, MESSAGE, time, data);
    }

    @Test
    public void test_constructor(){
        errorCollector.checkThat(continuedOtherListItem, notNullValue());
        errorCollector.checkThat(continuedOtherListItem.getItemType(), is(equalTo(MessengerListType.SIMPLE_MESSAGE_CONTINUED_OTHER)));
        errorCollector.checkThat(continuedOtherListItem.getMessage(), is(equalTo(MESSAGE)));
    }

    @Test
    public void test_getMessage() {
        errorCollector.checkThat(continuedOtherListItem.getMessage(), is(equalTo(MESSAGE)));
    }

    @Test
    public void test_setMessage() {
        continuedOtherListItem.setMessage("Hello!!!");
        errorCollector.checkThat(continuedOtherListItem.getMessage(), is(equalTo("Hello!!!")));
    }

    @Test
    public void test_getTime(){
        errorCollector.checkThat(continuedOtherListItem.getTime(), is(time));
    }

    @Test
    public void test_setTime(){
        continuedOtherListItem.setTime(2345L);
        errorCollector.checkThat(continuedOtherListItem.getTime(), is(2345L));
    }

    @Test
    public void test_getContents() {
        Map map = continuedOtherListItem.getContents();
        errorCollector.checkThat(map.size() > 0, is(true));
        errorCollector.checkThat(map.size(), is(2));
        errorCollector.checkThat(map.get("time").toString(), is(String.valueOf(time)));
    }

}
