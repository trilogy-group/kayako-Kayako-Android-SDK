package com.kayako.sdk.android.k5.messenger.toolbarview;

import com.kayako.sdk.android.k5.messenger.data.RepoFactory;

public class MessengerToolbarFactory {

    public static MessengerToolbarContract.Presenter getPresenter(MessengerToolbarContract.ConfigureView view) {
        return new MessengerToolbarPresenter(view, RepoFactory.getConversationStarterRepository());
    }

}
