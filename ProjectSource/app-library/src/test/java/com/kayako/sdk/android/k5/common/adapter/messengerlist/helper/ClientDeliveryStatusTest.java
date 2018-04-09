package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

public class ClientDeliveryStatusTest {

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();
    @Test
    public void test_enum() {
        errorCollector.checkThat(ClientDeliveryStatus.SENDING.name(), is(equalTo("SENDING")));
        errorCollector.checkThat(ClientDeliveryStatus.FAILED_TO_SEND.name(), is(equalTo("FAILED_TO_SEND")));
    }
}
