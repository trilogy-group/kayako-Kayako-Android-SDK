package com.kayako.sdk.android.k5.messenger.messagelistpage;

public class MessageListFactory {

    private static MessageListContract.Presenter mPresenter;

    public static MessageListContract.Presenter getPresenter(MessageListContract.View view) {
        if (mPresenter == null) {
            return mPresenter = new MessageListPresenter(view);
        } else {
            mPresenter.setView(view);
            return mPresenter;
        }
    }
}
