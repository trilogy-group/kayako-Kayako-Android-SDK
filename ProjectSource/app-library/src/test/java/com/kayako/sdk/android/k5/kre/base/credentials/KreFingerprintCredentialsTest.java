package com.kayako.sdk.android.k5.kre.base.credentials;

import android.text.TextUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.hamcrest.CoreMatchers.is;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static com.kayako.sdk.android.k5.kre.base.credentials.KreCredentials.Type;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TextUtils.class)
public class KreFingerprintCredentialsTest {

    private static final String FINGERPRINT_ID = "123BCA";
    private static final String REAL_TIME_URL = "/realTimeUrl";
    private static final String INSTANCE_URL = "/instanceUrl";
    private KreFingerprintCredentials fingerprintCredentials;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        mockStatic(TextUtils.class);
        when(TextUtils.isEmpty(Mockito.anyString())).thenReturn(false);
        fingerprintCredentials = new KreFingerprintCredentials(REAL_TIME_URL, INSTANCE_URL, FINGERPRINT_ID);
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        errorCollector.checkThat(fingerprintCredentials.getFingerprintId(), is(FINGERPRINT_ID));
        errorCollector.checkThat(fingerprintCredentials.getRealtimeUrl(), is(REAL_TIME_URL));
        errorCollector.checkThat(fingerprintCredentials.getInstanceUrl(), is(INSTANCE_URL));
        errorCollector.checkThat(fingerprintCredentials.getType(), is(Type.FINGERPRINT));
    }

    @Test
    public void whenBothObjectsSameThenEqual() {
        errorCollector.checkThat(fingerprintCredentials.equals(fingerprintCredentials), is(true));
    }

    @Test
    public void whenObjectNullThenFalse() {
        errorCollector.checkThat(fingerprintCredentials.equals(null), is(false));
    }

    @Test
    public void whenFingerPrintIdDifferentThenObjectDifferent() {
        //Arrange
        final String newFingerPrintId = "XYZ321";

        //Act
        final KreFingerprintCredentials fingerprintCredentialsLocal = new KreFingerprintCredentials(
                                    REAL_TIME_URL, INSTANCE_URL, newFingerPrintId);

        //Assert
        errorCollector.checkThat(fingerprintCredentials.equals(fingerprintCredentialsLocal), is(false));
    }

    @Test
    public void hashCodeCheck() {
        int expectedHashCode = (((((REAL_TIME_URL.hashCode()*31)+Type.FINGERPRINT.hashCode())*31)
                +INSTANCE_URL.hashCode())*31) + FINGERPRINT_ID.hashCode();
        errorCollector.checkThat(fingerprintCredentials.hashCode(), is(expectedHashCode));
    }
}
