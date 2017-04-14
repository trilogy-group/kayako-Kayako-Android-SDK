package com.kayako.sdk.android.k5.kre.helpers;

public interface MinimalClientTypingListener {

    void onUserTyping(long userId, String userName, String userAvatar, boolean isTyping);

    void onConnectionError();
}
