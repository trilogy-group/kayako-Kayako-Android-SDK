package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.hamcrest.CoreMatchers.is;

@RunWith(MockitoJUnitRunner.class)
public class InputEmailListItemTest {

    private static final String SUBMITTED_EMAIL  = "abc@xyz.com";
    private InputEmailListItem emailListItem;

    @Mock
    private InputEmailListItem.OnClickSubmitListener onClickSubmitListener;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        emailListItem = new InputEmailListItem(SUBMITTED_EMAIL);
    }

    @Test
    public void whenValidParamsInConstructorGivenThenObjectCreated() {
        errorCollector.checkThat(emailListItem.getItemType(), is(MessengerListType.INPUT_FIELD_EMAIL));
        errorCollector.checkThat(emailListItem.getSubmittedValue(), is(SUBMITTED_EMAIL));
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        final InputEmailListItem emailListItemLocal = new InputEmailListItem(onClickSubmitListener);
        errorCollector.checkThat(emailListItemLocal.getOnClickSubmitListener(), is(onClickSubmitListener));
        errorCollector.checkThat(emailListItemLocal.getItemType(), is(MessengerListType.INPUT_FIELD_EMAIL));
    }

    @Test
    public void getContents() {
        errorCollector.checkThat(emailListItem.getContents().size(), is(2));
        errorCollector.checkThat(emailListItem.getContents().get("getSubmittedValue"), is(SUBMITTED_EMAIL));
    }
}
