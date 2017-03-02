package com.kayako.sdk.android.k5.kre.helpers;

import android.support.annotation.NonNull;


import com.kayako.sdk.android.k5.kre.base.KreSubscription;
import com.kayako.sdk.android.k5.kre.helpers.presence.KrePresenceHelper;

import java.util.List;

public class KreOnlinePresenceHelper {

    private static final String TAG = "KreOnlinePresenceHelper";

    private KreOnlinePresenceHelper() {
    }

    public static void addRawOnlinePresenceHelper(@NonNull KreSubscription kreSubscription, final long userId, @NonNull final RawUserOnlinePresenceListener listener) {
        KrePresenceHelper krePresenceHelper = new KrePresenceHelper(kreSubscription, false, userId);
        krePresenceHelper.setRawUserSubscribedPresenceListener(new RawUserSubscribedPresenceListener() {
            @Override
            public void onUsersAlreadySubscribed(List<Long> onlineUserIds, long entryTime) {
                if (onlineUserIds.contains(userId)) {
                    listener.onUserOnline();
                }
            }

            @Override
            public void onNewUserSubscribing(Long onlineUser, long entryTime) {
                if (onlineUser.equals(userId)) {
                    listener.onUserOnline();
                }
            }

            @Override
            public void onUserNoLongerSubscribed(Long offlineUserId) {
                if (offlineUserId.equals(userId)) {
                    listener.onUserOffline();
                }
            }

            @Override
            public void onConnectionError() {
                listener.onConnectionError();
            }
        });
    }

}
