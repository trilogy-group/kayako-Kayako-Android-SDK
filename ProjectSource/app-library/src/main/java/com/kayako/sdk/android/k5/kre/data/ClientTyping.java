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
}


