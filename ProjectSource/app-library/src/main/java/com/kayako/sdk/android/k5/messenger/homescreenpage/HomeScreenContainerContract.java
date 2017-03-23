package com.kayako.sdk.android.k5.messenger.homescreenpage;

public class HomeScreenContainerContract {

    public interface View {

        void showNewConversationButton();

        void hideNewConversationButton();

        void openNewConversationPage();
    }

    public interface Presenter {

        void initPage();

        void onScrollList(boolean isScrolling);

        void onClickNewConversationButton();
    }
}
