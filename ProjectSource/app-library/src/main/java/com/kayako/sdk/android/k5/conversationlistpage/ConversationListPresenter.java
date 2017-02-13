package com.kayako.sdk.android.k5.conversationlistpage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.conversationlist.ConversationListItem;
import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.messenger.conversation.Conversation;
import com.kayako.sdk.messenger.conversation.fields.status.Status;

import java.util.ArrayList;
import java.util.List;

public class ConversationListPresenter implements ConversationListContract.Presenter {

    private ConversationListContract.Data mData;
    private ConversationListContract.View mView;

    private static final int LIMIT = 2;
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
        // TODO: Fingerprint?
        MessengerPref.getInstance().setFingerprintId("d0bc691c-62c5-468c-a4a5-3b096684dc96");
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
                        if (mOffset == 0) {
                            mView.showEmptyView();
                        } else {
                            configureIfMoreItemsAvailable(0);
                            mView.configureLoadMoreView(false);
                        }
                        return;
                    }

                    // Configuring list - load more or setup list
                    if (mOffset == 0) {
                        mView.setupList(convertConversationToListItems(conversations));
                    } else {
                        mView.appendToEndOfListAndStopLoadMoreProgress(convertConversationToListItems(conversations));
                    }

                    configureIfMoreItemsAvailable(conversations.size());
                    mOffset += conversations.size();
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
        mView.openMessageListPage(conversation.getId());
    }

    @Override
    public void onClickRetryOnError() {
        // TODO: Reload page
    }

    private List<BaseListItem> convertConversationToListItems(List<Conversation> conversations) {
        List<BaseListItem> baseListItems = new ArrayList<>();
        for (Conversation conversation : conversations) {
            baseListItems.add(new ConversationListItem(
                    conversation.getCreator().getAvatarUrl(), // TODO: Which photo to show? The agent?
                    conversation.getCreator().getFullName(),// TODO: Whose name? The agent?
                    conversation.getUpdatedAt(),
                    conversation.getSubject(), // TODO: Shouldn't we show the preview message
                    conversation.getStatus() != null ? conversation.getStatus().getLabel() : null,
                    getStatusColor(conversation.getStatus()),
                    conversation));

        }
        return baseListItems;
    }

    private ConversationListItem.StatusColor getStatusColor(@Nullable Status status) {
        if (status == null) {
            return null;
        }

        switch (status.getType()) {
            case NEW:
            case OPEN:
                return ConversationListItem.StatusColor.BLUE;

            case CLOSED:
                return ConversationListItem.StatusColor.GRAY;

            case COMPLETED:
                return ConversationListItem.StatusColor.GREEN;

            default:
            case CUSTOM:
                return ConversationListItem.StatusColor.YELLOW;
        }
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
