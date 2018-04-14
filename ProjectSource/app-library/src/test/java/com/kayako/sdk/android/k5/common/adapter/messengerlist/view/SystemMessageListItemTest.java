package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;

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
    public void whenValidParamsConstructorThenObjectCreated() {
        errorCollector.checkThat(messageListItem.getItemType(), is(MessengerListType.SYSTEM_MESSAGE));
        errorCollector.checkThat(messageListItem.getMessage(), is(MESSAGE));
    }

    @Test
    public void whenNullMessageThenIllegalArgumentException() {
        final Matcher<String> nullMatcher = new IsNull<>();
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(nullMatcher);
        new SystemMessageListItem(null);
    }

    @Test
    public void whenEmptyMessageThenIllegalArgumentException() {
        final Matcher<String> nullMatcher = new IsNull<>();
        final String emptyString = "";
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(nullMatcher);
        new SystemMessageListItem(emptyString);
    }

    @Test
    public void getContents() {
        Map<String, String> contentsMap = messageListItem.getContents();
        errorCollector.checkThat(contentsMap.size(), is(1));
        errorCollector.checkThat(contentsMap.get("message"), is(MESSAGE));
    }
}
