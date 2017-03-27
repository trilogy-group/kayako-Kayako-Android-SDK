package com.kayako.sdk.android.k5.messenger.data;

import com.kayako.sdk.android.k5.messenger.data.conversationstarter.ConversationStarterRepositoryManyListeners;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.IConversationStarterRepository;

public class RepoFactory {

    private RepoFactory() {
    }

    private static IConversationStarterRepository mConversationStarterData;

    public static IConversationStarterRepository getConversationStarterRepository() {
        if (mConversationStarterData == null) {
            return mConversationStarterData = new ConversationStarterRepositoryManyListeners();
        }
        return mConversationStarterData;
    }
}
