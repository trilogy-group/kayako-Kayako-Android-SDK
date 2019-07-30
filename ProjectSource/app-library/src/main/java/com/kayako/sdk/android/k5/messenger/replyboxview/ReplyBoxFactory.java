package com.kayako.sdk.android.k5.messenger.replyboxview;

public class ReplyBoxFactory {

    private ReplyBoxFactory() {
    }

    private static volatile ReplyBoxContract.Presenter mPresenter;

    public static ReplyBoxContract.Presenter getPresenter(ReplyBoxContract.View view) {
        if (mPresenter == null) {
            synchronized (ReplyBoxFactory.class) {
                if (mPresenter == null) {
                    return mPresenter = new ReplyBoxPresenter(view);
                } else {
                    mPresenter.setView(view);
                    return mPresenter;
                }
            }
        }
    }

}
