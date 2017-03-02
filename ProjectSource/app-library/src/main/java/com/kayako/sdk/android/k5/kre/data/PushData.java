package com.kayako.sdk.android.k5.kre.data;

import com.google.gson.Gson;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public abstract class PushData {

    @Override
    public String toString() {
        try {
            return new Gson().toJson(this);
        } catch (Exception e) {
            return super.toString();
        }
    }
}
