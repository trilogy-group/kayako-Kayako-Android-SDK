package com.kayako.sdk.android.k5.messenger.data.realtime;

import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.auth.FingerprintAuth;
import com.kayako.sdk.base.callback.ItemCallback;
import com.kayako.sdk.messenger.Messenger;
import com.kayako.sdk.messenger.conversation.Conversation;
import com.kayako.sdk.messenger.message.Message;

public class LoadResourceHelper {

    private LoadResourceHelper() {
    }

    private static Messenger getMessenger() {
        String url = MessengerPref.getInstance().getUrl();
        String fingerprintId = MessengerPref.getInstance().getFingerprintId();

        return new Messenger(url, new FingerprintAuth(fingerprintId));
    }

    public static void loadConversation(long conversationId, ItemCallback<Conversation> conversationItemCallback) {
        getMessenger().getConversation(conversationId, conversationItemCallback);
    }

    public static void loadMessage(long conversationId, long messageId, ItemCallback<Message> messageCallback) {
        getMessenger().getMessage(conversationId, messageId, messageCallback);
    }
}
