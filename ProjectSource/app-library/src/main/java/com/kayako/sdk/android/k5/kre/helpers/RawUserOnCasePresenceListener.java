package com.kayako.sdk.android.k5.kre.helpers;

import java.util.List;

public interface RawUserOnCasePresenceListener {

    void onUsersAlreadyViewingCase(List<Long> onlineUserIds, long entryTime);

    void onNewUserViewingCase(Long onlineUser, long entryTime);

    void onUserNoLongerViewingCase(Long offlineUserId);

    void onExistingUserPerformingSomeActivity(Long onlineUser, long lastActiveTime);

    void onConnectionError();
}
