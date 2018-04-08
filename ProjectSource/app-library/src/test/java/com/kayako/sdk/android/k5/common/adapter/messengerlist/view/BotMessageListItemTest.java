package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class BotMessageListItemTest {

    private static final String message = "message";
    private long time;
    private BotMessageListItem botMessageListItem;

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        time = 1000L;
        botMessageListItem = new BotMessageListItem(message, time);
    }

    @Test
    public void test_constructor() {
        errorCollector.checkThat(botMessageListItem, notNullValue());
        errorCollector.checkThat(botMessageListItem.getTime(), is(time));
    }

    @Test
    public void test_getMessage(){
        errorCollector.checkThat(botMessageListItem.getMessage(), is(equalTo(message)));
    }

    @Test
    public void test_setMessage(){
        botMessageListItem.setMessage("test");
        errorCollector.checkThat(botMessageListItem.getMessage(), is(equalTo("test")));
    }

    @Test
    public void test_getTime(){
        errorCollector.checkThat(botMessageListItem.getTime(), is(time));
    }

    @Test
    public void test_setTime(){
        botMessageListItem.setTime(2000L);
        errorCollector.checkThat(botMessageListItem.getTime(), is(2000L));
    }

    @Test
    public void test_getContents() {
        errorCollector.checkThat(botMessageListItem.getContents().size() > 0, is(true));
        errorCollector.checkThat(botMessageListItem.getContents().size(), is(2));
        errorCollector.checkThat(botMessageListItem.getContents().get("message"), is(equalTo(message)));
    }
}
