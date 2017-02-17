package com.kayako.sdk.android.k5.messagelistpage;

import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.common.mvp.BaseData;
import com.kayako.sdk.android.k5.common.mvp.BasePresenter;
import com.kayako.sdk.android.k5.common.mvp.BaseView;
import com.kayako.sdk.messenger.message.Message;

public class MessageListContainerContract {

    public interface Data extends BaseData {

        void postNewMessage(long conversationId, String contents, final MessageListContainerContract.PostNewMessageCallback callback);
    }

    public interface View extends BaseView {

        void refreshMessageListing();

        void showToastMessage(String errorMessage);
    }

    public interface Presenter extends BasePresenter<MessageListContainerContract.View> {

        void initPage(@Nullable Long conversationId);

        void onClickSendInReplyView(String message);

        void setData(Data data);
    }


    public interface PostNewMessageCallback {
        void onSuccess(Message message);

        void onFailure(String errorMessage);
    }

}
