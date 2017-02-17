package com.kayako.sdk.android.k5.messagelistpage;

public class ReplyBoxFactory {

    private ReplyBoxFactory() {
    }

    private static ReplyBoxContract.Presenter mPresenter;

    public static ReplyBoxContract.Presenter getPresenter(ReplyBoxContract.View view) {
        if (mPresenter == null) {
            return mPresenter = new ReplyBoxPresenter(view);
        } else {
            mPresenter.setView(view);
            return mPresenter;
        }
    }

}
