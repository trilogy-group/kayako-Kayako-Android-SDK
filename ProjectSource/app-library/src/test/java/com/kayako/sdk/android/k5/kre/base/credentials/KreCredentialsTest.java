package com.kayako.sdk.android.k5.kre.base.credentials;

import android.text.TextUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static com.kayako.sdk.android.k5.kre.base.credentials.KreCredentials.Type;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TextUtils.class)
public class KreCredentialsTest {

    private static final String REAL_TIME_URL = "/realTimeUrl";
    private static final String INSTANCE_URL = "/instanceUrl";
    private static final String EXCEPTION_MESSAGE = "Null Values are not allowed";
    private Type type;
    private KreCredentials kreCredentials;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        type = Type.FINGERPRINT;
        mockStatic(TextUtils.class);
        when(TextUtils.isEmpty(Mockito.anyString())).thenReturn(false);
        kreCredentials = new KreCredentials(REAL_TIME_URL, type, INSTANCE_URL);
    }

    @Test
    public void whenValidPramsConstructorThenObjectCreated() {
        errorCollector.checkThat(kreCredentials.getInstanceUrl(), is(INSTANCE_URL));
        errorCollector.checkThat(kreCredentials.getRealtimeUrl(), is(REAL_TIME_URL));
        errorCollector.checkThat(kreCredentials.getType(), not(Type.SESSION));
        errorCollector.checkThat(kreCredentials.getType(), is(Type.FINGERPRINT));
    }

    @Test
    public void whenTypeNullThenIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(EXCEPTION_MESSAGE);
        new KreCredentials(REAL_TIME_URL, null, INSTANCE_URL);
    }

    @Test
    public void whenRealTimeUrlNullThenIllegalArgumentException() {
        when(TextUtils.isEmpty(null)).thenReturn(true);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(EXCEPTION_MESSAGE);
        new KreCredentials(null, type, INSTANCE_URL);
    }

    @Test
    public void whenInstanceUrlNullThenIllegalArgumentException() {
        when(TextUtils.isEmpty(null)).thenReturn(true);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(EXCEPTION_MESSAGE);
        new KreCredentials(REAL_TIME_URL, type, null);
    }

    @Test
    public void whenBothObjectsSameThenEqual() {
        errorCollector.checkThat(kreCredentials.equals(kreCredentials), is(true));
    }

    @Test
    public void whenObjectNullThenFalse() {
        errorCollector.checkThat(kreCredentials.equals(null), is(false));
    }

    @Test
    public void whenRealTimeUrlNotSameThenFalse() {
        //Arrange
        final String newRealTimeUrl = "/newRealTimeUrl";

        //Act
        final KreCredentials kreCredentialsLocal = new KreCredentials(newRealTimeUrl, type, INSTANCE_URL);

        //Assert
        errorCollector.checkThat(kreCredentials.equals(kreCredentialsLocal), is(false));
    }

    @Test
    public void whenTypeNotSameThenBothObjectDifferent() {
        //Arrange
        final Type newType = Type.SESSION;

        //Act
        final KreCredentials kreCredentialsLocal = new KreCredentials(REAL_TIME_URL, newType, INSTANCE_URL);

        //Assert
        errorCollector.checkThat(kreCredentials.equals(kreCredentialsLocal), is(false));
    }

    @Test
    public void whenInstanceUrlNotSameThenBothObjectsDifferent() {
        //Arrange
        final String newInstanceUrl = "/newInstanceUrl";

        //Act
        final KreCredentials kreCredentialsLocal = new KreCredentials(REAL_TIME_URL, type, newInstanceUrl);

        //Assert
        errorCollector.checkThat(kreCredentials.equals(kreCredentialsLocal), is(false));
    }

    @Test
    public void hashCodeValidate() {
        int expectedHashCode = (((REAL_TIME_URL.hashCode()*31)+type.hashCode())*31)
                                +INSTANCE_URL.hashCode();
        errorCollector.checkThat(kreCredentials.hashCode(), is(expectedHashCode));
    }
}
