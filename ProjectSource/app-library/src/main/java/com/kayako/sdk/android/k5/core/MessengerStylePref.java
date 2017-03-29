package com.kayako.sdk.android.k5.core;

import android.content.Context;
import android.content.SharedPreferences;

import com.kayako.sdk.android.k5.common.jsonstore.JsonStore;
import com.kayako.sdk.android.k5.common.jsonstore.JsonStoreHelper;
import com.kayako.sdk.android.k5.messenger.style.type.Background;
import com.kayako.sdk.android.k5.messenger.style.type.Foreground;

public class MessengerStylePref extends JsonStore {

    final static private String PREF_NAME = "kayako_messenger_style_info";

    final static private String KEY_FOREGROUND = "key_foreground";
    final static private String KEY_BACKGROUND = "key_background";
    final static private String KEY_PRIMARY_COLOR = "key_primary_color";

    private static MessengerStylePref sInstance;

    private MessengerStylePref(Context context) {
        super(PREF_NAME);
    }

    public static void createInstance(Context context) {
        sInstance = new MessengerStylePref(context);
    }

    public static MessengerStylePref getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("Please call Kayako.initialize() in your Application class");
        }
        return sInstance;
    }

    public void setForeground(Foreground foreground) {
        saveItem(KEY_FOREGROUND, JsonStoreHelper.getJsonStringRepresentation(foreground, Foreground.class));
    }

    public Foreground getForeground() {
        String json = retrieveItem(KEY_FOREGROUND);
        if (json == null) {
            return null;
        } else {
            return JsonStoreHelper.getOriginalClassRepresentation(json, Foreground.class);
        }
    }

    public void setBackground(Background background) {
        saveItem(KEY_BACKGROUND, JsonStoreHelper.getJsonStringRepresentation(background, Background.class));
    }

    public Background getBackground() {
        String json = retrieveItem(KEY_BACKGROUND);
        if (json == null) {
            return null;
        } else {
            return JsonStoreHelper.getOriginalClassRepresentation(json, Background.class);
        }
    }

    public void setPrimaryColor(String hexColor) {
        saveItem(KEY_PRIMARY_COLOR, hexColor);
    }

    public String getPrimaryColor() {
        return retrieveItem(KEY_BACKGROUND);
    }
}
