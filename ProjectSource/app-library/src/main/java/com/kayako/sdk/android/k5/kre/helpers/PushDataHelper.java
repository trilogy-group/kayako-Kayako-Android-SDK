package com.kayako.sdk.android.k5.kre.helpers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kayako.sdk.android.k5.kre.data.PushData;

public class PushDataHelper {

    private static final String TAG = "PushDataHelper";

    public static String convertToJsonString(Object data) {
        try {
            Gson gson = new Gson();
            return gson.toJson(data);
        } catch (JsonSyntaxException e) {
            KreLogHelper.printStackTrace(TAG, e);
            return null;
        }
    }

    public static <T extends PushData> T convertFromJsonString(Class<T> clazz, String jsonData) {
        try {
            Gson gson = new Gson();
            T t = gson.fromJson(jsonData, clazz);
            return t;
        } catch (JsonSyntaxException e) {
            KreLogHelper.printStackTrace(TAG, e);
            return null;
        }
    }

}
