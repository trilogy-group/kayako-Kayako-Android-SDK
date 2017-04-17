package com.kayako.sdk.android.k5.messenger.data.realtime;

import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.UserViewModel;
import com.kayako.sdk.messenger.conversation.Conversation;

public interface OnConversationViewChangeListener {

    void onChange(Conversation conversation);

    void onTyping(long conversationId, UserViewModel userTyping, boolean isTyping);
}
