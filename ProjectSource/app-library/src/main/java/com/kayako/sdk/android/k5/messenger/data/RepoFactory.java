package com.kayako.sdk.android.k5.messenger.data;

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
