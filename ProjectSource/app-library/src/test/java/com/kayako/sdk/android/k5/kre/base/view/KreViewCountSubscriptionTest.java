package com.kayako.sdk.android.k5.kre.base.view;

import com.kayako.sdk.android.k5.kre.base.KreConnection;
import com.kayako.sdk.android.k5.kre.base.KreSubscription;
import com.kayako.sdk.android.k5.kre.base.credentials.KreCredentials;
import com.kayako.sdk.android.k5.kre.data.ViewCountChange;
import com.kayako.sdk.android.k5.kre.helpers.RawChangeListener;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertEquals;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.suppress;
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import java.util.List;
import java.util.Set;

@PrepareForTest(KreSubscription.class)
@RunWith(PowerMockRunner.class)
public class KreViewCountSubscriptionTest {

    private static final String NAME = "name";
    private static final String CHANNEL = "channel";
    private KreViewCountSubscription kreViewCountSubscription;

    @Mock
    private KreConnection kreConnection;

    @Mock
    private RawChangeListener listener;

    @Mock
    private KreCredentials kreCredentials;

    @Mock
    private KreSubscription.OnSubscriptionListener onSubscriptionListener;

    @Mock
    private KreSubscription mKreSubscription;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void whenValidParamsConstructorThenObjectCreated() {
        kreViewCountSubscription = new KreViewCountSubscription(kreConnection, NAME);
    }

    @Test
    public void whenNullKreConnectionConnectionThenException() {
        //Arrange
        final KreConnection kreConnectionLocal = null;
        final Matcher<String> nullMatcher = new IsNull<>();

        //Assert
        thrown.expectMessage(nullMatcher);
        thrown.expect(IllegalArgumentException.class);

        //Act
        new KreViewCountSubscription(kreConnectionLocal, NAME);
    }

    @Test
    public void addRawChangeListener() {
        //Act
        kreViewCountSubscription.addRawChangeListener(listener);

        //Assert
        final Set<RawChangeListener<ViewCountChange>> mRawChangeListeners =
                Whitebox.getInternalState(kreViewCountSubscription, "mRawChangeListeners");
        assertEquals(1, mRawChangeListeners.size());
    }

    @Test
    public void removeRawChangeListener() {
        //Act
        kreViewCountSubscription.addRawChangeListener(listener);
        kreViewCountSubscription.removeRawChangeListener(listener);

        //Assert
        final Set<RawChangeListener<ViewCountChange>> mRawChangeListeners =
                Whitebox.getInternalState(kreViewCountSubscription, "mRawChangeListeners");
        assertTrue(mRawChangeListeners.isEmpty());
    }

    @Test
    public void subscribe() {
        //Act
        kreViewCountSubscription.subscribe(kreCredentials, CHANNEL, onSubscriptionListener);

        //Assert
        final List<KreSubscription.OnSubscriptionListener> mChildListeners =
                Whitebox.getInternalState(kreViewCountSubscription, "mChildListeners");
        assertEquals(1, mChildListeners.size());
    }

    @Test
    public void unSubscribe() {
        //Arrange
        suppress(MemberMatcher.method(KreSubscription.class, "runUnSubscribeTask"));

        //Act
        kreViewCountSubscription.subscribe(kreCredentials, CHANNEL, onSubscriptionListener);
        kreViewCountSubscription.unSubscribe(onSubscriptionListener);

        //Assert
        final List<KreSubscription.OnSubscriptionListener> mChildListeners =
                Whitebox.getInternalState(kreViewCountSubscription, "mChildListeners");
        assertTrue(mChildListeners.isEmpty());
    }

    @Test
    public void isReady() {
        //Arrange
        Whitebox.setInternalState(kreViewCountSubscription, "mKreSubscription", mKreSubscription);
        when(mKreSubscription.isConnected()).thenReturn(true);
        when(mKreSubscription.hasSubscribed()).thenReturn(true);

        //Act & Assert
        assertTrue(kreViewCountSubscription.isReady());
    }

    @Test
    public void whenKreSubscriptionNullThenGetMainListenerThrowException() {
        //Arrange
        final String exceptionMessage =
                "This method should only be called once kreSubscription is initialized";
        final KreSubscription kreSubscriptionLocal = null;
        Whitebox.setInternalState(kreViewCountSubscription, "mKreSubscription", kreSubscriptionLocal);

        //Assert
        thrown.expectMessage(exceptionMessage);
        thrown.expect(IllegalStateException.class);

        //Act
        kreViewCountSubscription.getMainListener();
    }
}
