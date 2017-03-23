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

    private static MessengerStylePref sInstance;

    private MessengerStylePref(Context context) {
        super(PREF_NAME);
    }

    public static void createInstance(Context context) {
        sInstance = new MessengerStylePref(context);
    }

    public static MessengerStylePref getInstance() {
        if (sInstance == null) {
            throw new NullPointerException("Please call Kayako.initialize() in your Application class");
        }
        return sInstance;
    }

    public static void setForeground(Foreground foreground) {
        sInstance.saveItem(KEY_FOREGROUND, JsonStoreHelper.getJsonStringRepresentation(foreground, Foreground.class));
    }

    public static Foreground getForeground() {
        String json = sInstance.retrieveItem(KEY_FOREGROUND);
        if (sInstance == null) {
            return null;
        } else {
            return JsonStoreHelper.getOriginalClassRepresentation(json, Foreground.class);
        }
    }

    public static void setBackground(Background background) {
        sInstance.saveItem(KEY_BACKGROUND, JsonStoreHelper.getJsonStringRepresentation(background, Background.class));
    }

    public static Background getBackground() {
        String json = sInstance.retrieveItem(KEY_BACKGROUND);
        if (sInstance == null) {
            return null;
        } else {
            return JsonStoreHelper.getOriginalClassRepresentation(json, Background.class);
        }
    }

}
