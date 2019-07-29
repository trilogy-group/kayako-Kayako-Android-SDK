package com.kayako.sdk.android.k5.core;

import android.content.Context;
import android.content.SharedPreferences;

public class MessengerUserPref {

    private static final String PREF_NAME = "kayako_messenger_user_info";

    private static final String KEY_CURRENT_USER_ID = "current_user_id";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_AVATAR = "avatar";
    private static final String KEY_PRESENCE_CHANNEL = "presence_channel";

    private static MessengerUserPref sInstance;
    private static SharedPreferences sPrefs;

    private MessengerUserPref(Context context) {
        context = context.getApplicationContext();
        if (sPrefs == null) {
            sPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
    }

    public static void createInstance(Context context) {
        sInstance = new MessengerUserPref(context);
    }

    public static MessengerUserPref getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("Please call Kayako.initialize() in your Application class");
        }
        return sInstance;
    }

    public String getFullName() {
        return sPrefs.getString(KEY_FULL_NAME, null);
    }

    public void setFullName(String fullName) {
        sPrefs.edit().putString(KEY_FULL_NAME, fullName).apply();
    }

    public String getAvatar() {
        return sPrefs.getString(KEY_AVATAR, null);
    }

    public void setAvatar(String avatarUrl) {
        sPrefs.edit().putString(KEY_AVATAR, avatarUrl).apply();
    }

    public Long getUserId() {
        long userId = sPrefs.getLong(KEY_CURRENT_USER_ID, 0);
        if (userId == 0) {
            return null;
        } else {
            return userId;
        }
    }

    public void setUserId(long userId) {
        sPrefs.edit().putLong(KEY_CURRENT_USER_ID, userId).apply();
    }

    public String getPresenceChannel() {
        return sPrefs.getString(KEY_PRESENCE_CHANNEL, null);
    }

    public void setPresenceChannel(String presenceChannel) {
        sPrefs.edit().putString(KEY_PRESENCE_CHANNEL, presenceChannel).apply();
    }


    public void clearAll() {
        sPrefs.edit().clear().apply();
    }
}
