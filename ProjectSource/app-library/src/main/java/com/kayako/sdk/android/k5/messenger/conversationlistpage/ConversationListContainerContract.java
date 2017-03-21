package com.kayako.sdk.android.k5.messenger.conversationlistpage;

import com.kayako.sdk.android.k5.common.fragments.ListPageState;
import com.kayako.sdk.android.k5.common.mvp.BasePresenter;
import com.kayako.sdk.android.k5.common.mvp.BaseView;

public class ConversationListContainerContract {

    interface View extends BaseView {

        void hideNewConversationButton();

        void showNewConversationButton();

        void openNewConversationPage(int requestCode);

        void reloadConversations();

        void configureDefaultToolbar();
    }

    interface Presenter extends BasePresenter<View> {

        void onOpenPage();

        void onClosePage();

        void onClickNewConversation();

        void onScrollConversationList(boolean isScrolling);

        void onPageStateChange(ListPageState state);

        void onActivityResult(int requestCode, int resultCode);
    }
}
