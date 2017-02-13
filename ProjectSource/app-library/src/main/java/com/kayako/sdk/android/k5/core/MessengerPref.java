package com.kayako.sdk.android.k5.core;

import android.content.Context;
import android.content.SharedPreferences;

public class MessengerPref {
    final private String PREF_NAME = "kayako_messenger_info";
    final private String KEY_FINGERPRINT_ID = "fingerprint_id";

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

}
