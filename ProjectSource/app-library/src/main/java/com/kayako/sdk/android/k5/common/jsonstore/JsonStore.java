package com.kayako.sdk.android.k5.common.jsonstore;

import android.content.Context;
import android.content.SharedPreferences;

import com.kayako.sdk.android.k5.core.Kayako;

import java.util.Set;

public class JsonStore {

    private SharedPreferences mPrefs;

    public JsonStore(String prefName) {
        Context context = Kayako.getApplicationContext();
        mPrefs = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }

    public void saveItem(String key, String json) {
        mPrefs.edit().putString(key, json).apply();
    }

    public String retrieveItem(String key) {
        return mPrefs.getString(key, null);
    }

    public void removeItem(String key) {
        mPrefs.edit().remove(key).apply();
    }

    public void clearAll() {
        mPrefs.edit().clear().apply();
    }

    public Set<String> retrieveAllKeys(){
        return mPrefs.getAll().keySet();
    }
}
