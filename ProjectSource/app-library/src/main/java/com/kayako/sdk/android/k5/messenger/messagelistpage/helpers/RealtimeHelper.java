package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import com.kayako.sdk.android.k5.core.MessengerUserPref;
import com.kayako.sdk.android.k5.messenger.data.realtime.OnConversationChangeListener;
import com.kayako.sdk.android.k5.messenger.data.realtime.OnConversationClientActivityListener;
import com.kayako.sdk.android.k5.messenger.data.realtime.OnConversationMessagesChangeListener;
import com.kayako.sdk.android.k5.messenger.data.realtime.OnConversationUserOnlineListener;
import com.kayako.sdk.android.k5.messenger.data.realtime.RealtimeConversationHelper;
import com.kayako.sdk.android.k5.messenger.data.realtime.RealtimeCurrentUserTrackerHelper;
import com.kayako.sdk.messenger.conversation.Conversation;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class RealtimeHelper {

    public RealtimeHelper() {
    }

    private AtomicBoolean mIsTrackingConversation = new AtomicBoolean();
    private OnConversationChangeListener mOnConversationChangeListener;
    private OnConversationClientActivityListener mOnConversationClientActivityListener;
    private OnConversationMessagesChangeListener mOnConversationMessagesChangeListener;
    private OnAgentPresenceChangeListener mOnAgentPresenceChangeListener;

    private OnConversationUserOnlineListener mInternalOnConversationUserOnlineListener = new OnConversationUserOnlineListener() {
        @Override
        public void onUserOnline(long conversationId, long userId) {
            if (mConversationId != null && mConversationId == conversationId) { // ensure only current converation gets presence updates
                triggerPresenceCallbacks();
            }
        }

        @Override
        public void onUserOffline(long conversationId, long userId) {
            if (mConversationId != null && mConversationId == conversationId) { // ensure only current converation gets presence updates
                triggerPresenceCallbacks();
            }
        }
    };

    private String mLastMessageTyped;
    private Long mAgentUserId;
    private Long mConversationId;

    public void addRealtimeListeners(OnConversationChangeListener onConversationChangeListener,
                                     OnConversationClientActivityListener onConversationClientActivityListener,
                                     OnConversationMessagesChangeListener onConversationMessagesChangeListener,
                                     OnAgentPresenceChangeListener onAgentPresenceChangeListener) {

        if (mOnConversationChangeListener != null
                || mOnConversationClientActivityListener != null
                || mOnConversationMessagesChangeListener != null) {
            throw new IllegalStateException("This method should only be called once!");
        }

        mOnConversationChangeListener = onConversationChangeListener;
        mOnConversationClientActivityListener = onConversationClientActivityListener;
        mOnConversationMessagesChangeListener = onConversationMessagesChangeListener;
        mOnAgentPresenceChangeListener = onAgentPresenceChangeListener;
    }

    public void triggerTyping(Conversation conversation, String messageBeingTyped) {
        // Assertions
        if (MessengerUserPref.getInstance().getUserId() == null) {
            throw new IllegalStateException("Client Activity events can not be triggered before user is created");
        }

        // Don't send typing event if nothing changed
        if (mLastMessageTyped == null && messageBeingTyped == null) { // both same
            return;
        } else if (mLastMessageTyped != null && mLastMessageTyped.equals(messageBeingTyped)) { // both same
            return;
        } else {
            mLastMessageTyped = messageBeingTyped; // new

            if (messageBeingTyped == null || messageBeingTyped.length() == 0) {
                RealtimeConversationHelper.triggerTyping(conversation.getRealtimeChannel(), conversation.getId(), false);
            } else {
                RealtimeConversationHelper.triggerTyping(conversation.getRealtimeChannel(), conversation.getId(), true);
            }
        }
    }

    public void subscribeForRealtimeConversationChanges(Conversation conversation) {
        if (mOnConversationChangeListener == null
                || mOnConversationClientActivityListener == null
                || mOnConversationMessagesChangeListener == null) {
            throw new IllegalStateException("Please call addRealtimeListeners() before this method!");
        }

        if (conversation == null) {
            throw new IllegalArgumentException("Conversation can not be null!");
        }

        if (!mIsTrackingConversation.get()) { // Prevent multiple tracking callback
            RealtimeConversationHelper.trackChange(conversation.getRealtimeChannel(), conversation.getId(), mOnConversationChangeListener);
            RealtimeConversationHelper.trackClientActivity(conversation.getRealtimeChannel(), conversation.getId(), mOnConversationClientActivityListener);
            RealtimeConversationHelper.trackMessageChange(conversation.getRealtimeChannel(), conversation.getId(), mOnConversationMessagesChangeListener);
            RealtimeConversationHelper.trackPresenceUser(conversation.getRealtimeChannel(), conversation.getId(), mInternalOnConversationUserOnlineListener);
            mIsTrackingConversation.set(true);
        }
    }

    public void unsubscribeFromRealtimeConversationChanges() {
        RealtimeConversationHelper.untrack(mOnConversationChangeListener);
        RealtimeConversationHelper.untrack(mOnConversationClientActivityListener);
        RealtimeConversationHelper.untrack(mOnConversationMessagesChangeListener);
        RealtimeConversationHelper.untrack(mInternalOnConversationUserOnlineListener);
        mIsTrackingConversation.set(false);
    }

    public void subscribeForCurrentUserOnlinePresence() {
        // Subscribe to mark online presence of current user
        RealtimeCurrentUserTrackerHelper.trackCurrentUserIfNotTrackedAlready();
    }

    public void updateAssignedAgent(Conversation conversation) {
        if (conversation.getLastAgentReplier() != null) {
            mAgentUserId = conversation.getLastAgentReplier().getId();
            mConversationId = conversation.getId();
            triggerPresenceCallbacks();
        }
    }

    private void triggerPresenceCallbacks() {
        if (mOnAgentPresenceChangeListener != null && mAgentUserId != null && mConversationId != null) {
            Set<Long> set = RealtimeConversationHelper.getActiveUsers(mConversationId);
            mOnAgentPresenceChangeListener.onAgentPresenceChange(mAgentUserId, set.contains(mAgentUserId));
        }
    }

    public interface OnAgentPresenceChangeListener {
        void onAgentPresenceChange(long agentUserId, boolean isOnline);
    }
}
