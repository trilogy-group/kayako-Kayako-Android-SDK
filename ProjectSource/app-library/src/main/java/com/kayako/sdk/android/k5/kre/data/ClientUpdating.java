package com.kayako.sdk.android.k5.kre.data;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class ClientUpdating extends PushData {

    public boolean is_updating; // NOTE: retain this pascal case - required for json representation
    public long last_active_at;

    public ClientUpdating(boolean is_updating, long last_active_at) {
        this.is_updating = is_updating;
        this.last_active_at = last_active_at;
    }
}

