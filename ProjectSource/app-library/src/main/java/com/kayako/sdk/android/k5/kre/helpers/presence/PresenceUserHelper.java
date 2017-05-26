package com.kayako.sdk.android.k5.kre.helpers.presence;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class PresenceUserHelper {

    private static final PresenceUser DEFAULT_PRESENCE_USER = new PresenceUser(0L); // ensures non-null values in activityData and sets default values = false

    private PresenceUserHelper() {
    }

    public static List<Long> convertToListOfIds(Collection<PresenceUser> presenceUsers) {
        List<Long> ids = new ArrayList<>();
        for (PresenceUser presenceUser : presenceUsers) {
            ids.add(presenceUser.getUserData().getId());
        }
        return ids;
    }

    public static Long convertToId(PresenceUser presenceUser) {
        return presenceUser.getUserData().getId();
    }

    public static boolean isExistingUser(Collection<PresenceUser> onlineUsers, long userId) {
        PresenceUser newUser = new PresenceUser(userId);
        return onlineUsers.contains(newUser);
    }

    public static PresenceUser getExistingUser(Collection<PresenceUser> onlineUsers, long userId) {
        if (!isExistingUser(onlineUsers, userId)) {
            throw new IllegalStateException("This method should not be called if an existing user is not available! Call isExistingUser() before");
        }

        for (PresenceUser user : onlineUsers) {
            if (user.getUserData().getId().equals(userId)) {
                return user;
            }
        }

        throw new IllegalArgumentException("The user should be existing and contained in onlineUsers!");
    }

    public static PresenceUser getDefaultUser() {
        return DEFAULT_PRESENCE_USER; // All values are set as false (default values)
    }

    /**
     * This method identifies all null values and sets them to values present in userDataToCopyFrom.
     * If a value is not present in userDataToCopyFrom, then default values will be used.
     *
     * @param userWithNullValues
     * @param userDataToCopyFrom
     */
    public static void replaceNullMetaValues(@NonNull PresenceUser userWithNullValues, @NonNull PresenceUser userDataToCopyFrom) {
        if (userWithNullValues == null || userWithNullValues.getActivityData() == null
                || userDataToCopyFrom == null || userDataToCopyFrom.getActivityData() == null) {
            throw new IllegalArgumentException("The userWithNullValues can not be null!");
        }

        PresenceMetaActivityData currentUserActivityData = userWithNullValues.getActivityData();
        PresenceMetaActivityData defaultActivityData = getDefaultUser().getActivityData();
        PresenceMetaActivityData targetActivityData = userDataToCopyFrom.getActivityData();

        currentUserActivityData.setForeground(getValueToReplaceNull(currentUserActivityData.isForeground(), targetActivityData.isForeground(), defaultActivityData.isForeground()));
        currentUserActivityData.setViewing(getValueToReplaceNull(currentUserActivityData.isViewing(), targetActivityData.isViewing(), defaultActivityData.isViewing()));
        currentUserActivityData.setLastActiveAt(getValueToReplaceNull(currentUserActivityData.getLastActiveAt(), targetActivityData.getLastActiveAt(), defaultActivityData.getLastActiveAt()));
        currentUserActivityData.setTyping(getValueToReplaceNull(currentUserActivityData.isTyping(), targetActivityData.isTyping(), defaultActivityData.isTyping()));
        currentUserActivityData.setUpdating(getValueToReplaceNull(currentUserActivityData.isUpdating(), targetActivityData.isUpdating(), defaultActivityData.isUpdating()));
    }

    public static void replaceNullMetaValuesWithDefaultValues(PresenceUser userWithNullValues) {
        replaceNullMetaValues(userWithNullValues, DEFAULT_PRESENCE_USER);
    }

    private static <T extends Object> T getValueToReplaceNull(T originalValue, T targetValue, T defaultValue) {
        if (originalValue == null) {
            if (targetValue == null) {
                return defaultValue;
            } else {
                return targetValue;
            }
        } else {
            return originalValue;
        }
    }

}
