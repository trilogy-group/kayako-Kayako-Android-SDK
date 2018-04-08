package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.equalTo;

public class SystemMessageListItemTest {

    private static final String MESSAGE = "dummy message";
    private SystemMessageListItem messageListItem;

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        messageListItem = new SystemMessageListItem(MESSAGE);
    }

    @Test
    public void test_constructor1(){
        errorCollector.checkThat(messageListItem, notNullValue());
        errorCollector.checkThat(messageListItem.getItemType(), is(equalTo(MessengerListType.SYSTEM_MESSAGE)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_constructor2() {
        SystemMessageListItem listItem = new SystemMessageListItem(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_constructor3() {
        SystemMessageListItem listItem = new SystemMessageListItem("");
    }

    @Test
    public void test_getMessage() {
        errorCollector.checkThat(messageListItem.getMessage(), is(equalTo(MESSAGE)));
    }

    @Test
    public void test_getContents() {
        Map map = messageListItem.getContents();
        errorCollector.checkThat(map, notNullValue());
        errorCollector.checkThat(map.size(), is(1));
        errorCollector.checkThat(map.get("message").toString(), is(equalTo(MESSAGE)));
    }
}
