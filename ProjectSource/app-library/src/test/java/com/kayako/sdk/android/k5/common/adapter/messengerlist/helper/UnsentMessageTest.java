package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import com.kayako.sdk.android.k5.common.utils.file.FileAttachment;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.containsString;

@RunWith(MockitoJUnitRunner.class)
public class UnsentMessageTest {

    private static final String CLIENT_ID = "123ABC";
    private static final String MESSAGE = "message";
    private static final String INVALID_ARGUMENTS = "Invalid Arguments";
    private ClientDeliveryStatus deliveryStatus;

    @Mock
    private FileAttachment attachment;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setup() {
        deliveryStatus = ClientDeliveryStatus.SENDING;
    }

    @Test
    public void whenNullMessageThenIllegalArgumentException() {
        final String newMessage = null;
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(containsString(INVALID_ARGUMENTS));
        new UnsentMessage(newMessage, deliveryStatus, CLIENT_ID);
    }

    @Test
    public void whenNullDeliveryStatusThenIllegalArgumentException() {
        deliveryStatus = null;
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(containsString(INVALID_ARGUMENTS));
        new UnsentMessage(MESSAGE, deliveryStatus, CLIENT_ID);
    }

    @Test
    public void whenNullClientIdThenIllegalArgumentException() {
        final String newClientId = null;
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(containsString(INVALID_ARGUMENTS));
        new UnsentMessage(MESSAGE, deliveryStatus, newClientId);
    }

    @Test
    public void whenNullAttachmentThenIllegalArgumentException() {
        attachment = null;
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(containsString(INVALID_ARGUMENTS));
        new UnsentMessage(attachment, deliveryStatus, CLIENT_ID);
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        final UnsentMessage unsentMessage = new UnsentMessage(attachment, deliveryStatus, CLIENT_ID);
        errorCollector.checkThat(unsentMessage.getClientId(), is(CLIENT_ID));
        errorCollector.checkThat(unsentMessage.getAttachment(), is(attachment));
        errorCollector.checkThat(unsentMessage.getDeliveryStatus(), is(deliveryStatus));
        errorCollector.checkThat(unsentMessage.getMessage(), nullValue());
    }

    @Test
    public void whenValidParamsGivenInConstructorThenObjectCreated() {
        final UnsentMessage unsentMessageLocal = new UnsentMessage(MESSAGE, deliveryStatus, CLIENT_ID);
        errorCollector.checkThat(unsentMessageLocal.getClientId(), is(CLIENT_ID));
        errorCollector.checkThat(unsentMessageLocal.getDeliveryStatus(), is(ClientDeliveryStatus.SENDING));
        errorCollector.checkThat(unsentMessageLocal.getMessage(), is(MESSAGE));
        errorCollector.checkThat(unsentMessageLocal.getAttachment(), nullValue());
    }
}
