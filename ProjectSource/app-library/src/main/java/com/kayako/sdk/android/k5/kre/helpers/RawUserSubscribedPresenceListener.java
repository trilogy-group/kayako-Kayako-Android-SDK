package com.kayako.sdk.android.k5.kre.helpers;

import java.util.List;

public interface RawUserSubscribedPresenceListener {

    void onUsersAlreadySubscribed(List<Long> onlineUserIds, long entryTime);

    void onNewUserSubscribing(Long onlineUser, long entryTime);

    void onUserNoLongerSubscribed(Long offlineUserId);

    void onConnectionError();
}
