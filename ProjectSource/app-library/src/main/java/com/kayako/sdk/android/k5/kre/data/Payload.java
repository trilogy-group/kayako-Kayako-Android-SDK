package com.kayako.sdk.android.k5.kre.data;

import com.google.gson.Gson;

public abstract class Payload {

    @Override
    public String toString() {
        try {
            return new Gson().toJson(this);
        } catch (Exception e) {
            return super.toString();
        }
    }

    @Override
    public boolean equals(Object o) {
        try {
            String otherPayload = ((Payload) o).toString();
            String thisPayload = this.toString();

            return otherPayload.equals(thisPayload);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();

        String string = toString();
        result = 31 * result + (string != null ? string.hashCode() : 0);
        return result;
    }
}
