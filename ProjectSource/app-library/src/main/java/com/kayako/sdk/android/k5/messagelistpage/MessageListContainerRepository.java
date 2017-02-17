package com.kayako.sdk.android.k5.messagelistpage;

import com.kayako.sdk.auth.FingerprintAuth;
import com.kayako.sdk.base.callback.ItemCallback;
import com.kayako.sdk.error.KayakoException;
import com.kayako.sdk.messenger.Messenger;
import com.kayako.sdk.messenger.message.Message;
import com.kayako.sdk.messenger.message.MessageSourceType;
import com.kayako.sdk.messenger.message.PostMessageBodyParams;

public class MessageListContainerRepository implements MessageListContainerContract.Data {

    private Messenger mMessenger;

    public MessageListContainerRepository(String helpCenterUrl, FingerprintAuth fingerpritnAuth) {
        mMessenger = new Messenger(helpCenterUrl, fingerpritnAuth);
    }

    @Override
    public void postNewMessage(long conversationId, String contents, final MessageListContainerContract.PostNewMessageCallback callback) {
        mMessenger.postMessage(conversationId, new PostMessageBodyParams(contents, MessageSourceType.MESSENGER), new ItemCallback<Message>() {
            @Override
            public void onSuccess(Message item) {
                callback.onSuccess(item);
            }

            @Override
            public void onFailure(KayakoException exception) {
                // TODO: Parse and see if there's a notification in KayakoException
                callback.onFailure(exception.getMessage());
            }
        });
    }
}
