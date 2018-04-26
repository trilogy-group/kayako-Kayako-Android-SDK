package com.kayako.sdk.android.k5.kre.base.kase;

import com.kayako.sdk.android.k5.kre.base.KreConnection;
import com.kayako.sdk.android.k5.kre.base.KreSubscription;
import com.kayako.sdk.android.k5.kre.base.credentials.KreCredentials;
import com.kayako.sdk.android.k5.kre.helpers.MinimalClientTypingListener;
import com.kayako.sdk.android.k5.kre.helpers.RawCaseChangeListener;
import com.kayako.sdk.android.k5.kre.helpers.RawCasePostChangeListener;
import com.kayako.sdk.android.k5.kre.helpers.RawClientActivityListener;
import com.kayako.sdk.android.k5.kre.helpers.RawClientTypingListener;
import com.kayako.sdk.android.k5.kre.helpers.RawUserOnCasePresenceListener;
import com.kayako.sdk.android.k5.kre.helpers.presence.KrePresenceHelper;
import static org.hamcrest.CoreMatchers.is;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.api.support.membermodification.MemberModifier.suppress;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import java.util.List;

@PrepareForTest(KreSubscription.class)
@RunWith(PowerMockRunner.class)
public class KreCaseSubscriptionTest {

    private static final String NAME = "TEST_NAME";
    private static final long USER_ID = 1L;
    private KreCaseSubscription kreCaseSubscription;
    private List<RawClientTypingListener> mClientTypingListeners;
    private List<MinimalClientTypingListener> mMinimalClientTypingListeners;
    private List<RawClientActivityListener> mClientActivityListeners;
    private List<RawCaseChangeListener> mCaseChangeListeners;
    private List<RawCasePostChangeListener> mRawCasePostChangeListener;
    private List<RawUserOnCasePresenceListener> mUserPresenceListeners;

    @Mock
    private KreConnection kreConnection;

    @Mock
    private RawClientTypingListener rawClientTypingListener;

    @Mock
    private MinimalClientTypingListener minimalClientTypingListener;

    @Mock
    private RawClientActivityListener rawClientActivityListener;

    @Mock
    private RawCaseChangeListener rawCaseChangeListener;

    @Mock
    private RawCasePostChangeListener rawCasePostChangeListener;

    @Mock
    private RawUserOnCasePresenceListener rawUserOnCasePresenceListener;

    @Mock
    private KreSubscription.OnSubscriptionListener onSubscriptionListener;

    @Mock
    private KreCredentials kreCredentials;

    @Mock
    private KreSubscription kreSubscription;

    @Mock
    private KrePresenceHelper mKrePresenceHelper;

    @Mock
    private List<KreSubscription.OnSubscriptionListener> mChildListeners;

    @Captor
    private ArgumentCaptor<Boolean> booleanArgumentCaptor;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void whenValidParamsThenObjectCreated() throws  Exception {
        kreCaseSubscription = new KreCaseSubscription(kreConnection, NAME, USER_ID);
        mClientTypingListeners =
                Whitebox.getInternalState(kreCaseSubscription, "mClientTypingListeners");
        mMinimalClientTypingListeners =
                Whitebox.getInternalState(kreCaseSubscription, "mMinimalClientTypingListeners");
        mClientActivityListeners =
                Whitebox.getInternalState(kreCaseSubscription, "mClientActivityListeners");
        mCaseChangeListeners =
                Whitebox.getInternalState(kreCaseSubscription, "mCaseChangeListeners");
        mRawCasePostChangeListener =
                Whitebox.getInternalState(kreCaseSubscription, "mRawCasePostChangeListeners");
        mUserPresenceListeners =
                Whitebox.getInternalState(kreCaseSubscription, "mUserPresenceListeners");
    }

    @Test
    public void whenKreConnectionNullThenObjectCreationFailed() {
        //Arrange
        final KreConnection kreConnectionLocal = null;
        final Matcher<String> nullMatcher = new IsNull<>();

        //Assert
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(nullMatcher);

        //Act
        new KreCaseSubscription(kreConnectionLocal, NAME, USER_ID);
    }

    @Test
    public void addDifferentClientTypeListener() {
        //Act
        kreCaseSubscription.addClientTypingListener(rawClientTypingListener);
        kreCaseSubscription.addMinimalClientTypingListener(minimalClientTypingListener);
        kreCaseSubscription.addClientActivityListener(rawClientActivityListener);
        kreCaseSubscription.addCaseChangeListener(rawCaseChangeListener);
        kreCaseSubscription.addCasePostChangeListener(rawCasePostChangeListener);
        kreCaseSubscription.addUserPresenceListener(rawUserOnCasePresenceListener);

        //Assert
        errorCollector.checkThat(mClientTypingListeners.size(), is(1));
        errorCollector.checkThat(mMinimalClientTypingListeners.size(), is(1));
        errorCollector.checkThat(mClientActivityListeners.size(), is(1));
        errorCollector.checkThat(mCaseChangeListeners.size(), is(1));
        errorCollector.checkThat(mRawCasePostChangeListener.size(), is(1));
        errorCollector.checkThat(mUserPresenceListeners.size(), is(1));
    }

    @Test
    public void removeDifferentClientTypeListener() {
        //Act
        kreCaseSubscription.removeClientTypingListener(rawClientTypingListener);
        kreCaseSubscription.removeMinimalClientTypingListener(minimalClientTypingListener);
        kreCaseSubscription.removeClientActivityListener(rawClientActivityListener);
        kreCaseSubscription.removeCaseChangeListener(rawCaseChangeListener);
        kreCaseSubscription.removeCasePostChangeListener(rawCasePostChangeListener);
        kreCaseSubscription.removeUserPresenceListener(rawUserOnCasePresenceListener);

        //Assert
        errorCollector.checkThat(mClientTypingListeners.size(), is(0));
        errorCollector.checkThat(mMinimalClientTypingListeners.size(), is(0));
        errorCollector.checkThat(mClientActivityListeners.size(), is(0));
        errorCollector.checkThat(mCaseChangeListeners.size(), is(0));
        errorCollector.checkThat(mRawCasePostChangeListener.size(), is(0));
        errorCollector.checkThat(mUserPresenceListeners.size(), is(0));
    }

    @Test
    public void triggerUpdatingEvent() {
        //Arrange
        final boolean isUpdating = true;
        final KreSubscription kreSubscriptionLocal = Whitebox.getInternalState(
                kreCaseSubscription, "mKreSubscription");
        when(kreSubscriptionLocal.isConnected()).thenReturn(true);
        Whitebox.setInternalState(kreCaseSubscription, "mKrePresenceHelper", mKrePresenceHelper);

        //Act
        kreCaseSubscription.triggerUpdatingEvent(isUpdating);
        verify(mKrePresenceHelper).triggerClientUpdatingCaseEvent(booleanArgumentCaptor.capture());

        //Assert
        errorCollector.checkThat(isUpdating, is(booleanArgumentCaptor.getValue()));
    }

    @Test
    public void triggerTypingEvent() {
        //Arrange
        final boolean isTyping = true;
        final boolean autoDisableTyping = true;
        final KreSubscription kreSubscriptionLocal = Whitebox.getInternalState(kreCaseSubscription, "mKreSubscription");
        when(kreSubscriptionLocal.isConnected()).thenReturn(true);
        Whitebox.setInternalState(kreCaseSubscription, "mKrePresenceHelper", mKrePresenceHelper);

        //Act
        kreCaseSubscription.triggerTypingEvent(isTyping, autoDisableTyping);
        verify(mKrePresenceHelper).triggerClientTypingCaseEvent(booleanArgumentCaptor.capture(),
                booleanArgumentCaptor.capture());
        final List<Boolean> capturedValues = booleanArgumentCaptor.getAllValues();

        //Assert
        errorCollector.checkThat(isTyping, is(capturedValues.get(0)));
        errorCollector.checkThat(autoDisableTyping, is(capturedValues.get(1)));
    }

    @Test
    public void isReady() {
        //Arrange
        Whitebox.setInternalState(kreCaseSubscription, "mKreSubscription", kreSubscription);
        when(kreSubscription.isConnected()).thenReturn(true);
        when(kreSubscription.hasSubscribed()).thenReturn(true);

        //Act
        errorCollector.checkThat(kreCaseSubscription.isReady(), is(true));
    }

    @Test
    public void configureReconnection() {
        //Arrange
        final boolean allowReConnectionsOnFailure = true;
        Whitebox.setInternalState(kreCaseSubscription, "mKreSubscription", kreSubscription);

        //Act
        kreCaseSubscription.configureReconnection(allowReConnectionsOnFailure);
        verify(kreSubscription).configureReconnectOnFailure(booleanArgumentCaptor.capture());

        //Assert
        errorCollector.checkThat(booleanArgumentCaptor.getValue(), is(true));
    }

    @Test
    public void whenNullKreCredentialThenSubscribeThrowException() {
        //Arrange
        final KreCredentials kreCredentialsLocal = null;
        final String channel = "mode";
        final String exceptionMessage = "Null arguments are not allowed";

        //Assert
        thrown.expectMessage(exceptionMessage);
        thrown.expect(IllegalArgumentException.class);

        //Act
        kreCaseSubscription.subscribe(kreCredentialsLocal, channel, true, onSubscriptionListener);
    }

    @Test
    public void subscribe() {
        //Arrange
        final String channel = "mode";
        final boolean isAgent = true;

        //Act
        kreCaseSubscription.subscribe(kreCredentials, channel, isAgent, onSubscriptionListener);

        //Assert
        final boolean mIsAgent = Whitebox.getInternalState(kreCaseSubscription, "mIsAgent");
        final List<KreSubscription.OnSubscriptionListener> mChildListeners =
                    Whitebox.getInternalState(kreCaseSubscription, "mChildListeners");
        errorCollector.checkThat(mChildListeners.size(), is(1));
        errorCollector.checkThat(mIsAgent, is(isAgent));
    }

    @Test
    public void unSubscribe() {
        //Arrange
        final String channel = "mode";
        final boolean isAgent = true;
        suppress(method(KreSubscription.class, "runUnSubscribeTask"));
        kreCaseSubscription.subscribe(kreCredentials, channel, isAgent, onSubscriptionListener);

        //Act
        kreCaseSubscription.unSubscribe(onSubscriptionListener);

        //Assert
        final List<KreSubscription.OnSubscriptionListener> mChildListeners =
                Whitebox.getInternalState(kreCaseSubscription, "mChildListeners");
        errorCollector.checkThat(mChildListeners.size(), is(0));
    }

    @Test
    public void unSubscribeWithResetVariables() {
        //Arrange
        suppress(method(KreSubscription.class, "runUnSubscribeTask"));
        Whitebox.setInternalState(kreCaseSubscription, "mChildListeners", mChildListeners);
        when(mChildListeners.size()).thenReturn(1);

        //Act
        kreCaseSubscription.unSubscribe(onSubscriptionListener);

        //Assert
        final KreSubscription.OnSubscriptionListener mMainListenerLocal =
                Whitebox.getInternalState(kreCaseSubscription, "mMainListener");
        final List<MinimalClientTypingListener> mMinimalClientTypingListenersList =
                Whitebox.getInternalState(kreCaseSubscription, "mMinimalClientTypingListeners");
        final List<RawClientTypingListener> mClientTypingListenersList =
                Whitebox.getInternalState(kreCaseSubscription, "mClientTypingListeners");
        final List<RawClientActivityListener> mClientActivityListenersList =
                Whitebox.getInternalState(kreCaseSubscription, "mClientActivityListeners");
        final List<RawCaseChangeListener> mCaseChangeListenersList =
                Whitebox.getInternalState(kreCaseSubscription, "mCaseChangeListeners");
        final List<RawUserOnCasePresenceListener> mUserPresenceListenersList =
                Whitebox.getInternalState(kreCaseSubscription, "mUserPresenceListeners");
        final List<RawCasePostChangeListener> mRawCasePostChangeListenersList =
                Whitebox.getInternalState(kreCaseSubscription, "mRawCasePostChangeListeners");
        errorCollector.checkThat(mMainListenerLocal, nullValue());
        errorCollector.checkThat(mMinimalClientTypingListenersList.size(), is(0));
        errorCollector.checkThat(mClientTypingListenersList.size(), is(0));
        errorCollector.checkThat(mClientActivityListenersList.size(), is(0));
        errorCollector.checkThat(mCaseChangeListenersList.size(), is(0));
        errorCollector.checkThat(mUserPresenceListenersList.size(), is(0));
        errorCollector.checkThat(mRawCasePostChangeListenersList.size(), is(0));
    }
}
