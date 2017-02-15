package com.kayako.sdk.android.k5.conversationlistpage;

import com.kayako.sdk.android.k5.common.mvp.BasePresenter;
import com.kayako.sdk.android.k5.common.mvp.BaseView;

public class ConversationListContainerContract {

    interface View extends BaseView {

        void hideNewConversationButton();

        void showNewConversationButton();

        void openNewConversationPage();
    }

    interface Presenter extends BasePresenter<View> {

        void onOpenPage();

        void onClosePage();

        void onClickNewConversation();

        void onScrollConversationList(boolean isScrolling);
    }
}
