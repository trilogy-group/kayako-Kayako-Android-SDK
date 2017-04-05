package com.kayako.sdk.android.k5.messenger.data.conversationstarter;

public class RecentConversation {

    private String avatarUrl;
    private String name;
    private Long timeInMilleseconds;
    private String subject;
    private long conversationId;
    private int unreadCount;

    public RecentConversation(long conversationId, String avatarUrl, String name, Long timeInMilleseconds, String subject, int unreadCount) {
        this.conversationId = conversationId;
        this.avatarUrl = avatarUrl;
        this.name = name;
        this.timeInMilleseconds = timeInMilleseconds;
        this.subject = subject;
        this.unreadCount = unreadCount;
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
}
