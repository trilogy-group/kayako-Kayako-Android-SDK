package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.ClientIdHelper.MessageType;
import static org.hamcrest.CoreMatchers.is;

public class ClientIdHelperTest {

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Test
    public void generateClientId() {
        //Arrange
        final String android = "android";
        final ClientIdHelper idHelperFirst = new ClientIdHelper();
        final ClientIdHelper idHelperSecond = new ClientIdHelper();

        //Act
        String firstClientId = idHelperFirst.generateClientId(MessageType.MESSAGE);
        String secondClientId = idHelperSecond.generateClientId(MessageType.ATTACHMENT);

        //Assert
        errorCollector.checkThat(firstClientId.contains(MessageType.MESSAGE.name()), is(true));
        errorCollector.checkThat(secondClientId.contains(MessageType.ATTACHMENT.name()), is(true));
        errorCollector.checkThat(firstClientId.contains(android), is(true));
        errorCollector.checkThat(secondClientId.contains(android), is(true));
    }
}
