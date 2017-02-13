package com.kayako.sdk.android.k5.conversationlistpage;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.conversationlistpage.ConversationListContract.Data;
import com.kayako.sdk.auth.FingerprintAuth;
import com.kayako.sdk.base.callback.ListCallback;
import com.kayako.sdk.error.KayakoException;
import com.kayako.sdk.messenger.Messenger;
import com.kayako.sdk.messenger.conversation.Conversation;

import java.util.List;

public class ConversationListRepository implements Data {

    private Messenger messenger;

    public ConversationListRepository(@NonNull String helpDeskUrl, @Nullable FingerprintAuth fingerprintAuth) {
        if (fingerprintAuth == null) {
            messenger = new Messenger(helpDeskUrl);
        } else {
            messenger = new Messenger(helpDeskUrl, fingerprintAuth);
        }
    }

    @Override
    public void getConversationList(final ConversationListContract.OnLoadConversationsListener callback, final int offset, final int limit) {
        final Handler handler = new Handler();
        messenger.getConversationList(offset, limit, new ListCallback<Conversation>() {
            @Override
            public void onSuccess(final List<Conversation> items) { // TODO: Null check for listeners?
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(items);
                    }
                });
            }

            @Override
            public void onFailure(KayakoException exception) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure();
                    }
                });
            }
        });
        // TODO: Handle offset and limits to test pagination
    }
}
