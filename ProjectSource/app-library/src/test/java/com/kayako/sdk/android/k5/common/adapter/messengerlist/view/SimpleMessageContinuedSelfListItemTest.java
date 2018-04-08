package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.DeliveryIndicator;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.is;
import java.util.HashMap;
import java.util.Map;

public class SimpleMessageContinuedSelfListItemTest {

    private long id;
    private static final String MESSAGE = "dummy message";
    private long time;
    private DeliveryIndicator deliveryIndicator;
    private boolean fadeBackground;
    private Map<String, Object> data;
    private SimpleMessageContinuedSelfListItem simpleMessageContinuedSelfListItem;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp(){
        id = 1L;
        time = 2222L;
        deliveryIndicator = new DeliveryIndicator(1,1,1000L);
        fadeBackground = true;
        data = new HashMap<String, Object>();
        simpleMessageContinuedSelfListItem = new SimpleMessageContinuedSelfListItem(id, MESSAGE, time, deliveryIndicator, fadeBackground, data);
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        errorCollector.checkThat(simpleMessageContinuedSelfListItem, notNullValue());
        errorCollector.checkThat(simpleMessageContinuedSelfListItem.getId(), is(id));
        errorCollector.checkThat(simpleMessageContinuedSelfListItem.getItemType(), is(equalTo(MessengerListType.SIMPLE_MESSAGE_CONTINUED_SELF)));
        errorCollector.checkThat(simpleMessageContinuedSelfListItem.getMessage(), is(equalTo(MESSAGE)));
    }

    @Test
    public void getMessage() {
        errorCollector.checkThat(simpleMessageContinuedSelfListItem.getMessage(), is(equalTo(MESSAGE)));
    }

    @Test
    public void setMessage() {
        simpleMessageContinuedSelfListItem.setMessage("message2");
        errorCollector.checkThat(simpleMessageContinuedSelfListItem.getMessage(), is(equalTo("message2")));
    }

    @Test
    public void test_getTime(){
        errorCollector.checkThat(simpleMessageContinuedSelfListItem.getTime(), is(time));
    }

    @Test
    public void setTime(){
        simpleMessageContinuedSelfListItem.setTime(12345L);
        errorCollector.checkThat(simpleMessageContinuedSelfListItem.getTime(), is(12345L));
    }

    @Test
    public void getDeliveryIndicator() {
        DeliveryIndicator deliveryIndicator1 = simpleMessageContinuedSelfListItem.getDeliveryIndicator();
        errorCollector.checkThat(deliveryIndicator, notNullValue());
        errorCollector.checkThat(deliveryIndicator1.getDeliveryTime().longValue(), is(1000L));
    }

    @Test
    public void isFadeBackground() {
        errorCollector.checkThat(simpleMessageContinuedSelfListItem.isFadeBackground(), is(true));
    }

    @Test
    public void getContents() {
        Map map = simpleMessageContinuedSelfListItem.getContents();
        errorCollector.checkThat(map.size() > 0, is(true));
        errorCollector.checkThat(map.get("message").toString(), is(equalTo(MESSAGE)));
        errorCollector.checkThat(map.get("fadeBackground").toString(), is(equalTo(String.valueOf(fadeBackground))));
    }
}
