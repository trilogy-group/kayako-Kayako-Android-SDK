package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.UserViewModel;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.AssignedAgentData;
import com.kayako.sdk.messenger.conversation.Conversation;

public class AssignedAgentToolbarHelper {

    public AssignedAgentToolbarHelper() {
    }

    private AssignedAgentData assignedAgentData;
    private boolean lastActiveStatus;

    public void setAssignedAgentData(Conversation conversation) {
        if (conversation == null && conversation.getLastAgentReplier() == null) {
            throw new IllegalArgumentException("INVALID");
        }

        assignedAgentData = new AssignedAgentData(
                new UserViewModel(
                        conversation.getLastAgentReplier().getAvatarUrl(),
                        conversation.getLastAgentReplier().getFullName(),
                        conversation.getLastAgentReplier().getLastActiveAt()
                ),
                lastActiveStatus
        );
    }

    public void markActiveStatus(boolean isActive) {
        lastActiveStatus = isActive;

        assignedAgentData = new AssignedAgentData(
                new UserViewModel(
                        assignedAgentData.getUser().getAvatar(),
                        assignedAgentData.getUser().getFullName(),
                        assignedAgentData.getUser().getLastActiveAt()
                ),
                isActive
        );
    }

    public AssignedAgentData getAssignedAgentData() {
        return assignedAgentData;
    }
}
