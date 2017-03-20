package com.kayako.sdk.android.k5.messagelistpage;

import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.fragments.ListPageState;
import com.kayako.sdk.android.k5.common.fragments.OnScrollListListener;
import com.kayako.sdk.android.k5.common.mvp.BaseData;
import com.kayako.sdk.android.k5.common.mvp.BasePresenter;
import com.kayako.sdk.android.k5.common.mvp.BaseView;
import com.kayako.sdk.messenger.conversation.Conversation;
import com.kayako.sdk.messenger.conversation.PostConversationBodyParams;
import com.kayako.sdk.messenger.message.Message;

import java.util.List;

public class MessageListContainerContract {

    public interface Data extends BaseData {

        void postNewMessage(long conversationId, String contents, final PostNewMessageCallback callback);

        void getMessages(final OnLoadMessagesListener listener, long conversationId, int offset, int limit);

        void startNewConversation(PostConversationBodyParams bodyParams, MessageListContainerContract.OnLoadConversationListener onLoadConversationListener);

        void getConversation(long conversationId, final OnLoadConversationListener onLoadConversationListener);

        void registerCaseChangeListener(long currentUserId, String conversationPresenceChannel, OnConversationChangeListener listener);

        void unregisterCaseChangeListener();
    }

    public interface View extends BaseView {

        void showToastMessage(String errorMessage);

        void setupListInMessageListingView(List<BaseListItem> baseListItems);

        void showEmptyViewInMessageListingView();

        void showErrorViewInMessageListingView();

        void showLoadingViewInMessageListingView();

        void hideReplyBox();

        void showReplyBox();

        void focusOnReplyBox();

        void collapseToolbar();

        void expandToolbar();
    }

    public interface Presenter extends BasePresenter<MessageListContainerContract.View> {

        void initPage(boolean isNewConversation, @Nullable Long conversationId);

        void onClickSendInReplyView(String message);

        void setData(Data data);

        void onClickRetryInErrorView();

        void onPageStateChange(ListPageState state);

        void onScrollList(boolean isScrolling, OnScrollListListener.ScrollDirection direction);
    }


    public interface PostNewMessageCallback {
        void onSuccess(Message message);

        void onFailure(String errorMessage);
    }

    public interface OnLoadMessagesListener {
        void onSuccess(List<Message> messageList);

        void onFailure(String message);
    }

    public interface OnLoadConversationListener {
        void onSuccess(Conversation conversation);

        void onFailure(String message);
    }

    public interface OnConversationChangeListener {
        void onConversationChange(Conversation conversation);

        void onNewMessage(long messageId);

        void onUpdateMessage(long messageId);
    }

}
