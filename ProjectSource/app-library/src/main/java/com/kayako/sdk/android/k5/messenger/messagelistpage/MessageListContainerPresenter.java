package com.kayako.sdk.android.k5.messenger.messagelistpage;

import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.DataItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.DataItemHelper;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.DeliveryIndicatorHelper;
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
import java.util.concurrent.atomic.AtomicBoolean;
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

    private MarkReadHelper mMarkReadHelper = new MarkReadHelper();
    private OnboardingHelper mOnboardingHelper = new OnboardingHelper();
    private MessengerPrefHelper mMessengerPrefHelper = new MessengerPrefHelper();
    private NewConversationHelper mNewConversationHelper = new NewConversationHelper();
    private ExistingConversationHelper mExistingConversationHelper = new ExistingConversationHelper();
    private ReplyBoxHelper mReplyBoxHelper = new ReplyBoxHelper();
    private ListHelper mListHelper = new ListHelper();

    // private ResourceList<Message> mMessageList = new ResourceList<>();
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

        if (mNewConversationHelper.isNewConversation()) {
            mNewConversationHelper.onClickSendInReplyView(message);
        } else {
            mExistingConversationHelper.onClickSendInReplyView(message);
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
            mExistingConversationHelper.reloadMessagesOfConversation();
            mExistingConversationHelper.reloadConversation();
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
                            conversation.getCreator().getFullName());


            // If originally new conversation, set as existing conversation
            if (mNewConversationHelper.isNewConversation()) {
                mNewConversationHelper.setIsNewConversation(false);
            }

            // Update existing Conversation
            mExistingConversationHelper.setConversationId(conversation.getId());
            mExistingConversationHelper.setConversation(conversation);

            // Reload the messages of existing conversation
            mExistingConversationHelper.reloadMessagesOfConversation();
        }

        @Override
        public void onFailure(String message) {
            // TODO: Show error mesasage?
        }
    };

    MessageListContainerContract.OnLoadMessagesListener onLoadMessagesListener = new MessageListContainerContract.OnLoadMessagesListener() {
        @Override
        public void onSuccess(List<Message> messageList) {
            // Set the new messages - includes incremental messages from pagination
            mExistingConversationHelper.addOrReplaceMessages(messageList); // TODO: mMessageList.addOrReplaceIfExisting(messageList);

            //
            mListHelper.displayList();

            // Once messages have been loaded AND displayed in view, mark the last message as read
            mMarkReadHelper.markLastMessageAsRead(
                    mMarkReadHelper.extractLastMessageId(messageList),
                    mExistingConversationHelper.getConversationId());
        }

        @Override
        public void onFailure(String message) {
            // TODO: Add conditions to show toast if load-more and show error view if offset=0
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

        // TODO: Created a problem - The onboarding messages disappear

        public void displayList() {
            List<BaseListItem> allListItems = new ArrayList<>();
            List<BaseListItem> onboardingItems = mOnboardingHelper.getOnboardingListItemViews(); // Helper automatically checks if for new conversation, for originally new conversation or originally existing conversation

            if (mNewConversationHelper.isNewConversation()) {
                allListItems.addAll(onboardingItems);
                mView.setupListInMessageListingView(allListItems); // No empty state view for Conversation Helper

            } else {
                allListItems.addAll(onboardingItems);
                allListItems.addAll(getMessageAsListItemViews(mExistingConversationHelper.getMessages()));

                if (allListItems.size() == 0) {
                    mView.showEmptyViewInMessageListingView();
                } else {
                    mView.setupListInMessageListingView(allListItems);
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
                                DeliveryIndicatorHelper.getDeliveryIndicator(message, mMessengerPrefHelper.getUserId()), // TODO:
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
            Collections.reverse(dataItems);
            return DataItemHelper.getInstance().convertDataItemToListItems(dataItems);
        }
    }

    private class MarkReadHelper {

        private AtomicBoolean useOriginalLastMessage = new AtomicBoolean(true);
        private AtomicLong originalLastMessageMarkedRead = new AtomicLong(0); // the getOriginalLastMessageMarkedReadlatest message that was read (only when this page is opened, should not update later!)
        private AtomicLong lastMessageMarkedRead = new AtomicLong(0); // the latest message that has been read (constantly updates are the latest messages gets read)

        // TODO: When KRE is implemented, ensure the messages and conversartion is not reloaded unless there is a change made!
        // This should be done because the app is already making requests on its own

        /**
         * Call this to set the lastMessageMarkedRead.
         * This method only sets the value if it hasn't been set before, thus ensuring only the original is saved
         *
         * @param lastMessageMarkedRead
         */
        public void setOriginalLastMessageMarkedReadIfNotSetBefore(Long lastMessageMarkedRead) {
            // Set the original last message read (This will be the first non-zero value set)
            if (originalLastMessageMarkedRead.get() == 0) {
                originalLastMessageMarkedRead.set(lastMessageMarkedRead);
            }
        }

        public long getOriginalLastMessageMarkedRead() {
            // Save the unread marker so that it doesn't go away when conversation is reloaded.
            // Instead, it should only be reset when the user leaves the conversation.
            // Code should rely on this presaved value, and not the conversation variable to check read marker status
            if (useOriginalLastMessage.get()) {
                return originalLastMessageMarkedRead.get();
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
            useOriginalLastMessage.set(false);
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

            // If there is nothing new to update, skip to prevent multiple API calls to mark same message as read
            if (this.lastMessageMarkedRead.get() == lastMessageMarkedRead) {
                return false;
            }

            // Call API to mark message read
            mData.markMessageAsRead(conversationId,
                    lastMessageMarkedRead,
                    new MessageListContainerContract.OnMarkMessageAsReadListener() {
                        @Override
                        public void onSuccess() {
                            // Set last read message
                            MarkReadHelper.this.lastMessageMarkedRead.set(lastMessageMarkedRead);
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

    private class ExistingConversationHelper {

        private Long mConversationId;
        private AtomicReference<Conversation> mConversation = new AtomicReference<>();
        private List<Message> messages = new ArrayList<>();

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

        public void addOrReplaceMessages(List<Message> messageList) {
            this.messages = messageList; // TODO: Your own resource list! Although a list with order, no 2 ids can conflict!
        }

        public List<Message> getMessages() {
            return messages;
        }

        public void reloadConversation() {
            if (mConversationId == null || mConversationId == 0) {
                throw new AssertionError("ConversationId must be valid to call this method!");
            }

            mData.getConversation(mConversationId, onLoadConversationListener);
        }

        public void reloadMessagesOfConversation() {
            if (mConversationId == null || mConversationId == 0) {
                throw new AssertionError("ConversationId must be valid to call this method!");
            }

            mData.getMessages(onLoadMessagesListener, mConversationId, 0, 10);
        }


        public void onClickSendInReplyView(String message) {
            mData.postNewMessage(mConversationId, message, new MessageListContainerContract.PostNewMessageCallback() {
                @Override
                public void onSuccess(Message message) {
                    mMarkReadHelper.disableOriginalLastMessageMarked(); // Should be called before any list rendering is done
                    mExistingConversationHelper.reloadMessagesOfConversation();
                }

                @Override
                public void onFailure(final String errorMessage) {
                    mView.showToastMessage(errorMessage);
                }
            });
        }

    }

    private class NewConversationHelper {

        private boolean mIsNewConversation;

        public boolean isNewConversation() {
            return mIsNewConversation;
        }

        public void setIsNewConversation(boolean mIsNewConversation) {
            this.mIsNewConversation = mIsNewConversation;
        }

        public void onClickSendInReplyView(String message) {
            String email = mMessengerPrefHelper.getEmail();
            if (email == null) {
                throw new AssertionError("If it's a new conversation and email is null, the user should not have had the chance to send a reply!");
            }

            String name = extractName(email);
            String subject = extractSubject(message);

            // This method should only be called (during onboarding) when a new conversation needs to be made
            mData.startNewConversation(new PostConversationBodyParams(name, email, subject, message), onLoadConversationListener);
            mListHelper.displayList();
        }

        private String extractName(String email) {
            return email.substring(0, email.indexOf("@"));
        }

        private String extractSubject(String message) {
            return message; // Subject = first message sent in new conversation
        }
    }

    private class MessengerPrefHelper {

        private String mEmail;
        private Long mCurrentUserId;

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

        private void setUserInfo(Long currentUserId, String fullName) {
            if (currentUserId == null || currentUserId == 0 || fullName == null) {
                throw new IllegalArgumentException("Values can not be null!");
            }

            setUserId(currentUserId);
            MessengerUserPref.getInstance().setFullName(fullName);
        }

    }

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
}
