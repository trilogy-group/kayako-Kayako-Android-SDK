package com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class ConversationViewModel {

    @NonNull
    private long conversationId;

    @NonNull
    private String name;

    @NonNull
    private String avatarUrl;

    @NonNull
    private long timeInMilleseconds;

    @NonNull
    private String subject;

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

    public ClientTypingActivity getLastAgentReplierTyping() {
        return lastAgentReplierTyping;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConversationViewModel that = (ConversationViewModel) o;

        if (conversationId != that.conversationId) return false;
        if (timeInMilleseconds != that.timeInMilleseconds) return false;
        if (unreadCount != that.unreadCount) return false;
        if (!name.equals(that.name)) return false;
        if (!avatarUrl.equals(that.avatarUrl)) return false;
        if (!subject.equals(that.subject)) return false;
        return lastAgentReplierTyping.equals(that.lastAgentReplierTyping);

    }

    @Override
    public int hashCode() {
        int result = (int) (conversationId ^ (conversationId >>> 32));
        result = 31 * result + name.hashCode();
        result = 31 * result + avatarUrl.hashCode();
        result = 31 * result + (int) (timeInMilleseconds ^ (timeInMilleseconds >>> 32));
        result = 31 * result + subject.hashCode();
        result = 31 * result + unreadCount;
        result = 31 * result + lastAgentReplierTyping.hashCode();
        return result;
    }
}
