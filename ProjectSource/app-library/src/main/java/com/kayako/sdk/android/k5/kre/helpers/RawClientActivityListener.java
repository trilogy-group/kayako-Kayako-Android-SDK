package com.kayako.sdk.android.k5.kre.helpers;

public interface RawClientActivityListener {

    void onClientActivity(long userId, boolean isUpdating);

    void onConnectionError();
}
