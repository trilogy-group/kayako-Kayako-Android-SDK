package com.kayako.sdk.android.k5.kre.helpers;

public interface RawCasePostChangeListener {

    void onNewPost(long messageId);

    void onChangePost(long messageId);

    void onConnectionError();
}
