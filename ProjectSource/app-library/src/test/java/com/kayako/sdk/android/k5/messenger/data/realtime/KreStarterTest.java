package com.kayako.sdk.android.k5.messenger.data.realtime;

import static org.hamcrest.CoreMatchers.is;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

public class KreStarterTest {

    private static final String REAL_TIME_URL = "/realTimeUrl";
    private static final long CURRENT_USER_ID = 1L;
    private static final String INSTANCE_URL = "/instanceUrl";
    private static final String FINGERPRINT_ID = "123ABC";
    private static final String EXCEPTION_MESSAGE =
            "Invalid Arguments. All of these are mandatory!";

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void whenCurrentUserIdZeroThenException() {
        //Arrange
        final long newUserId = 0L;

        //Assert
        thrown.expectMessage(EXCEPTION_MESSAGE);
        thrown.expect(IllegalArgumentException.class);

        //Act
        new KreStarter(REAL_TIME_URL, newUserId, INSTANCE_URL, FINGERPRINT_ID);
    }

    @Test
    public void whenNullInstanceUrlThenException() {
        //Arrange
        final String newInstanceUrl = null;

        //Assert
        thrown.expectMessage(EXCEPTION_MESSAGE);
        thrown.expect(IllegalArgumentException.class);

        //Act
        new KreStarter(REAL_TIME_URL, CURRENT_USER_ID, newInstanceUrl, FINGERPRINT_ID);
    }

    @Test
    public void whenNullFingerprintIdThenException() {
        //Arrange
        final String newFingerprintId = null;

        //Assert
        thrown.expectMessage(EXCEPTION_MESSAGE);
        thrown.expect(IllegalArgumentException.class);

        //Act
        new KreStarter(REAL_TIME_URL, CURRENT_USER_ID, INSTANCE_URL, newFingerprintId);
    }

    @Test
    public void whenNullRealTimeUrlThenException() {
        //Arrange
        final String newRealTimeUrl = null;

        //Assert
        thrown.expectMessage(EXCEPTION_MESSAGE);
        thrown.expect(IllegalArgumentException.class);

        //Act
        new KreStarter(newRealTimeUrl, CURRENT_USER_ID, INSTANCE_URL, FINGERPRINT_ID);
    }

    @Test
    public void whenValidParamsConstructorObjectCreated() {
        //Act
        final KreStarter kreStarter = new KreStarter(
                REAL_TIME_URL, CURRENT_USER_ID, INSTANCE_URL, FINGERPRINT_ID);

        //Assert
        errorCollector.checkThat(kreStarter.getCurrentUserId(), is(CURRENT_USER_ID));
        errorCollector.checkThat(kreStarter.getFingerprintId(), is(FINGERPRINT_ID));
        errorCollector.checkThat(kreStarter.getInstanceUrl(), is(INSTANCE_URL));
        errorCollector.checkThat(kreStarter.getRealtimeUrl(), is(REAL_TIME_URL));
    }
}
