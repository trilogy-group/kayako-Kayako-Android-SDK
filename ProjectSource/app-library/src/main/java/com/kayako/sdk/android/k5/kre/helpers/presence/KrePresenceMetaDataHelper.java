package com.kayako.sdk.android.k5.kre.helpers.presence;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.kre.helpers.MinimalClientTypingListener;
import com.kayako.sdk.android.k5.kre.helpers.RawClientActivityListener;
import com.kayako.sdk.android.k5.kre.helpers.RawClientTypingListener;
import com.kayako.sdk.android.k5.kre.helpers.RawUserOnCasePresenceListener;
import com.kayako.sdk.android.k5.kre.helpers.RawUserSubscribedPresenceListener;

import java.util.Collection;
import java.util.Set;

/**
 * Most of the logic has been transfered to the meta data of the json payload of presence_diff and presence_state events.
 * <p>
 * Therefore, this helper class is responsible for determining:
 * 1. If a user is actively viewing a case or not (is_foreground)
 * 2. If a user has opened the case or not (is_viewing)
 * 3. If a user is updating the case or not (is_updating)
 * 4. If a user is typing (is_typing)
 * <p>
 * It should track changes  of the above fields and trigger callbacks accordingly.
 */
class KrePresenceMetaDataHelper {

    private KrePresenceMetaDataHelper() {
    }

    public static void setOnlineUsers(@NonNull Set<PresenceUser> onlineUsers,
                                      @NonNull Set<PresenceUser> newUsers,
                                      @Nullable RawUserSubscribedPresenceListener mRawUserSubscribedPresenceListener,
                                      @Nullable RawUserOnCasePresenceListener rawUserPresenceListener) {
        // Assertions
        if (onlineUsers == null || newUsers == null) {
            throw new IllegalArgumentException("Null arguments for onlineUsers and newUsers are not allowed!");
        }

        if (onlineUsers.size() != 0) {
            throw new IllegalArgumentException("Online Users should not have been previously set! Ensure calling class calls this method only once");
        }

        // Handle on state event change
        onlineUsers.clear();
        onlineUsers.addAll(newUsers);
        triggerOnlineStatusesForUserOnCase(newUsers, rawUserPresenceListener);
        triggerSubscribedStatuses(newUsers, mRawUserSubscribedPresenceListener);
    }

    public static void updateOnlineUsersAndTriggerCallbacks(@NonNull Set<PresenceUser> onlineUsers,
                                                            @NonNull Set<PresenceUser> newUsers,
                                                            @NonNull Set<PresenceUser> oldUsers,
                                                            @Nullable RawUserSubscribedPresenceListener rawUserSubscribedPresenceListener,
                                                            @Nullable RawUserOnCasePresenceListener rawUserPresenceListener,
                                                            @Nullable RawClientTypingListener rawClientTypingListener,
                                                            @Nullable MinimalClientTypingListener minimalClientTypingListener,
                                                            @Nullable RawClientActivityListener rawClientActivityListener) {
        // Assertions
        if (onlineUsers == null || newUsers == null || oldUsers == null) {
            throw new IllegalArgumentException("Null arguments for onlineUsers and newUsers and oldUsers are not allowed!");
        }

        // NOTE: Order is very important - Check leaves first, then joins!
        // Identify which users have unsubscribed (leaving) completely (and not joining at same time)
        for (PresenceUser leavingUser : oldUsers) {

            // Check for incomplete values and set them - This line is crucial to preventing NPE
            PresenceUserHelper.replaceNullMetaValues(leavingUser, PresenceUserHelper.getDefaultUser());

            // Trigger callback to leave
            if (isUserLeavingWithoutJoiningAtSameTime(onlineUsers, newUsers, leavingUser)) {
                // Remove from online user set
                onlineUsers.remove(leavingUser);

                // Trigger callback to update online status for User on case - force user to leave
                leavingUser.getActivityData().setForeground(false);
                leavingUser.getActivityData().setViewing(false);
                triggerOnlineStatusForUserOnCase(leavingUser, rawUserPresenceListener);
            }
        }

        // Handle on diff event change - Iterate through all new users and go through meta variables
        // Identify (1) new users, and (2) any change in existing users
        for (PresenceUser newUser : newUsers) {

            // Check for incomplete values and set up with - This line is crucial to preventing NPE
            setupNewUserMetaValuesIfNotComplete(onlineUsers, newUser);

            PresenceUser userToCompareChangeWith = getUserToCompareChangeWith(onlineUsers, PresenceUserHelper.convertToId(newUser));

            // Add user to the online users set (if existing, replaced = removed + added)
            if (!userToCompareChangeWith.equals(PresenceUserHelper.getDefaultUser())) {
                // Removes existing user with older values
                onlineUsers.remove(new PresenceUser(newUser.getUserData().getId()));
                // Add same user with new values after = REPLACE
                onlineUsers.add(newUser);
            } else {
                // Add new user to online users
                onlineUsers.add(newUser);
            }

            // Check for any changes to state and trigger event if changed
            if (hasOnlineStatusChanged(userToCompareChangeWith, newUser)) {
                triggerOnlineStatusForUserOnCase(newUser, rawUserPresenceListener);
            }

            if (hasLastActivityChanged(userToCompareChangeWith, newUser)) {
                triggerRecentActivity(newUser, rawUserPresenceListener);
            }

            if (hasUpdatingStatusChanged(userToCompareChangeWith, newUser)) {
                triggerUpdatingStatus(newUser, rawClientActivityListener);
            }

            if (hasTypingStatusChanged(userToCompareChangeWith, newUser)) {
                triggerTypingStatus(newUser, rawClientTypingListener);
                triggerTypingStatus(newUser, minimalClientTypingListener);
            }
        }

        // NOTE: The order is vital here - sometimes, users join and leave at same time > give precedence to JOIN (since meta values may just be getting updated)
        // Identify unsubscribed users - does not look at meta
        for (PresenceUser oldUser : oldUsers) {
            if (onlineUsers.contains(oldUser)) {
                triggerUnsubscribedStatus(oldUser, rawUserSubscribedPresenceListener);
            }
        }

        // Identify newly subscribed users - does not look at meta
        for (PresenceUser newUser : newUsers) {
            if (!onlineUsers.contains(newUser)) {
                triggerSubscribedStatus(newUser, rawUserSubscribedPresenceListener);
            }
        }
    }

    private static boolean isUserLeavingWithoutJoiningAtSameTime(Set<PresenceUser> onlineUsers, Set<PresenceUser> newUsers, PresenceUser leavingUser) {
        return onlineUsers.contains(leavingUser)  // leaving user is existing in current online user set
                && !newUsers.contains(leavingUser); // leaving user is not joining with new values at same time
    }

    private static boolean hasTypingStatusChanged(@NonNull PresenceUser existingUserData, @NonNull PresenceUser newUserData) {
        return existingUserData.getActivityData() != null
                && newUserData.getActivityData() != null
                && existingUserData.getActivityData().isTyping() != newUserData.getActivityData().isTyping();
    }

    private static boolean hasLastActivityChanged(@NonNull PresenceUser existingUserData, @NonNull PresenceUser newUserData) {
        return existingUserData.getActivityData() != null
                && newUserData.getActivityData() != null
                && !newUserData.getActivityData().getLastActiveAt().equals(existingUserData.getActivityData().getLastActiveAt());
    }

    private static boolean hasUpdatingStatusChanged(PresenceUser existingUserData, PresenceUser newUser) {
        return existingUserData.getActivityData() != null
                && newUser.getActivityData() != null
                && existingUserData.getActivityData().isUpdating() != newUser.getActivityData().isUpdating();
    }

    private static boolean hasOnlineStatusChanged(PresenceUser existingUserData, PresenceUser newUser) {
        // If it's open in the background, is_viewing will be true, but is_foreground will be false.
        // It should not be possible for is viewing to be false and is foreground to be true HOWEVER events are still received with this wrong values!
        return existingUserData.getActivityData() != null
                && newUser.getActivityData() != null
                && ( // look at isForeground and isViewing (if either one changes)
                existingUserData.getActivityData().isForeground() != newUser.getActivityData().isForeground()
                        || existingUserData.getActivityData().isViewing() != newUser.getActivityData().isViewing());
    }

    private static void triggerTypingStatus(@NonNull PresenceUser newUserData, RawClientTypingListener rawClientTypingListener) {
        if (rawClientTypingListener != null) {
            rawClientTypingListener.onUserTyping(
                    newUserData.getUserData().getId(),
                    newUserData.getActivityData().isTyping());
        }
    }

    private static void triggerTypingStatus(@NonNull PresenceUser newUserData, MinimalClientTypingListener minimalClientTypingListener) {
        if (minimalClientTypingListener != null
                && newUserData.getUserData().getFullName() != null // Ensure callback triggered only if the name and avatar is avaialble!
                && newUserData.getUserData().getAvatar() != null) {
            minimalClientTypingListener.onUserTyping(
                    newUserData.getUserData().getId(),
                    newUserData.getUserData().getFullName(),
                    newUserData.getUserData().getAvatar(),
                    newUserData.getActivityData().isTyping());
        }
    }

    private static void triggerUpdatingStatus(@NonNull PresenceUser newUser, RawClientActivityListener rawClientActivityListener) {
        if (rawClientActivityListener != null) {
            rawClientActivityListener.onClientActivity(
                    newUser.getUserData().getId(),
                    newUser.getActivityData().isUpdating());
        }
    }

    private static void triggerRecentActivity(@NonNull PresenceUser newUser, RawUserOnCasePresenceListener rawUserPresenceListener) {
        if (rawUserPresenceListener != null) {
            rawUserPresenceListener.onExistingUserPerformingSomeActivity(newUser.getUserData().getId(), newUser.getActivityData().getLastActiveAt());
        }
    }

    private static void triggerOnlineStatusForUserOnCase(PresenceUser newUser, RawUserOnCasePresenceListener rawUserOnlinePresenceListener) {
        if (rawUserOnlinePresenceListener != null) {
            if (newUser.getActivityData().isForeground() && newUser.getActivityData().isViewing()) { // look at isForeground and isViewing (both needs to be true to be online)
                rawUserOnlinePresenceListener.onNewUserViewingCase(newUser.getUserData().getId(), newUser.getActivityData().getLastActiveAt());
            } else {
                rawUserOnlinePresenceListener.onUserNoLongerViewingCase(newUser.getUserData().getId());
            }
        }
    }

    private static void triggerOnlineStatusesForUserOnCase(Set<PresenceUser> users, RawUserOnCasePresenceListener rawUserOnlinePresenceListener) {
        if (rawUserOnlinePresenceListener != null) {
            rawUserOnlinePresenceListener.onUsersAlreadyViewingCase(PresenceUserHelper.convertToListOfIds(users), System.currentTimeMillis());
        }
    }

    private static void triggerSubscribedStatuses(Set<PresenceUser> newUsers, RawUserSubscribedPresenceListener listener) {
        if (listener != null) {
            listener.onUsersAlreadySubscribed(PresenceUserHelper.convertToListOfIds(newUsers), System.currentTimeMillis());
        }
    }

    private static void triggerSubscribedStatus(PresenceUser newUser, RawUserSubscribedPresenceListener rawUserSubscribedPresenceListener) {
        if (rawUserSubscribedPresenceListener != null) {
            rawUserSubscribedPresenceListener.onNewUserSubscribing(PresenceUserHelper.convertToId(newUser), System.currentTimeMillis());
        }
    }

    private static void triggerUnsubscribedStatus(PresenceUser oldUser, RawUserSubscribedPresenceListener rawUserSubscribedPresenceListener) {
        if (rawUserSubscribedPresenceListener != null) {
            rawUserSubscribedPresenceListener.onUserNoLongerSubscribed(PresenceUserHelper.convertToId(oldUser));
        }
    }

    public static PresenceUser getUserToCompareChangeWith(Collection<PresenceUser> onlineUsers, long userId) {
        if (PresenceUserHelper.isExistingUser(onlineUsers, userId)) { // check if contained, to trigger any change event
            return PresenceUserHelper.getExistingUser(onlineUsers, userId);
        } else {
            return PresenceUserHelper.getDefaultUser(); // All values are set as false - which should trigger for any status = true
        }
    }

    /**
     * This method has been added to fix the following issue that arises when frontend is being used via the same user on multiple clients, expecially with the new KRE rules. (28/FEB/2017)
     * <p>
     * The issue?
     * Sometimes the new user does not contain all necessary meta values. For example, while isTyping is provided in one presence_diff event, the isUpdating event is not provided.
     * Which leads to the question. What value should I consider for the unprovided values?
     * <p>
     * The solution?
     * 1. If the meta value is provided, use the provided meta value as the final value.
     * 2. If the meta value is not provided, and the presence_diff is of a NEW user, set the meta value as false by default.
     * 3. If the meta value is not provided, and the presence_diff is of an EXISTING user, carry forward the meta value from the existing user info.
     *
     * @param onlineUsers
     * @param user
     */
    private static void setupNewUserMetaValuesIfNotComplete(Set<PresenceUser> onlineUsers, PresenceUser user) {
        if (PresenceUserHelper.isExistingUser(onlineUsers, user.getUserData().getId())) {
            PresenceUserHelper.replaceNullMetaValues(
                    user,
                    PresenceUserHelper.getExistingUser(onlineUsers, user.getUserData().getId()));
        } else {
            PresenceUserHelper.replaceNullMetaValuesWithDefaultValues(user);
        }
    }


}
