package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.is;
import java.util.HashMap;
import java.util.Map;

public class SimpleMessageContinuedOtherListItemTest {

    private final static String MESSAGE = "message for testing";
    private final Map<String, Object> data = new HashMap<>();
    private long id;
    private long time;
    private SimpleMessageContinuedOtherListItem continuedOtherListItem;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        id = 2L;
        time = 9_876L;
        continuedOtherListItem = new SimpleMessageContinuedOtherListItem(id, MESSAGE, time, data);
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated(){
        errorCollector.checkThat(continuedOtherListItem.getId(), is(id));
        errorCollector.checkThat(continuedOtherListItem.getTime(), is(time));
        errorCollector.checkThat(continuedOtherListItem.getData(), is(data));
        errorCollector.checkThat(continuedOtherListItem.getMessage(), is(MESSAGE));
        errorCollector.checkThat(continuedOtherListItem.getItemType(),
                is(MessengerListType.SIMPLE_MESSAGE_CONTINUED_OTHER));
    }

    @Test
    public void setMessage() {
        //Arrange
        final String newMessage = "Hello!!!";

        //Act
        continuedOtherListItem.setMessage(newMessage);

        //Assert
        errorCollector.checkThat(continuedOtherListItem.getMessage(), is(newMessage));
    }

    @Test
    public void setTime() {
        //Arrange
        final long newTime = 2_345L;

        //Act
        continuedOtherListItem.setTime(newTime);

        //Assert
        errorCollector.checkThat(continuedOtherListItem.getTime(), is(newTime));
    }

    @Test
    public void getContents() {
        final Map<String, String> contentsMap = continuedOtherListItem.getContents();
        errorCollector.checkThat(contentsMap.size(), is(2));
        errorCollector.checkThat(contentsMap.get("time"), is(String.valueOf(time)));
    }
}
