package com.kayako.sdk.android.k5.common.adapter.conversationlist;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.ConversationViewModel;

public class ConversationListItem extends BaseListItem {

    public ConversationViewModel conversationViewModel;

    public ConversationListItem(ConversationViewModel conversationViewModel) {
        super(ConversationListType.CONVERSATION_LIST_ITEM);
        this.conversationViewModel = conversationViewModel;
    }
}
