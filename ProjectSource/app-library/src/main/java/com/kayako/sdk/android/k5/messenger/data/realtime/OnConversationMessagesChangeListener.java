package com.kayako.sdk.android.k5.messenger.data.realtime;

import com.kayako.sdk.messenger.message.Message;

public interface OnConversationMessagesChangeListener {

    void onNewMessage(long conversationId, long messageId); // should load all recent messages

    void onUpdateMessage(long conversationId, Message message); // update existing loaded message
}
