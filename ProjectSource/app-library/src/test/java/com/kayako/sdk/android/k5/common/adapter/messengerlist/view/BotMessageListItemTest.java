package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class BotMessageListItemTest {

    private static final String MESSAGE = "message";
    private long time;
    private BotMessageListItem botMessageListItem;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        time = 1_000L;
        botMessageListItem = new BotMessageListItem(MESSAGE, time);
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        errorCollector.checkThat(botMessageListItem.getMessage(), is(equalTo(MESSAGE)));
        errorCollector.checkThat(botMessageListItem.getTime(), is(time));
    }

    @Test
    public void setMessage(){
        final String newMessage = "newMessage_test";
        botMessageListItem.setMessage(newMessage);
        errorCollector.checkThat(botMessageListItem.getMessage(), is(equalTo(newMessage)));
    }

    @Test
    public void setTime(){
        final long newTime = 2_000L;
        botMessageListItem.setTime(newTime);
        errorCollector.checkThat(botMessageListItem.getTime(), is(newTime));
    }

    @Test
    public void getContents() {
        errorCollector.checkThat(botMessageListItem.getContents().size(), is(2));
        errorCollector.checkThat(botMessageListItem.getContents().get("message"), is(equalTo(MESSAGE)));
    }
}