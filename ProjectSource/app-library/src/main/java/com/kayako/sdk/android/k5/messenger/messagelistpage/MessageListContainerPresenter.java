package com.kayako.sdk.android.k5.messenger.messagelistpage;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.UnsentMessage;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputEmailListItem;
import com.kayako.sdk.android.k5.common.fragments.ListPageState;
import com.kayako.sdk.android.k5.common.fragments.OnScrollListListener;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.AddReplyHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.ClientIdHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.ConversationHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.ConversationMessagesHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.ListHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.MarkReadHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.MessengerPrefHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.OnboardingHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.OptimisticSendingViewHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.ReplyBoxViewHelper;
import com.kayako.sdk.messenger.conversation.Conversation;
import com.kayako.sdk.messenger.message.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private ConversationHelper mConversationHelper = new ConversationHelper();
    private ReplyBoxViewHelper mReplyBoxHelper = new ReplyBoxViewHelper();
    private ListHelper mListHelper = new ListHelper();
    private OptimisticSendingViewHelper mOptimisticMessageHelper = new OptimisticSendingViewHelper();
    private ClientIdHelper mClientIdHelper = new ClientIdHelper();
    private ConversationMessagesHelper mConversationMessagesHelper = new ConversationMessagesHelper();
    private AddReplyHelper mAddReplyHelper = new AddReplyHelper();

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
        if (mOptimisticMessageHelper.isOptimisticMessage(messageData)
                && mOptimisticMessageHelper.isFailedToSendMessage(messageData)) {
            // Retry all optimistic messages
            mOptimisticMessageHelper.resendAllOptimisticMessages(optimisticSendingViewCallback);
        }
    }

    @Override
    public void onLoadMoreItems() {
        if (mConversationHelper.isConversationCreated()) {
            loadNextMessages();
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

        mOptimisticMessageHelper.setUserAvatar(mMessengerPrefHelper.getAvatar());

        mConversationHelper.setIsConversationCreated(!isNewConversation);
        if (!isNewConversation) {
            mConversationHelper.setConversationId(conversationId);
        }

        mAddReplyHelper.setIsConversationCreated(mConversationHelper.isConversationCreated());

        reloadPage(true);
    }

    @Override
    public void onClickSendInReplyView(String message) {
        // Collapse toolbar once a message has been sent!
        mView.collapseToolbar();

        // Assertion: If it's existing conversation, then mConversationId can not be null or invalid
        if (mConversationHelper.isConversationCreated() &&
                (mConversationHelper.getConversationId() == null
                        || mConversationHelper.getConversationId() == 0)) {
            throw new AssertionError("If it is NOT a new conversation, conversation id should NOT be null");
        }

        onSendReply(message);
    }

    ////// USER ACTION METHODS //////

    private void resetVariables() {
        mMarkReadHelper = new MarkReadHelper();
        // TODO: Others?
    }

    private void reloadPage(boolean resetView) {
        if (resetView) {
            mView.showLoadingViewInMessageListingView();
        }

        if (!mConversationHelper.isConversationCreated()) {
            // Show expanded toolbar when it's a new conversation
            mView.expandToolbar();

            // Load view
            displayList();
        } else {
            reloadConversation();
            reloadLatestMessages();
        }

        configureReplyBoxViewState();
    }

    private void onSendReply(String message) {
        String clientId = mClientIdHelper.generateClientId(); // GENERATE Client Id

        mOptimisticMessageHelper.addOptimisitcMessageView(message, clientId, optimisticSendingViewCallback);

        // Covers both adding of new messages and new conversation
        mAddReplyHelper.addNewReply(
                message,
                clientId,
                mOnAddReplyCallback);
    }

    private OptimisticSendingViewHelper.OptimisticSendingViewCallback optimisticSendingViewCallback = new OptimisticSendingViewHelper.OptimisticSendingViewCallback() {
        @Override
        public void onRefreshListView() {
            displayList();
        }

        @Override
        public void onResendReply(UnsentMessage unsentMessage) {
            onSendReply(unsentMessage.getMessage());
        }
    };

    private AddReplyHelper.OnAddReplyCallback mOnAddReplyCallback = new AddReplyHelper.OnAddReplyCallback() {
        @Override
        public void onCreateConversation(UnsentMessage unsentMessage) {
            createNewConversation(unsentMessage.getMessage(), unsentMessage.getClientId());
        }

        @Override
        public void onAddMessage(UnsentMessage unsentMessage) {
            addNewMessage(mConversationHelper.getConversationId(), unsentMessage.getMessage(), unsentMessage.getClientId());

        }
    };

    ////// VIEW MODIFYING METHODS //////

    private List<BaseListItem> generateListItems() {
        List<BaseListItem> allListItems = new ArrayList<>();

        List<BaseListItem> onboardingItems = getOnboardingListItemViews();
        List<BaseListItem> optimisticSendingItems = mOptimisticMessageHelper.getOptimisticSendingListItems();

        allListItems.addAll(onboardingItems);

        // If existing conversation, load messages too - inbetween the onboarding items and the optimistic sending items
        if (mConversationHelper.isConversationCreated()) {
            Long userId = mMessengerPrefHelper.getUserId();
            if (userId == null || userId == 0) {
                throw new IllegalStateException("User Id should be known if conversation is created!");
            }

            allListItems.addAll(
                    mListHelper.getMessageAsListItemViews(
                            mConversationMessagesHelper.getMessages(),
                            mMarkReadHelper.getOriginalLastMessageMarkedRead(),
                            userId
                    )
            );
        }

        allListItems.addAll(optimisticSendingItems);

        return allListItems;
    }

    private void displayList() {
        List<BaseListItem> allListItems = generateListItems();

        if (allListItems.size() != 0) {
            mView.setupListInMessageListingView(allListItems);

            if (!mConversationHelper.isConversationCreated()) {
                mView.setHasMoreItems(false);
            } else {
                mView.setHasMoreItems(mConversationMessagesHelper.hasMoreMessages());
            }
        } else {
            // There should never be an empty state view shown! Leave it blank inviting customer to add stuff.
            mView.setupListInMessageListingView(allListItems); // Set empty list to show as blank
        }
    }

    private void configureReplyBoxViewState() {
        ReplyBoxViewHelper.ReplyBoxViewState replyBoxViewState =
                mReplyBoxHelper.getReplyBoxVisibility(
                        !mConversationHelper.isConversationCreated(),
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

                        displayList();
                    }
                });

            case PREFILLED_EMAIL:
                return mOnboardingHelper.generateOnboardingMessagesWithPrefilledEmail(email);

            default:
            case NONE:
                return mOnboardingHelper.generateOnboardingMessagesWithoutEmail();
        }
    }

    ////// API CALLBACKS //////
    private void onLoadConversation(Conversation conversation) {
        // Update existing Conversation
        mConversationHelper.setIsConversationCreated(true);
        mConversationHelper.setConversationId(conversation.getId());
        mConversationHelper.setConversation(conversation);

        mMarkReadHelper.setOriginalLastMessageMarkedReadIfNotSetBefore(conversation.getReadMarker() == null ? 0 : conversation.getReadMarker().getLastReadPostId());

        // Update user details (creator of new conversation) - This has been done EVERY TIME to ensure that even if the current userid is lost due to any reason, a new conversation will get it back! - eg: using the same fingerprint for multiple users
        mMessengerPrefHelper
                .setUserInfo(
                        conversation.getCreator().getId(), // Assuming the current user is always the creator of conversation,
                        conversation.getCreator().getFullName(),
                        conversation.getCreator().getAvatarUrl()
                );

        // Reload the messages of existing conversation
        reloadLatestMessages();
    }

    private MessageListContainerContract.OnLoadConversationListener onLoadConversationListener = new MessageListContainerContract.OnLoadConversationListener() {
        @Override
        public void onSuccess(Conversation conversation) {
            onLoadConversation(conversation);
        }

        @Override
        public void onFailure(String message) {
            // TODO: Show error mesasage?
        }
    };

    private MessageListContainerContract.PostConversationCallback postConversationCallback = new MessageListContainerContract.PostConversationCallback() {
        @Override
        public void onSuccess(String clientId, Conversation conversation) {
            if (!(mIsNewConversation && !mConversationHelper.isConversationCreated())) {
                throw new IllegalStateException("The conditions should be true at this point!");
            }

            // IMPORTANT: onLoadConversation() should be called FIRST, then new conversation properties should be set
            // This is required for all dependencies of mConversationHelper and mMessengerPrefHelper
            onLoadConversation(conversation);

            // Update the user avatar (which may have been null before)
            mOptimisticMessageHelper.setUserAvatar(mMessengerPrefHelper.getAvatar());

            // Ensure task removed from queue and other messages get added
            mAddReplyHelper.onSuccessfulCreationOfConversation(clientId, mOnAddReplyCallback);
        }

        @Override
        public void onFailure(String clientId, String message) {
            // TODO: Show error mesasage?
        }
    };

    private MessageListContainerContract.OnLoadMessagesListener onLoadMessagesListener = new MessageListContainerContract.OnLoadMessagesListener() {
        @Override
        public void onSuccess(List<Message> messageList, int offset) {
            if (offset != 0) {
                mView.hideLoadMoreView();
            }

            boolean scrollToBottom = !mConversationMessagesHelper.hasLoadedMessagesBefore(); // scroll to bottom if messages are being loaded for the first time
            mConversationMessagesHelper.onLoadNextMessages(messageList, offset);

            // Remove optimisitc sending items - to be done before display list
            mOptimisticMessageHelper.removeOptimisticMessagesThatIsSuccessfullySentAndDisplayed(messageList);

            // Display list once necessary changes to list data are made above
            displayList();

            // Scroll to bottom after list is displayed
            if (scrollToBottom) {
                mView.scrollToBottomOfList();
            }

            // Once messages have been loaded AND displayed in view, mark the last message as read
            markLastMessageAsRead(
                    messageList,
                    mConversationHelper.getConversationId());
        }

        @Override
        public void onFailure(String message) {
            // TODO: Add conditions to show toast if load-more and show error view if lastSuccessfulOffset=0
            mView.showErrorViewInMessageListingView();
        }
    };

    private MessageListContainerContract.PostNewMessageCallback onPostMessageListener = new MessageListContainerContract.PostNewMessageCallback() {
        @Override
        public void onSuccess(Message message) {
            mAddReplyHelper.onSuccessfulSendingOfMessage(message.getClientId(), mOnAddReplyCallback);
            mMarkReadHelper.disableOriginalLastMessageMarked(); // Should be called before any list rendering is done
            reloadLatestMessages();
        }

        @Override
        public void onFailure(final String clientId) {
            // Now that AddReplyHelper ensures only one message is sent at a time, all pending items should be marked as failed too
            // TODO: Mark only the client id as failed? ANd others as not-sent?
            mOptimisticMessageHelper.markAllAsFailed(optimisticSendingViewCallback);
        }
    };

    private MessageListContainerContract.OnMarkMessageAsReadListener onMarkMessageAsReadListener = new MessageListContainerContract.OnMarkMessageAsReadListener() {
        @Override
        public void onSuccess(long messageId) {
            mMarkReadHelper.setLastMessageMarkedReadSuccessfully(messageId);

        }

        @Override
        public void onFailure(String message) {
            mMarkReadHelper.setLastMessageBeingMarkedRead(0L); // TODO: Confirm logic here?
        }
    };

    ////// API CALLING METHODS //////

    private void reloadLatestMessages() {
        Long conversationId = mConversationHelper.getConversationId();

        mData.getMessages(onLoadMessagesListener, conversationId, 0, mConversationMessagesHelper.getLimit());
    }

    public void loadNextMessages() {
        Long conversationId = mConversationHelper.getConversationId();

        mView.showLoadMoreView();
        mData.getMessages(onLoadMessagesListener,
                conversationId,
                mConversationMessagesHelper.getLastSuccessfulOffset() + mConversationMessagesHelper.getLimit(),
                mConversationMessagesHelper.getLimit());
    }

    public void reloadConversation() {
        Long conversationId = mConversationHelper.getConversationId();

        if (conversationId == null || conversationId == 0) {
            throw new AssertionError("ConversationId must be valid to call this method!");
        }

        mData.getConversation(conversationId, onLoadConversationListener);
    }

    private void createNewConversation(String message, String clientId) {
        // This method should only be called (during onboarding) when a new conversation needs to be made
        mData.startNewConversation(
                mConversationHelper.getNewConversationBodyParams(mMessengerPrefHelper.getEmail(), message, clientId),
                postConversationCallback
        );
    }

    private void addNewMessage(long conversationId, String message, String clientId) {
        mData.postNewMessage(conversationId, message, clientId, onPostMessageListener);
    }

    private boolean markLastMessageAsRead(final List<Message> messages, final Long conversationId) {
        // Call this method to mark the last message as read by the current customer. It involves making an API request.

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
                    onMarkMessageAsReadListener
            );
            return true;
        } else {
            return false;
        }
    }

}
