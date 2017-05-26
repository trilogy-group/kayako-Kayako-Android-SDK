package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.UserDecoration;
import com.kayako.sdk.messenger.message.Message;

public class UserDecorationHelper {

    private UserDecorationHelper() {
    }

    public static UserDecoration getUserDecoration(Message message, Long currentUserId) {
        if (message == null || message.getCreator() == null) {
            return null;
        }

        return new UserDecoration(
                message.getCreator().getFullName(),
                message.getCreator().getAvatarUrl(),
                message.getCreator().getId(),
                currentUserId != null && message.getCreator().getId().equals(currentUserId));
    }
}
