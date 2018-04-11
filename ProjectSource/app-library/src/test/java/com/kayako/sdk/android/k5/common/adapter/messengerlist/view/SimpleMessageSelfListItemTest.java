package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.DeliveryIndicator;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.is;
import java.util.HashMap;
import java.util.Map;

public class SimpleMessageSelfListItemTest {

    private static final String MESSAGE = "dummy message";
    private final Map<String, Object> data = new HashMap<>();
    private long id;
    private long time;
    private DeliveryIndicator deliveryIndicator;
    private boolean fadeBackground;
    private SimpleMessageSelfListItem selfListItem;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        id = 1L;
        time = 2_222L;
        deliveryIndicator = new DeliveryIndicator(1,1,time);
        fadeBackground = true;
        selfListItem = new SimpleMessageSelfListItem(id, MESSAGE, time, deliveryIndicator, fadeBackground, data);
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        errorCollector.checkThat(selfListItem.getId(), is(id));
        errorCollector.checkThat(selfListItem.getMessage(), is(MESSAGE));
        errorCollector.checkThat(selfListItem.getTime(), is(time));
        errorCollector.checkThat(selfListItem.getDeliveryIndicator(), is(deliveryIndicator));
        errorCollector.checkThat(selfListItem.isFadeBackground(), is(fadeBackground));
        errorCollector.checkThat(selfListItem.getData(), is(data));
        errorCollector.checkThat(selfListItem.getItemType(), is(MessengerListType.SIMPLE_MESSAGE_SELF));
    }

    @Test
    public void setMessage() {
        final String newMessage = "new_message";
        selfListItem.setMessage(newMessage);
        errorCollector.checkThat(selfListItem.getMessage(), is(newMessage));
    }

    @Test
    public void setTime() {
        final long newTime = 12_345L;
        selfListItem.setTime(newTime);
        errorCollector.checkThat(selfListItem.getTime(), is(newTime));
    }

    @Test
    public void getContents() {
        Map<String, String> contentsMap = selfListItem.getContents();
        errorCollector.checkThat(contentsMap.isEmpty(), is(false));
        errorCollector.checkThat(contentsMap.get("message"), is(MESSAGE));
        errorCollector.checkThat(contentsMap.get("fadeBackground"), is(String.valueOf(fadeBackground)));
    }
}
