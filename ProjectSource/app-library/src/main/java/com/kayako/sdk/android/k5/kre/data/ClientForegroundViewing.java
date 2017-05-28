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

    @Override
    public boolean equals(Object o) {
        // DO NOT COMPARE the lastActiveAt - check used in KreSubscription to prevent the same TriggerTask from executing
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientForegroundViewing that = (ClientForegroundViewing) o;

        if (is_viewing != that.is_viewing) return false;
        return is_foreground == that.is_foreground;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (is_viewing ? 1 : 0);
        result = 31 * result + (is_foreground ? 1 : 0);
        return result;
    }
}


