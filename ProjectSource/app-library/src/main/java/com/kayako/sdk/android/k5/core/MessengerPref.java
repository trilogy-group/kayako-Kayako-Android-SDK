package com.kayako.sdk.android.k5.core;

import android.content.Context;
import android.content.SharedPreferences;

public class MessengerPref {
    final private static String PREF_NAME = "kayako_messenger_info";
    final private static String KEY_FINGERPRINT_ID = "fingerprint_id";
    final private static String KEY_REALTIME_URL = "realtime_url";
    final private static String KEY_BRAND_NAME = "brand_name";
    final private static String KEY_TITLE = "title";
    final private static String KEY_DESCRIPTION = "description";
    final private static String KEY_URL = "url";
    final private static String KEY_EMAIL_ID = "email_id";
    final private static String KEY_USER_NAME = "user_name";

    private static MessengerPref sInstance;
    private static SharedPreferences sPrefs;

    private MessengerPref(Context context) {
        context = context.getApplicationContext();
        if (sPrefs == null) {
            sPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
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

    public String getBrandName() {
        return sPrefs.getString(KEY_BRAND_NAME, null);
    }

    public void setBrandName(String brandName) {
        sPrefs.edit().putString(KEY_BRAND_NAME, brandName).apply();
    }

    public String getTitle() {
        return sPrefs.getString(KEY_TITLE, null);
    }

    public void setTitle(String title) {
        sPrefs.edit().putString(KEY_TITLE, title).apply();
    }

    public String getDescription() {
        return sPrefs.getString(KEY_DESCRIPTION, null);
    }

    public void setDescription(String description) {
        sPrefs.edit().putString(KEY_DESCRIPTION, description).apply();
    }

    public String getUrl() {
        return sPrefs.getString(KEY_URL, null);
    }

    public void setUrl(String url) {
        sPrefs.edit().putString(KEY_URL, url).apply();
    }

    public String getRealtimeUrl() {
        return sPrefs.getString(KEY_REALTIME_URL, null);
    }

    public void setRealtimeUrl(String realtimeUrl) {
        sPrefs.edit().putString(KEY_REALTIME_URL, realtimeUrl).apply();
    }

    public String getEmailId() {
        return sPrefs.getString(KEY_EMAIL_ID, null);
    }

    public void setEmailId(String email) {
        sPrefs.edit().putString(KEY_EMAIL_ID, email).apply();
    }

    public String getUserName() {
        return sPrefs.getString(KEY_USER_NAME, null);
    }

    public void setUserName(String userName) {
        sPrefs.edit().putString(KEY_USER_NAME, userName).apply();
    }

    public void clearAll() {
        sPrefs.edit().clear().apply();
    }
}
