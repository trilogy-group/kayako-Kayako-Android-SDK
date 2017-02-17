package com.kayako.sdk.android.k5.conversationlistpage;

public class ConversationListContainerPresenter implements ConversationListContainerContract.Presenter {


    // TODO: Refresh this page on returning to it - onActivityResult?

    ConversationListContainerContract.View mView;

    public ConversationListContainerPresenter(ConversationListContainerContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void setView(ConversationListContainerContract.View view) {
        mView = view;
    }

    @Override
    public void onOpenPage() {

    }

    @Override
    public void onClosePage() {

    }

    @Override
    public void onClickNewConversation() {
        mView.openNewConversationPage();
    }

    @Override
    public void onScrollConversationList(boolean isScrolling) {
        if (isScrolling) {
            mView.hideNewConversationButton();
        } else {
            mView.showNewConversationButton();
        }
    }
}
