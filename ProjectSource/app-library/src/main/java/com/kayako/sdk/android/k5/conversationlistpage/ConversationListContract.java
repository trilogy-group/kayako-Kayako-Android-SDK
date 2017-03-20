package com.kayako.sdk.android.k5.conversationlistpage;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.fragments.OnListPageStateChangeListener;
import com.kayako.sdk.android.k5.common.mvp.BaseData;
import com.kayako.sdk.android.k5.common.mvp.BasePresenter;
import com.kayako.sdk.android.k5.common.mvp.BaseView;
import com.kayako.sdk.android.k5.toolbarview.MessengerToolbarContract;
import com.kayako.sdk.messenger.conversation.Conversation;

import java.util.List;

public class ConversationListContract {

    interface Data extends BaseData {
        void getConversationList(OnLoadConversationsListener listener, int offset, int limit);
    }

    interface View extends BaseView {

        void setupList(List<BaseListItem> conversations);

        void appendToEndOfListAndStopLoadMoreProgress(List<BaseListItem> conversations);

        void showEmptyView();

        void showErrorView();

        void showLoadingView();

        void openMessageListPage(Long id, int requestCode);

        void showMessage(String message);

        void setListHasMoreItems(boolean hasMore);

        void configureLoadMoreView(boolean showLoadMoreProgress);

        boolean getIfListHasMoreItems();
    }

    interface Presenter extends BasePresenter<View> {

        void setData(Data mData);

        void initPage();

        void closePage();

        void onLoadMoreItems();

        void onClickConversation(Conversation conversation);

        void onClickRetryOnError();

        void onActivityResult(int requestCode, int resultCode);

        void reloadConversations();
    }

    interface ConfigureView {

        void reloadConversations();

        void setOnScrollListener(OnScrollListener onScrollListener);

        void setOnPageStateChangeListener(OnListPageStateChangeListener onListPageStateChangeListener);
    }

    interface OnLoadConversationsListener {
        void onSuccess(List<Conversation> conversations);

        void onFailure();
    }

    interface OnScrollListener {
        void onScroll(boolean isScrolling);
    }
}
