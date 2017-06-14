package com.kayako.sdk.android.k5.messenger.conversationlistpage;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.messenger.conversationlistpage.ConversationListContract.Data;
import com.kayako.sdk.android.k5.messenger.data.conversation.ConversationStore;
import com.kayako.sdk.auth.FingerprintAuth;
import com.kayako.sdk.base.callback.ListCallback;
import com.kayako.sdk.error.KayakoException;
import com.kayako.sdk.messenger.Messenger;
import com.kayako.sdk.messenger.conversation.Conversation;

import java.util.List;

public class ConversationListRepository implements Data {

    public ConversationListRepository() {
    }

    @Override
    public void getConversationList(final ConversationListContract.OnLoadConversationsListener callback, final int offset, final int limit) {
        final Handler handler = new Handler();
        ConversationStore.getInstance().getConversations(
                offset,
                limit,
                new ConversationStore.ConversationListLoaderCallback() {
                    @Override
                    public void onLoadConversations(final List<Conversation> items) {
                        if (callback == null) {
                            return;
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onSuccess(items);
                            }
                        });
                    }

                    @Override
                    public void onFailure(KayakoException exception) {
                        if (callback == null) {
                            return;
                        }

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onFailure();
                            }
                        });
                    }
                }
        );
    }
}
