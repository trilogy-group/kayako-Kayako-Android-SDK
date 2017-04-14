package com.kayako.sdk.android.k5.messenger.data.realtime;

import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.ConversationViewModel;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.UserViewModel;

public interface OnConversationMessagesViewChangeListener {

    void onChange(long conversationId, ConversationViewModel conversationViewModel);

    void onTyping(long conversationId, UserViewModel userViewModel, boolean isTyping);

    void onNewMessage(long conversationId, long messageId);

    void onUpdateMessage(long conversationId, long messageId);
}
