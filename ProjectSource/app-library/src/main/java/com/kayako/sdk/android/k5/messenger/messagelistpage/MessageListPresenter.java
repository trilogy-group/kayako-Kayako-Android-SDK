package com.kayako.sdk.android.k5.messenger.messagelistpage;

public class MessageListPresenter implements MessageListContract.Presenter {

    // TODO: Decide what presentation logic should go here - only that which is specific to the Message Listing but doesn't require syncing between the other components

    private MessageListContract.View mView;

    public MessageListPresenter(MessageListContract.View view) {
        mView = view;
    }

    @Override
    public void setView(MessageListContract.View view) {
        mView = view;
    }

    @Override
    public void initPage() {
    }


    @Override
    public void closePage() {
    }
}
