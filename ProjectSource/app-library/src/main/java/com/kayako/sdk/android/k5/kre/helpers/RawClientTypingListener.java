package com.kayako.sdk.android.k5.kre.helpers;

public interface RawClientTypingListener {

    void onUserTyping(long userId, boolean isTyping);

    void onConnectionError();
}
