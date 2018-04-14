package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

public class UnreadSeparatorListItemTest {

    private final static String TEXT = "some text";
    private UnreadSeparatorListItem item;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        item = new UnreadSeparatorListItem(TEXT);
    }

    @Test
    public void whenValidParamsInConstructorThenObjectCreated() {
        errorCollector.checkThat(item.getItemType(), is(MessengerListType.UNREAD_SEPARATOR));
        errorCollector.checkThat(item.getText(), is(equalTo(TEXT)));
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        UnreadSeparatorListItem itemLocal = new UnreadSeparatorListItem();
        errorCollector.checkThat(itemLocal.getItemType(), is(MessengerListType.UNREAD_SEPARATOR));
    }

    @Test
    public void getContents() {
        errorCollector.checkThat(item.getContents().isEmpty(), is(false));
        errorCollector.checkThat(item.getContents().get("text"), is(TEXT));
    }
}
