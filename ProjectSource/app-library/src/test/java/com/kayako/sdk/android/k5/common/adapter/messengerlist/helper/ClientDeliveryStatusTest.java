package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.is;

public class ClientDeliveryStatusTest {

    private static final String SENDING_STRING = "SENDING";
    private static final String FAILED_TO_SEND_STRING = "FAILED_TO_SEND";

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Test
    public void givenClientDeliveryStatusWhenNameThenExpected() {
        errorCollector.checkThat(ClientDeliveryStatus.SENDING.name(), is(SENDING_STRING));
        errorCollector.checkThat(ClientDeliveryStatus.FAILED_TO_SEND.name(), is(FAILED_TO_SEND_STRING));
    }
}
