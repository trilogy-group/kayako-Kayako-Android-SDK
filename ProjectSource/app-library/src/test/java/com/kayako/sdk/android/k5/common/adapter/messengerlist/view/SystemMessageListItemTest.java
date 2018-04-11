package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

public class SystemMessageListItemTest {

    private static final String MESSAGE = "dummy message";
    private SystemMessageListItem messageListItem;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        messageListItem = new SystemMessageListItem(MESSAGE);
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated(){
        errorCollector.checkThat(messageListItem.getItemType(), is(equalTo(MessengerListType.SYSTEM_MESSAGE)));
        errorCollector.checkThat(messageListItem.getMessage(), is(equalTo(MESSAGE)));
    }

    @Test
    public void whenNullMessageThenIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        SystemMessageListItem listItem = new SystemMessageListItem(null);
    }

    @Test
    public void whenEmptyMessageThenIllegalArgumentException() {
        final String emptyString = "";
        thrown.expect(IllegalArgumentException.class);
        SystemMessageListItem listItem = new SystemMessageListItem(emptyString);
    }

    @Test
    public void getContents() {
        Map<String, String> contentsMap = messageListItem.getContents();
        errorCollector.checkThat(contentsMap.size(), is(1));
        errorCollector.checkThat(contentsMap.get("message"), is(equalTo(MESSAGE)));
    }
}