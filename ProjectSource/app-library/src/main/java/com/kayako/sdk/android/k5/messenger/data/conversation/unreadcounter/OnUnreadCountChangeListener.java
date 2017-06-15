package com.kayako.sdk.android.k5.messenger.data.conversation.unreadcounter;

/**
 * Used in Kayako for developers to track the unread count.
 * Also used in Toolbar view to track changes to unread count.
 */
public interface OnUnreadCountChangeListener {
    void onUnreadCountChanged(int unreadCount);
}
