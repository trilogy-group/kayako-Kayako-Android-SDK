package com.kayako.sdk.android.k5.messenger.data.realtime;

import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.UserViewModel;

public interface OnConversationViewChangeListener {

    void onChange(long conversationId);

    void onTyping(long conversationId, UserViewModel userTyping, boolean isTyping);
}
