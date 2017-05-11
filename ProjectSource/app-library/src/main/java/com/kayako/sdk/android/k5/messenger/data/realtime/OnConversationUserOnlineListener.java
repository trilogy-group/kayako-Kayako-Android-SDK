package com.kayako.sdk.android.k5.messenger.data.realtime;

public interface OnConversationUserOnlineListener {

    void onUserOnline(long conversationId, long userId);

    void onUserOffline(long conversationId, long userId);
}
