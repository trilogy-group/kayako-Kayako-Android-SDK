package com.kayako.sdk.android.k5.kre.helpers.presence;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class PresenceUser {

    @NonNull
    private PresenceMetaUserData userData;

    @Nullable
    private PresenceMetaActivityData activityData;

    /**
     * Constructor used to find, replace or remove users in a list.
     * <p>
     * This constructor also ensures all activityData is non-null and contains default values = false, 0L
     *
     * @param id
     */
    public PresenceUser(@NonNull Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id can not be null");
        }

        this.userData = new PresenceMetaUserData(id);
        this.activityData = new PresenceMetaActivityData(false, false, 0L, false, false);
    }

    /**
     * Constructor used after parsing JSON
     *
     * @param userData
     * @param activityData
     */
    public PresenceUser(@NonNull PresenceMetaUserData userData, @NonNull PresenceMetaActivityData activityData) {
        if (userData == null || userData.getId() == null || userData.getId() == 0L || activityData == null) {
            throw new IllegalArgumentException("id can not be null");
        }

        this.userData = userData;
        this.activityData = activityData;
    }

    public PresenceMetaUserData getUserData() {
        return userData;
    }

    public PresenceMetaActivityData getActivityData() {
        return activityData;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            PresenceUser userOther = (PresenceUser) obj;
            PresenceUser userCurrent = this;
            return userCurrent.getUserData().equals(userOther.getUserData());
        } catch (NullPointerException | ClassCastException e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return getUserData().hashCode();
    }

}
