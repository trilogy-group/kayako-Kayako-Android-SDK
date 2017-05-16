package com.kayako.sdk.android.k5.common.adapter.conversationlist;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.ConversationViewModel;

import java.util.HashMap;
import java.util.Map;

public class ConversationListItem extends BaseListItem {

    public ConversationViewModel conversationViewModel;

    public ConversationListItem(ConversationViewModel conversationViewModel) {
        super(ConversationListType.CONVERSATION_LIST_ITEM);
        this.conversationViewModel = conversationViewModel;
    }

    @Override
    public Map<String, String> getContents() {
        Map<String, String> map = new HashMap<>();
        if (conversationViewModel != null) {
            map.putAll(conversationViewModel.getContents());
        }
        return map;
    }
}
