package com.kayako.sdk.android.k5.messenger.messagelistpage;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.FileAttachmentHelper;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.TypingViewHelper;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.UnsentMessage;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.EmptyListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputEmailListItem;
import com.kayako.sdk.android.k5.common.fragments.ListPageState;
import com.kayako.sdk.android.k5.common.fragments.OnScrollListListener;
import com.kayako.sdk.android.k5.common.utils.FailsafePollingHelper;
import com.kayako.sdk.android.k5.common.utils.file.FileAttachment;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.UserViewModel;
import com.kayako.sdk.android.k5.messenger.data.realtime.OnConversationChangeListener;
import com.kayako.sdk.android.k5.messenger.data.realtime.OnConversationClientActivityListener;
import com.kayako.sdk.android.k5.messenger.data.realtime.OnConversationMessagesChangeListener;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.AddReplyHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.ClientIdHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.ConversationHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.ConversationMessagesHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.ListHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.MarkReadHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.MessengerPrefHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.OffboardingHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.OnboardingHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.OptimisticSendingViewHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.RealtimeHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.ReplyBoxViewHelper;
import com.kayako.sdk.base.requester.AttachmentFile;
import com.kayako.sdk.messenger.conversation.Conversation;
import com.kayako.sdk.messenger.message.Message;
import com.kayako.sdk.messenger.message.MessageSourceType;
import com.kayako.sdk.messenger.message.PostMessageBodyParams;
import com.kayako.sdk.messenger.rating.PostRatingBodyParams;
import com.kayako.sdk.messenger.rating.PutRatingBodyParams;
import com.kayako.sdk.messenger.rating.Rating;

import java.util.ArrayList;
import java.util.Collections;
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

    // TODO: on attachment click - if failed to send, resend. if sent, open to view

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
    private RealtimeHelper mRealtimeHelper = new RealtimeHelper();
    private TypingViewHelper mTypingViewHelper = new TypingViewHelper();
    private FailsafePollingHelper mFailsafePollingHelper = new FailsafePollingHelper();
    private FileAttachmentHelper mFileAttachmentHelper = new FileAttachmentHelper();
    private OffboardingHelper mOffboardingHelper = new OffboardingHelper();

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
            mOptimisticMessageHelper.markAllAsSending(mOptimisticSendingViewCallback);
            mAddReplyHelper.resendReplies(mOnAddReplyCallback);
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

        mFileAttachmentHelper.onReset();

        this.mIsNewConversation = isNewConversation;

        mOptimisticMessageHelper.setUserAvatar(mMessengerPrefHelper.getAvatar());

        mConversationHelper.setIsConversationCreated(!isNewConversation);
        if (!isNewConversation) {
            mConversationHelper.setConversationId(conversationId);
        }

        mAddReplyHelper.setIsConversationCreated(mConversationHelper.isConversationCreated());

        mRealtimeHelper.addRealtimeListeners(onConversationChangeListener, onConversationClientActivityListener, onConversationMessagesChangeListener);

        reloadPage(true);
    }

    @Override
    public void closePage() {
        mRealtimeHelper.unsubscribeFromRealtimeChanges();
        mFailsafePollingHelper.stopPolling();
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

        onSendMessageReply(message);
    }

    @Override
    public void onTypeReply(String messageInProcess) {
        if (mConversationHelper.isConversationCreated() && mConversationHelper.getConversation() != null) {
            mRealtimeHelper.triggerTyping(mConversationHelper.getConversation(), messageInProcess);
        }
    }

    @Override
    public void onClickAddAttachment() {
        mView.openFilePickerForAttachments();
    }

    @Override
    public void onAttachmentAttached(FileAttachment file) {
        //TODO: Later, create a separate attachment preview screen so the user has the option to cancel-
        onSendAttachmentReply(file);
    }

    ////// USER ACTION METHODS //////

    private void resetVariables() {
        mMarkReadHelper = new MarkReadHelper();
        mMessengerPrefHelper = new MessengerPrefHelper();
        mMarkReadHelper = new MarkReadHelper();
        mOnboardingHelper = new OnboardingHelper();
        mConversationHelper = new ConversationHelper();
        mReplyBoxHelper = new ReplyBoxViewHelper();
        mListHelper = new ListHelper();
        mOptimisticMessageHelper = new OptimisticSendingViewHelper();
        mClientIdHelper = new ClientIdHelper();
        mConversationMessagesHelper = new ConversationMessagesHelper();
        mAddReplyHelper = new AddReplyHelper();
        mRealtimeHelper = new RealtimeHelper();
        mTypingViewHelper = new TypingViewHelper();
        mFailsafePollingHelper = new FailsafePollingHelper();
        mFileAttachmentHelper = new FileAttachmentHelper();
        mOffboardingHelper = new OffboardingHelper();
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

    private void onSendMessageReply(String message) {
        String clientId = mClientIdHelper.generateClientId(); // GENERATE Client Id

        mOptimisticMessageHelper.addOptimisitcMessageView(message, clientId, mOptimisticSendingViewCallback);

        // Covers both adding of new messages and new conversation
        mAddReplyHelper.addNewReply(
                message,
                clientId,
                mOnAddReplyCallback);

        // On sending reply, scroll to bottom of list
        mView.scrollToBottomOfList();
    }

    private void onSendAttachmentReply(FileAttachment fileAttachment) {
        String clientId = mClientIdHelper.generateClientId(); // GENERATE Client Id

        mOptimisticMessageHelper.addOptimisitcMessageView(fileAttachment, clientId, mOptimisticSendingViewCallback);

        mFileAttachmentHelper.onSendingUnsentMessage(clientId, fileAttachment.getFile());

        // Covers both adding of new messages and new conversation
        mAddReplyHelper.addNewReply(
                fileAttachment,
                clientId,
                mOnAddReplyCallback);

        // On sending reply, scroll to bottom of list
        mView.scrollToBottomOfList();
    }

    private OptimisticSendingViewHelper.OptimisticSendingViewCallback mOptimisticSendingViewCallback = new OptimisticSendingViewHelper.OptimisticSendingViewCallback() {
        @Override
        public void onRefreshListView() {
            displayList();
        }

        @Override
        public void onResendReply(UnsentMessage unsentMessage) {
            if (unsentMessage.getAttachment() == null) {
                onSendMessageReply(unsentMessage.getMessage());
            } else {
                onSendAttachmentReply(unsentMessage.getAttachment());
            }
        }
    };

    private AddReplyHelper.OnAddReplyCallback mOnAddReplyCallback = new AddReplyHelper.OnAddReplyCallback() {
        @Override
        public void onCreateConversation(UnsentMessage unsentMessage) {
            createNewConversation(unsentMessage.getMessage(), unsentMessage.getClientId());
        }

        @Override
        public void onAddMessage(UnsentMessage unsentMessage) {
            if (unsentMessage.getAttachment() != null) {
                mFileAttachmentHelper.onSuccessfulSendingOfMessage(unsentMessage.getClientId());
                addNewMessage(mConversationHelper.getConversationId(), unsentMessage.getAttachment(), unsentMessage.getClientId());
            } else {
                addNewMessage(mConversationHelper.getConversationId(), unsentMessage.getMessage(), unsentMessage.getClientId());
            }
        }
    };

    private OffboardingHelper.OffboardingHelperViewCallback mOffboardingHelperViewCallback = new OffboardingHelper.OffboardingHelperViewCallback() {
        @Override
        public void onRefreshListView() {
            displayList();
        }

        @Override
        public void onLoadRatings() {
            if (mConversationHelper.isConversationCreated()) {
                getConversationRating(mConversationHelper.getConversationId());
            } else {
                throw new IllegalStateException("This method should never be called before the conversation is created!");
            }
        }

        @Override
        public void onAddRating(Rating.SCORE ratingScore) {
            if (mConversationHelper.isConversationCreated()) {
                addConversationRating(mConversationHelper.getConversationId(), ratingScore);
            } else {
                throw new IllegalStateException("This method should never be called before the conversation is created!");
            }
        }

        @Override
        public void onAddFeedback(long ratingId, Rating.SCORE score, String message) {
            if (mConversationHelper.isConversationCreated()) {
                updateConversationRating(mConversationHelper.getConversationId(), ratingId, score, message);
            } else {
                throw new IllegalStateException("This method should never be called before the conversation is created!");
            }

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

        // footer items
        allListItems.addAll(mTypingViewHelper.getTypingViews());

        // Offloading items
        allListItems.addAll(getOffboardingListItemViews());

        // Add space at end
        allListItems.add(new EmptyListItem());
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
                        // TODO: THIS IS TEMPORARY - WAITING FOR API TO USE STATES to check if conversation is closed, complete, etc
                        mConversationHelper.getConversation() == null || mConversationHelper.getConversation().isCompleted() == null
                                ? false
                                : mConversationHelper.getConversation().isCompleted(),
                        // TODO: THIS IS TEMPORARY - WAITING FOR API TO USE STATES to check if conversation is closed, complete, etc
                        false,
                        mListHelper.getListPageState());
        switch (replyBoxViewState) {

            case DISABLED:
                // TODO: Have not handled for disabled state yet!
                break;

            case VISIBLE:
                mView.showReplyBox();
                configureAttachmentButton();
                break;

            case HIDDEN:
                mView.hideReplyBox();
                break;
        }
    }

    private void configureAttachmentButton() {
        if (mFileAttachmentHelper.getAttachmentButtonVisibility(mConversationHelper.isConversationCreated())) {
            mView.setAttachmentButtonVisibilityInReplyBox(true);
        } else {
            mView.setAttachmentButtonVisibilityInReplyBox(false);
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

    private List<BaseListItem> getOffboardingListItemViews() {
        if (mConversationHelper.getConversation() != null) {
            final boolean isCompleted = true; // TODO: FOR TESTING, forcing = true
            // TODO: THIS IS TEMPORARY - WAITING FOR API TO USE STATES to check if conversation is closed, complete, etc
//                    mConversationHelper.getConversation().isCompleted() == null
//                    ? false
//                    : mConversationHelper.getConversation().isCompleted();

            final String nameOfAgent = mConversationHelper.getConversation().getLastAgentReplier() == null
                    ? MessengerPref.getInstance().getBrandName()
                    : mConversationHelper.getConversation().getLastAgentReplier().getFullName();

            return mOffboardingHelper.getOffboardingListItems(
                    nameOfAgent,
                    isCompleted,
                    mOffboardingHelperViewCallback
            );
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    ////// API / REALTIME / DATA CHANGE CALLBACKS //////

    /**
     * Method called in any callback that loads the latest conversation data - create new converastion, load existing conversation
     *
     * @param conversation
     */
    private void onLoadConversation(Conversation conversation) {
        Conversation lastConversationValue = mConversationHelper.getConversation();

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


        mRealtimeHelper.subscribeForRealtimeChanges(conversation);
        mFailsafePollingHelper.startPolling(new FailsafePollingHelper.PollingListener() {
            @Override
            public void onPoll() {
                reloadLatestMessages();
            }
        });

        // Reload the messages of existing conversation if a new message is added
        if (lastConversationValue == null // first time load
                || !lastConversationValue.getLastRepliedAt().equals(conversation.getLastRepliedAt())) {
            reloadLatestMessages();
        }

        // Load rating when conversation is loaded for first time
        mOffboardingHelper.onLoadConversation(mOffboardingHelperViewCallback);
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
            // Indicate the request failed
            mAddReplyHelper.onFailedSendingOfConversation(clientId);

            // Mark all optimistic views as failed
            mOptimisticMessageHelper.markAllAsFailed(mOptimisticSendingViewCallback);
        }
    };

    private MessageListContainerContract.OnLoadMessagesListener onLoadMessagesListener = new MessageListContainerContract.OnLoadMessagesListener() {
        @Override
        public void onSuccess(List<Message> messageList, int offset) {
            if (offset != 0) {
                mView.hideLoadMoreView();
            }

            boolean scrollToBottom = !mConversationMessagesHelper.hasLoadedMessagesBefore(); // scroll to bottom if messages are being loaded for the first time
            // TODO: Scroll to bottom if near to bottom

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

            // Show error view only if there are no messages to show
            if (mConversationMessagesHelper.getSize() == 0) {
                mView.showErrorViewInMessageListingView();
            }
        }
    };

    private MessageListContainerContract.PostNewMessageCallback onPostMessageListener = new MessageListContainerContract.PostNewMessageCallback() {
        @Override
        public void onSuccess(Message message) {
            mAddReplyHelper.onSuccessfulSendingOfMessage(message.getClientId(), mOnAddReplyCallback);
            mMarkReadHelper.disableOriginalLastMessageMarked(); // Should be called before any list rendering is done

            if (!mConversationMessagesHelper.exists(message.getId())) { // Because of realtime updates, only reload latest messages if not existing
                reloadLatestMessages();
            }
        }

        @Override
        public void onFailure(final String clientId) {
            // Indicate the request failed
            mAddReplyHelper.onFailedSendingOfMessage(clientId);

            // Mark all optimistic views as failed
            // Now that AddReplyHelper ensures only one message is sent at a time, all pending items should be marked as failed too
            // TODO: Mark only the client id as failed? ANd others as not-sent?
            mOptimisticMessageHelper.markAllAsFailed(mOptimisticSendingViewCallback);
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

    private OnConversationChangeListener onConversationChangeListener = new OnConversationChangeListener() {
        @Override
        public void onChange(Conversation conversation) {
            onLoadConversation(conversation);
        }
    };

    private OnConversationClientActivityListener onConversationClientActivityListener = new OnConversationClientActivityListener() {
        @Override
        public void onTyping(long conversationId, UserViewModel userTyping, boolean isTyping) {
            if (conversationId != mConversationHelper.getConversationId()) {
                return;
            }

            boolean isUpdated = mTypingViewHelper.setTypingStatus(userTyping, isTyping);
            if (isUpdated) { // Refresh UI only if there are new changes - prevent mulitple ui revisions
                displayList();
            }
        }
    };

    private OnConversationMessagesChangeListener onConversationMessagesChangeListener = new OnConversationMessagesChangeListener() {
        @Override
        public void onNewMessage(long conversationId, long messageId) {
            if (conversationId != mConversationHelper.getConversationId()) {
                return;
            }

            if (!mConversationMessagesHelper.exists(messageId)) { // Load latest messages only if not loaded before
                reloadLatestMessages();
            }
        }

        @Override
        public void onUpdateMessage(long conversationId, Message message) {
            if (conversationId != mConversationHelper.getConversationId()) {
                return;
            }

            if (mConversationMessagesHelper.exists(message.getId())) { // Update existing message
                mConversationMessagesHelper.updateMessage(message);
                displayList(); // update view with new message data
            } else { // CHANGE_POST is sometimes called for new posts instead of NEW_POST. If ever the case, ensure onNewMessage is also called!
                onNewMessage(conversationId, message.getId());
            }
        }
    };

    private MessageListContainerContract.OnLoadRatingsListener onLoadRatingsListener = new MessageListContainerContract.OnLoadRatingsListener() {
        @Override
        public void onSuccess(List<Rating> ratings) {
            mOffboardingHelper.onLoadRatings(ratings, mOffboardingHelperViewCallback);
        }

        @Override
        public void onFailure(String message) {
        }
    };

    private MessageListContainerContract.OnUpdateRatingListener onUpdateRatingListener = new MessageListContainerContract.OnUpdateRatingListener() {
        @Override
        public void onSuccess(Rating rating) {
            mOffboardingHelper.onUpdateRating(rating, mOffboardingHelperViewCallback);
        }

        @Override
        public void onFailure(String message) {
        }
    };


    ////// API CALLING METHODS //////

    private void reloadLatestMessages() {
        if (!mConversationHelper.isConversationCreated()) {
            throw new IllegalStateException("Method should only be called once conversation is created");
        }

        Long conversationId = mConversationHelper.getConversationId();

        mData.getMessages(onLoadMessagesListener, conversationId, 0, mConversationMessagesHelper.getLimit());
    }

    public void loadNextMessages() {
        if (!mConversationHelper.isConversationCreated()) {
            throw new IllegalStateException("Method should only be called once conversation is created");
        }

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
        mData.postNewMessage(conversationId,
                new PostMessageBodyParams(
                        message,
                        MessageSourceType.MESSENGER,
                        clientId
                ),
                clientId,
                onPostMessageListener);
    }

    private void addNewMessage(long conversationId, FileAttachment fileAttachment, String clientId) {
        List<AttachmentFile> attachmentFiles = new ArrayList<>();
        attachmentFiles.add(new AttachmentFile(
                fileAttachment.getFile(),
                fileAttachment.getMimeType(),
                fileAttachment.getName()
        ));

        mData.postNewMessage(conversationId,
                new PostMessageBodyParams(
                        fileAttachment.getName(), // TODO: Find a way to send ONLY attachment without title
                        MessageSourceType.MESSENGER,
                        clientId,
                        attachmentFiles
                ),
                clientId,
                onPostMessageListener);
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

    private void getConversationRating(long conversationId) {
        mData.getConversationRatings(conversationId, onLoadRatingsListener);
    }

    private void addConversationRating(long conversationId, Rating.SCORE score) {
        mData.addConversationRating(
                conversationId,
                new PostRatingBodyParams(score),
                onUpdateRatingListener);
    }

    private void updateConversationRating(long conversationId, long ratingId, Rating.SCORE score, String feedback) {
        mData.updateConversationRating(
                conversationId,
                ratingId,
                new PutRatingBodyParams(score, feedback),
                onUpdateRatingListener);
    }

}
