package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import android.support.annotation.NonNull;

public class Attachment {

    @NonNull private TYPE type;

    public Attachment(TYPE type) {
        if (type == null) {
            throw new IllegalArgumentException("Invalid argument");
        }
        this.type = type;
    }

    public TYPE getType() {
        return type;
    }

    public enum TYPE {
        URL, FILE
    }
}
