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

import static com.kayako.sdk.android.k5.kre.base.credentials.KreCredentials.Type;
import static org.hamcrest.CoreMatchers.is;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TextUtils.class)
public class KreFingerprintCredentialsTest {

    private static final String FINGERPRINT_ID = "123BCA";
    private static final String OTHER_FINGERPRINT_ID = "XYZ987";
    private static final String REAL_TIME_URL = "/realTimeUrl";
    private static final String INSTANCE_URL = "/instanceUrl";
    private KreFingerprintCredentials one;
    private KreFingerprintCredentials same;
    private KreFingerprintCredentials secondSame;
    private KreFingerprintCredentials other;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        mockStatic(TextUtils.class);
        when(TextUtils.isEmpty(Mockito.anyString())).thenReturn(false);
        one = new KreFingerprintCredentials(REAL_TIME_URL, INSTANCE_URL, FINGERPRINT_ID);
        same = new KreFingerprintCredentials(REAL_TIME_URL, INSTANCE_URL, FINGERPRINT_ID);
        secondSame = new KreFingerprintCredentials(REAL_TIME_URL, INSTANCE_URL, FINGERPRINT_ID);
        other = new KreFingerprintCredentials(REAL_TIME_URL, INSTANCE_URL, OTHER_FINGERPRINT_ID);
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        errorCollector.checkThat(one.getFingerprintId(), is(FINGERPRINT_ID));
        errorCollector.checkThat(one.getRealtimeUrl(), is(REAL_TIME_URL));
        errorCollector.checkThat(one.getInstanceUrl(), is(INSTANCE_URL));
        errorCollector.checkThat(one.getType(), is(Type.FINGERPRINT));
    }

    @Test
    public void whenFingerPrintIdDifferentThenObjectDifferent() {
        //Arrange
        final String newFingerPrintId = "XYZ321";

        //Act
        final KreFingerprintCredentials fingerprintCredentialsLocal = new KreFingerprintCredentials(
                                    REAL_TIME_URL, INSTANCE_URL, newFingerPrintId);

        //Assert
        errorCollector.checkThat(one.equals(fingerprintCredentialsLocal), is(false));
    }

    @Test
    public void relexivity() {
        errorCollector.checkThat(one, is(one));
    }

    @Test
    public void nullInequality() {
        errorCollector.checkThat(one.equals(null), is(false));
    }

    @Test
    public void symmetry() {
        errorCollector.checkThat(one, is(same));
        errorCollector.checkThat(same, is(one));
        errorCollector.checkThat(one.hashCode(), is(same.hashCode()));
        errorCollector.checkThat(one.equals(other), is(false));
        errorCollector.checkThat(other.equals(one), is(false));
    }

    @Test
    public void transitivity() {
        errorCollector.checkThat(one, is(same));
        errorCollector.checkThat(same, is(secondSame));
        errorCollector.checkThat(one, is(secondSame));
        errorCollector.checkThat(one.hashCode(), is(same.hashCode()));
        errorCollector.checkThat(one.hashCode(), is(secondSame.hashCode()));
    }
}
