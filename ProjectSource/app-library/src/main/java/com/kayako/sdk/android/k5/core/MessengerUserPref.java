package com.kayako.sdk.android.k5.core;

import android.content.Context;
import android.content.SharedPreferences;

public class MessengerUserPref {

    final private static String PREF_NAME = "kayako_messenger_user_info";

    final private static String KEY_CURRENT_USER_ID = "current_user_id";
    final private static String KEY_FULL_NAME = "full_name";
    final private static String KEY_AVATAR = "avatar";

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
        sPrefs.edit().putString(KEY_FULL_NAME, fullName).commit();
    }

    public String getAvatar() {
        return sPrefs.getString(KEY_AVATAR, null);
    }

    public void setAvatar(String avatarUrl) {
        sPrefs.edit().putString(KEY_AVATAR, avatarUrl).commit();
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
        sPrefs.edit().putLong(KEY_CURRENT_USER_ID, userId).commit();
    }


    public void clearAll() {
        sPrefs.edit().clear().apply();
    }
}
