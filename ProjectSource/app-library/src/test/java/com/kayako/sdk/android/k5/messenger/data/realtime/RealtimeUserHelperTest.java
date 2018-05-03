package com.kayako.sdk.android.k5.messenger.data.realtime;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import android.os.Handler;
import android.text.TextUtils;
import com.kayako.sdk.android.k5.common.activities.MessengerOpenTracker;
import com.kayako.sdk.android.k5.core.KayakoLogHelper;
import com.kayako.sdk.android.k5.kre.base.KreConnection;
import com.kayako.sdk.android.k5.kre.base.KreConnectionFactory;
import com.kayako.sdk.android.k5.kre.base.KreSubscription.OnSubscriptionListener;
import com.kayako.sdk.android.k5.kre.base.credentials.KreCredentials;
import com.kayako.sdk.android.k5.kre.base.user.KreUserSubscription;
import com.kayako.sdk.android.k5.kre.helpers.RawUserOnlinePresenceListener;
import com.kayako.sdk.android.k5.messenger.data.realtime.RealtimeUserHelper.UserPresenceListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        MessengerOpenTracker.class,
        KreStarterFactory.class,
        KreUserSubscription.class,
        KreConnectionFactory.class,
        RealtimeUserHelper.class,
        KayakoLogHelper.class,
        Handler.class,
        TextUtils.class
})
public class RealtimeUserHelperTest {
    private static final String USER_PRESENCE_CHANNEL = "userPresenceChannel";
    private static final long USER_ID = 1;

    @Mock
    private UserPresenceListener userPresenceListener;
    @Mock
    private KreStarter kreStarter;
    @Mock
    private KreUserSubscription kreUserSubscription;
    @Mock
    private KreConnection kreConnection;
    @Captor
    private ArgumentCaptor<OnSubscriptionListener> subscriptionListenerCaptor;
    @Captor
    private ArgumentCaptor<RawUserOnlinePresenceListener> onlinePresenceListenerCaptor;
    @Captor
    private ArgumentCaptor<Runnable> runnableCaptor;
    @Captor
    private ArgumentCaptor<Long> userIdCaptor;
    @Mock
    private Handler handler;

    @Before
    public void setUp() throws Exception {
        mockStatic(MessengerOpenTracker.class);
        mockStatic(KreStarterFactory.class);
        mockStatic(TextUtils.class);
        mockStatic(KreConnectionFactory.class);
        mockStatic(KayakoLogHelper.class);

        when(KreConnectionFactory.getConnection(false)).thenReturn(kreConnection);
        whenNew(KreUserSubscription.class).withArguments(kreConnection, USER_PRESENCE_CHANNEL).thenReturn(kreUserSubscription);
        whenNew(Handler.class).withNoArguments().thenReturn(handler);
        when(KreStarterFactory.getKreStarterValues()).thenReturn(kreStarter);
    }

    @After
    public void teatDown() {
        RealtimeUserHelper.closeAll();
    }

    private void verifyCallbackCommon(boolean online) {
        verify(kreUserSubscription).subscribe(any(KreCredentials.class), eq(USER_PRESENCE_CHANNEL), eq(USER_ID), subscriptionListenerCaptor.capture());
        subscriptionListenerCaptor.getValue().onSubscription();
        verify(kreUserSubscription).addUserOnlinePresenceListener(onlinePresenceListenerCaptor.capture());
        RawUserOnlinePresenceListener onlinePresenceListener = onlinePresenceListenerCaptor.getValue();
        if (online) {
            onlinePresenceListener.onUserOnline();
        } else {
            onlinePresenceListener.onUserOffline();
        }
        verify(handler, atLeastOnce()).post(runnableCaptor.capture());
        runnableCaptor.getValue().run();
    }

    private void verifyCallbackNotCalled() {
        verifyCallbackCommon(false);
        verify(userPresenceListener, never()).onUserOffline(userIdCaptor.capture());
    }

    private void verifyCallbackCalled(boolean online) {
       verifyCallbackCommon(online);
        if (online) {
            verify(userPresenceListener).onUserOnline(userIdCaptor.capture());
        } else {
            verify(userPresenceListener).onUserOffline(userIdCaptor.capture());
        }
    }

    @Test
    public void callBackCalledWhenOnline() {
        //Act
        RealtimeUserHelper.trackUser(USER_PRESENCE_CHANNEL, USER_ID, userPresenceListener);

        //Assert
        verifyCallbackCalled(true);
        assertThat(userIdCaptor.getValue(), is(equalTo(USER_ID)));
    }

    @Test
    public void callBackCalledWhenOffline() {
        //Act
        RealtimeUserHelper.trackUser(USER_PRESENCE_CHANNEL, USER_ID, userPresenceListener);

        //Assert
        verifyCallbackCalled(false);
        assertThat(userIdCaptor.getValue(), is(equalTo(USER_ID)));
    }

    @Test
    public void callBackNotCalledAfterCloseAll() {
        //Arrange
        RealtimeUserHelper.trackUser(USER_PRESENCE_CHANNEL, USER_ID, userPresenceListener);

        //Act
        RealtimeUserHelper.closeAll();

        //Assert
        verifyCallbackNotCalled();
    }

    @Test
    public void callBackNotCalledAfterUnTrack() {
        //Arrange
        RealtimeUserHelper.trackUser(USER_PRESENCE_CHANNEL, USER_ID, userPresenceListener);

        //Act
        RealtimeUserHelper.untrackUser(userPresenceListener);

        //Assert
        verifyCallbackNotCalled();
    }
}