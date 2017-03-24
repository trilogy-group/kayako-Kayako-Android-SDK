package com.kayako.sdk.android.k5.messenger.data;

import com.kayako.sdk.error.KayakoException;
import com.kayako.sdk.messenger.conversationstarter.ConversationStarter;

public interface IConversationStarterRepository {
    void getConversationStarter(OnLoadConversationStarterListener metrics);

    interface OnLoadConversationStarterListener {
        void onLoadConversationMetrics(ConversationStarter conversationStarter);

        void onFailure(KayakoException exception);
    }

}
