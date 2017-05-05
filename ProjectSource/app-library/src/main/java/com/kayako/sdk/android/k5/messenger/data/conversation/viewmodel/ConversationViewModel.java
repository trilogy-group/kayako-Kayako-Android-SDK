package com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class ConversationViewModel {

    @NonNull
    private long conversationId;

    @Nullable  // if null, brand name will be shown
    private String name;

    @Nullable // if null, default avatar will be shown
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
        this.avatarUrl = avatarUrl;
        this.name = name;

        commonConstructor(conversationId, timeInMilleseconds, subject, unreadCount, lastAgentReplierTyping);
    }

    private void commonConstructor(long conversationId, long timeInMilleseconds, String subject, int unreadCount, ClientTypingActivity lastAgentReplierTyping) {
        this.conversationId = conversationId;
        this.timeInMilleseconds = timeInMilleseconds;
        this.subject = subject;

        if (conversationId == 0
                || timeInMilleseconds == 0
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
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (avatarUrl != null ? !avatarUrl.equals(that.avatarUrl) : that.avatarUrl != null)
            return false;
        if (!subject.equals(that.subject)) return false;
        return lastAgentReplierTyping.equals(that.lastAgentReplierTyping);

    }

    @Override
    public int hashCode() {
        int result = (int) (conversationId ^ (conversationId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (avatarUrl != null ? avatarUrl.hashCode() : 0);
        result = 31 * result + (int) (timeInMilleseconds ^ (timeInMilleseconds >>> 32));
        result = 31 * result + subject.hashCode();
        result = 31 * result + unreadCount;
        result = 31 * result + lastAgentReplierTyping.hashCode();
        return result;
    }
}
