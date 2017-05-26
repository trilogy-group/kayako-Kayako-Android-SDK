package com.kayako.sdk.android.k5.kre.helpers.presence;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class PresenceMetaUserData {

    @NonNull
    private Long id;

    @Nullable
    private String fullName; // NOTE: retain this pascal case - required for json representation

    @Nullable
    private String avatar;

    public PresenceMetaUserData(@NonNull Long id) {
        this.id = id;
    }

    public PresenceMetaUserData(@NonNull Long id, String fullName, String avatar) {
        this.id = id;
        this.fullName = fullName;
        this.avatar = avatar;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    @Nullable
    public String getFullName() {
        return fullName;
    }

    @Nullable
    public String getAvatar() {
        return avatar;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            PresenceMetaUserData userOther = (PresenceMetaUserData) obj;
            PresenceMetaUserData userCurrent = this;
            return userCurrent.getId().equals(userOther.getId());
        } catch (NullPointerException | ClassCastException e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return id.intValue();
    }
}
