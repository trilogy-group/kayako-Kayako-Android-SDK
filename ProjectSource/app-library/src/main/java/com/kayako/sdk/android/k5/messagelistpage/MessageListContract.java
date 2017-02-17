package com.kayako.sdk.android.k5.messagelistpage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.mvp.BaseData;
import com.kayako.sdk.android.k5.common.mvp.BasePresenter;
import com.kayako.sdk.android.k5.common.mvp.BaseView;
import com.kayako.sdk.messenger.message.Message;

import java.util.List;

public class MessageListContract {

    interface Data extends BaseData {
        void getMessages(OnLoadMessagesListener listener, long conversationId, int offset, int limit);
    }

    interface View extends BaseView {

        void setupList(List<BaseListItem> conversation);

        void showEmptyView();

        void showErrorView();

        void showLoadingView();
    }

    interface Presenter extends BasePresenter<MessageListContract.View> {

        void setData(@NonNull MessageListContract.Data mData);

        void initPage(@Nullable Long conversationId);

        void closePage();

        void onLoadMoreItems();

        void onClickRetryOnError();

        void refreshList();
    }

    interface ConfigureView {

        void refreshList();

    }

    public interface OnLoadMessagesListener {
        void onSuccess(List<Message> messageList);

        void onFailure(String message);
    }

}
