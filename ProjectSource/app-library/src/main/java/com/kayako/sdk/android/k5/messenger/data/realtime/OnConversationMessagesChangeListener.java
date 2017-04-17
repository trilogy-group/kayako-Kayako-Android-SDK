package com.kayako.sdk.android.k5.messenger.data.realtime;

public interface OnConversationMessagesChangeListener {

    void onNewMessage(long conversationId, long messageId);

    void onUpdateMessage(long conversationId, long messageId);
}
