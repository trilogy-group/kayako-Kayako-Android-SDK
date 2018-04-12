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

import static com.kayako.sdk.android.k5.kre.base.credentials.KreCredentials.Type;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TextUtils.class)
public class KreCredentialsTest {

    private static final String REAL_TIME_URL = "/realTimeUrl";
    private static final String INSTANCE_URL = "/instanceUrl";
    private static final String NEW_INSTANCE_URL = "/newInstanceUrl";
    private static final String EXCEPTION_MESSAGE = "Null Values are not allowed";
    private Type type;
    private KreCredentials one;
    private KreCredentials same;
    private KreCredentials secondSame;
    private KreCredentials other;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        type = Type.FINGERPRINT;
        mockStatic(TextUtils.class);
        when(TextUtils.isEmpty(Mockito.anyString())).thenReturn(false);
        one = new KreCredentials(REAL_TIME_URL, type, INSTANCE_URL);
        same = new KreCredentials(REAL_TIME_URL, type, INSTANCE_URL);
        secondSame = new KreCredentials(REAL_TIME_URL, type, INSTANCE_URL);
        other = new KreCredentials(REAL_TIME_URL, type, NEW_INSTANCE_URL);
    }

    @Test
    public void whenValidPramsConstructorThenObjectCreated() {
        errorCollector.checkThat(one.getInstanceUrl(), is(INSTANCE_URL));
        errorCollector.checkThat(one.getRealtimeUrl(), is(REAL_TIME_URL));
        errorCollector.checkThat(one.getType(), not(Type.SESSION));
        errorCollector.checkThat(one.getType(), is(Type.FINGERPRINT));
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
    public void whenRealTimeUrlNotSameThenFalse() {
        //Arrange
        final String newRealTimeUrl = "/newRealTimeUrl";

        //Act
        final KreCredentials kreCredentialsLocal = new KreCredentials(newRealTimeUrl, type, INSTANCE_URL);

        //Assert
        errorCollector.checkThat(one.equals(kreCredentialsLocal), is(false));
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
