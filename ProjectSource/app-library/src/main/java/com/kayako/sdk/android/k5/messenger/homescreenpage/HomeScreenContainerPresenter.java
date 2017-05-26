package com.kayako.sdk.android.k5.messenger.homescreenpage;

public class HomeScreenContainerPresenter implements HomeScreenContainerContract.Presenter {

    private HomeScreenContainerContract.View mView;

    public HomeScreenContainerPresenter(HomeScreenContainerContract.View view) {
        mView = view;
    }

    @Override
    public void initPage() {
        mView.showNewConversationButton();
    }

    @Override
    public void onScrollList(boolean isScrolling) {
        if (isScrolling) {
            mView.hideNewConversationButton();
        } else {
            mView.showNewConversationButton();
        }
    }

    @Override
    public void onClickNewConversationButton() {
        mView.openNewConversationPage();
    }
}
