package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.ClientDeliveryStatus;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.OptimisticSendingHelper;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.UnsentMessage;
import com.kayako.sdk.messenger.message.Message;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * All logic involving the views of optimistic sending.
 * Note: <p>
 * 1. This is common for messages of a New Conversation and Existing Conversation.
 * 2. This helper class is focusing mainly on views and user actions on such views
 */
public class OptimisticSendingViewHelper {
    // TODO: Show a dialog on tap - Delete unsent messages or resent unsent messages?

    private OptimisticSendingHelper optimisticSendingHelper;
    private String avatarUrl;
    private AtomicBoolean messagesFailedToSend = new AtomicBoolean(false); // ensure messages sent after a failed message is considered failed too

    public OptimisticSendingViewHelper() {
    }

    public void setUserAvatar(@Nullable String avatarUrl) {
        // NOTE: Do not validate helper here - helper is created here!

        if (this.avatarUrl != null
                && this.avatarUrl.equals(avatarUrl)) { // Prevent recreating instance if no changes to avatarUrl
            return;
        }

        this.avatarUrl = avatarUrl;
        optimisticSendingHelper = new OptimisticSendingHelper(avatarUrl);
    }

    public List<BaseListItem> getOptimisticSendingListItems() {
        validateHelper();

        return optimisticSendingHelper.getMessageViews();
    }

    public boolean isFailedToSendMessage(Map<String, Object> messageData) {
        ClientDeliveryStatus clientDeliveryStatus = optimisticSendingHelper.getDeliveryStatus(messageData);
        return clientDeliveryStatus != null
                && clientDeliveryStatus == ClientDeliveryStatus.FAILED_TO_SEND;
    }

    public boolean isOptimisticMessage(Map<String, Object> messageData) {
        validateHelper();

        return optimisticSendingHelper.isOptimisticMessage(messageData);
    }

    public void removeOptimisticMessagesThatIsSuccessfullySentAndDisplayed(List<Message> newMessages) {
        validateHelper();

        messagesFailedToSend.set(false); // if successful, set to false

        for (Message message : newMessages) {
            optimisticSendingHelper.removeMessage(message.getClientId());
        }
    }

    public void addOptimisitcMessageView(String message, String clientId, OptimisticSendingViewCallback callback) {
        validateCallback(callback);
        validateHelper();


        ClientDeliveryStatus clientDeliveryStatus;
        if (messagesFailedToSend.get()) {
            clientDeliveryStatus = ClientDeliveryStatus.FAILED_TO_SEND;
        } else {
            clientDeliveryStatus = ClientDeliveryStatus.SENDING;
        }

        optimisticSendingHelper.addMessage(
                new UnsentMessage(
                        clientDeliveryStatus,
                        message,
                        clientId
                )
        );

        callback.onRefreshListView();  // Display list with optimistic sending views
    }

    public void markAllAsFailed(OptimisticSendingViewCallback callback) {
        validateCallback(callback);
        validateHelper();

        messagesFailedToSend.set(true); // if one failed to send, assume all failed to send

        optimisticSendingHelper.markAllAsFailed();
        callback.onRefreshListView();
    }

    public void markAllAsSending(OptimisticSendingViewCallback callback) {
        validateCallback(callback);
        validateHelper();

        messagesFailedToSend.set(false); // if resending messages, old ones are cleared for new ones - set to false

        optimisticSendingHelper.markAllAsSending();
        callback.onRefreshListView();
    }

    private void validateHelper() {
        if (optimisticSendingHelper == null) {
            throw new IllegalStateException("Call setUserAvatar() first");
        }
    }

    private void validateCallback(OptimisticSendingViewCallback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Callback can not be null!");
        }
    }

    public interface OptimisticSendingViewCallback {
        void onRefreshListView();

        void onResendReply(UnsentMessage unsentMessage);
    }
}
