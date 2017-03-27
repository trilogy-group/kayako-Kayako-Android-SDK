package com.kayako.sdk.android.k5.messenger.data.conversationstarter;

public class AssignedAgentData {

    private ActiveUser user;

    private boolean isActive;

    public AssignedAgentData(ActiveUser user, boolean isActive) {
        this.user = user;
        this.isActive = isActive;

        if (user == null) {
            throw new IllegalArgumentException("User can not be null!");
        }
    }

    public ActiveUser getUser() {
        return user;
    }

    public boolean isActive() {
        return isActive;
    }
}
