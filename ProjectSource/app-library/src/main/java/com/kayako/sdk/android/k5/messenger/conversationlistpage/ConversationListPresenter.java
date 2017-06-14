package com.kayako.sdk.android.k5.messenger.conversationlistpage;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.conversationlist.ConversationListItem;
import com.kayako.sdk.android.k5.common.utils.FailsafePollingHelper;
import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.android.k5.messenger.data.conversation.unreadcounter.UnreadCounterRepository;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.ClientTypingActivity;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.ConversationViewModel;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.ConversationViewModelHelper;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.UserViewModel;
import com.kayako.sdk.android.k5.messenger.data.realtime.OnConversationChangeListener;
import com.kayako.sdk.android.k5.messenger.data.realtime.OnConversationClientActivityListener;
import com.kayako.sdk.android.k5.messenger.data.realtime.RealtimeConversationHelper;
import com.kayako.sdk.messenger.conversation.Conversation;
import com.kayako.sdk.utils.FingerprintUtils;

import java.util.ArrayList;
import java.util.List;

public class ConversationListPresenter implements ConversationListContract.Presenter, OnConversationChangeListener, OnConversationClientActivityListener {

    private ConversationListContract.Data mData;
    private ConversationListContract.View mView;

    private static final int LIMIT = 20;
    private int mOffset = 0;

    private ConversationViewModelHelper mConversationViewModelHelper = new ConversationViewModelHelper();
    private FailsafePollingHelper mFailsafePollingHelper = new FailsafePollingHelper();

    // TODO: Refactor conversation list to retain list of realtimeconversation and re-render whole list everytime
    // TODO: Scroll to the top of the list when there's a new item - gets hidden and user assumes there're no new conversations

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

        mView.showLoadingView();
        // loadConversations will be started in onResume()

        mFailsafePollingHelper.startPolling(new FailsafePollingHelper.PollingListener() {
            @Override
            public void onPoll() {
                loadConversations(0);
            }
        });
    }

    public void closePage() {
        // TODO: Stop all tasks in Data

        // Stop tracking the following
        RealtimeConversationHelper.untrack((OnConversationChangeListener) this);
        RealtimeConversationHelper.untrack((OnConversationClientActivityListener) this);

        mFailsafePollingHelper.stopPolling();
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

                    // Save list items
                    for (Conversation conversation : conversations) {

                        mConversationViewModelHelper.addOrUpdateElement(conversation, null);

                        RealtimeConversationHelper.trackChange(
                                conversation.getRealtimeChannel(),
                                conversation.getId(),
                                ConversationListPresenter.this
                        );

                        RealtimeConversationHelper.trackClientActivity(
                                conversation.getRealtimeChannel(),
                                conversation.getId(),
                                ConversationListPresenter.this
                        );
                    }

                    // Configuring list - load more or setWasNewConversation list
                    if (offset != 0) {
                        mView.configureLoadMoreView(false);
                    }

                    refreshListView();

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
        }, offset, LIMIT);
    }

    private void refreshListView() {
        mView.setupList(
                convertConversationToListItems(
                        mConversationViewModelHelper.getConversationList()));

    }

    @Override
    public void onLoadMoreItems() {
        if (mView.getIfListHasMoreItems()) {
            mView.configureLoadMoreView(true);
            loadConversations(mOffset);
        }
    }

    @Override
    public void onClickConversation(long conversationId) {
        mView.openMessageListPage(conversationId, RequestCodes.REQUEST_CODE_OPEN_EXISTING_CONVERSATION);
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

    @Override
    public void onStart() {
        loadConversations(0);
    }

    private List<BaseListItem> convertConversationToListItems(List<ConversationViewModel> conversations) {
        List<BaseListItem> baseListItems = new ArrayList<>();
        for (ConversationViewModel conversation : conversations) {
            baseListItems.add(new ConversationListItem(conversation));
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

    @Override
    public void onChange(Conversation conversation) {
        if (!mConversationViewModelHelper.exists(conversation.getId())) {
            return;
        }

        // Load and replace only that one conversation
        boolean isUpdated = mConversationViewModelHelper.updateConversationProperty(conversation.getId(), conversation);
        if (isUpdated) {
            refreshListView();
        }
    }

    @Override
    public void onTyping(long conversationId, UserViewModel userTyping, boolean isTyping) {
        if (!mConversationViewModelHelper.exists(conversationId)) {
            return;
        }

        boolean isUpdated = mConversationViewModelHelper.updateRealtimeProperty(conversationId, new ClientTypingActivity(isTyping, userTyping));
        if (isUpdated) {
            refreshListView();
        }
    }
}
