package com.kayako.sdk.android.k5.common.adapter.conversationlist;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.messenger.conversation.Conversation;

public class ConversationListItem extends BaseListItem {

    public String avatarUrl;
    public String name;
    public Long timeInMilleseconds;
    public String subject;

    public Conversation conversation;

    public ConversationListItem(String avatarUrl, String name, Long timeInMilleseconds, String subject, Conversation conversation) {
        super(ConversationListType.CONVERSATION_LIST_ITEM);
        this.avatarUrl = avatarUrl;
        this.name = name;
        this.timeInMilleseconds = timeInMilleseconds;
        this.subject = subject;
        this.conversation = conversation;
    }
}
