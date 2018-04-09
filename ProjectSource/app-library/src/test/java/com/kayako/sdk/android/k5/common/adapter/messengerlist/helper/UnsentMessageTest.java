package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import com.kayako.sdk.android.k5.common.utils.file.FileAttachment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(MockitoJUnitRunner.class)
public class UnsentMessageTest {

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Mock
    private FileAttachment attachment;

    private ClientDeliveryStatus deliveryStatus;
    private String clientId;
    private String message;

    @Before
    public void setup() {
        deliveryStatus = ClientDeliveryStatus.SENDING;
        clientId = "clientId";
        message = "message";
    }

    /* message as null*/
    @Test(expected = IllegalArgumentException.class)
    public void whenNullMessageThenIllegalArgumentException() {
        message = null;
        UnsentMessage unsentMessage = new UnsentMessage(message, deliveryStatus, clientId);
    }

    /* deliveryStatus as null*/
    @Test(expected = IllegalArgumentException.class)
    public void whenNullDeliveryStatusThenIllegalArgumentException() {
        deliveryStatus = null;
        UnsentMessage unsentMessage = new UnsentMessage(message, deliveryStatus, clientId);
    }

    /* deliveryStatus as null*/
    @Test(expected = IllegalArgumentException.class)
    public void whenNullClientIdThenIllegalArgumentException() {
        clientId = null;
        UnsentMessage unsentMessage = new UnsentMessage(message, deliveryStatus, clientId);
    }

    /* attachment as null*/
    @Test(expected = IllegalArgumentException.class)
    public void whenNullAttachmentThenIllegalArgumentException() {
        attachment = null;
        UnsentMessage unsentMessage = new UnsentMessage(attachment, deliveryStatus, clientId);
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        UnsentMessage unsentMessage = new UnsentMessage(attachment, deliveryStatus, clientId);
        errorCollector.checkThat(unsentMessage, notNullValue());
        errorCollector.checkThat(unsentMessage.getClientId(), is(clientId));
        errorCollector.checkThat(unsentMessage.getAttachment(), notNullValue());
    }

    @Test
    public void whenValidParamsGivenInConstructorThenObjectCreated() {
        UnsentMessage unsentMessage = new UnsentMessage(message, deliveryStatus, clientId);
        errorCollector.checkThat(unsentMessage, notNullValue());
        errorCollector.checkThat(unsentMessage.getClientId(), is(clientId));
        errorCollector.checkThat(unsentMessage.getDeliveryStatus(), notNullValue());
        errorCollector.checkThat(unsentMessage.getAttachment(), nullValue());
    }

    @Test
    public void getDeliveryStatus() {
        UnsentMessage unsentMessage = new UnsentMessage(message, deliveryStatus, clientId);
        errorCollector.checkThat(unsentMessage.getDeliveryStatus(), is(equalTo(deliveryStatus)));
    }

    @Test
    public void getMessage() {
        UnsentMessage unsentMessage = new UnsentMessage(message, deliveryStatus, clientId);
        errorCollector.checkThat(unsentMessage.getMessage(), is(equalTo(message)));
    }

    @Test
    public void getClientId() {
        UnsentMessage unsentMessage = new UnsentMessage(message, deliveryStatus, clientId);
        errorCollector.checkThat(unsentMessage.getClientId(), is(clientId));
    }

    @Test
    public void getAttachment() {
        UnsentMessage unsentMessage = new UnsentMessage(attachment, deliveryStatus, clientId);
        errorCollector.checkThat(unsentMessage.getAttachment(), is(attachment));
    }
}
