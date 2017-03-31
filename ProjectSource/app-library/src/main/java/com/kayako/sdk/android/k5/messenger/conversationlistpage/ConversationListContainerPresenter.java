package com.kayako.sdk.android.k5.messenger.conversationlistpage;

import com.kayako.sdk.android.k5.common.fragments.ListPageState;

public class ConversationListContainerPresenter implements ConversationListContainerContract.Presenter {

    private ConversationListContainerContract.View mView;

    private boolean mIsScrolling;
    private ListPageState mListPageState;

    public ConversationListContainerPresenter(ConversationListContainerContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void setView(ConversationListContainerContract.View view) {
        mView = view;
    }

    @Override
    public void onOpenPage() {
        mView.configureDefaultToolbar();
    }

    @Override
    public void onClosePage() {
    }

    /**
     * All conditions to show the New Conversation button visibility should be restricted to this method
     */
    private void configureNewConversationVisibility() {
        if (mListPageState != null) {
            switch (mListPageState) {
                case LIST:
                    if (mIsScrolling) {
                        mView.hideNewConversationButton();
                    } else {
                        mView.showNewConversationButton();
                    }
                    break;

                case EMPTY:
                    mView.showNewConversationButton();
                    break;

                case NONE:
                case ERROR:
                case LOADING:
                    mView.hideNewConversationButton();
                    break;
            }
        } else {
            mView.hideNewConversationButton();
        }
    }

    @Override
    public void onClickNewConversation() {
        mView.openNewConversationPage(RequestCodes.REQUEST_CODE_CREATE_NEW_CONVERSATION);
    }

    @Override
    public void onScrollConversationList(boolean isScrolling) {
        mIsScrolling = isScrolling;
        configureNewConversationVisibility();

        // Collapse Toolbar on scroll
        mView.collapseToolbar();
    }

    @Override
    public void onPageStateChange(ListPageState state) {
        mListPageState = state;
        configureNewConversationVisibility();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode) {
        switch (requestCode) {
            case RequestCodes.REQUEST_CODE_CREATE_NEW_CONVERSATION:
            case RequestCodes.REQUEST_CODE_OPEN_EXISTING_CONVERSATION:
                mView.reloadConversations();
        }
    }
}
