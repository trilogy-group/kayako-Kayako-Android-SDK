package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import com.kayako.sdk.android.k5.messenger.data.realtime.OnConversationChangeListener;
import com.kayako.sdk.android.k5.messenger.data.realtime.OnConversationClientActivityListener;
import com.kayako.sdk.android.k5.messenger.data.realtime.OnConversationMessagesChangeListener;
import com.kayako.sdk.android.k5.messenger.data.realtime.RealtimeConversationHelper;
import com.kayako.sdk.messenger.conversation.Conversation;

import java.util.concurrent.atomic.AtomicBoolean;

public class RealtimeHelper {

    public RealtimeHelper() {
    }

    private AtomicBoolean mIsTracking = new AtomicBoolean();
    private OnConversationChangeListener mOnConversationChangeListener;
    private OnConversationClientActivityListener mOnConversationClientActivityListener;
    private OnConversationMessagesChangeListener mOnConversationMessagesChangeListener;

    public void addRealtimeListeners(OnConversationChangeListener onConversationChangeListener,
                                     OnConversationClientActivityListener onConversationClientActivityListener,
                                     OnConversationMessagesChangeListener onConversationMessagesChangeListener) {

        if (mOnConversationChangeListener != null
                || mOnConversationClientActivityListener != null
                || mOnConversationMessagesChangeListener != null) {
            throw new IllegalStateException("This method should only be called once!");
        }

        mOnConversationChangeListener = onConversationChangeListener;
        mOnConversationClientActivityListener = onConversationClientActivityListener;
        mOnConversationMessagesChangeListener = onConversationMessagesChangeListener;
    }

    public void subscribeForRealtimeChanges(Conversation conversation) {
        if (mOnConversationChangeListener == null
                || mOnConversationClientActivityListener == null
                || mOnConversationMessagesChangeListener == null) {
            throw new IllegalStateException("Please call addRealtimeListeners() before this method!");
        }

        if (conversation == null) {
            throw new IllegalArgumentException("Conversation can not be null!");
        }

        if (!mIsTracking.get()) { // Prevent multiple tracking callback
            RealtimeConversationHelper.trackChange(conversation.getRealtimeChannel(), conversation.getId(), mOnConversationChangeListener);
            RealtimeConversationHelper.trackClientActivity(conversation.getRealtimeChannel(), conversation.getId(), mOnConversationClientActivityListener);
            RealtimeConversationHelper.trackMessageChange(conversation.getRealtimeChannel(), conversation.getId(), mOnConversationMessagesChangeListener);
            mIsTracking.set(true);
        }
    }

    public void unsubscribeFromRealtimeChanges() {
        RealtimeConversationHelper.untrack(mOnConversationChangeListener);
        RealtimeConversationHelper.untrack(mOnConversationClientActivityListener);
        RealtimeConversationHelper.untrack(mOnConversationMessagesChangeListener);
        mIsTracking.set(false);
    }

}
