package com.kayako.sdk.android.k5.messenger.conversationlistpage;

public class ConversationListFactory {

    private static ConversationListContract.Data mData;
    private static volatile ConversationListContract.Presenter mPresenter;

    public static ConversationListContract.Presenter getPresenter(ConversationListContract.View view) {
        mData = getDataSource();
        if (mPresenter == null) {
            synchronized (ConversationListFactory.class) {
                if (mPresenter == null) {
                    return mPresenter = new ConversationListPresenter(view, mData);
                } else {
                    mPresenter.setView(view);
                    mPresenter.setData(mData);
                    return mPresenter;
                }
            }
        }
    }

    public static ConversationListContract.Data getDataSource() {
        return new ConversationListRepository();
    }

}
