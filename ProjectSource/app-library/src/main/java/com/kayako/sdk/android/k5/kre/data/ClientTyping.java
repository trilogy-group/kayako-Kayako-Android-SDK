package com.kayako.sdk.android.k5.kre.data;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class ClientTyping extends PushData {

    public boolean is_typing; // NOTE: retain this pascal case - required for json representation
    public long last_active_at;

    public ClientTyping(boolean is_typing, long last_active_at) {
        this.is_typing = is_typing;
        this.last_active_at = last_active_at;
    }

    @Override
    public boolean equals(Object o) {
        // DO NOT COMPARE the lastActiveAt - check used in KreSubscription to prevent the same TriggerTask from executing
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientTyping that = (ClientTyping) o;

        return is_typing == that.is_typing;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (is_typing ? 1 : 0);
        return result;
    }
}


