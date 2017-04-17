package com.kayako.sdk.android.k5.messenger.data.realtime;

import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.auth.FingerprintAuth;
import com.kayako.sdk.base.callback.ItemCallback;
import com.kayako.sdk.messenger.Messenger;
import com.kayako.sdk.messenger.conversation.Conversation;

public class LoadResourceHelper {

    private LoadResourceHelper() {
    }

    public static void loadConversation(long conversationId, ItemCallback<Conversation> conversationItemCallback) {
        String url = MessengerPref.getInstance().getUrl();
        String fingerprintId = MessengerPref.getInstance().getFingerprintId();

        new Messenger(url, new FingerprintAuth(fingerprintId))
                .getConversation(conversationId, conversationItemCallback);
    }
}
