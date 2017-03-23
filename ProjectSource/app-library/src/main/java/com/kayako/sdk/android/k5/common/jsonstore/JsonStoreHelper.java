package com.kayako.sdk.android.k5.common.jsonstore;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.kayako.sdk.android.k5.messenger.style.GsonFactory;

public class JsonStoreHelper {

    private static Gson getGson() {
        return GsonFactory.getGson();
    }

    public static <T> String getJsonStringRepresentation(@NonNull T tObject, @NonNull Class<T> tClass) {
        return getGson().toJson(tObject, tClass);
    }

    public static <T> T getOriginalClassRepresentation(@NonNull String json, @NonNull Class<T> tClass) {
        return getGson().fromJson(json, tClass);
    }
}
