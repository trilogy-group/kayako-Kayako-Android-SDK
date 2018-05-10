package com.kayako.sdk.android.k5.kre.base;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.api.support.membermodification.MemberModifier.suppress;

import android.os.AsyncTask;
import com.fasterxml.jackson.databind.JsonNode;
import com.kayako.sdk.android.k5.kre.base.KreSubscription.OnEventListener;
import com.kayako.sdk.android.k5.kre.base.KreSubscription.OnSubscriptionListener;
import com.kayako.sdk.android.k5.kre.base.credentials.KreCredentials;
import com.kayako.sdk.android.k5.kre.data.PushData;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.phoenixframework.channels.Channel;
import org.phoenixframework.channels.IMessageCallback;
import org.phoenixframework.channels.Push;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
    KreSubscription.class,
    KreConnection.class,
    KreCredentials.class,
    AsyncTask.class,
    OnSubscriptionListener.class,
    OnEventListener.class,
    Channel.class,
    Push.class
})
@Ignore
public class KreSubscriptionTest {

  public static final String TAG_WITH_NAME_FIELD = "mTagWithName";
  public static final String TAG_CONSTANT_FIELD = "TAG";
  public static final String KRE_CONNECTION_LISTENER_FIELD = "mKreConnectionListener";
  public static final String TEST_PUSH_DATA = "testPushData";
  public static final String HAS_SUBSCRIBED_SUCCESSFULLY_FIELD = "mHasSubscribedSuccessfully";
  public static final String EVENT_OK_CONSTANT = "EVENT_OK";
  private KreSubscription kreSubscription;

  private static final String SUBSCRIPTION_NAME = "testSubscription";
  private static final String EVENT_NAME = "testEvent";
  private static final String CHANNEL_NAME = "testChannel";

  @Mock
  private KreConnection kreConnection;
  @Mock
  private KreCredentials kreCredentials;
  @Mock
  private Channel channel;
  @Mock
  private Push push;
  @Rule
  public final ExpectedException thrown = ExpectedException.none();
  @Captor
  private ArgumentCaptor<JsonNode> jsonNodeArgumentCaptor;
  @Captor
  private ArgumentCaptor<KreConnection.OnOpenConnectionListener> openConnectionListenerArgumentCaptor;

  @Before
  public void setUp() {
    suppress(method(AsyncTask.class, "executeOnExecutor"));

    kreSubscription = new KreSubscription(kreConnection, SUBSCRIPTION_NAME);
  }

  @After
  public void validate() {
    validateMockitoUsage();
  }

  @Test
  public void shouldThrowExceptionOnConstructorIfConnectionIsNull() {
    // Arrange

    // Assert
    thrown.expect(IllegalArgumentException.class);

    // Act
    kreSubscription = new KreSubscription(null, SUBSCRIPTION_NAME);
  }

  @Test
  public void shouldSetDefaultNameIfNullNameOnConstructor() {
    // Arrange

    // Act
    kreSubscription = new KreSubscription(kreConnection, null);

    // Assert
    assertThat(Whitebox.getInternalState(kreSubscription, TAG_WITH_NAME_FIELD),
        is(equalTo(Whitebox.getInternalState(KreSubscription.class, TAG_CONSTANT_FIELD))));
  }

  @Test
  public void shouldPrefixTagToNameOnConstructor() {
    // Arrange
    String expectedName = String.format("%s-%s",
        Whitebox.getInternalState(KreSubscription.class, TAG_CONSTANT_FIELD),
        SUBSCRIPTION_NAME);

    // Act

    // Assert
    assertThat(Whitebox.getInternalState(kreSubscription, TAG_WITH_NAME_FIELD).toString(),
        is(equalTo(expectedName)));
  }

  @Test
  public void subscribeToNewChannel() {
    // Arrange
    OnSubscriptionListener onSubscriptionListener = mock(OnSubscriptionListener.class);

    // Act
    kreSubscription.subscribe(kreCredentials, CHANNEL_NAME, onSubscriptionListener);

    // Assert
    verify(kreConnection).connect(eq(kreCredentials), eq(CHANNEL_NAME),
        openConnectionListenerArgumentCaptor.capture(), jsonNodeArgumentCaptor.capture());
    assertNotNull(openConnectionListenerArgumentCaptor.getValue());
  }

  @Test
  public void listenForEventAfterSubscribe() throws Exception {
    // Arrange
    setUpChannel();
    OnEventListener listener = mock(OnEventListener.class);
    OnSubscriptionListener onSubscriptionListener = mock(OnSubscriptionListener.class);

    // Act
    kreSubscription.subscribe(kreCredentials, CHANNEL_NAME, onSubscriptionListener);
    KreConnection.OnOpenConnectionListener connectionListener =
        Whitebox.getInternalState(kreSubscription, KRE_CONNECTION_LISTENER_FIELD);
    connectionListener.onOpen(channel);
    kreSubscription.listenFor(EVENT_NAME, listener);

    // Assert
    verify(kreConnection).connect(eq(kreCredentials), eq(CHANNEL_NAME),
        openConnectionListenerArgumentCaptor.capture(), jsonNodeArgumentCaptor.capture());
    assertNotNull(openConnectionListenerArgumentCaptor.getValue());
  }

  @Test
  public void triggerEventAfterSubscribe() throws Exception {
    // Arrange
    setUpChannel();
    OnSubscriptionListener subscriptionListener = mock(OnSubscriptionListener.class);
    OnEventListener eventListener = mock(OnEventListener.class);
    PushData pushData = new PushData() {
      @Override
      public String toString() {
        return TEST_PUSH_DATA;
      }

      @Override
      public boolean equals(Object o) {
        return o.toString().equals(this.toString());
      }

      @Override
      public int hashCode() {
        return super.hashCode();
      }
    };

    // Act
    kreSubscription.subscribe(kreCredentials, CHANNEL_NAME, subscriptionListener);
    KreConnection.OnOpenConnectionListener connectionListener =
        Whitebox.getInternalState(kreSubscription, KRE_CONNECTION_LISTENER_FIELD);
    connectionListener.onOpen(channel);
    kreSubscription.listenFor(EVENT_NAME, eventListener);
    boolean actual = kreSubscription.triggerEvent(EVENT_NAME, pushData);

    // Assert
    assertTrue(actual);
  }

  @Test
  public void triggerEventBeforeSubscribe() {
    // Arrange
    PushData pushData = new PushData() {
      @Override
      public String toString() {
        return TEST_PUSH_DATA;
      }

      @Override
      public boolean equals(Object o) {
        return o.toString().equals(this.toString());
      }

      @Override
      public int hashCode() {
        return super.hashCode();
      }
    };

    // Act
    boolean actual = kreSubscription.triggerEvent(EVENT_NAME, pushData);

    // Assert
    assertFalse(actual);
  }

  @Test
  public void subscribeAndThenUnsubscribe() {
    // Arrange
    OnSubscriptionListener onSubscriptionListener = mock(OnSubscriptionListener.class);

    // Act
    kreSubscription.subscribe(kreCredentials, CHANNEL_NAME, onSubscriptionListener);
    kreSubscription.unSubscribe(onSubscriptionListener);

    // Assert
    verify(kreConnection).connect(eq(kreCredentials), eq(CHANNEL_NAME),
        openConnectionListenerArgumentCaptor.capture(), jsonNodeArgumentCaptor.capture());
    assertNotNull(openConnectionListenerArgumentCaptor.getValue());
  }

  @Test
  public void hasSubscribedShouldCallInternalObjectGet() {
    // Arrange
    AtomicBoolean mHasSubscribedSuccessfully = Whitebox.getInternalState(kreSubscription,
        HAS_SUBSCRIBED_SUCCESSFULLY_FIELD);
    boolean expected = mHasSubscribedSuccessfully.get();

    // Act
    boolean actual = kreSubscription.hasSubscribed();

    // Verify
    assertThat(actual, is(expected));
  }

  private void setUpChannel() throws Exception {
    ArgumentCaptor<IMessageCallback> captor = ArgumentCaptor.forClass(IMessageCallback.class);
    when(channel.join()).thenReturn(push);
    when(push.receive(eq(Whitebox.getInternalState(KreSubscription.class, EVENT_OK_CONSTANT)
        .toString()), captor.capture()))
        .then(new Answer<Push>() {
          @Override
          public Push answer(InvocationOnMock invocationOnMock) throws Throwable {
            AtomicBoolean mHasSubscribedSuccessfully = Whitebox.getInternalState(kreSubscription,
                HAS_SUBSCRIBED_SUCCESSFULLY_FIELD);
            mHasSubscribedSuccessfully.set(true);
            return push;
          }
        });
  }

}
