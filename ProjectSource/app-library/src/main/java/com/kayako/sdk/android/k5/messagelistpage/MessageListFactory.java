package com.kayako.sdk.android.k5.messagelistpage;

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
