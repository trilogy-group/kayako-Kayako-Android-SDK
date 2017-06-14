package com.kayako.sdk.android.k5.messenger.data.conversation;

import android.os.Handler;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.UniqueSortedUpdatableResourceList;
import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.android.k5.messenger.data.conversation.unreadcounter.UnreadCounterRepository;
import com.kayako.sdk.auth.FingerprintAuth;
import com.kayako.sdk.base.callback.ItemCallback;
import com.kayako.sdk.base.callback.ListCallback;
import com.kayako.sdk.error.KayakoException;
import com.kayako.sdk.messenger.Messenger;
import com.kayako.sdk.messenger.conversation.Conversation;
import com.kayako.sdk.messenger.conversation.PostConversationBodyParams;

import java.util.Comparator;
import java.util.List;

public class ConversationStore {

    private static final Object key = new Object();
    private static ConversationStore mInstance;

    private UniqueSortedUpdatableResourceList<Conversation> mConversations = new UniqueSortedUpdatableResourceList<>();
    private Messenger mMessenger;

    private ConversationStore() {
        mConversations.setSortComparator(new Comparator() {
            @Override
            public int compare(Object lhs, Object rhs) {
                long leftUpdatedTime = ((Conversation) lhs).getUpdatedAt();
                long rightUpdatedTime = ((Conversation) rhs).getUpdatedAt();

                if (leftUpdatedTime == rightUpdatedTime) {
                    return 0;
                } else if (leftUpdatedTime < rightUpdatedTime) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });

        String url = MessengerPref.getInstance().getUrl();
        FingerprintAuth fingerprintAuth = new FingerprintAuth(MessengerPref.getInstance().getFingerprintId());
        mMessenger = new Messenger(url, fingerprintAuth);
    }

    public synchronized static ConversationStore getInstance() {
        if (mInstance == null) {
            synchronized (key) {
                if (mInstance == null) {
                    return mInstance = new ConversationStore();
                }
            }
        }
        return mInstance;
    }

    public List<Conversation> getCachedConversations() {
        return mConversations.getList();
    }

    public void getConversation(final long conversationId, final ConversationLoaderCallback callback) {
        final Handler handler = new Handler();
        if (mConversations.exists(conversationId)) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onLoadConversation(mConversations.getElement(conversationId));
                }
            });
        }

        mMessenger.getConversation(conversationId, new ItemCallback<Conversation>() {
            @Override
            public void onSuccess(final Conversation item) {
                addElement(item);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onLoadConversation(item);
                    }
                });
            }

            @Override
            public void onFailure(final KayakoException exception) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(exception);
                    }
                });
            }
        });
    }

    public void getConversations(int offset, int limit, final ConversationListLoaderCallback callback) {
        final Handler handler = new Handler();

        if (mConversations.getSize() > 0 && offset == 0) {
            List<Conversation> conversationList = mConversations.getList();

            if (conversationList.size() > limit) {
                conversationList = conversationList.subList(0, limit);
            }

            final List<Conversation> finalConversationList = conversationList;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onLoadConversations(finalConversationList);
                }
            });
        }

        mMessenger.getConversationList(offset, limit, new ListCallback<Conversation>() {
            @Override
            public void onSuccess(final List<Conversation> items) {

                for (Conversation item : items) {
                    addElement(item);
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onLoadConversations(items);
                    }
                });
            }

            @Override
            public void onFailure(final KayakoException exception) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(exception);
                    }
                });
            }
        });
    }

    public void postConversation(final PostConversationBodyParams bodyParams, final ConversationLoaderCallback callback) {
        final Handler handler = new Handler();
        mMessenger.postConversation(bodyParams, new ItemCallback<Conversation>() {
            @Override
            public void onSuccess(final Conversation item) {
                addElement(item);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callback == null) {
                            return;
                        }

                        callback.onLoadConversation(item);
                    }
                });
            }

            @Override
            public void onFailure(final KayakoException exception) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callback == null) {
                            return;
                        }

                        callback.onFailure(exception);
                    }
                });
            }
        });
    }

    public void clear() {
        mConversations = new UniqueSortedUpdatableResourceList<>();
    }

    private void addElement(Conversation item) {
        mConversations.addElement(item.getId(), item);
        UnreadCounterRepository.refreshUnreadCounter();
    }

    public interface ConversationLoaderCallback {
        void onLoadConversation(Conversation conversation);

        void onFailure(KayakoException e);
    }

    public interface ConversationListLoaderCallback {
        void onLoadConversations(List<Conversation> conversationList);

        void onFailure(KayakoException e);
    }
}


