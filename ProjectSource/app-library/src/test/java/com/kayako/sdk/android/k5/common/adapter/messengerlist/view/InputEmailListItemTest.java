package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.mock;

public class InputEmailListItemTest {

    private InputEmailListItem.OnClickSubmitListener onClickSubmitListener;
    private static final String SUBMITTEDEMAIL  = "abc@xyz.com";

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp(){
        onClickSubmitListener = mock(InputEmailListItem.OnClickSubmitListener.class);
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        InputEmailListItem emailListItem = new InputEmailListItem(onClickSubmitListener);
        errorCollector.checkThat(emailListItem, notNullValue());
        errorCollector.checkThat(emailListItem.getOnClickSubmitListener(), is(onClickSubmitListener));
        errorCollector.checkThat(emailListItem.getItemType(), is(equalTo(MessengerListType.INPUT_FIELD_EMAIL)));
    }

    @Test
    public void whenValidParamsInConstructorGivenThenObjectCreated() {
        InputEmailListItem emailListItem = new InputEmailListItem(SUBMITTEDEMAIL);
        errorCollector.checkThat(emailListItem, notNullValue());
        errorCollector.checkThat(emailListItem.getItemType(), is(equalTo(MessengerListType.INPUT_FIELD_EMAIL)));
        errorCollector.checkThat(emailListItem.getSubmittedValue(), is(equalTo(SUBMITTEDEMAIL)));
    }

    @Test
    public void getOnClickSubmitListener(){
        InputEmailListItem emailListItem = new InputEmailListItem(onClickSubmitListener);
        errorCollector.checkThat(emailListItem.getOnClickSubmitListener(), is(onClickSubmitListener));
    }

    @Test
    public void getContents() {
        InputEmailListItem emailListItem = new InputEmailListItem(SUBMITTEDEMAIL);
        errorCollector.checkThat(emailListItem.getContents().size() > 0 , is(true));
        errorCollector.checkThat(emailListItem.getContents().size(), is(2));
        errorCollector.checkThat(emailListItem.getContents().get("getSubmittedValue"), is(equalTo(SUBMITTEDEMAIL)));
    }
}
