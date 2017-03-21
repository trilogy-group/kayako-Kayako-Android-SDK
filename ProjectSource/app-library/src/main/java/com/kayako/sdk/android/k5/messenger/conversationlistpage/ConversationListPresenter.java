package com.kayako.sdk.android.k5.messenger.conversationlistpage;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.conversationlist.ConversationListItem;
import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.messenger.conversation.Conversation;
import com.kayako.sdk.utils.FingerprintUtils;

import java.util.ArrayList;
import java.util.List;

public class ConversationListPresenter implements ConversationListContract.Presenter {

    private ConversationListContract.Data mData;
    private ConversationListContract.View mView;

    private static final int LIMIT = 20;
    private int mOffset = 0;


    public ConversationListPresenter(ConversationListContract.View mView, ConversationListContract.Data mData) {
        this.mData = mData;
        this.mView = mView;
    }

    @Override
    public void setData(ConversationListContract.Data data) {
        mData = data;
    }

    @Override
    public void setView(ConversationListContract.View view) {
        mView = view;
    }

    public void initPage() {
        resetVariables();

        String fingerprintId = MessengerPref.getInstance().getFingerprintId();
        if (fingerprintId == null) {
            MessengerPref.getInstance().setFingerprintId(FingerprintUtils.generateUUIDv4());
        }
        // TODO: For testing
        //.setFingerprintId("d0bc691c-62c5-468c-a4a5-3b096684dc96");

        reloadPage();

    }

    private void reloadPage() {
        mView.showLoadingView();
        loadConversations(mOffset = 0);
    }

    private void loadConversations(final int offset) {
        mData.getConversationList(new ConversationListContract.OnLoadConversationsListener() {
            @Override
            public void onSuccess(List<Conversation> conversations) {
                synchronized (this) { // To avoid race conditions - especially when using load more
                    // Error Handling
                    if (conversations == null || conversations.size() == 0) {
                        if (offset == 0) {
                            mView.showEmptyView();
                        } else {
                            configureIfMoreItemsAvailable(0);
                            mView.configureLoadMoreView(false);
                        }
                        return;
                    }

                    // Configuring list - load more or setup list
                    if (offset == 0) {
                        mView.setupList(convertConversationToListItems(conversations));
                    } else {
                        mView.appendToEndOfListAndStopLoadMoreProgress(convertConversationToListItems(conversations));
                    }

                    configureIfMoreItemsAvailable(conversations.size());
                    mOffset = offset + conversations.size();
                }
            }

            @Override
            public void onFailure() {
                synchronized (this) {
                    if (offset == 0) {
                        mView.showErrorView();
                    } else {
                        mView.configureLoadMoreView(false);
                        mView.showMessage("Failed to load more!"); // TODO
                    }
                }
            }
        }, offset, LIMIT); // TODO: Offset, Limit

    }


    public void closePage() {
        // TODO: Stop all tasks in Data
    }

    @Override
    public void onLoadMoreItems() {
        if (mView.getIfListHasMoreItems()) {
            mView.configureLoadMoreView(true);
            loadConversations(mOffset);
        }
    }

    @Override
    public void onClickConversation(Conversation conversation) {
        mView.openMessageListPage(conversation.getId(), RequestCodes.REQUEST_CODE_OPEN_EXISTING_CONVERSATION);
    }

    @Override
    public void onClickRetryOnError() {
        reloadPage();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode) {
        if (requestCode == RequestCodes.REQUEST_CODE_OPEN_EXISTING_CONVERSATION) {
            reloadConversations();
        }
    }

    @Override
    public void reloadConversations() {
        loadConversations(0);
    }

    private List<BaseListItem> convertConversationToListItems(List<Conversation> conversations) {
        List<BaseListItem> baseListItems = new ArrayList<>();
        for (Conversation conversation : conversations) {
            baseListItems.add(new ConversationListItem(
                    conversation.getCreator().getAvatarUrl(), // TODO: Which photo to show? The agent?
                    conversation.getCreator().getFullName(),// TODO: Whose name? The agent?
                    conversation.getUpdatedAt(),
                    conversation.getSubject(), // TODO: Shouldn't we show the preview message
                    conversation));

        }
        return baseListItems;
    }

    private void resetVariables() {
        mOffset = 0;
    }

    private void configureIfMoreItemsAvailable(int numberOfItems) {
        if (numberOfItems < LIMIT) {
            mView.setListHasMoreItems(false);
        } else {
            mView.setListHasMoreItems(true);
        }
    }

}
