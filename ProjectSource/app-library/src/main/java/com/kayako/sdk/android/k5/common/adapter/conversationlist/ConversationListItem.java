package com.kayako.sdk.android.k5.common.adapter.conversationlist;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.messenger.conversation.Conversation;

public class ConversationListItem extends BaseListItem {

    public String avatarUrl;
    public String name;
    public Long timeInMilleseconds;
    public String subject;
    public int unreadCount;

    public Conversation conversation;

    public ConversationListItem(String avatarUrl, String name, Long timeInMilleseconds, String subject, int unreadCount, Conversation conversation) {
        super(ConversationListType.CONVERSATION_LIST_ITEM);
        this.avatarUrl = avatarUrl;
        this.name = name;
        this.timeInMilleseconds = timeInMilleseconds;
        this.subject = subject;
        this.unreadCount = unreadCount;
        this.conversation = conversation;
    }
}
