package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import com.kayako.sdk.messenger.conversation.Conversation;
import com.kayako.sdk.messenger.conversation.PostConversationBodyParams;
import com.kayako.sdk.messenger.conversation.fields.status.Status;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import static org.hamcrest.CoreMatchers.is;

public class ConversationHelperTest {
    private static final String EXCEPTION_MESSAGE =
        "If it's a new conversation and email is null, the user should not have had the chance to send a reply!";
    private static final String EMAIL = "aaa@aaa.com";
    private static final String USER_NAME = "test_user";
    private static final String MESSAGE = "dummy_message";
    private static final String CLIENT_ID = "123ABC";
    private static final long CONVERSATION_ID = 1_234L;
    private final ConversationHelper conversationHelper = new ConversationHelper();
    private Conversation conversation;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        conversation = mock(Conversation.class, Mockito.RETURNS_DEEP_STUBS);
    }

    @Test
    public void setConversationId() {
        //Act
        conversationHelper.setConversationId(CONVERSATION_ID);

        //Assert
        errorCollector.checkThat(conversationHelper.getConversationId(), is(CONVERSATION_ID));
    }

    @Test
    public void setConversation() {
        //Act
        conversationHelper.setConversation(conversation);

        //Assert
        errorCollector.checkThat(conversationHelper.getConversation(), is(conversation));
    }

    @Test
    public void setIsConversationCreated() {
        //Act
        conversationHelper.setIsConversationCreated(Boolean.TRUE);

        //Assert
        errorCollector.checkThat(conversationHelper.isConversationCreated(), is(Boolean.TRUE));
    }

    @Test
    public void isConversationCompleted() {
        //Arrange
        when(conversation.getStatus().getType()).thenReturn(Status.Type.COMPLETED);

        //Act
        conversationHelper.setConversation(conversation);

        //Assert
        errorCollector.checkThat(conversationHelper.isConversationCompleted(), is(true));
    }

    @Test
    public void isConversationClosed() {
        //Arrange
        when(conversation.getStatus().getType()).thenReturn(Status.Type.CLOSED);

        //Act
        conversationHelper.setConversation(conversation);

        //Assert
        errorCollector.checkThat(conversationHelper.isConversationClosed(), is(true));
    }

    @Test
    public void whenNullEmailThenAssertionError() {
        //Contract
        thrown.expect(AssertionError.class);
        thrown.expectMessage(EXCEPTION_MESSAGE);

        //Act
        conversationHelper.getNewConversationBodyParams(null, USER_NAME, MESSAGE, CLIENT_ID);
    }

    @Test
    public void whenValidParamsThenObjectCreated() {
        //Act
        PostConversationBodyParams bodyParams = conversationHelper.getNewConversationBodyParams(
                EMAIL, USER_NAME, MESSAGE, CLIENT_ID);

        //Assert
        errorCollector.checkThat(bodyParams.getClientId(), is(CLIENT_ID));
        errorCollector.checkThat(bodyParams.getName(), is(USER_NAME));
        errorCollector.checkThat(bodyParams.getEmail(), is(EMAIL));
        errorCollector.checkThat(bodyParams.getSubject(), is(MESSAGE));
        errorCollector.checkThat(bodyParams.getContents(), is(MESSAGE));
        errorCollector.checkThat(bodyParams.getSource(),
                is(PostConversationBodyParams.SourceType.MESSENGER.name()));
    }

    @Test
    public void whenNullUserThenExtractedFromEmail() {
        //Arrange
        final String extractedUserName  = EMAIL.substring(0, EMAIL.indexOf("@"));

        //Act
        PostConversationBodyParams bodyParams = conversationHelper.getNewConversationBodyParams(
                EMAIL, null, MESSAGE, CLIENT_ID);

        //Assert
        errorCollector.checkThat(bodyParams.getName(), is(extractedUserName));
    }
}
