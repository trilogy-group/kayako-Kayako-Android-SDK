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

    @Override
    public boolean equals(Object o) {
        // DO NOT COMPARE the lastActiveAt - check used in KreSubscription to prevent the same TriggerTask from executing
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientUpdating that = (ClientUpdating) o;

        return is_updating == that.is_updating;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (is_updating ? 1 : 0);
        return result;
    }
}

