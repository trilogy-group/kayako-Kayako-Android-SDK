package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import com.kayako.sdk.messenger.message.Message;

import java.util.List;

public class MarkDeliveredHelper {


    public void onLoadMessages(List<Message> messageList){

    }

    public interface OnReceiveMessageListener {
        void markDelivered(long postId);
    }
}
