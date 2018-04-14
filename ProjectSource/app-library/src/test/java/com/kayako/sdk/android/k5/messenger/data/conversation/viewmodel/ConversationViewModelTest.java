package com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.containsString;

@RunWith(MockitoJUnitRunner.class)
public class ConversationViewModelTest {

    private static final String NAME = "name";
    private static final String AVATAR_URL = "/avatarUrl";
    private static final String SUBJECT = "subject";
    private static final long CONVERSATION_ID = 12_345L;
    private static final long TIME_IN_MILLISECONDS = 1_000L;
    private static final int UNREAD_COUNT = 3;
    private ConversationViewModel one;
    private ConversationViewModel same;
    private ConversationViewModel secondSame;
    private ConversationViewModel other;

    @Mock
    private ClientTypingActivity lastAgentReplierTyping;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        final String newAvatarUrl = "/newAvatarUrl";
        one = new ConversationViewModel(CONVERSATION_ID, AVATAR_URL, NAME, TIME_IN_MILLISECONDS,
                SUBJECT, UNREAD_COUNT, lastAgentReplierTyping);
        same = new ConversationViewModel(CONVERSATION_ID, AVATAR_URL, NAME, TIME_IN_MILLISECONDS,
                SUBJECT, UNREAD_COUNT, lastAgentReplierTyping);
        secondSame = new ConversationViewModel(CONVERSATION_ID, AVATAR_URL, NAME, TIME_IN_MILLISECONDS,
                SUBJECT, UNREAD_COUNT, lastAgentReplierTyping);
        other = new ConversationViewModel(CONVERSATION_ID, newAvatarUrl, NAME, TIME_IN_MILLISECONDS,
                SUBJECT, UNREAD_COUNT, lastAgentReplierTyping);
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        errorCollector.checkThat(one.getConversationId(), is(CONVERSATION_ID));
        errorCollector.checkThat(one.getAvatarUrl(), is(AVATAR_URL));
        errorCollector.checkThat(one.getName(), is(NAME));
        errorCollector.checkThat(one.getTimeInMilleseconds(), is(TIME_IN_MILLISECONDS));
        errorCollector.checkThat(one.getSubject(), is(SUBJECT));
        errorCollector.checkThat(one.getUnreadCount(), is(UNREAD_COUNT));
        errorCollector.checkThat(one.getLastAgentReplierTyping(), is(lastAgentReplierTyping));
    }

    @Test
    public void whenLastAgentReplierTypingNullThenException() {
        final String exceptionMessage = new StringBuilder()
                .append("lastAgentReplierTyping should not be null! ")
                .append("If not applicable, create an object with isTyping = false!").toString();
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(containsString(exceptionMessage));
        new ConversationViewModel(CONVERSATION_ID, AVATAR_URL, NAME, TIME_IN_MILLISECONDS, SUBJECT,
                                    UNREAD_COUNT, null);
    }

    @Test
    public void whenConversionIdZeroThenException() {
        final String exceptionMessage = "Invalid values";
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(containsString(exceptionMessage));
        new ConversationViewModel(0, AVATAR_URL, NAME, TIME_IN_MILLISECONDS, SUBJECT,
                        UNREAD_COUNT, lastAgentReplierTyping);
    }

    @Test
    public void whenSubjectNullThenException() {
        final String exceptionMessage = "Invalid values";
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(containsString(exceptionMessage));
        new ConversationViewModel(CONVERSATION_ID, AVATAR_URL, NAME, TIME_IN_MILLISECONDS, null,
                        UNREAD_COUNT, lastAgentReplierTyping);
    }

    @Test
    public void reflexivity() {
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

    @Test
    public void getContents() {
        errorCollector.checkThat(one.getContents().isEmpty(), is(false));
        errorCollector.checkThat(one.getContents().get("subject"), is(SUBJECT));
    }
}
