package com.kayako.sdk.android.k5.messagelistpage;


import android.os.Handler;
import android.support.annotation.NonNull;

import com.kayako.sdk.auth.FingerprintAuth;
import com.kayako.sdk.base.callback.ListCallback;
import com.kayako.sdk.error.KayakoException;
import com.kayako.sdk.error.ResponseMessages;
import com.kayako.sdk.error.response.Notification;
import com.kayako.sdk.messenger.Messenger;
import com.kayako.sdk.messenger.message.Message;

import java.util.List;

public class MessageListRepository implements MessageListContract.Data {

    private Messenger mMessenger;

    public MessageListRepository(@NonNull String helpCenterUrl, @NonNull FingerprintAuth auth) {
        if (auth == null) {
            throw new AssertionError("Fingerprint Auth is required for this class");
        } else {
            mMessenger = new Messenger(helpCenterUrl, auth);
        }
    }

    @Override
    public void getMessages(final MessageListContract.OnLoadMessagesListener listener, long conversationId, int offset, int limit) {
        final Handler handler = new Handler(); // Needed to ensure that the callbacks run on the UI Thread
        mMessenger.getMessages(conversationId, offset, limit, new ListCallback<Message>() {
            @Override
            public void onSuccess(final List<Message> items) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (listener != null) {
                            listener.onSuccess(items);
                        }
                    }
                });
            }

            @Override
            public void onFailure(final KayakoException exception) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (listener == null) {
                            return;
                        }

                        ResponseMessages responseMessages = exception.getResponseMessages();
                        if (responseMessages != null) {
                            List<Notification> notifications = responseMessages.getNotifications();
                            if (notifications != null && notifications.size() > 0) {
                                listener.onFailure(notifications.get(0).message);
                                return;
                            }
                        }


                        listener.onFailure(exception.getMessage());
                    }
                });
            }
        });
    }
}
