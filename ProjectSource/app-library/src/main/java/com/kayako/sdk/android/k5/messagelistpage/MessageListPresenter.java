package com.kayako.sdk.android.k5.messagelistpage;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.ChannelDecoration;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.DataItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.DataItemHelper;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.UserDecoration;
import com.kayako.sdk.android.k5.messagelistpage.MessageListContract.Presenter;
import com.kayako.sdk.messenger.message.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessageListPresenter implements Presenter {


    private MessageListContract.View mView;
    private MessageListContract.Data mData;

    public MessageListPresenter(MessageListContract.View view, MessageListContract.Data data) {
        mView = view;
        mData = data;
    }

    @Override
    public void setData(MessageListContract.Data data) {
        mData = data;
    }

    @Override
    public void setView(MessageListContract.View view) {
        mView = view;
    }

    @Override
    public void initPage(long conversationId) {
        mView.showLoadingView();
        mData.getMessages(new MessageListContract.OnLoadMessagesListener() {
            @Override
            public void onSuccess(List<Message> messageList) {
                List<DataItem> dataItems = convertMessagesToDataItems(messageList);

                if (dataItems.size() == 0) {
                    mView.showEmptyView();
                } else {
                    Collections.reverse(dataItems);
                    List<BaseListItem> baseListItems = DataItemHelper.getInstance().convertDataItemToListItems(dataItems);
                    mView.setupList(baseListItems);
                }
            }

            @Override
            public void onFailure(String message) {
                // TODO: Add conditions to show toast if load-more and show error view if offset=0
                mView.showErrorView();
            }
        }, conversationId, 0, 10);

    }

    private List<DataItem> convertMessagesToDataItems(List<Message> messageList) {
        List<DataItem> dataItems = new ArrayList<>();
        for (Message message : messageList) {
            dataItems.add(
                    new DataItem(
                            message.getId(),
                            null,
                            new UserDecoration(
                                    message.getCreator().getFullName(),
                                    message.getCreator().getAvatarUrl(),
                                    message.getCreator().getId(),
                                    false), // TODO: On creating a conversation for the first time - save the userId of the creator of the conversation and check if it matches
                            new ChannelDecoration(
                                    R.drawable.ko__img_helpcenter),
                            message.getContentText(),
                            message.getCreatedAt(),
                            Collections.EMPTY_LIST, // TODO: Attachments
                            false
                    )
            );
        }

        return dataItems;
    }

    @Override
    public void closePage() {

    }

    @Override
    public void onLoadMoreItems() {

    }

    @Override
    public void onClickRetryOnError() {

    }
}
