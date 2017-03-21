package com.kayako.sdk.android.k5.messenger.homescreenpage;

public class HomeScreenContainerPresenter implements HomeScreenContainerContract.Presenter {

    private HomeScreenContainerContract.View mView;
    private boolean showReplyBoxOverNewConversationButton;

    public HomeScreenContainerPresenter(HomeScreenContainerContract.View view) {
        mView = view;
    }

    @Override
    public void initPage() {
        // TODO: Decide the value of showReplyBoxOverNewConversationButton here based on Global states
        setNewConversationPanel();
    }

    @Override
    public void onScrollList(boolean isScrolling) {
        if (showReplyBoxOverNewConversationButton) {
            return;
        }

        if (isScrolling) {
            mView.showNewConversationButton();
        } else {
            mView.hideNewConversationButton();
        }
    }

    private void setNewConversationPanel() {
        if (showReplyBoxOverNewConversationButton) {
            mView.showReplyBoxButton();
            mView.hideNewConversationButton();
        } else {
            mView.showNewConversationButton();
            mView.hideReplyBoxButton();
        }
    }
}
