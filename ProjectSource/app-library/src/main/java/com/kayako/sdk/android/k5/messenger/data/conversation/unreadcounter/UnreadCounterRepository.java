package com.kayako.sdk.android.k5.messenger.data.conversation.unreadcounter;

import android.support.annotation.NonNull;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.UniqueSortedUpdatableResourceList;
import com.kayako.sdk.messenger.conversation.Conversation;
import com.kayako.sdk.messenger.conversation.fields.readmarker.ReadMarker;

import java.util.ArrayList;
import java.util.List;

/**
 * Track all the unread posts in all conversations.
 * <p>
 * This class needs to be used in every class which loads conversations
 */
public class UnreadCounterRepository {

    private static final Object sConversationKey = new Object();
    private static UniqueSortedUpdatableResourceList<ReadMarker> sConversationReadMarkerList = new UniqueSortedUpdatableResourceList<>();

    private static int sUnreadCounter;

    private static final Object sListenerKey = new Object();
    private static List<OnUnreadCounterChangeListener> sListeners = new ArrayList<>();

    public static void addOrUpdateConversation(@NonNull Conversation conversation) {
        synchronized (sConversationKey) {
            if (conversation == null) {
                throw new IllegalArgumentException("Invalid. Can't be null!");
            }

            if (conversation.getReadMarker() == null) { // Skip
                return;
            }

            sConversationReadMarkerList.addElement(conversation.getId(), conversation.getReadMarker());
            refreshUnreadCounter();
        }
    }

    public static void addOrUpdateConversations(@NonNull List<Conversation> conversations) {
        synchronized (sConversationKey) {
            if (conversations == null) {
                throw new IllegalArgumentException("Invalid. Can't be null!");
            }

            for (Conversation conversation : conversations) {
                if (conversation.getReadMarker() == null) { // Skip
                    continue;
                }

                sConversationReadMarkerList.addElement(conversation.getId(), conversation.getReadMarker());
            }

            refreshUnreadCounter();
        }
    }

    public static void clear() {
        synchronized (sConversationKey) {
            sConversationReadMarkerList = new UniqueSortedUpdatableResourceList<>();
            sUnreadCounter = 0;
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

    private static void refreshUnreadCounter() {
        synchronized (sConversationKey) {
            int previousCount = sUnreadCounter;
            sUnreadCounter = 0;
            for (ReadMarker readMarker : sConversationReadMarkerList.getList()) {
                if (readMarker != null) {
                    sUnreadCounter += readMarker.getUnreadCount();
                }
            }

            if (previousCount != sUnreadCounter) {
                callListeners(sUnreadCounter);
            }
        }
    }

    public interface OnUnreadCounterChangeListener {
        void onUnreadCounterChanged(int newUnreadCount);
    }

}
