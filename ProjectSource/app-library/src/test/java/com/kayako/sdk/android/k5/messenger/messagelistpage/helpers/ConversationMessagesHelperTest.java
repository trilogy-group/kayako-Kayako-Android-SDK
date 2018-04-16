package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import com.kayako.sdk.messenger.message.Message;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.OngoingStubbing;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConversationMessagesHelperTest {

    private static final int LIMIT = 30;
    private static final long ID = 1L;
    private final ConversationMessagesHelper helper = new ConversationMessagesHelper();
    private List<Message> newMessages;

    @Mock
    private Message message;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Test
    public void whenMessageNullThenIllegalArgumentException() {
        final String exceptionMessage = "Invalid argument. Can not be null!";
        final Message messageLocal = null;
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(exceptionMessage);
        helper.updateMessage(messageLocal);
    }

    @Test
    public void whenMessageNotNullThenUpdate() {
        when(message.getId()).thenReturn(ID);
        helper.updateMessage(message);
        errorCollector.checkThat(helper.getSize(), is(1));
        errorCollector.checkThat(helper.exists(ID), is(true));
    }

    @Test
    public void whenZeroMessagesThenHasMoreMessagesFalse() {
        final int offset = 0;
        newMessages = new ArrayList<>();
        helper.onLoadNextMessages(newMessages, offset);
        errorCollector.checkThat(helper.hasMoreMessages(), is(Boolean.FALSE));
        errorCollector.checkThat(helper.hasLoadedMessagesBefore(), is(Boolean.TRUE));
        errorCollector.checkThat(helper.getLastSuccessfulOffset(), is(offset));
        errorCollector.checkThat(helper.getSize(), is(0));
    }

    @Test
    public void whenMessagesLessLimitThenHasMoreMessagesFalse() {
        final int offset = 1;
        newMessages = new ArrayList<>();
        newMessages.add(message);
        when(message.getId()).thenReturn(ID);
        helper.onLoadNextMessages(newMessages, offset);
        errorCollector.checkThat(helper.hasMoreMessages(), is(Boolean.FALSE));
        errorCollector.checkThat(helper.hasLoadedMessagesBefore(), is(Boolean.TRUE));
        errorCollector.checkThat(helper.getLastSuccessfulOffset(), is(offset));
        errorCollector.checkThat(helper.getSize(), is(1));
    }

    @Test
    public void whenMessagesEqualLimitThenHasMoreMessagesFalse() {
        final int offset = 5;
        newMessages = new ArrayList<>();
        for(long i = 1; i<= LIMIT; i++){
            newMessages.add(message);
        }
        final OngoingStubbing stubbing = when(message.getId()).thenReturn(ID);
        for(long j = 2; j <= LIMIT+2; j++) {
            stubbing.thenReturn(j);
        }
        helper.onLoadNextMessages(newMessages, offset);
        errorCollector.checkThat(helper.hasMoreMessages(), is(Boolean.TRUE));
        errorCollector.checkThat(helper.hasLoadedMessagesBefore(), is(Boolean.TRUE));
        errorCollector.checkThat(helper.getLastSuccessfulOffset(), is(offset));
        errorCollector.checkThat(helper.getSize(), is(LIMIT));
        errorCollector.checkThat(helper.getMessages().size(), is(helper.getLimit()));
    }
}
