package com.kayako.sdk.android.k5.messenger.conversationlistpage;

public class ConversationListContainerFactory {

    private ConversationListContainerFactory() {
    }

    private static volatile ConversationListContainerContract.Presenter mPresenter;

    public static ConversationListContainerContract.Presenter getPresenter(ConversationListContainerContract.View view) {
        if (mPresenter == null) {
            synchronized (ConversationListContainerFactory.class) {
                if (mPresenter == null) {
                    mPresenter = new ConversationListContainerPresenter(view);
                } else {
                    mPresenter.setView(view);
                }
            }
        }
        return mPresenter;
    }

}

