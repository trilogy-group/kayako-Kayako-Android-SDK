package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.ClientDeliveryStatus;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.OptimisticSendingHelper;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.UnsentMessage;
import com.kayako.sdk.messenger.message.Message;

import java.util.List;
import java.util.Map;

/**
 * All logic involving the views of optimistic sending.
 * Note: <p>
 * 1. This is common for messages of a New Conversation and Existing Conversation.
 * 2. This helper class is focusing mainly on views and user actions on such views
 * 3. This is where the unsent messages are saved - removed only if successfully sent!
 */
public class OptimisticSendingViewHelper {
    // TODO: Show a dialog on tap - Delete unsent messages or resent unsent messages?

    private OptimisticSendingHelper optimisticSendingHelper;
    private String avatarUrl;

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

    public List<UnsentMessage> getUnsentMessages() {
        validateHelper();

        return optimisticSendingHelper.getUnsentMessages();
    }

    public void removeOptimisticMessagesThatIsSuccessfullySentAndDisplayed(List<Message> newMessages) {
        validateHelper();

        for (Message message : newMessages) {
            optimisticSendingHelper.removeMessage(message.getClientId());
        }
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

    public void addOptimisitcMessageView(String message, String clientId, OptimisticSendingViewCallback callback) {
        validateCallback(callback);
        validateHelper();

        optimisticSendingHelper.addMessage(
                new UnsentMessage(
                        ClientDeliveryStatus.SENDING,
                        message,
                        clientId
                )
        );

        callback.onRefreshListView();  // Display list with optimistic sending views
    }

    public void resendOptimisticMessage(String clientId, UnsentMessage unsentMessage, OptimisticSendingViewCallback callback) {
        validateCallback(callback);
        validateHelper();

        if (unsentMessage.getDeliveryStatus() == ClientDeliveryStatus.FAILED_TO_SEND) {
            optimisticSendingHelper.removeMessage(clientId);
            if (unsentMessage.getMessage() != null) {
                callback.onResendReply(unsentMessage);
            }

            callback.onRefreshListView();

        } else {
            // Do nothing if not FAILED_TO_SEND state
        }
    }

    public void resendAllOptimisticMessages(OptimisticSendingViewCallback callback) {
        validateCallback(callback);
        validateHelper();

        List<UnsentMessage> unsentMessages = getUnsentMessages();
        for (UnsentMessage unsentMessage : unsentMessages) {
            resendOptimisticMessage(unsentMessage.getClientId(), unsentMessage, callback);
        }
    }

    public void markAllAsFailed(OptimisticSendingViewCallback callback) {
        validateCallback(callback);
        validateHelper();

        optimisticSendingHelper.markAllAsFailed();
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
