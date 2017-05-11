package com.kayako.sdk.android.k5.messenger.toolbarview;

import com.kayako.sdk.android.k5.messenger.data.MessengerRepoFactory;

public class MessengerToolbarFactory {

    public static MessengerToolbarContract.Presenter getPresenter(MessengerToolbarContract.ConfigureView view) {
        return new MessengerToolbarPresenter(view, MessengerRepoFactory.getConversationStarterRepository());
    }

}
