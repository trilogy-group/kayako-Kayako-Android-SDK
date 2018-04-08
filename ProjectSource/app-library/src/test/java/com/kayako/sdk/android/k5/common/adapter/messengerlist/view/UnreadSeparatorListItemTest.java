package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

public class UnreadSeparatorListItemTest {

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        UnreadSeparatorListItem item = new UnreadSeparatorListItem();
        errorCollector.checkThat(item, notNullValue());
        errorCollector.checkThat(item.getItemType(), is(MessengerListType.UNREAD_SEPARATOR));
    }

    @Test
    public void whenValidParamsInConstructorThenObjectCreated() {
        String text = "some text";
        UnreadSeparatorListItem item = new UnreadSeparatorListItem(text);
        errorCollector.checkThat(item, notNullValue());
        errorCollector.checkThat(item.getItemType(), is(MessengerListType.UNREAD_SEPARATOR));
        errorCollector.checkThat(item.getText(), is(equalTo(text)));
    }

    @Test
    public void getText() {
        String text = "some text";
        UnreadSeparatorListItem item = new UnreadSeparatorListItem(text);
        errorCollector.checkThat(item.getText(), is(equalTo(text)));
    }

    @Test
    public void getContents() {
        String text = "some text";
        UnreadSeparatorListItem item = new UnreadSeparatorListItem(text);
        errorCollector.checkThat(item.getContents().size() > 0, is(true));
        errorCollector.checkThat(item.getContents().get("text"), is(text));
    }
}
