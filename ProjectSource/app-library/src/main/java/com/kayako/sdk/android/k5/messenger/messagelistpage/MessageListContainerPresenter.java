package com.kayako.sdk.android.k5.messenger.messagelistpage;

import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.DataItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.DataItemHelper;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.DeliveryIndicatorHelper;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.OptimisticSendingHelper;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.UniqueResourceList;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.UserDecorationHelper;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.BotMessageListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputEmailListItem;
import com.kayako.sdk.android.k5.common.fragments.ListPageState;
import com.kayako.sdk.android.k5.common.fragments.OnScrollListListener;
import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.android.k5.core.MessengerUserPref;
import com.kayako.sdk.messenger.conversation.Conversation;
import com.kayako.sdk.messenger.conversation.PostConversationBodyParams;
import com.kayako.sdk.messenger.message.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Terms used:
 * <p>
 * New Conversation : a conversation that is yet to be created via APIs
 * Existing Conversation : a conversation that has been created via APIs
 */
public class MessageListContainerPresenter implements MessageListContainerContract.Presenter {

    private MessageListContainerContract.View mView;
    private MessageListContainerContract.Data mData;

    private MessengerPrefHelper mMessengerPrefHelper = new MessengerPrefHelper();
    private MarkReadHelper mMarkReadHelper = new MarkReadHelper();
    private OnboardingHelper mOnboardingHelper = new OnboardingHelper();
    private NewConversationHelper mNewConversationHelper = new NewConversationHelper();
    private ExistingConversationHelper mExistingConversationHelper = new ExistingConversationHelper();
    private ReplyBoxHelper mReplyBoxHelper = new ReplyBoxHelper();
    private ListHelper mListHelper = new ListHelper();
    private OptimisticMessageHelper mOptimisticMessageHelper = new OptimisticMessageHelper();
    private ClientIdHelper mClientIdHelper = new ClientIdHelper();
    private ExistingMessagesHelper mExistingMessagesHelper = new ExistingMessagesHelper();

    // private UniqueResourceList<Message> mMessageList = new UniqueResourceList<>();
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
        mReplyBoxHelper.configureReplyBoxVisibility(state);
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
            mOptimisticMessageHelper.resendOptimisticMessage(messageData);
        }
    }

    @Override
    public void onLoadMoreItems() {
        if (!mNewConversationHelper.isNewConversation()) {
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

        mOnboardingHelper.setWasNewConversation(isNewConversation);
        mNewConversationHelper.setIsNewConversation(isNewConversation);
        mExistingConversationHelper.setConversationId(conversationId);

        reloadPage(true);
    }

    @Override
    public void onClickSendInReplyView(String message) {
        // TODO: Optimisitc Sending? Show it in messagelist?
        // TODO: Should both MessageListContainerView and MessageListView determine if it's a new conversation separately?

        // Collapse toolbar once a message has been sent!
        mView.collapseToolbar();

        // Assertion: If it's existing conversation, then conversationId can not be null or invalid
        if (!mNewConversationHelper.isNewConversation() &&
                (mExistingConversationHelper.getConversationId() == null
                        || mExistingConversationHelper.getConversationId() == 0)) {
            throw new AssertionError("If it is NOT a new conversation, conversation id should NOT be null");
        }

        onSendReply(message);
    }

    private void onSendReply(String message) {
        String clientId = mClientIdHelper.generateClientId(); // GENERATE Client Id

        (mOptimisticMessageHelper).onClickSendInReplyView(message, clientId);

        if (mNewConversationHelper.isNewConversation()) {
            (mNewConversationHelper).onClickSendInReplyView(message, clientId);
        } else {
            (mExistingConversationHelper).onClickSendInReplyView(message, clientId);
        }
    }

    private void resetVariables() {
        mMarkReadHelper = new MarkReadHelper();
    }

    private void reloadPage(boolean resetView) {
        if (resetView) {
            mView.showLoadingViewInMessageListingView();
        }

        if (mNewConversationHelper.isNewConversation()) {
            // Show expanded toolbar when it's a new conversation
            mView.expandToolbar();

            // Load view
            mListHelper.displayList();
        } else {
            mExistingConversationHelper.reloadConversation();
            mExistingMessagesHelper.reloadLatestMessages(mExistingConversationHelper.getConversationId());
        }

        mReplyBoxHelper.configureReplyBoxVisibility(mListHelper.getListPageState());
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
            if (mNewConversationHelper.isNewConversation()) {
                mNewConversationHelper.setIsNewConversation(false);
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
            if (mExistingMessagesHelper.getLastSuccessfulOffset() == 0) { // don't only rely on offset (which can mean reloading recent messages)
                mView.scrollToBottomOfList();
            }

            mExistingMessagesHelper.onLoadNextMessages(messageList, offset);

            // Remove optimisitc sending items
            mOptimisticMessageHelper.removeOptimisticMessagesThatSuccessfullyGotSent(messageList);

            // Display list once necessary changes to list data are made above
            mListHelper.displayList();

            // Once messages have been loaded AND displayed in view, mark the last message as read
            mMarkReadHelper.markLastMessageAsRead(
                    mMarkReadHelper.extractLastMessageId(messageList),
                    mExistingConversationHelper.getConversationId());
        }

        @Override
        public void onFailure(String message) {
            // TODO: Add conditions to show toast if load-more and show error view if lastSuccessfulOffset=0
            mView.showErrorViewInMessageListingView();
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
            List<BaseListItem> onboardingItems = mOnboardingHelper.getOnboardingListItemViews(); // Helper automatically checks if for new conversation, for originally new conversation or originally existing conversation
            List<BaseListItem> optimisticSendingItems = mOptimisticMessageHelper.getOptimisticSendingListItems();

            if (mNewConversationHelper.isNewConversation()) {
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
                                DeliveryIndicatorHelper.getDeliveryIndicator(message), // TODO:
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
     * All logic involving optimistic sending
     */
    private class OptimisticMessageHelper implements OnClickSendReplyListener {
        // TODO: Handle failure states and offer user to RESEND message

        private OptimisticSendingHelper optimisticSendingHelper;

        public OptimisticMessageHelper() {
            optimisticSendingHelper = new OptimisticSendingHelper(mMessengerPrefHelper.getAvatar());
        }

        public List<BaseListItem> getOptimisticSendingListItems() {
            return optimisticSendingHelper.getOptimisticMessages();
        }

        public void removeOptimisticMessagesThatSuccessfullyGotSent(List<Message> newMessages) {
            for (Message message : newMessages) {
                optimisticSendingHelper.removeOptimisticMessage(message.getClientId());
            }
        }

        @Override
        public void onClickSendInReplyView(String message, String clientId) {
            optimisticSendingHelper.addOptimisticMessage(
                    DeliveryIndicatorHelper.ClientDeliveryStatus.SENDING,
                    message,
                    clientId);

            mListHelper.displayList(); // Display list with optimistic sending views
        }

        public boolean isOptimisticMessage(Map<String, Object> messageData) {
            return optimisticSendingHelper.isOptimisticMessage(messageData);
        }

        public void resendOptimisticMessage(Map<String, Object> messageData) {
            String clientId = optimisticSendingHelper.extractClientId(messageData);
            OptimisticSendingHelper.OptimisticMessage optimisticMessage = optimisticSendingHelper.getOptimisticMessage(clientId);

            if (optimisticMessage.getDeliveryStatus() == DeliveryIndicatorHelper.ClientDeliveryStatus.FAILED_TO_SEND) {
                optimisticSendingHelper.removeOptimisticMessage(clientId);
                if (optimisticMessage.getMessage() != null) {
                    MessageListContainerPresenter.this.onSendReply(optimisticMessage.getMessage());
                }

                mListHelper.displayList();

            } else {
                // Do nothing if not FAILED_TO_SEND state
            }
        }

        public void markAsFailed(String clientId) {
            optimisticSendingHelper.markOptimisticMessageAsFailed(clientId);

            mListHelper.displayList(); // Now that optimisitic values are changed, refresh view
        }
    }

    /**
     * All logic involved in marking a message as read
     */
    private class MarkReadHelper {

        private AtomicBoolean mUseOriginalLastMessage = new AtomicBoolean(true);
        private AtomicLong mOriginalLastMessageMarkedRead = new AtomicLong(0); // the getOriginalLastMessageMarkedReadlatest message that was read (only when this page is opened, should not update later!)
        private AtomicLong mLastMessageMarkedReadSuccessfully = new AtomicLong(0); // the latest message that has been read (constantly updates as the latest messages gets loaded and read)
        private AtomicLong mLastMessageBeingMarkedRead = new AtomicLong(0); // the latest message that is being marked read (created to prevent multiple network requests being made for same id)

        // TODO: When KRE is implemented, ensure the messages and conversartion is not reloaded unless there is a change made!
        // This should be done because the app is already making requests on its own

        /**
         * Call this to set the mLastMessageMarkedReadSuccessfully.
         * This method only sets the value if it hasn't been set before, thus ensuring only the original is saved
         *
         * @param lastMessageMarkedRead
         */
        public void setOriginalLastMessageMarkedReadIfNotSetBefore(Long lastMessageMarkedRead) {
            // Set the original last message read (This will be the first non-zero value set)
            if (mOriginalLastMessageMarkedRead.get() == 0) {
                mOriginalLastMessageMarkedRead.set(lastMessageMarkedRead);
            }
        }

        public long getOriginalLastMessageMarkedRead() {
            // Save the unread marker so that it doesn't go away when conversation is reloaded.
            // Instead, it should only be reset when the user leaves the conversation.
            // Code should rely on this presaved value, and not the conversation variable to check read marker status
            if (mUseOriginalLastMessage.get()) {
                return mOriginalLastMessageMarkedRead.get();
            } else {
                return 0; // If original last message is not used, then no read marker should be shown!
            }
        }

        /**
         * This method should be called when the originalLastMessageRead is no longer needed.
         * Example: When a reply is made by the user, the "new messages" separator should disappear.
         *
         * @return
         */
        public void disableOriginalLastMessageMarked() {
            mUseOriginalLastMessage.set(false);
        }

        /**
         * Call this method to mark the last message as read by the current customer. It involves making an API request.
         *
         * @param lastMessageMarkedRead
         * @param conversationId
         * @return
         */
        public boolean markLastMessageAsRead(final Long lastMessageMarkedRead, final Long conversationId) {
            // If the argument is null, skip
            if (lastMessageMarkedRead == null || conversationId == null) {
                return false;
            }

            // If an earlier message (say id=120) needs to be marked as read, when the latest message is already marked read (id=150), then skip.
            if (lastMessageMarkedRead < mLastMessageMarkedReadSuccessfully.get() // Prevents redundant calls as we load more messages
                    || lastMessageMarkedRead < mOriginalLastMessageMarkedRead.get()) { // Prevents marking read an earlier message than the message in conversation resource read marker node
                return false;
            }

            if (this.mLastMessageMarkedReadSuccessfully.get() == lastMessageMarkedRead // Prevents redundant calls as we mark the same message as read again and again (while
                    || lastMessageMarkedRead == mLastMessageBeingMarkedRead.get()) { // Prevents redundant calls as we mark the same message as read again and again (while it is being marked)
                return false;
            }


            // Call API to mark message read
            mLastMessageBeingMarkedRead.set(lastMessageMarkedRead);
            mData.markMessageAsRead(conversationId,
                    lastMessageMarkedRead,
                    new MessageListContainerContract.OnMarkMessageAsReadListener() {
                        @Override
                        public void onSuccess() {
                            // Set last read message
                            MarkReadHelper.this.mLastMessageMarkedReadSuccessfully.set(lastMessageMarkedRead);
                        }

                        @Override
                        public void onFailure(String message) {
                        }
                    }
            );
            return true;
        }

        public Long extractLastMessageId(List<Message> messageList) {
            if (messageList == null || messageList.size() == 0) {
                return null;
            }

            /*
            There's a lot of order reversing going on in the code which may cause issues in this snippet
            Therefore, to be absolutely certain the last element (greatest id) is selected, messages at the extremes are collected and the largest id is returned.
            This is based on the assumption that the items are sorted (which it should be!)
             */
            Message firstMessage = messageList.get(0);
            Message lastMessage = messageList.get(messageList.size() - 1);

            return firstMessage.getId() > lastMessage.getId() ? firstMessage.getId() : lastMessage.getId();
        }

    }

    /**
     * All logic involving retrieving messages of an existing conversation which includes pagination.
     */
    private class ExistingMessagesHelper {

        private UniqueResourceList<Message> messages = new UniqueResourceList<>();
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
    private class ExistingConversationHelper implements OnClickSendReplyListener {

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

        @Override
        public void onClickSendInReplyView(final String message, final String clientId) {
            mData.postNewMessage(mConversationId, message, clientId, new MessageListContainerContract.PostNewMessageCallback() {
                @Override
                public void onSuccess(Message message) {
                    mMarkReadHelper.disableOriginalLastMessageMarked(); // Should be called before any list rendering is done
                    mExistingMessagesHelper.reloadLatestMessages(mConversationId);
                }

                @Override
                public void onFailure(final String errorMessage) {
                    mOptimisticMessageHelper.markAsFailed(clientId);
                }
            });
        }
    }

    /**
     * All logic for handling a new conversation
     */
    private class NewConversationHelper implements OnClickSendReplyListener {

        private boolean mIsNewConversation;

        public boolean isNewConversation() {
            return mIsNewConversation;
        }

        public void setIsNewConversation(boolean mIsNewConversation) {
            this.mIsNewConversation = mIsNewConversation;
        }

        private String extractName(String email) {
            return email.substring(0, email.indexOf("@"));
        }

        private String extractSubject(String message) {
            return message; // Subject = first message sent in new conversation
        }

        @Override
        public void onClickSendInReplyView(String message, String clientId) {
            String email = mMessengerPrefHelper.getEmail();
            if (email == null) {
                throw new AssertionError("If it's a new conversation and email is null, the user should not have had the chance to send a reply!");
            }

            String name = extractName(email);
            String subject = extractSubject(message);

            // This method should only be called (during onboarding) when a new conversation needs to be made
            mData.startNewConversation(new PostConversationBodyParams(name, email, subject, message, clientId), onLoadConversationListener);
            mListHelper.displayList();
        }
    }

    /**
     * All logic involving saving user email and other information
     */
    private class MessengerPrefHelper {

        private String mEmail;
        private Long mCurrentUserId;
        private String mAvatar;

        public String getEmail() {
            if (mEmail == null) {
                mEmail = MessengerPref.getInstance().getEmailId(); // can be null too
            }
            return mEmail;
        }

        public void setEmail(String email) {
            this.mEmail = email;
            MessengerPref.getInstance().setEmailId(email);
        }

        public String getAvatar() {
            if (mAvatar == null) {
                mAvatar = MessengerUserPref.getInstance().getAvatar(); // can be null too
            }
            return mAvatar;
        }

        public void setAvatar(String avatarUrl) {
            this.mAvatar = avatarUrl;
            MessengerUserPref.getInstance().setAvatar(avatarUrl);
        }

        public Long getUserId() {
            if (mCurrentUserId == null) {
                return mCurrentUserId = MessengerUserPref.getInstance().getUserId();
            }
            return mCurrentUserId;
        }

        public void setUserId(Long currentUserId) {
            this.mCurrentUserId = currentUserId;
            MessengerUserPref.getInstance().setUserId(currentUserId);
        }

        private void setUserInfo(Long currentUserId, String fullName, String avatarUrl) {
            if (currentUserId == null || currentUserId == 0 || fullName == null || avatarUrl == null) {
                throw new IllegalArgumentException("Values can not be null!");
            }

            setUserId(currentUserId);
            setAvatar(avatarUrl);
            MessengerUserPref.getInstance().setFullName(fullName);
        }

    }

    /**
     * All logic involving onboarding items that ask for email
     */
    private class OnboardingHelper {

        // TODO: Varun wants email to be asked after the first message is sent?
        private boolean mWasNewConversation; // If this was originally a new conversation (that may have become an existing conversation later)
        private boolean mWasEmailAskedInThisConversation; // Where email was not known before, but was set by user in this conversation
        private List<BaseListItem> mOnboardingItems = new ArrayList<>();

        public void setWasNewConversation(boolean isNewConversation) {
            mWasNewConversation = isNewConversation; //  Since a new conversation can become a new conversation, we need to remember original state
        }

        public List<BaseListItem> getOnboardingListItemViews() {
            if (mWasNewConversation && mMessengerPrefHelper.getEmail() == null) { // New conversation (first time) where email is unknown
                mOnboardingItems = generateOnboardingMessagesAskingForEmail();
                mWasEmailAskedInThisConversation = true;
            } else if (mWasNewConversation && mWasEmailAskedInThisConversation) { // New conversation (first time) where email is now known (but was asked in this conversation)
                mOnboardingItems = generateOnboardingMessagesWithPrefilledEmail();
            } else { // New conversation (not the first time)
                mOnboardingItems = generateOnboardingMessagesWithoutEmail();
            }

            if (mOnboardingItems == null) {
                throw new AssertionError("Onboarding items should be a proper list, size = 0 is acceptable, null is not!");
            }

            return mOnboardingItems;
        }

        private List<BaseListItem> generateOnboardingMessagesAskingForEmail() {
            List<BaseListItem> baseListItems = new ArrayList<>();

            baseListItems.add(new InputEmailListItem(new InputEmailListItem.OnClickSubmitListener() {
                @Override
                public void onClickSubmit(String email) {
                    mMessengerPrefHelper.setEmail(email);

                    mReplyBoxHelper.configureReplyBoxVisibility(mListHelper.getListPageState());
                    mReplyBoxHelper.focusOnReplyBox(); // Immediately after the Email is entered, the reply box should be focused upon for more information

                    mListHelper.displayList();
                }
            }));
            return baseListItems;
        }

        private List<BaseListItem> generateOnboardingMessagesWithPrefilledEmail() {
            List<BaseListItem> baseListItems = new ArrayList<>();
            baseListItems.add(new InputEmailListItem(mMessengerPrefHelper.getEmail()));
            baseListItems.add(new BotMessageListItem("Thanks! You will be notified here or via email", 0, null)); // TODO: Strings.xml
            return baseListItems;
        }

        private List<BaseListItem> generateOnboardingMessagesWithoutEmail() {
            return Collections.EMPTY_LIST; // If email is known, there's no need to show any bot message asking user to type something
        }

    }

    /**
     * All logic for showing and hiding reply box should be in this class
     */
    private class ReplyBoxHelper {

        /**
         * Anywhere on this class, by calling one method, configureReplyBoxVisibility(), the reply box will make the necessary checks to alter it's visibility
         */
        public void configureReplyBoxVisibility(@Nullable ListPageState listPageState) {
            if (listPageState != null) {
                switch (listPageState) {
                    case LIST:
                        if (mNewConversationHelper.isNewConversation()) { // Starting a new Conversation
                            if (mMessengerPrefHelper.getEmail() == null) { // Email step is yet to be entered (Reply box shouldn't be shown until the email is known
                                mView.hideReplyBox();
                            } else {
                                mView.showReplyBox();
                            }
                        } else { // Existing Conversation
                            mView.showReplyBox();
                        }
                        break;

                    default:
                    case EMPTY:
                        mView.showReplyBox();
                        break;

                    case ERROR:
                    case LOADING:
                    case NONE:
                        mView.hideReplyBox();
                        break;
                }
            } else {
                mView.hideReplyBox();
            }
        }

        private void focusOnReplyBox() {
            mView.focusOnReplyBox(); // refocus on reply box for easy typing
        }
    }


    private class ClientIdHelper {

        private final String PREFIX = "android-%s-%s";
        private final String UUID_FOR_THIS_SESSION = UUID.randomUUID().toString();
        private AtomicInteger counter = new AtomicInteger(0);

        public String generateClientId() {
            return String.format(PREFIX, UUID_FOR_THIS_SESSION, counter.incrementAndGet());
        }
    }

    private interface OnClickSendReplyListener {
        void onClickSendInReplyView(String message, String clientId);
    }
}
