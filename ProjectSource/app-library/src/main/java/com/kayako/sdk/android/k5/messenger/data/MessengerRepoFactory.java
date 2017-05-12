package com.kayako.sdk.android.k5.messenger.data;

import com.kayako.sdk.android.k5.messenger.data.conversation.unreadcounter.UnreadCounterRepository;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.ConversationStarterRepositoryManyListeners;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.IConversationStarterRepository;

public class MessengerRepoFactory {

    // TODO: One problem with having a universal ConversationStarter data - when the data is cleared - old data persists in-memory
    // TODO: Need to clear all in-memory cache too!

    private MessengerRepoFactory() {
    }

    private static IConversationStarterRepository mConversationStarterData;

    public static IConversationStarterRepository getConversationStarterRepository() {
        if (mConversationStarterData == null) {
            return mConversationStarterData = new ConversationStarterRepositoryManyListeners();
        }
        return mConversationStarterData;
    }

    public static void reset() {
        UnreadCounterRepository.clear();

        if (mConversationStarterData != null) {
            mConversationStarterData.clear();
            mConversationStarterData = null;
        }
    }
}
