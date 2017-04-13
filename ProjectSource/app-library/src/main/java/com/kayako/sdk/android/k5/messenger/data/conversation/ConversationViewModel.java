package com.kayako.sdk.android.k5.messenger.data.conversation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class ConversationViewModel {

    @NonNull
    private String avatarUrl;

    @NonNull
    private String name;

    @NonNull
    private long timeInMilleseconds;

    @NonNull
    private String subject;

    @NonNull
    private long conversationId;

    @NonNull
    private int unreadCount;

    @NonNull
    private ClientTypingActivity lastAgentReplierTyping;

    public ConversationViewModel(long conversationId, String avatarUrl, String name, long timeInMilleseconds, String subject, int unreadCount, ClientTypingActivity lastAgentReplierTyping) {
        this.conversationId = conversationId;
        this.avatarUrl = avatarUrl;
        this.name = name;
        this.timeInMilleseconds = timeInMilleseconds;
        this.subject = subject;

        if (conversationId == 0
                || timeInMilleseconds == 0
                || avatarUrl == null
                || name == null
                || subject == null) {
            throw new IllegalArgumentException("Invalid values");
        }

        this.unreadCount = unreadCount;

        this.lastAgentReplierTyping = lastAgentReplierTyping;

        if (lastAgentReplierTyping == null) {
            throw new IllegalArgumentException("lastAgentReplierTyping should not be null! If not applicable, create an object with isTyping = false!");
        }
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getName() {
        return name;
    }

    public Long getTimeInMilleseconds() {
        return timeInMilleseconds;
    }

    public String getSubject() {
        return subject;
    }

    public long getConversationId() {
        return conversationId;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    @Nullable
    public ClientTypingActivity getLastAgentReplierTyping() {
        return lastAgentReplierTyping;
    }
}
