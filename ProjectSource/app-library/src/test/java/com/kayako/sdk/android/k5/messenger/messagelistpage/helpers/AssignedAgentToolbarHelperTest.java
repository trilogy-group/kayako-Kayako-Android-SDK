package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import com.kayako.sdk.android.k5.messenger.data.conversationstarter.AssignedAgentData;
import com.kayako.sdk.messenger.conversation.Conversation;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import static org.hamcrest.CoreMatchers.is;

@RunWith(MockitoJUnitRunner.class)
public class AssignedAgentToolbarHelperTest {

    private static final String AVATAR_URL = "/avatarUrl";
    private static final String FULL_NAME = "full_name";
    private static final long LAST_ACTIVE_AT = 1_000L;
    private AssignedAgentToolbarHelper assignedAgentToolbarHelper;
    private Conversation conversation;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        assignedAgentToolbarHelper = new AssignedAgentToolbarHelper();
        conversation = mock(Conversation.class, RETURNS_DEEP_STUBS);
        when(conversation.getLastAgentReplier().getAvatarUrl()).thenReturn(AVATAR_URL);
        when(conversation.getLastAgentReplier().getFullName()).thenReturn(FULL_NAME);
        when(conversation.getLastAgentReplier().getLastActiveAt()).thenReturn(LAST_ACTIVE_AT);
    }

    @Test
    public void setAssignedAgentData() {
        assignedAgentToolbarHelper.setAssignedAgentData(conversation);
        final AssignedAgentData assignedAgentData = assignedAgentToolbarHelper.getAssignedAgentData();
        errorCollector.checkThat(assignedAgentData.getUser().getLastActiveAt(), is(LAST_ACTIVE_AT));
        errorCollector.checkThat(assignedAgentData.getUser().getFullName(), is(FULL_NAME));
        errorCollector.checkThat(assignedAgentData.getUser().getAvatar(), is(AVATAR_URL));
        errorCollector.checkThat(assignedAgentData.isActive(), is(false));
    }

    @Test
    public void markActiveStatus() {
        assignedAgentToolbarHelper.setAssignedAgentData(conversation);
        assignedAgentToolbarHelper.markActiveStatus(Boolean.TRUE);
        final AssignedAgentData assignedAgentData = assignedAgentToolbarHelper.getAssignedAgentData();
        errorCollector.checkThat(assignedAgentData.getUser().getLastActiveAt(), is(LAST_ACTIVE_AT));
        errorCollector.checkThat(assignedAgentData.getUser().getFullName(), is(FULL_NAME));
        errorCollector.checkThat(assignedAgentData.getUser().getAvatar(), is(AVATAR_URL));
        errorCollector.checkThat(assignedAgentData.isActive(), is(true));
    }
}
