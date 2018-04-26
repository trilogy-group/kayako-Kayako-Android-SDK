package com.kayako.sdk.android.k5.kre.base;

import static org.hamcrest.CoreMatchers.notNullValue;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

public class KreConnectionFactoryTest {

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Test
    public void getConnection() {
        final boolean agentTrue = Boolean.TRUE;
        final boolean agentFalse = Boolean.FALSE;
        final KreConnection sAgentKreConnection =
                KreConnectionFactory.getConnection(agentTrue);
        final KreConnection sMessengerKreConnection =
                KreConnectionFactory.getConnection(agentFalse);
        errorCollector.checkThat(sAgentKreConnection, notNullValue());
        errorCollector.checkThat(sMessengerKreConnection, notNullValue());
    }
}
