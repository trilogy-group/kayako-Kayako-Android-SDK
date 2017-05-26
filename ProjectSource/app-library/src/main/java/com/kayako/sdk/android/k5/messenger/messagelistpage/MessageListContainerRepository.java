package com.kayako.sdk.android.k5.messenger.messagelistpage;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.common.utils.NetworkUtils;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.auth.FingerprintAuth;
import com.kayako.sdk.base.callback.EmptyCallback;
import com.kayako.sdk.base.callback.ItemCallback;
import com.kayako.sdk.base.callback.ListCallback;
import com.kayako.sdk.base.parser.Resource;
import com.kayako.sdk.base.requester.AttachmentFile;
import com.kayako.sdk.error.KayakoException;
import com.kayako.sdk.error.ResponseMessages;
import com.kayako.sdk.error.response.Notification;
import com.kayako.sdk.messenger.Messenger;
import com.kayako.sdk.messenger.conversation.Conversation;
import com.kayako.sdk.messenger.conversation.PostConversationBodyParams;
import com.kayako.sdk.messenger.message.Message;
import com.kayako.sdk.messenger.message.PostMessageBodyParams;
import com.kayako.sdk.messenger.message.PutMessageBodyParams;
import com.kayako.sdk.messenger.rating.PostRatingBodyParams;
import com.kayako.sdk.messenger.rating.PutRatingBodyParams;
import com.kayako.sdk.messenger.rating.Rating;

import java.util.List;

public class MessageListContainerRepository implements MessageListContainerContract.Data {

    private static final long DELAY_BEFORE_NO_NETWORK_NOTICE = 1000;
    private Messenger mMessenger;

    public MessageListContainerRepository(String helpCenterUrl, FingerprintAuth fingerpritnAuth) {
        mMessenger = new Messenger(helpCenterUrl, fingerpritnAuth);
    }

    @Override
    public void postNewMessage(long conversationId, PostMessageBodyParams postMessageBodyParams, final String clientId, final MessageListContainerContract.PostNewMessageCallback callback) {
        if (postMessageBodyParams == null) {
            throw new IllegalArgumentException("Can't be null");
        }

        final Handler handler = new Handler();

        if (!NetworkUtils.isConnectedToNetwork(Kayako.getApplicationContext())) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    callback.onFailure(clientId);
                }
            }, DELAY_BEFORE_NO_NETWORK_NOTICE);
            return;
        }

        mMessenger.postMessage(conversationId, postMessageBodyParams, new ItemCallback<Message>() {
            @Override
            public void onSuccess(final Message item) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(item);
                    }
                });
            }

            @Override
            public void onFailure(final KayakoException exception) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // TODO: Parse and see if there's a notification in KayakoException
                        callback.onFailure(clientId);
                    }
                });
            }
        });
    }

    @Override
    public void getMessages(final MessageListContainerContract.OnLoadMessagesListener listener, long conversationId, final int offset, int limit) {
        final Handler handler = new Handler(); // Needed to ensure that the callbacks run on the UI Thread

        if (!NetworkUtils.isConnectedToNetwork(Kayako.getApplicationContext())) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    listener.onFailure(null);
                }
            }, DELAY_BEFORE_NO_NETWORK_NOTICE);
            return;
        }

        mMessenger.getMessages(conversationId, offset, limit, new ListCallback<Message>() {
            @Override
            public void onSuccess(final List<Message> items) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (listener != null) {
                            listener.onSuccess(items, offset);
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

    @Override
    public void startNewConversation(final PostConversationBodyParams bodyParams, final MessageListContainerContract.PostConversationCallback postConversationCallback) {
        final Handler handler = new Handler(); // Needed to ensure that the callbacks run on the UI Thread

        if (!NetworkUtils.isConnectedToNetwork(Kayako.getApplicationContext())) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    postConversationCallback.onFailure(bodyParams.getClientId(), null);
                }
            }, DELAY_BEFORE_NO_NETWORK_NOTICE);
            return;
        }


        mMessenger.postConversation(bodyParams, new ItemCallback<Conversation>() {
            @Override
            public void onSuccess(final Conversation item) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (postConversationCallback == null) {
                            return;
                        }

                        postConversationCallback.onSuccess(bodyParams.getClientId(), item);
                    }
                });
            }

            @Override
            public void onFailure(final KayakoException exception) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (postConversationCallback == null) {
                            return;
                        }

                        postConversationCallback.onFailure(bodyParams.getClientId(), exception.getMessage()); // TODO: Add a method to extract the latest NOTIFICATION
                    }
                });
            }
        });
    }

    @Override
    public void getConversation(long conversationId, final MessageListContainerContract.OnLoadConversationListener onLoadConversationListener) {
        final Handler handler = new Handler(); // Needed to ensure that the callbacks run on the UI Thread

        if (!NetworkUtils.isConnectedToNetwork(Kayako.getApplicationContext())) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    onLoadConversationListener.onFailure(null);
                }
            }, DELAY_BEFORE_NO_NETWORK_NOTICE);
            return;
        }

        mMessenger.getConversation(conversationId, new ItemCallback<Conversation>() {
            @Override
            public void onSuccess(final Conversation item) {
                if (onLoadConversationListener == null) {
                    return;
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onLoadConversationListener.onSuccess(item);
                    }
                });
            }

            @Override
            public void onFailure(final KayakoException exception) {
                if (onLoadConversationListener == null) {
                    return;
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (exception != null) {
                            onLoadConversationListener.onFailure(exception.getMessage());
                        }
                    }
                });
            }
        });
    }

    @Override
    public void markMessageAsRead(long conversationId, final long messageId, final MessageListContainerContract.OnMarkMessageAsReadListener onMarkMessageAsReadListener) {
        markMessage(PutMessageBodyParams.MessageStatus.SEEN, conversationId, messageId, onMarkMessageAsReadListener);
    }

    @Override
    public void markMessageAsDelivered(long conversationId, long postId, MessageListContainerContract.OnMarkMessageAsReadListener onMarkMessageAsReadListener) {
        markMessage(PutMessageBodyParams.MessageStatus.DELIVERED, conversationId, postId, onMarkMessageAsReadListener);
    }

    public void markMessage(PutMessageBodyParams.MessageStatus status, long conversationId, final long messageId, final MessageListContainerContract.OnMarkMessageAsReadListener onMarkMessageAsReadListener) {
        final Handler handler = new Handler(); // Needed to ensure that the callbacks run on the UI Thread

        if (!NetworkUtils.isConnectedToNetwork(Kayako.getApplicationContext())) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    onMarkMessageAsReadListener.onFailure(null);
                }
            }, DELAY_BEFORE_NO_NETWORK_NOTICE);
            return;
        }

        mMessenger.putMessage(
                conversationId,
                messageId,
                new PutMessageBodyParams(status),
                new EmptyCallback() {
                    @Override
                    public void onSuccess() {
                        if (onMarkMessageAsReadListener == null) {
                            return;
                        }

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                onMarkMessageAsReadListener.onSuccess(messageId);
                            }
                        });
                    }

                    @Override
                    public void onFailure(final KayakoException exception) {
                        if (onMarkMessageAsReadListener == null) {
                            return;
                        }

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                onMarkMessageAsReadListener.onFailure(exception.getMessage());
                            }
                        });

                    }
                });
    }

    @Override
    public void getConversationRatings(long conversationId, final MessageListContainerContract.OnLoadRatingsListener onLoadRatingsListener) {
        final Handler handler = new Handler(); // Needed to ensure that the callbacks run on the UI Thread

        if (!NetworkUtils.isConnectedToNetwork(Kayako.getApplicationContext())) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    onLoadRatingsListener.onFailure(null);
                }
            }, DELAY_BEFORE_NO_NETWORK_NOTICE);
            return;
        }

        mMessenger
                .getRatingList(
                        conversationId,
                        new ListCallback<Rating>() {
                            @Override
                            public void onSuccess(final List<Rating> items) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (onLoadRatingsListener != null) {
                                            onLoadRatingsListener.onSuccess(items);
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onFailure(final KayakoException exception) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (onLoadRatingsListener != null) {
                                            onLoadRatingsListener.onFailure(exception.getMessage());
                                        }
                                    }
                                });

                            }
                        }
                );
    }

    @Override
    public void addConversationRating(long conversationId, final PostRatingBodyParams postRatingBodyParams, final MessageListContainerContract.OnUpdateRatingListener onUpdateRatingListener) {
        final Handler handler = new Handler(); // Needed to ensure that the callbacks run on the UI Thread

        if (!NetworkUtils.isConnectedToNetwork(Kayako.getApplicationContext())) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    onUpdateRatingListener.onFailure(postRatingBodyParams.getScore(), postRatingBodyParams.getComment(), null);
                }
            }, DELAY_BEFORE_NO_NETWORK_NOTICE);
            return;
        }

        mMessenger.postRating(
                conversationId,
                postRatingBodyParams,
                new ItemCallback<Rating>() {

                    @Override
                    public void onSuccess(final Rating item) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (onUpdateRatingListener != null)
                                    onUpdateRatingListener.onSuccess(item);
                            }
                        });
                    }

                    @Override
                    public void onFailure(final KayakoException exception) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (onUpdateRatingListener != null)
                                    onUpdateRatingListener.onFailure(postRatingBodyParams.getScore(), postRatingBodyParams.getComment(), exception.getMessage());
                            }
                        });
                    }
                });
    }

    @Override
    public void updateConversationRating(long conversationId, long ratingId, final PutRatingBodyParams putRatingBodyParams, final MessageListContainerContract.OnUpdateRatingListener onUpdateRatingListener) {
        final Handler handler = new Handler(); // Needed to ensure that the callbacks run on the UI Thread

        if (!NetworkUtils.isConnectedToNetwork(Kayako.getApplicationContext())) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    onUpdateRatingListener.onFailure(putRatingBodyParams.getScore(), putRatingBodyParams.getComment(), null);
                }
            }, DELAY_BEFORE_NO_NETWORK_NOTICE);
            return;
        }

        mMessenger.putRating(
                conversationId,
                ratingId,
                putRatingBodyParams,
                new ItemCallback<Rating>() {

                    @Override
                    public void onSuccess(final Rating item) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (onUpdateRatingListener != null)
                                    onUpdateRatingListener.onSuccess(item);
                            }
                        });
                    }

                    @Override
                    public void onFailure(final KayakoException exception) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (onUpdateRatingListener != null) {
                                    onUpdateRatingListener.onFailure(putRatingBodyParams.getScore(), putRatingBodyParams.getComment(), exception.getMessage());
                                }
                            }
                        });
                    }
                });

    }
}
