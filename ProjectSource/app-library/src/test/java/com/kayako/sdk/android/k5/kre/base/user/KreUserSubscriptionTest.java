package com.kayako.sdk.android.k5.kre.base.user;

import com.kayako.sdk.android.k5.kre.base.KreConnection;
import com.kayako.sdk.android.k5.kre.base.KreSubscription;
import com.kayako.sdk.android.k5.kre.base.credentials.KreCredentials;
import com.kayako.sdk.android.k5.kre.helpers.RawUserOnlinePresenceListener;
import static junit.framework.Assert.assertEquals;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import static junit.framework.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.suppress;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import java.util.List;

@PrepareForTest(KreSubscription.class)
@RunWith(PowerMockRunner.class)
public class KreUserSubscriptionTest {

    private static final String NAME = "test name";
    private static final String USER_PRESENCE_CHANNEL = "test channel";
    private static final long USER_ID = 1L;
    private KreUserSubscription kreUserSubscription;

    @Mock
    private KreConnection kreConnection;

    @Mock
    private RawUserOnlinePresenceListener listener;

    @Mock
    private KreCredentials kreCredentials;

    @Mock
    private KreSubscription.OnSubscriptionListener onSubscriptionListener;

    @Mock
    private KreSubscription kreSubscription;

    @Captor
    private ArgumentCaptor<Boolean> booleanArgumentCaptor;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        kreUserSubscription = new KreUserSubscription(kreConnection, NAME);
    }
    @Test
    public void whenNullKreConnectionThenException() {
        //Arrange
        final KreConnection kreConnection = null;
        final Matcher<String> nullMatcher = new IsNull<>();

        //Assert
        thrown.expectMessage(nullMatcher);
        thrown.expect(IllegalArgumentException.class);

        //Act
        new KreUserSubscription(kreConnection, NAME);
    }

    @Test
    public void addUserOnlinePresenceListener() {
        //Act
        kreUserSubscription.addUserOnlinePresenceListener(listener);

        //Assert
        final List<RawUserOnlinePresenceListener> mOnlinePresenceListeners =
                Whitebox.getInternalState(kreUserSubscription, "mOnlinePresenceListeners");
        assertEquals(1, mOnlinePresenceListeners.size());
    }

    @Test
    public void subscribe() {
        //Act
        kreUserSubscription.subscribe(kreCredentials, USER_PRESENCE_CHANNEL, USER_ID, onSubscriptionListener);

        //Assert
        final List<KreSubscription.OnSubscriptionListener> mChildListeners =
                Whitebox.getInternalState(kreUserSubscription, "mChildListeners");
        assertEquals(1, mChildListeners.size());
    }

    @Test
    public void unSubscribe() {
        //Arrange
        suppress(method(KreSubscription.class, "runUnSubscribeTask"));

        //Act
        kreUserSubscription.subscribe(kreCredentials, USER_PRESENCE_CHANNEL, USER_ID, onSubscriptionListener);
        kreUserSubscription.unSubscribe(onSubscriptionListener);

        //Assert
        final List<KreSubscription.OnSubscriptionListener> mChildListeners =
                Whitebox.getInternalState(kreUserSubscription, "mChildListeners");
        assertTrue(mChildListeners.isEmpty());
    }

    @Test
    public void isReady() {
        //Arrange
        Whitebox.setInternalState(kreUserSubscription, "mKreSubscription", kreSubscription);
        Mockito.when(kreSubscription.isConnected()).thenReturn(true);
        Mockito.when(kreSubscription.hasSubscribed()).thenReturn(true);

        //Act & Assert
        assertEquals(true, kreUserSubscription.isReady());
    }

    @Test
    public void configureReconnection() {
        //Arrange
        final boolean allowReconnectionsOnFailure = true;
        Whitebox.setInternalState(kreUserSubscription, "mKreSubscription", kreSubscription);

        //Act
        kreUserSubscription.configureReconnection(allowReconnectionsOnFailure);


        Mockito.verify(kreSubscription).configureReconnectOnFailure(booleanArgumentCaptor.capture());
        assertEquals(allowReconnectionsOnFailure, booleanArgumentCaptor.getValue().booleanValue());
    }
}
