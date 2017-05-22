package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.UnsentMessage;

public interface AddReplyQueueInterface {

    void addNewReply(UnsentMessage unsentMessage, String clientId, AddReplyListener callback);

    void onSuccessfulSendingOfReply(String clientId, AddReplyListener callback);

    void onFailedToSendReply(String clientId);

    void sendNext(AddReplyListener callback);

    String getLastSentReplyClientId();

    interface AddReplyListener {

        void onAddReply(UnsentMessage unsentMessage);
    }
}
