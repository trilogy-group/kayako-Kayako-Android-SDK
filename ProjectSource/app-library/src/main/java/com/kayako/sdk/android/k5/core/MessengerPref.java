package com.kayako.sdk.android.k5.core;

import android.content.Context;
import android.content.SharedPreferences;

public class MessengerPref {
    final private String PREF_NAME = "kayako_messenger_info";
    final private String KEY_FINGERPRINT_ID = "fingerprint_id";

    final private String KEY_EMAIL_ID = "email_id";
    final private String KEY_FULL_NAME = "full_name";
    final private String KEY_CURRENT_USER_ID = "current_user_id";

    private static MessengerPref sInstance;
    private static SharedPreferences sPrefs;
    private Context mContext;

    private MessengerPref(Context context) {
        mContext = context.getApplicationContext();
        if (sPrefs == null) {
            sPrefs = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
    }

    public static void createInstance(Context context) {
        sInstance = new MessengerPref(context);
    }

    public static MessengerPref getInstance() {
        if (sInstance == null) {
            throw new NullPointerException("Please call Kayako.initialize() in your Application class");
        }
        return sInstance;
    }

    public String getFingerprintId() {
        return sPrefs.getString(KEY_FINGERPRINT_ID, null);
    }

    public void setFingerprintId(String fingerprintId) {
        sPrefs.edit().putString(KEY_FINGERPRINT_ID, fingerprintId).apply();
    }

    public String getEmailId() {
        return sPrefs.getString(KEY_EMAIL_ID, null);
    }

    public void setEmailId(String email) {
        sPrefs.edit().putString(KEY_EMAIL_ID, email).commit();
    }

    public String getFullName() {
        return sPrefs.getString(KEY_FULL_NAME, null);
    }

    public void setFullName(String fullName) {
        sPrefs.edit().putString(KEY_FULL_NAME, fullName).commit();
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