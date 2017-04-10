package com.kayako.sdk.android.k5.messenger.messagelistpage;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.DataItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.ClientDeliveryStatus;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.DataItemHelper;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.OptimisticSendingHelper;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.UniqueSortedUpdatableResourceList;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.UnsentMessage;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.UserDecorationHelper;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputEmailListItem;
import com.kayako.sdk.android.k5.common.fragments.ListPageState;
import com.kayako.sdk.android.k5.common.fragments.OnScrollListListener;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.AddMessageHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.ClientIdHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.MarkReadHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.MessengerPrefHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.NewConversationHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.OnboardingHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.ReplyBoxHelper;
import com.kayako.sdk.messenger.conversation.Conversation;
import com.kayako.sdk.messenger.message.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Terms used:
 * <p>
 * New Conversation : a conversation that is yet to be created via APIs
 * Existing Conversation : a conversation that has been created via APIs
 */
public class MessageListContainerPresenter implements MessageListContainerContract.Presenter {

    private boolean mIsNewConversation;  // original value when page is opened

    private MessageListContainerContract.View mView;
    private MessageListContainerContract.Data mData;

    private MessengerPrefHelper mMessengerPrefHelper = new MessengerPrefHelper();
    private MarkReadHelper mMarkReadHelper = new MarkReadHelper();
    private OnboardingHelper mOnboardingHelper = new OnboardingHelper();
    private NewConversationHelper mNewConversationHelper = new NewConversationHelper();
    private ExistingConversationHelper mExistingConversationHelper = new ExistingConversationHelper();
    private ReplyBoxHelper mReplyBoxHelper = new ReplyBoxHelper();
    private ListHelper mListHelper = new ListHelper();
    private OptimisticSendingViewHelper mOptimisticMessageHelper = new OptimisticSendingViewHelper();
    private ClientIdHelper mClientIdHelper = new ClientIdHelper();
    private ExistingMessagesHelper mExistingMessagesHelper = new ExistingMessagesHelper();
    private AddMessageHelper mAddMessageHelper = new AddMessageHelper();

    // private UniqueSortedUpdatableResourceList<Message> mMessageList = new UniqueSortedUpdatableResourceList<>();
    // TODO: Group onboarding messages logic in a private class? - confusing otherwise as more and more methods are used
    // TODO: Mark conversation as READ - add API Endpoint to Java SDK too.

    public MessageListContainerPresenter(MessageListContainerContract.View view, MessageListContainerContract.Data data) {
        mView = view;
        mData = data;
    }

    @Override
    public void setData(MessageListContainerContract.Data data) {
        mData = data;
    }

    @Override
    public void onClickRetryInErrorView() {
        reloadPage(true);
    }

    @Override
    public void onPageStateChange(ListPageState state) {
        mListHelper.setListPageState(state);
        configureReplyBoxViewState();
    }

    @Override
    public void onScrollList(boolean isScrolling, OnScrollListListener.ScrollDirection direction) {
        // Collapse the expanded toolbar if any scrolling operation is noticed
        if (isScrolling && direction != null) {
            switch (direction) {
                case UP:
                case DOWN:
                    mView.collapseToolbar();
            }
        }
    }

    @Override
    public void onListItemClick(int messageType, Long id, Map<String, Object> messageData) {
        if (mOptimisticMessageHelper.isOptimisticMessage(messageData)) {
            // TODO: Identify if it's SENDING... or FAILED...
            // Retry all optimistic messages
            mOptimisticMessageHelper.resendAllOptimisticMessages();
        }
    }

    @Override
    public void onLoadMoreItems() {
        if (mNewConversationHelper.isConversationCreated()) {
            mExistingMessagesHelper.loadNextMessages(mExistingConversationHelper.getConversationId());
        }
    }

    @Override
    public void setView(MessageListContainerContract.View view) {
        mView = view;
    }

    @Override
    public void initPage(boolean isNewConversation, Long conversationId) {
        resetVariables(); // TODO: Figure out how to handle orientations

        this.mIsNewConversation = isNewConversation;

        mNewConversationHelper.setIsConversationCreated(!isNewConversation);
        mExistingConversationHelper.setConversationId(conversationId);

        reloadPage(true);
    }

    @Override
    public void onClickSendInReplyView(String message) {
        // Collapse toolbar once a message has been sent!
        mView.collapseToolbar();

        // Assertion: If it's existing conversation, then mConversationId can not be null or invalid
        if (mNewConversationHelper.isConversationCreated() &&
                (mExistingConversationHelper.getConversationId() == null
                        || mExistingConversationHelper.getConversationId() == 0)) {
            throw new AssertionError("If it is NOT a new conversation, conversation id should NOT be null");
        }

        onSendReply(message);
    }

    private void onSendReply(String message) {
        String clientId = mClientIdHelper.generateClientId(); // GENERATE Client Id

        mOptimisticMessageHelper.onClickSendInReplyView(message, clientId);
        if (!mNewConversationHelper.isConversationCreated()) {
            createNewConversation(message, clientId);
            mListHelper.displayList();
        } else {
            mAddMessageHelper.addMessageToSend(
                    message,
                    clientId,
                    new AddMessageHelper.OnAddNextMessageCallback() {
                        @Override
                        public void onNextMessage(UnsentMessage unsentMessage) {
                            addNewMessage(mExistingConversationHelper.getConversationId(), unsentMessage.getMessage(), unsentMessage.getClientId());
                        }
                    });
        }
    }

    private void resetVariables() {
        mMarkReadHelper = new MarkReadHelper();
    }

    private void reloadPage(boolean resetView) {
        if (resetView) {
            mView.showLoadingViewInMessageListingView();
        }

        if (!mNewConversationHelper.isConversationCreated()) {
            // Show expanded toolbar when it's a new conversation
            mView.expandToolbar();

            // Load view
            mListHelper.displayList();
        } else {
            mExistingConversationHelper.reloadConversation();
            mExistingMessagesHelper.reloadLatestMessages(mExistingConversationHelper.getConversationId());
        }

        configureReplyBoxViewState();
    }

    MessageListContainerContract.OnLoadConversationListener onLoadConversationListener = new MessageListContainerContract.OnLoadConversationListener() {
        @Override
        public void onSuccess(Conversation conversation) {
            mMarkReadHelper.setOriginalLastMessageMarkedReadIfNotSetBefore(conversation.getReadMarker() == null ? 0 : conversation.getReadMarker().getLastReadPostId());

            // Update user details (creator of new conversation) - This has been done EVERY TIME to ensure that even if the current userid is lost due to any reason, a new conversation will get it back! - eg: using the same fingerprint for multiple users
            mMessengerPrefHelper
                    .setUserInfo(
                            conversation.getCreator().getId(), // Assuming the current user is always the creator of conversation,
                            conversation.getCreator().getFullName(),
                            conversation.getCreator().getAvatarUrl()
                    );


            // If originally new conversation, set as existing conversation
            if (mIsNewConversation && !mNewConversationHelper.isConversationCreated()) {
                mNewConversationHelper.setIsConversationCreated(true);
            }

            // Update existing Conversation
            mExistingConversationHelper.setConversationId(conversation.getId());
            mExistingConversationHelper.setConversation(conversation);

            // Reload the messages of existing conversation
            mExistingMessagesHelper.reloadLatestMessages(conversation.getId());
        }

        @Override
        public void onFailure(String message) {
            // TODO: Show error mesasage?
        }
    };

    MessageListContainerContract.OnLoadMessagesListener onLoadMessagesListener = new MessageListContainerContract.OnLoadMessagesListener() {
        @Override
        public void onSuccess(List<Message> messageList, int offset) {
            // if first time the page was loaded, scroll to bottom of list
            if (mExistingMessagesHelper.getLastSuccessfulOffset() == 0) { // don't only rely on function argument, offset, being == 0 (which can mean reloading recent messages)
                mView.scrollToBottomOfList();
            }

            mExistingMessagesHelper.onLoadNextMessages(messageList, offset);

            // Remove optimisitc sending items
            mOptimisticMessageHelper.removeOptimisticMessagesThatSuccessfullyGotSent(messageList);

            // Display list once necessary changes to list data are made above
            mListHelper.displayList();

            // Once messages have been loaded AND displayed in view, mark the last message as read
            markLastMessageAsRead(
                    messageList,
                    mExistingConversationHelper.getConversationId());
        }

        @Override
        public void onFailure(String message) {
            // TODO: Add conditions to show toast if load-more and show error view if lastSuccessfulOffset=0
            mView.showErrorViewInMessageListingView();
        }
    };

    MessageListContainerContract.PostNewMessageCallback onPostMessageListener = new MessageListContainerContract.PostNewMessageCallback() {
        @Override
        public void onSuccess(Message message) {
            mAddMessageHelper.onSuccessfulSendingOfMessage();
            mMarkReadHelper.disableOriginalLastMessageMarked(); // Should be called before any list rendering is done
            mExistingMessagesHelper.reloadLatestMessages(mExistingConversationHelper.getConversationId());
        }

        @Override
        public void onFailure(final String clientId) {
            // Now that AddMessageHelper ensures only one message is sent at a time, all pending items should be marked as failed too
            mOptimisticMessageHelper.markAllAsFailed();
        }
    };

    /**
     * Logic to show onboarding messages, actual api messages, optimistic sending messages and KRE indicator messages
     */
    private class ListHelper {

        private ListPageState mListPageState;

        public ListPageState getListPageState() {
            return mListPageState;
        }

        public void setListPageState(ListPageState mListPageState) {
            this.mListPageState = mListPageState;
        }

        public void displayList() {
            List<BaseListItem> allListItems = new ArrayList<>();
            List<BaseListItem> onboardingItems = getOnboardingListItemViews();
            List<BaseListItem> optimisticSendingItems = mOptimisticMessageHelper.getOptimisticSendingListItems();

            if (!mNewConversationHelper.isConversationCreated()) {
                allListItems.addAll(onboardingItems);
                allListItems.addAll(optimisticSendingItems);

                mView.setupListInMessageListingView(allListItems); // No empty state view for Conversation Helper
                mView.setHasMoreItems(false);
            } else {
                allListItems.addAll(onboardingItems);
                allListItems.addAll(getMessageAsListItemViews(mExistingMessagesHelper.getMessages()));
                allListItems.addAll(optimisticSendingItems);

                if (allListItems.size() == 0) {
                    // There should never be an empty state view shown! Leave it blank inviting customer to add stuff.
                } else {
                    mView.setupListInMessageListingView(allListItems);
                    mView.setHasMoreItems(mExistingMessagesHelper.hasMoreMessages());
                }
            }
        }

        private List<DataItem> convertMessagesToDataItems(List<Message> messageList) {
            List<DataItem> dataItems = new ArrayList<>();

            final long lastOriginalMessageMarkedRead = mMarkReadHelper.getOriginalLastMessageMarkedRead();
            for (Message message : messageList) {

                boolean isRead = false;
                /* Note: Using MarkReadHelper and not Conversation variable directly because the unread status should be preserved
                   That is, even though a conversation is marked read, the unread marker should not disappear until the user reopens the page!
                 */
                if (lastOriginalMessageMarkedRead != 0
                        && lastOriginalMessageMarkedRead >= message.getId()) {
                    isRead = true;
                } else {
                    isRead = false;
                }

                // TODO: Leverage client_ids for optimistic sending
                dataItems.add(
                        new DataItem(
                                message.getId(),
                                null,
                                UserDecorationHelper.getUserDecoration(message, mMessengerPrefHelper.getUserId()),
                                null, // no channelDecoration
                                com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.DeliveryIndicatorHelper.getDeliveryIndicator(message), // TODO:
                                message.getContentText(),
                                message.getCreatedAt(),
                                Collections.EMPTY_LIST, // TODO: Attachments
                                isRead
                        )
                );
            }

            return dataItems;
        }

        private List<BaseListItem> getMessageAsListItemViews(List<Message> messages) {
            List<DataItem> dataItems = convertMessagesToDataItems(messages);
            return DataItemHelper.getInstance().convertDataItemToListItems(dataItems);
        }
    }

    /**
     * All logic involving the views of optimistic sending.
     * <p>
     * This is common for messages of a New Conversation and Existing Conversation.
     */
    private class OptimisticSendingViewHelper {
        // TODO: Handle failure states and offer user to RESEND message
        // TODO: Show a dialog on tap - Delete unsent messages or resent unsent messages?

        private OptimisticSendingHelper optimisticSendingHelper;

        public OptimisticSendingViewHelper() {
            optimisticSendingHelper = new OptimisticSendingHelper(mMessengerPrefHelper.getAvatar());
        }

        public List<BaseListItem> getOptimisticSendingListItems() {
            return optimisticSendingHelper.getMessageViews();
        }

        public List<UnsentMessage> getUnsentMessages() {
            return optimisticSendingHelper.getUnsentMessages();
        }

        public void removeOptimisticMessagesThatSuccessfullyGotSent(List<Message> newMessages) {
            for (Message message : newMessages) {
                optimisticSendingHelper.removeMessage(message.getClientId());
            }
        }

        public void onClickSendInReplyView(String message, String clientId) {
            optimisticSendingHelper.addMessage(
                    ClientDeliveryStatus.SENDING,
                    message,
                    clientId
            );

            mListHelper.displayList(); // Display list with optimistic sending views
        }

        public boolean isOptimisticMessage(Map<String, Object> messageData) {
            return optimisticSendingHelper.isOptimisticMessage(messageData);
        }

        public void resendOptimisticMessage(String clientId, UnsentMessage unsentMessage) {
            if (unsentMessage.getDeliveryStatus() == ClientDeliveryStatus.FAILED_TO_SEND) {
                optimisticSendingHelper.removeMessage(clientId);
                if (unsentMessage.getMessage() != null) {
                    MessageListContainerPresenter.this.onSendReply(unsentMessage.getMessage());
                }

                mListHelper.displayList();

            } else {
                // Do nothing if not FAILED_TO_SEND state
            }
        }

        public void resendAllOptimisticMessages() {
            List<UnsentMessage> unsentMessages = getUnsentMessages();
            for (UnsentMessage unsentMessage : unsentMessages) {
                resendOptimisticMessage(unsentMessage.getClientId(), unsentMessage);
            }
        }

        public void markAllAsFailed() {
            optimisticSendingHelper.markAllAsFailed();
            mListHelper.displayList();
        }
    }


    /**
     * All logic involving retrieving messages of an existing conversation which includes pagination.
     */
    private class ExistingMessagesHelper {

        private UniqueSortedUpdatableResourceList<Message> messages = new UniqueSortedUpdatableResourceList<>();
        private final int LIMIT = 30;
        private AtomicInteger lastSuccessfulOffset = new AtomicInteger(0);
        private AtomicBoolean hasMoreMessages = new AtomicBoolean(true);

        public int getLastSuccessfulOffset() {
            return lastSuccessfulOffset.get();
        }

        public List<Message> getMessages() {
            return messages.getList();
        }

        public void reloadLatestMessages(long conversationId) {
            mData.getMessages(onLoadMessagesListener, conversationId, 0, LIMIT);
        }

        public void loadNextMessages(long conversationId) {
            mView.showLoadMoreView();
            mData.getMessages(onLoadMessagesListener, conversationId, lastSuccessfulOffset.get() + LIMIT, LIMIT);
        }

        public void onLoadNextMessages(List<Message> newMessages, int offset) {
            if (offset != 0) {
                mView.hideLoadMoreView();
            }

            // Check if hasMore
            if (newMessages.size() == 0) {
                hasMoreMessages.set(false);
            } else if (newMessages.size() < LIMIT) {
                hasMoreMessages.set(false);
            } else if (newMessages.size() == LIMIT) {
                hasMoreMessages.set(true);
            } else {
                // new messages should never be more than the limit. API BUG!
            }

            // Set last successful offset if it's greater than the current one
            if (offset > lastSuccessfulOffset.get()) {
                lastSuccessfulOffset.set(offset);
            }

            // Add new messages to messageList
            for (Message newMessage : newMessages) {
                messages.addElement(newMessage.getId(), newMessage);
            }
        }

        public boolean hasMoreMessages() {
            return hasMoreMessages.get();
        }
    }

    /**
     * All logic for loading existing conversation and messages of existing conversation and posting new message
     */
    private class ExistingConversationHelper {

        private Long mConversationId;
        private AtomicReference<Conversation> mConversation = new AtomicReference<>();

        public Long getConversationId() {
            return mConversationId;
        }

        public void setConversationId(Long conversationId) {
            this.mConversationId = conversationId;
        }

        public void setConversation(Conversation conversation) {
            this.mConversation.set(conversation);
        }

        public Conversation getConversation() {
            return mConversation.get();
        }

        public void reloadConversation() {
            if (mConversationId == null || mConversationId == 0) {
                throw new AssertionError("ConversationId must be valid to call this method!");
            }

            mData.getConversation(mConversationId, onLoadConversationListener);
        }
    }

    private void configureReplyBoxViewState() {
        ReplyBoxHelper.ReplyBoxViewState replyBoxViewState =
                mReplyBoxHelper.getReplyBoxVisibility(
                        !mNewConversationHelper.isConversationCreated(),
                        mMessengerPrefHelper.getEmail() != null,
                        mListHelper.getListPageState());
        switch (replyBoxViewState) {
            case DISABLED:
                // TODO: Have not handled for disabled state yet!
                break;

            case VISIBLE:
                mView.showReplyBox();
                break;

            case HIDDEN:
                mView.hideReplyBox();
                break;
        }
    }

    private List<BaseListItem> getOnboardingListItemViews() {

        String email = mMessengerPrefHelper.getEmail();

        // Helper automatically checks if for new conversation, for originally new conversation or originally existing conversation
        OnboardingHelper.OnboardingState onboardingState =
                mOnboardingHelper.getOnboardingState(
                        email != null,
                        mIsNewConversation);

        switch (onboardingState) {
            case ASK_FOR_EMAIL:
                return mOnboardingHelper.generateOnboardingMessagesAskingForEmail(new InputEmailListItem.OnClickSubmitListener() {
                    @Override
                    public void onClickSubmit(String email) {
                        mMessengerPrefHelper.setEmail(email);

                        configureReplyBoxViewState();

                        mView.focusOnReplyBox(); // Immediately after the Email is entered, the reply box should be focused upon for more information

                        mListHelper.displayList();
                    }
                });

            case PREFILLED_EMAIL:
                return mOnboardingHelper.generateOnboardingMessagesWithPrefilledEmail(email);

            default:
            case NONE:
                return mOnboardingHelper.generateOnboardingMessagesWithoutEmail();
        }
    }

    private void createNewConversation(String message, String clientId) {
        // This method should only be called (during onboarding) when a new conversation needs to be made
        mData.startNewConversation(
                mNewConversationHelper.getNewConversationBodyParams(mMessengerPrefHelper.getEmail(), message, clientId),
                onLoadConversationListener
        );
    }

    private void addNewMessage(long conversationId, String message, String clientId) {
        mData.postNewMessage(conversationId, message, clientId, onPostMessageListener);
    }

    /**
     * Call this method to mark the last message as read by the current customer. It involves making an API request.
     *
     * @param messages
     * @param conversationId
     * @return
     */
    private boolean markLastMessageAsRead(final List<Message> messages, final Long conversationId) {
        // If the argument is null, skip
        if (messages == null || messages.size() == 0 || conversationId == null) {
            return false;
        }

        final Long messageIdToBeMarkedRead = mMarkReadHelper.extractLastMessageId(messages);

        if (mMarkReadHelper.shouldMarkMessageAsRead(messageIdToBeMarkedRead)) {
            mMarkReadHelper.setLastMessageBeingMarkedRead(messageIdToBeMarkedRead);

            // Call API to mark message read
            mData.markMessageAsRead(conversationId,
                    messageIdToBeMarkedRead,
                    new MessageListContainerContract.OnMarkMessageAsReadListener() {
                        @Override
                        public void onSuccess() {
                            // Set last read message
                            mMarkReadHelper.setLastMessageMarkedReadSuccessfully(messageIdToBeMarkedRead);
                        }

                        @Override
                        public void onFailure(String message) {
                            mMarkReadHelper.setLastMessageBeingMarkedRead(0L);
                        }
                    }
            );
            return true;
        } else {
            return false;
        }
    }

}
