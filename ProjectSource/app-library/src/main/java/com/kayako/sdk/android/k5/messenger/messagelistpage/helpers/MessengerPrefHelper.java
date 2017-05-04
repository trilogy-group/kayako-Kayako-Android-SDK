package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.android.k5.core.MessengerUserPref;

/**
 * All logic involving saving user email and other information
 */
public class MessengerPrefHelper {

    private String mEmail;
    private Long mCurrentUserId;
    private String mAvatar;

    public String getEmail() {
        if (mEmail == null) {
            mEmail = MessengerPref.getInstance().getEmailId(); // can be null too
        }
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
        MessengerPref.getInstance().setEmailId(email);
    }

    public String getAvatar() {
        if (mAvatar == null) {
            mAvatar = MessengerUserPref.getInstance().getAvatar(); // can be null too
        }
        return mAvatar;
    }

    public void setAvatar(String avatarUrl) {
        this.mAvatar = avatarUrl;
        MessengerUserPref.getInstance().setAvatar(avatarUrl);
    }

    public Long getUserId() {
        if (mCurrentUserId == null) {
            return mCurrentUserId = MessengerUserPref.getInstance().getUserId();
        }
        return mCurrentUserId;
    }

    public void setUserId(Long currentUserId) {
        this.mCurrentUserId = currentUserId;
        MessengerUserPref.getInstance().setUserId(currentUserId);
    }

    public void setUserInfo(Long currentUserId, String fullName, String avatarUrl, String presenceChannel) {
        if (currentUserId == null || currentUserId == 0 || fullName == null || avatarUrl == null || presenceChannel == null) {
            throw new IllegalArgumentException("Values can not be null!");
        }

        setUserId(currentUserId);
        setAvatar(avatarUrl);
        MessengerUserPref.getInstance().setFullName(fullName);
        MessengerUserPref.getInstance().setPresenceChannel(presenceChannel);
    }
}