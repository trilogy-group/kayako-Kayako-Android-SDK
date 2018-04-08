package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.DeliveryIndicator;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.equalTo;
import java.util.HashMap;
import java.util.Map;

public class SimpleMessageSelfListItemTest {

    private Long id;
    private static final String MESSAGE = "dummy message";
    private long time;
    private DeliveryIndicator deliveryIndicator;
    private boolean fadeBackground;
    private Map<String, Object> data;
    private SimpleMessageSelfListItem selfListItem;

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp(){
        id = 1L;
        time = 2222L;
        deliveryIndicator = new DeliveryIndicator(1,1,1000L);
        fadeBackground = true;
        data = new HashMap<String, Object>();
        selfListItem = new SimpleMessageSelfListItem(id, MESSAGE, time, deliveryIndicator, fadeBackground, data);
    }

    @Test
    public void test_constructor() {
        errorCollector.checkThat(selfListItem, notNullValue());
        errorCollector.checkThat(selfListItem.getId(), is(id));
        errorCollector.checkThat(selfListItem.getItemType(), is(equalTo(MessengerListType.SIMPLE_MESSAGE_SELF)));
        errorCollector.checkThat(selfListItem.getMessage(), is(equalTo(MESSAGE)));
    }

    @Test
    public void test_getMessage() {
        errorCollector.checkThat(selfListItem.getMessage(), is(equalTo(MESSAGE)));
    }

    @Test
    public void test_setMessage() {
        selfListItem.setMessage("message2");
        errorCollector.checkThat(selfListItem.getMessage(), is(equalTo("message2")));
    }

    @Test
    public void test_getTime(){
        errorCollector.checkThat(selfListItem.getTime(), is(equalTo(time)));
    }

    @Test
    public void test_setTime(){
        selfListItem.setTime(12345L);
        errorCollector.checkThat(selfListItem.getTime(), is(equalTo(12345L)));
    }

    @Test
    public void test_getDeliveryIndicator() {
        DeliveryIndicator deliveryIndicator1 = selfListItem.getDeliveryIndicator();
        errorCollector.checkThat(deliveryIndicator1, notNullValue());
        errorCollector.checkThat(deliveryIndicator1.getDeliveryTime().longValue(), is(1000L));
    }

    @Test
    public void test_isFadeBackground() {
        errorCollector.checkThat(selfListItem.isFadeBackground(), is(true));
    }

    @Test
    public void test_getContents() {
        Map map = selfListItem.getContents();
        errorCollector.checkThat(map.size() > 0, is(true));
        errorCollector.checkThat(map.get("message").toString(), is(equalTo(MESSAGE)));
        errorCollector.checkThat(map.get("fadeBackground").toString(), is(equalTo(String.valueOf(fadeBackground))));
    }
}
