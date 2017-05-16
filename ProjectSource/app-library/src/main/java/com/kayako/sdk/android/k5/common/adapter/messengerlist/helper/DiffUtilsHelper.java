package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import java.util.Map;

public class DiffUtilsHelper {

    private DiffUtilsHelper() {
    }

    public static String convertToString(Map<String, String> map) {
        if (map == null) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String key : map.keySet()) {
            String value = map.get(key);
            if (value != null) {
                stringBuilder.append(key);
                stringBuilder.append("=");
                stringBuilder.append(value);
                stringBuilder.append("\n");
            }
        }

        return stringBuilder.toString();
    }
}
