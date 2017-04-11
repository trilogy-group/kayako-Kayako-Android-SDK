package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.DataItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.DataItemHelper;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.UserDecorationHelper;
import com.kayako.sdk.android.k5.common.fragments.ListPageState;
import com.kayako.sdk.messenger.message.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Logic to show onboarding messages, actual api messages, optimistic sending messages and KRE indicator messages
 */
public class ListHelper {

    private ListPageState mListPageState;

    public ListPageState getListPageState() {
        return mListPageState;
    }

    public void setListPageState(ListPageState mListPageState) {
        this.mListPageState = mListPageState;
    }

    public List<BaseListItem> getMessageAsListItemViews(List<Message> messages, long lastOriginalMessageMarkedRead, long userId) {
        List<DataItem> dataItems = convertMessagesToDataItems(messages, lastOriginalMessageMarkedRead, userId);
        return DataItemHelper.getInstance().convertDataItemToListItems(dataItems);
    }

    private List<DataItem> convertMessagesToDataItems(List<Message> messageList, long lastOriginalMessageMarkedRead, long userId) {
        List<DataItem> dataItems = new ArrayList<>();

        for (Message message : messageList) {

            boolean isRead = false;
                /* Note: Using MarkReadHelper and not Conversation variable directly because the unread status should be preserved
                   That is, even though a conversation is marked read, the unread marker should not disappear until the user reopens the page!
                 */
            if (lastOriginalMessageMarkedRead != 0
                    && lastOriginalMessageMarkedRead >= message.getId()) {
                isRead = true;
            } else {
                isRead = false;
            }

            dataItems.add(
                    new DataItem(
                            message.getId(),
                            null,
                            UserDecorationHelper.getUserDecoration(message, userId),
                            null, // no channelDecoration
                            com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.DeliveryIndicatorHelper.getDeliveryIndicator(message), // TODO:
                            message.getContentText(),
                            message.getCreatedAt(),
                            Collections.EMPTY_LIST, // TODO: Attachments
                            isRead
                    )
            );
        }

        return dataItems;
    }
}