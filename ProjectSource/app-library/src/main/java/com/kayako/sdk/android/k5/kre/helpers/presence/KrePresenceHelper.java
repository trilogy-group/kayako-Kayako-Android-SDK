package com.kayako.sdk.android.k5.kre.helpers.presence;

import android.support.annotation.NonNull;

import com.kayako.sdk.android.k5.kre.base.KreSubscription;
import com.kayako.sdk.android.k5.kre.helpers.KreLogHelper;
import com.kayako.sdk.android.k5.kre.helpers.MinimalClientTypingListener;
import com.kayako.sdk.android.k5.kre.helpers.RawClientActivityListener;
import com.kayako.sdk.android.k5.kre.helpers.RawClientTypingListener;
import com.kayako.sdk.android.k5.kre.helpers.RawUserOnCasePresenceListener;
import com.kayako.sdk.android.k5.kre.helpers.RawUserSubscribedPresenceListener;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class is designed in this manner because it needs to keep track of state.
 * DO NOT TRY TO CONVERT THIS CLASS INTO A STATIC METHODS LIKE THE OTHER HELPERS
 * <p> TODO: Refine the role of this class
 * Realizations: (Code accordingly)
 * 1. OnPresenceDiffEvent and OnPresenceStateEvent can be called at any time without any predefined order
 * 2. OnPresenceDiffEvent can be called multiple times to inform changes to the same user (not necessarily only to inform new users joining or old users leaving)
 * 3. The RawUserPesenceListener must be implemented such that OnPresenceStateEvent must be called ONLY ONCE and BEFORE OnPresenceDiffEvent. Because this is no longer implied in KRE, write code to ensure this behaviour
 */
public class KrePresenceHelper {

    private static final String TAG = "KrePresenceHelper";

    private static final String EVENT_PRESENCE_STATE = "presence_state";
    private static final String EVENT_PRESENCE_DIFF = "presence_diff";

    private KreSubscription mKreSubscription;
    private RawUserOnCasePresenceListener mRawUserOnCasePresenceListener;
    private RawClientTypingListener mRawTypingPresenceListener;
    private RawClientActivityListener mRawClientActivityListener;
    private RawUserSubscribedPresenceListener mRawUserSubscribedPresenceListener;
    private MinimalClientTypingListener mMinimalClientTypingListener;

    protected Set<PresenceUser> mOnlineUsers = new HashSet<>();
    protected boolean mShowLogs = true; // used in test cases
    private long mCurrentUserId;
    private boolean mHideCurrentUser; // if true, ensures the current user is removed from all user presence events
    private boolean mIsAlreadyTracking; // ensures multiple subscriptions are not made
    private AtomicBoolean mHasAlreadyBeenCalled_OnUsersAlreadyViewingCase = new AtomicBoolean(false); // ensures that the presence_state event is consumed ONLY once and onUsersAlreadyViewingCase() is called only once
    private final Object eventKey = new Object(); // ensure onDiff, onState, onError, mRawUserOnCasePresenceListener is called one at a time - Need: Ensure UI updates happen one at a time with correct info

    private KrePresencePushDataTypingHelper mKrePresencePushTypingHelper = new KrePresencePushDataTypingHelper(); // to record state and auto-disable Typing after 5 seconds of inactivity

    public KrePresenceHelper(@NonNull final KreSubscription kreSubscription, final boolean hideCurrentUser, final long currentUserId) {
        mKreSubscription = kreSubscription;
        mHideCurrentUser = hideCurrentUser;
        mCurrentUserId = currentUserId;
    }

    public void setRawUserSubscribedPresenceListener(RawUserSubscribedPresenceListener listener) {
        synchronized (eventKey) {
            mRawUserSubscribedPresenceListener = listener;
            subscribeForChangesIfNotAlreadyDone();
        }
    }

    public void removeRawUserSubscribedPresenceListener() {
        synchronized (eventKey) {
            mRawUserSubscribedPresenceListener = null;
        }
    }

    public void setRawUserOnCasePresenceListener(@NonNull final RawUserOnCasePresenceListener listener) {
        synchronized (eventKey) {
            mRawUserOnCasePresenceListener = listener;
            subscribeForChangesIfNotAlreadyDone();
        }
    }

    public void removeRawUserOnCasePresenceListener() {
        synchronized (eventKey) {
            mRawUserOnCasePresenceListener = null;
        }
    }

    public void setRawClientActivityListener(@NonNull final RawClientActivityListener listener) {
        synchronized (eventKey) {
            mRawClientActivityListener = listener;
            subscribeForChangesIfNotAlreadyDone();
        }
    }

    public void removeRawClientActivityListener() {
        synchronized (eventKey) {
            mRawClientActivityListener = null;
        }
    }

    public void setRawClientTypingListener(@NonNull final RawClientTypingListener listener) {
        synchronized (eventKey) {
            mRawTypingPresenceListener = listener;
            subscribeForChangesIfNotAlreadyDone();
        }
    }

    public void removeRawClientTypingListener() {
        synchronized (eventKey) {
            mRawTypingPresenceListener = null;
        }
    }

    public void setMinimalClientTypingListener(@NonNull final MinimalClientTypingListener listener) {
        synchronized (eventKey) {
            mMinimalClientTypingListener = listener;
            subscribeForChangesIfNotAlreadyDone();
        }
    }

    public void removeMinimalClientTypingListener() {
        synchronized (eventKey) {
            mMinimalClientTypingListener = null;
        }
    }

    public void triggerClientUpdatingCaseEvent(boolean isUpdating) {
        KrePresencePushDataHelper.triggerUpdatingEvent(mKreSubscription, isUpdating);
    }

    public void triggerClientTypingCaseEvent(boolean isTyping, boolean autoDisableTyping) {
        mKrePresencePushTypingHelper.triggerTypingEvent(mKreSubscription, isTyping, autoDisableTyping);
    }

    public void triggerClientForegroundEvent(boolean isViewing, boolean isForeground) {
        KrePresencePushDataHelper.triggerForegroundViewingEvent(mKreSubscription, isViewing, isForeground);
    }

    private void subscribeForChangesIfNotAlreadyDone() {
        synchronized (eventKey) {
            if (!mIsAlreadyTracking) {
                startTracking();
                mIsAlreadyTracking = true;
            }
        }
    }

    // Made protected for test cases
    protected void startTracking() {
        mKreSubscription.listenFor(EVENT_PRESENCE_DIFF, new KreSubscription.OnEventListener() {
            @Override
            public void onEvent(String event, String jsonBody) {
                callOnPresenceDiffEvent(jsonBody);
            }

            @Override
            public void onError(String message) {
                callOnError(message);
            }
        });
        mKreSubscription.listenFor(EVENT_PRESENCE_STATE, new KreSubscription.OnEventListener() {
            @Override
            public void onEvent(String event, String jsonBody) {
                callOnPresenceStateEvent(jsonBody);
            }

            @Override
            public void onError(String message) {
                callOnError(message);
            }
        });
    }

    // Made protected for test cases
    protected void callOnPresenceDiffEvent(String jsonBody) {
        if (mShowLogs) {
            KreLogHelper.d(EVENT_PRESENCE_DIFF, jsonBody);
        }
        synchronized (eventKey) {
            Set<PresenceUser> joinedUsers = KrePresenceJsonHelper.parsePresenceDiffJsonAndGetJoins(jsonBody);
            Set<PresenceUser> leftUsers = KrePresenceJsonHelper.parsePresenceDiffJsonAndGetLeaves(jsonBody);

            // Remove current user if set
            if (mHideCurrentUser) {
                joinedUsers.remove(new PresenceUser(mCurrentUserId)); // Ensure logged-in user is not part of it
            }

            // If there are no changes
            if (joinedUsers.size() == 0 && leftUsers.size() == 0) {
                return;
            }

            // Ensure that onUsersAlreadyViewingCase is always called before OnPresenceDiffEvent
            if (!mHasAlreadyBeenCalled_OnUsersAlreadyViewingCase.get()) {

                // set online users
                KrePresenceMetaDataHelper.setOnlineUsers(
                        mOnlineUsers,
                        joinedUsers,
                        mRawUserSubscribedPresenceListener,
                        mRawUserOnCasePresenceListener);

                // Set flag
                mHasAlreadyBeenCalled_OnUsersAlreadyViewingCase.set(true);
            }

            // Update users info and trigger events
            KrePresenceMetaDataHelper.updateOnlineUsersAndTriggerCallbacks(
                    mOnlineUsers,
                    joinedUsers,
                    leftUsers,
                    mRawUserSubscribedPresenceListener,
                    mRawUserOnCasePresenceListener,
                    mRawTypingPresenceListener,
                    mMinimalClientTypingListener,
                    mRawClientActivityListener);

        }
    }

    // Made protected for test cases
    protected synchronized void callOnPresenceStateEvent(String jsonBody) {
        if (mShowLogs) {
            KreLogHelper.d(EVENT_PRESENCE_STATE, jsonBody);
        }

        synchronized (eventKey) {
            if (mHasAlreadyBeenCalled_OnUsersAlreadyViewingCase.get()) {// Ensure the presence state is only called ONCE.
                return;
            }

            try {
                // Retrieve users from presence state
                Set<PresenceUser> newUsers = KrePresenceJsonHelper.parsePresenceStateJson(jsonBody);

                // Remove logged in user if hideCurrentUser set
                if (mHideCurrentUser) {
                    newUsers.remove(new PresenceUser(mCurrentUserId)); // Ensure logged-in user is not part of it
                }

                // If there are no other new users, exit
                if (newUsers.size() == 0) {
                    return;
                }

                // Set online users
                KrePresenceMetaDataHelper.setOnlineUsers(
                        mOnlineUsers,
                        newUsers,
                        mRawUserSubscribedPresenceListener,
                        mRawUserOnCasePresenceListener);

            } finally {
                // Set flag
                mHasAlreadyBeenCalled_OnUsersAlreadyViewingCase.set(true);
            }
        }
    }

    private void callOnError(String message) {
        KreLogHelper.e(TAG, message);
        synchronized (eventKey) {
            if (mRawUserOnCasePresenceListener != null) {
                mRawUserOnCasePresenceListener.onConnectionError();
            }
        }
    }
}
