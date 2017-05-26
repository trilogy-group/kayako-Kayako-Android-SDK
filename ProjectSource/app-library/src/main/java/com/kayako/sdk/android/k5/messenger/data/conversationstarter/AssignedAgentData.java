package com.kayako.sdk.android.k5.messenger.data.conversationstarter;

import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.UserViewModel;

public class AssignedAgentData {

    private UserViewModel user;

    private boolean isActive;

    public AssignedAgentData(UserViewModel user, boolean isActive) {
        this.user = user;
        this.isActive = isActive;

        if (user == null || user.getLastActiveAt() == null || user.getFullName() == null || user.getAvatar() == null) {
            throw new IllegalArgumentException("User and properties can not be null!");
        }
    }

    public UserViewModel getUser() {
        return user;
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssignedAgentData that = (AssignedAgentData) o;

        if (isActive != that.isActive) return false;
        return user != null ? user.equals(that.user) : that.user == null;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (isActive ? 1 : 0);
        return result;
    }
}
