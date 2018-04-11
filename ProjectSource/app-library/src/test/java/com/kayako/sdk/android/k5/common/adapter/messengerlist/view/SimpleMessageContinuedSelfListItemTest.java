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

public class SimpleMessageContinuedSelfListItemTest {

    private static final String MESSAGE = "dummy message";
    private Map<String, Object> data = new HashMap<>();
    private long id;
    private long time;
    private DeliveryIndicator deliveryIndicator;
    private boolean fadeBackground;
    private SimpleMessageContinuedSelfListItem simpleMessageContinuedSelfListItem;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        id = 1L;
        time = 2_222L;
        deliveryIndicator = new DeliveryIndicator(1, 1, 1_000L);
        fadeBackground = true;
        simpleMessageContinuedSelfListItem =
                new SimpleMessageContinuedSelfListItem(id, MESSAGE, time, deliveryIndicator, fadeBackground, data);
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        errorCollector.checkThat(simpleMessageContinuedSelfListItem.getId(), is(id));
        errorCollector.checkThat(simpleMessageContinuedSelfListItem.getMessage(), is(MESSAGE));
        errorCollector.checkThat(simpleMessageContinuedSelfListItem.getTime(), is(time));
        errorCollector.checkThat(simpleMessageContinuedSelfListItem.getDeliveryIndicator(), is(deliveryIndicator));
        errorCollector.checkThat(simpleMessageContinuedSelfListItem.isFadeBackground(), is(fadeBackground));
        errorCollector.checkThat(simpleMessageContinuedSelfListItem.getData(), is(data));
        errorCollector.checkThat(simpleMessageContinuedSelfListItem.getItemType(),
                    is(MessengerListType.SIMPLE_MESSAGE_CONTINUED_SELF));
    }

    @Test
    public void setMessage() {
        final String newMessage = "new_message";
        simpleMessageContinuedSelfListItem.setMessage(newMessage);
        errorCollector.checkThat(simpleMessageContinuedSelfListItem.getMessage(), is(newMessage));
    }

    @Test
    public void setTime() {
        final long newTime = 12_345L;
        simpleMessageContinuedSelfListItem.setTime(newTime);
        errorCollector.checkThat(simpleMessageContinuedSelfListItem.getTime(), is(newTime));
    }

    @Test
    public void getContents() {
        Map<String, String> contentsMap = simpleMessageContinuedSelfListItem.getContents();
        errorCollector.checkThat(contentsMap.size(), is(6));
        errorCollector.checkThat(contentsMap.get("message"), is(MESSAGE));
        errorCollector.checkThat(contentsMap.get("fadeBackground"), is(String.valueOf(fadeBackground)));
    }
}
