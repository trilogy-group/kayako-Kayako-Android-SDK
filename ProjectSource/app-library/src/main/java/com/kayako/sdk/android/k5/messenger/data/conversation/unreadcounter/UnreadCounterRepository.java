package com.kayako.sdk.android.k5.messenger.data.conversation.unreadcounter;

import android.support.annotation.NonNull;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.UniqueSortedUpdatableResourceList;
import com.kayako.sdk.android.k5.messenger.data.conversation.ConversationStore;
import com.kayako.sdk.messenger.conversation.Conversation;
import com.kayako.sdk.messenger.conversation.fields.readmarker.ReadMarker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Track all the unread posts in all conversations.
 * <p>
 * This class needs to be used in every class which loads conversations
 */
public class UnreadCounterRepository {

    private static final Object sConversationKey = new Object();

    private static int sUnreadCounter;

    private static final Object sListenerKey = new Object();
    private static List<OnUnreadCounterChangeListener> sListeners = new ArrayList<>();

    private static AtomicLong sCurrentConversationBeingViewedId = new AtomicLong(0);

    /**
     * @param conversationId 0 to unassign
     */
    public static void setCurrentConversationBeingViewed(long conversationId) {
        sCurrentConversationBeingViewedId.set(conversationId);
        refreshUnreadCounter();
    }

    public static void clear() {
        synchronized (sConversationKey) {
            sUnreadCounter = 0;
            sCurrentConversationBeingViewedId.set(0);
        }

        synchronized (sListenerKey) {
            sListeners.clear();
        }
    }

    public static int getsUnreadCounter() {
        synchronized (sConversationKey) {
            return sUnreadCounter;
        }
    }

    public static void addListener(OnUnreadCounterChangeListener listener) {
        synchronized (sListenerKey) {
            sListeners.add(listener);
        }
    }

    public static void removeListener(OnUnreadCounterChangeListener listener) {
        synchronized (sListenerKey) {
            sListeners.remove(listener);
        }
    }

    public static void callListeners(final int unreadCount) {
        synchronized (sListenerKey) {
            for (OnUnreadCounterChangeListener listener : sListeners) {
                listener.onUnreadCounterChanged(unreadCount);
            }
        }
    }

    public static void refreshUnreadCounter() {
        synchronized (sConversationKey) {
            int previousCount = sUnreadCounter;
            sUnreadCounter = 0;

            List<Conversation> conversations = ConversationStore.getInstance().getCachedConversations();
            for (Conversation conversation : conversations) {
                if (conversation.getReadMarker() != null
                        && sCurrentConversationBeingViewedId.get() != conversation.getId()) { // skip adding unread count of current conversation if the user is viewing it
                    sUnreadCounter += conversation.getReadMarker().getUnreadCount();
                }
            }

            if (previousCount != sUnreadCounter) { // prevent redundant callbacks
                callListeners(sUnreadCounter);
            }
        }
    }

    public interface OnUnreadCounterChangeListener {
        void onUnreadCounterChanged(int newUnreadCount);
    }

}
