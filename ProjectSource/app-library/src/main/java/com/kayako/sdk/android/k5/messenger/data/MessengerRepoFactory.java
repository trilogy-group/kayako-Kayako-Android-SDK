package com.kayako.sdk.android.k5.messenger.data;

import com.kayako.sdk.android.k5.messenger.data.conversationstarter.ConversationStarterRepositoryManyListeners;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.IConversationStarterRepository;

public class MessengerRepoFactory {

    private MessengerRepoFactory() {
    }

    private static volatile IConversationStarterRepository mConversationStarterData;

    public static IConversationStarterRepository getConversationStarterRepository() {
        if (mConversationStarterData == null) {
            synchronized (MessengerRepoFactory.class) {
                if (mConversationStarterData == null) {
                    return mConversationStarterData = new ConversationStarterRepositoryManyListeners();
                }
            }
        }
        return mConversationStarterData;
    }

    public static void reset() {
        if (mConversationStarterData != null) {
            mConversationStarterData.clear();
            mConversationStarterData = null;
        }
    }
}
