package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import android.support.annotation.NonNull;

import com.kayako.sdk.android.k5.common.adapter.ContentComparable;

import java.util.HashMap;
import java.util.Map;

public class Attachment implements ContentComparable {

    @NonNull
    private TYPE type;

    public Attachment(TYPE type) {
        if (type == null) {
            throw new IllegalArgumentException("Invalid argument");
        }
        this.type = type;
    }

    public TYPE getType() {
        return type;
    }

    @Override
    public Map<String, String> getContents() {
        Map<String, String> map = new HashMap<>();
        map.put("type", String.valueOf(type));
        return map;
    }

    public enum TYPE {
        URL, FILE
    }
}
