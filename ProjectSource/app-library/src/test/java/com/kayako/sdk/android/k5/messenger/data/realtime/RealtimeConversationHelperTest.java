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
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
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
import com.kayako.sdk.android.k5.kre.base.kase.KreCaseSubscription;
import com.kayako.sdk.android.k5.kre.data.Change;
import com.kayako.sdk.android.k5.kre.helpers.MinimalClientTypingListener;
import com.kayako.sdk.android.k5.kre.helpers.RawCaseChangeListener;
import com.kayako.sdk.android.k5.kre.helpers.RawCasePostChangeListener;
import com.kayako.sdk.android.k5.kre.helpers.RawUserOnCasePresenceListener;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.UserViewModel;
import com.kayako.sdk.base.callback.ItemCallback;
import com.kayako.sdk.error.ErrorCode;
import com.kayako.sdk.error.KayakoException;
import com.kayako.sdk.messenger.conversation.Conversation;
import com.kayako.sdk.messenger.message.Message;
import java.util.Collections;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        KreConnectionFactory.class,
        TextUtils.class,
        KayakoLogHelper.class,
        KreStarterFactory.class,
        MessengerOpenTracker.class,
        RealtimeConversationHelper.class,
        LoadResourceHelper.class,
        Handler.class
})
public class RealtimeConversationHelperTest {

    private static final long USER_ID = 1L;
    private static final long CONVERSATION_ID = 1L;
    private static final int ENTRY_TIME = 0;
    private static final long MESSAGE_ID = 1L;
    private static final String USER_AVATAR = "userAvatar";
    private static final String USER_NAME = "userName";
    private static final String CHANNEL_NAME = "channelName";
    private static final boolean IS_TYPING = true;
    private static final String ERROR_MESSAGE = "exceptionMessage";
    private static final KayakoException kayakoException = new KayakoException(ErrorCode.OTHER, ERROR_MESSAGE,
            new Throwable());
    private static final boolean AUTO_DISABLE_TYPING = true;
    @Mock
    private KreStarter kreStarter;
    @Mock
    private Handler handler;
    @Mock
    private KreCaseSubscription kreCaseSubscription;
    @Mock
    private KreConnection kreConnection;
    @Mock
    private OnConversationUserOnlineListener onConversationUserOnlineListener;
    @Mock
    private OnConversationChangeListener onConversationChangeListener;
    @Mock
    private Message message;
    @Mock
    private Change change;
    @Mock
    private Conversation conversation;
    @Captor
    private ArgumentCaptor<OnSubscriptionListener> onSubscriptionListenerCaptor;
    @Captor
    private ArgumentCaptor<RawCaseChangeListener> rawCaseChangeListenerCaptor;
    @Captor
    private ArgumentCaptor<MinimalClientTypingListener> minimalClientTypingListenerArgumentCaptor;
    @Captor
    private ArgumentCaptor<RawCasePostChangeListener> rawCasePostChangeListenerCaptor;
    @Captor
    private ArgumentCaptor<RawUserOnCasePresenceListener> rawUserOnCasePresenceListenerCaptor;
    @Captor
    private ArgumentCaptor<Runnable> runnableCaptor;
    @Captor
    private ArgumentCaptor<ItemCallback<Message>> itemCallbackCaptor;
    @Captor
    private ArgumentCaptor<ItemCallback<Conversation>> conversationItemCallbackCaptor;
    @Captor
    private ArgumentCaptor<Conversation> conversationCaptor;
    @Mock
    private OnConversationClientActivityListener onConversationClientActivityListener;
    @Captor
    private ArgumentCaptor<UserViewModel> userViewModelCaptor;
    @Captor
    private ArgumentCaptor<Message> messageCaptor;
    @Captor
    private ArgumentCaptor<Long> userIdCaptor;
    @Captor
    private ArgumentCaptor<Long> conversationIdCaptor;
    @Captor
    private ArgumentCaptor<Boolean> isTypingCaptor;
    @Mock
    private OnConversationMessagesChangeListener onConversationMessagesChangeListener;
    private final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() throws Exception {
        mockStatic(MessengerOpenTracker.class);
        mockStatic(TextUtils.class);
        mockStatic(KreConnectionFactory.class);
        mockStatic(KayakoLogHelper.class);
        mockStatic(KreStarterFactory.class);
        mockStatic(LoadResourceHelper.class);

        whenNew(KreCaseSubscription.class).withArguments(kreConnection, CHANNEL_NAME, USER_ID)
                .thenReturn(kreCaseSubscription);
        whenNew(Handler.class).withNoArguments().thenReturn(handler);

        when(KreConnectionFactory.getConnection(false)).thenReturn(kreConnection);
        when(KreStarterFactory.getKreStarterValues()).thenReturn(kreStarter);
        when(kreStarter.getCurrentUserId()).thenReturn(USER_ID);
        when(conversation.getId()).thenReturn(CONVERSATION_ID);
        when(message.getId()).thenReturn(MESSAGE_ID);
    }

    @After
    public void tearDown() {
        RealtimeConversationHelper.closeAll();
    }

    private void verifyCallbackCommon() {
        verify(kreCaseSubscription).subscribe(any(KreCredentials.class), eq(CHANNEL_NAME), eq(false),
                onSubscriptionListenerCaptor.capture());
        onSubscriptionListenerCaptor.getValue().onSubscription();
        onSubscriptionListenerCaptor.getValue().onUnsubscription();
        onSubscriptionListenerCaptor.getValue().onError(ERROR_MESSAGE);
    }

    private void verifyTrackChangeCallbackCommon() {
        verifyCallbackCommon();
        verify(kreCaseSubscription).addCaseChangeListener(rawCaseChangeListenerCaptor.capture());
        rawCaseChangeListenerCaptor.getValue().onCaseChange(change);
        verifyStatic(LoadResourceHelper.class);
        LoadResourceHelper.loadConversation(eq(CONVERSATION_ID), conversationItemCallbackCaptor.capture());
        conversationItemCallbackCaptor.getValue().onSuccess(conversation);
        verify(handler).post(runnableCaptor.capture());
        runnableCaptor.getValue().run();
    }

    private void verifyTrackChangeCallbackCalled() {
        verifyTrackChangeCallbackCommon();
        verify(onConversationChangeListener).onChange(conversationCaptor.capture());
    }

    private void verifyTrackChangeCallbackNotCalled() {
        verifyTrackChangeCallbackCommon();
        verify(onConversationChangeListener, never()).onChange(conversationCaptor.capture());
    }

    private void verifyTrackClientActivityCallbackCommon() {
        verifyCallbackCommon();
        verify(kreCaseSubscription).addMinimalClientTypingListener(minimalClientTypingListenerArgumentCaptor.capture());
        minimalClientTypingListenerArgumentCaptor.getValue().onUserTyping(USER_ID, USER_NAME, USER_AVATAR, IS_TYPING);
        minimalClientTypingListenerArgumentCaptor.getValue().onConnectionError();
        verify(handler).post(runnableCaptor.capture());
        runnableCaptor.getValue().run();
    }

    private void verifyTrackClientActivityCallbackCalled() {
        verifyTrackClientActivityCallbackCommon();
        verify(onConversationClientActivityListener)
                .onTyping(eq(CONVERSATION_ID), userViewModelCaptor.capture(), eq(IS_TYPING));
    }

    private void verifyTrackClientActivityCallbackNotCalled() {
        verifyTrackClientActivityCallbackCommon();
        verify(onConversationClientActivityListener, never())
                .onTyping(eq(CONVERSATION_ID), userViewModelCaptor.capture(), eq(IS_TYPING));
    }

    private void verifyTrackMessageChangeCallbackCommon() {
        verifyCallbackCommon();
        verify(kreCaseSubscription).addCasePostChangeListener(rawCasePostChangeListenerCaptor.capture());
        rawCasePostChangeListenerCaptor.getValue().onNewPost(MESSAGE_ID);
        rawCasePostChangeListenerCaptor.getValue().onChangePost(MESSAGE_ID);
        rawCasePostChangeListenerCaptor.getValue().onConnectionError();
        verifyStatic(LoadResourceHelper.class);
        LoadResourceHelper.loadMessage(eq(CONVERSATION_ID), eq(MESSAGE_ID), itemCallbackCaptor.capture());
        itemCallbackCaptor.getValue().onSuccess(message);
        itemCallbackCaptor.getValue().onFailure(kayakoException);
        verify(handler, atLeastOnce()).post(runnableCaptor.capture());
        for (Runnable runnable : runnableCaptor.getAllValues()) {
            runnable.run();
        }
    }

    private void verifyTrackMessageChangeCallbackCalled() {
        verifyTrackMessageChangeCallbackCommon();
        verify(onConversationMessagesChangeListener).onNewMessage(CONVERSATION_ID, MESSAGE_ID);
        verify(onConversationMessagesChangeListener).onUpdateMessage(eq(CONVERSATION_ID), messageCaptor.capture());
    }

    private void verifyTrackMessageChangeCallbackNotCalled() {
        verifyTrackMessageChangeCallbackCommon();
        verify(onConversationMessagesChangeListener, never()).onNewMessage(CONVERSATION_ID, MESSAGE_ID);
        verify(onConversationMessagesChangeListener, never())
                .onUpdateMessage(eq(CONVERSATION_ID), messageCaptor.capture());
    }

    private void verifyTrackPresenceUserCallbackCommon() {
        verifyCallbackCommon();
        verify(kreCaseSubscription).addUserPresenceListener(rawUserOnCasePresenceListenerCaptor.capture());
        rawUserOnCasePresenceListenerCaptor.getValue()
                .onUsersAlreadyViewingCase(Collections.singletonList(USER_ID), ENTRY_TIME);
        rawUserOnCasePresenceListenerCaptor.getValue().onNewUserViewingCase(USER_ID, ENTRY_TIME);
        rawUserOnCasePresenceListenerCaptor.getValue().onUserNoLongerViewingCase(USER_ID);
        rawUserOnCasePresenceListenerCaptor.getValue()
                .onExistingUserPerformingSomeActivity(USER_ID, System.currentTimeMillis());
        rawUserOnCasePresenceListenerCaptor.getValue().onConnectionError();
        verify(handler, atLeastOnce()).post(runnableCaptor.capture());
        for (Runnable runnable : runnableCaptor.getAllValues()) {
            runnable.run();
        }
    }

    private void verifyTrackPresenceUserCallbackCalled() {
        verifyTrackPresenceUserCallbackCommon();
        verify(onConversationUserOnlineListener, atLeastOnce()).onUserOnline(CONVERSATION_ID, USER_ID);
        verify(onConversationUserOnlineListener).onUserOffline(conversationIdCaptor.capture(), userIdCaptor.capture());
    }

    private void verifyTrackPresenceUserCallbackNotCalled() {
        verifyTrackPresenceUserCallbackCommon();
        verify(onConversationUserOnlineListener, never()).onUserOnline(CONVERSATION_ID, USER_ID);
        verify(onConversationUserOnlineListener, never())
                .onUserOffline(conversationIdCaptor.capture(), userIdCaptor.capture());
    }

    @Test
    public void trackChange() {
        //Act
        RealtimeConversationHelper.trackChange(CHANNEL_NAME, CONVERSATION_ID, onConversationChangeListener);

        //Assert
        verifyTrackChangeCallbackCalled();
        assertThat(conversationCaptor.getValue().getId(), is(equalTo(CONVERSATION_ID)));
    }

    @Test
    public void trackClientActivity() {
        //Act
        RealtimeConversationHelper
                .trackClientActivity(CHANNEL_NAME, CONVERSATION_ID, onConversationClientActivityListener);

        //Assert
        verifyTrackClientActivityCallbackCalled();
        errorCollector.checkThat(userViewModelCaptor.getValue().getAvatar(), is(equalTo(USER_AVATAR)));
        errorCollector.checkThat(userViewModelCaptor.getValue().getFullName(), is(equalTo(USER_NAME)));
    }

    @Test
    public void trackMessageChange() {
        //Act
        RealtimeConversationHelper
                .trackMessageChange(CHANNEL_NAME, CONVERSATION_ID, onConversationMessagesChangeListener);

        //Assert
        verifyTrackMessageChangeCallbackCalled();
        assertThat(messageCaptor.getValue().getId(), is(equalTo(MESSAGE_ID)));
    }

    @Test
    public void trackPresenceUser() {
        //Act
        RealtimeConversationHelper.trackPresenceUser(CHANNEL_NAME, CONVERSATION_ID, onConversationUserOnlineListener);

        //Assert
        verifyTrackPresenceUserCallbackCalled();
        errorCollector.checkThat(userIdCaptor.getValue(), is(equalTo(USER_ID)));
        errorCollector.checkThat(conversationIdCaptor.getValue(), is(equalTo(CONVERSATION_ID)));
    }

    @Test
    public void untrackChange() {
        //Arrange
        RealtimeConversationHelper.trackChange(CHANNEL_NAME, CONVERSATION_ID, onConversationChangeListener);

        //Act
        RealtimeConversationHelper.untrack(onConversationChangeListener);

        //Assert
        verifyTrackChangeCallbackNotCalled();
    }

    @Test
    public void unrackClientActivity() {
        //Arrange
        RealtimeConversationHelper
                .trackClientActivity(CHANNEL_NAME, CONVERSATION_ID, onConversationClientActivityListener);

        //Act
        RealtimeConversationHelper.untrack(onConversationClientActivityListener);

        //Assert
        verifyTrackClientActivityCallbackNotCalled();
    }

    @Test
    public void untrackMessageChange() {
        //Arrange
        RealtimeConversationHelper
                .trackMessageChange(CHANNEL_NAME, CONVERSATION_ID, onConversationMessagesChangeListener);

        //Act
        RealtimeConversationHelper.untrack(onConversationMessagesChangeListener);

        //Assert
        verifyTrackMessageChangeCallbackNotCalled();
    }

    @Test
    public void untrackPresenceUser() {
        //Arrange
        RealtimeConversationHelper.trackPresenceUser(CHANNEL_NAME, CONVERSATION_ID, onConversationUserOnlineListener);

        //Act
        RealtimeConversationHelper.untrack(onConversationUserOnlineListener);

        //Assert
        verifyTrackPresenceUserCallbackNotCalled();
    }

    public void triggerTyping() {
        //Act
        RealtimeConversationHelper.triggerTyping(CHANNEL_NAME, CONVERSATION_ID, IS_TYPING);

        //Assert
        verify(kreCaseSubscription).triggerTypingEvent(isTypingCaptor.capture(), eq(AUTO_DISABLE_TYPING));
        assertThat(isTypingCaptor.getValue(), is(equalTo(IS_TYPING)));
    }
}
