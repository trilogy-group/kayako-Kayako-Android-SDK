package com.kayako.sdk.android.k5.messagelistpage;

import android.os.Handler;

import com.kayako.sdk.messenger.message.Message;

public class MessageListContainerPresenter implements MessageListContainerContract.Presenter {

    private MessageListContainerContract.View mView;
    private MessageListContainerContract.Data mData;

    private Long mConversationId;

    public MessageListContainerPresenter(MessageListContainerContract.View view, MessageListContainerContract.Data data) {
        mView = view;
        mData = data;
    }

    @Override
    public void setData(MessageListContainerContract.Data data) {
        mData = data;
    }

    @Override
    public void setView(MessageListContainerContract.View view) {
        mView = view;
    }

    @Override
    public void initPage(Long conversationId) {
        mConversationId = conversationId;
    }

    @Override
    public void onClickSendInReplyView(String message) {
        final Handler handler = new Handler();
        // TODO: mConversationId can be null
        mData.postNewMessage(mConversationId, message, new MessageListContainerContract.PostNewMessageCallback() {
            @Override
            public void onSuccess(Message message) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.refreshMessageListing();
                    }
                });
            }

            @Override
            public void onFailure(final String errorMessage) {
                // TODO: show error message
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showToastMessage(errorMessage);
                    }
                });
            }
        });
    }
}
