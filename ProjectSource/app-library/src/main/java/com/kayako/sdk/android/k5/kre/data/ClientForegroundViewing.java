package com.kayako.sdk.android.k5.kre.data;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class ClientForegroundViewing extends PushData {

    public boolean is_viewing; // NOTE: retain this pascal case - required for json representation
    public boolean is_foreground;
    public long last_active_at;

    public ClientForegroundViewing(boolean is_viewing, boolean is_foreground, long last_active_at) {
        this.is_viewing = is_viewing;
        this.is_foreground = is_foreground;
        this.last_active_at = last_active_at;
    }
}


