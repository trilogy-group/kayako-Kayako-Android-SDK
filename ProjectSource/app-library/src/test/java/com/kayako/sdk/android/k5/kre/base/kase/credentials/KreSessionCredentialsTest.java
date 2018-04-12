package com.kayako.sdk.android.k5.kre.base.kase.credentials;

import android.text.TextUtils;

import com.kayako.sdk.android.k5.kre.base.credentials.KreCredentials;
import com.kayako.sdk.android.k5.kre.base.credentials.KreSessionCredentials;

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
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TextUtils.class)
public class KreSessionCredentialsTest {

    private static final String REAL_TIME_URL = "/realTimeUrl";
    private static final String INSTANCE_URL = "/instanceUrl";
    private static final String SESSION_ID = "12345EDCBA";
    private static final String USER_AGENT = "dummy_user_agent";
    private static final String NEW_USER_AGENT = "New dummy_user_agent";
    private static final String EXCEPTION_MESSAGE = "Null values are not allowed";
    private KreSessionCredentials one;
    private KreSessionCredentials same;
    private KreSessionCredentials secondSame;
    private KreSessionCredentials other;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        mockStatic(TextUtils.class);
        when(TextUtils.isEmpty(Mockito.anyString())).thenReturn(false);
        one = new KreSessionCredentials(
                REAL_TIME_URL, SESSION_ID, INSTANCE_URL, USER_AGENT);
        same = new KreSessionCredentials(
                REAL_TIME_URL, SESSION_ID, INSTANCE_URL, USER_AGENT);
        secondSame = new KreSessionCredentials(
                REAL_TIME_URL, SESSION_ID, INSTANCE_URL, USER_AGENT);
        other = new KreSessionCredentials(
                REAL_TIME_URL, SESSION_ID, INSTANCE_URL, NEW_USER_AGENT);
    }

    @Test
    public void whenValidPramsConstructorThenObjectCreated() {
        errorCollector.checkThat(one.getRealtimeUrl(), is(REAL_TIME_URL));
        errorCollector.checkThat(one.getSessionId(), is(SESSION_ID));
        errorCollector.checkThat(one.getInstanceUrl(), is(INSTANCE_URL));
        errorCollector.checkThat(one.getUserAgent(), is(USER_AGENT));
        errorCollector.checkThat(one.getType(), is(KreCredentials.Type.SESSION));
    }

    @Test
    public void whenSessionIdNullThenIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(EXCEPTION_MESSAGE);
        new KreSessionCredentials(REAL_TIME_URL, null, INSTANCE_URL, USER_AGENT);
    }

    @Test
    public void whenUserAgentNullThenIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(EXCEPTION_MESSAGE);
        new KreSessionCredentials(REAL_TIME_URL, SESSION_ID, INSTANCE_URL, null);
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
