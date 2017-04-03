package com.kayako.sdk.android.k5.messenger.messagelistpage;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.DataItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.DataItemHelper;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.UserDecoration;
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
import java.util.concurrent.atomic.AtomicLong;

public class MessageListContainerPresenter implements MessageListContainerContract.Presenter {

    private MessageListContainerContract.View mView;
    private MessageListContainerContract.Data mData;

    private Long mConversationId;
    private Conversation mConversation;
    private ListPageState mListPageState;

    private MarkReadHelper mMarkReadHelper = new MarkReadHelper();
    private OnboardingHelper mOnboardingHelper = new OnboardingHelper();
    private MessengerPrefHelper mMessengerPrefHelper = new MessengerPrefHelper();
    private NewConversationHelper mNewConversationHelper = new NewConversationHelper();
    private ExistingConversationHelper mExistingConversationHelper = new ExistingConversationHelper();
    private ReplyBoxHelper mReplyBoxHelper = new ReplyBoxHelper();


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
        mListPageState = state;
        mReplyBoxHelper.configureReplyBoxVisibility();
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

        mNewConversationHelper.setIsNewConversation(isNewConversation);
        if (conversationId != null) {
            mConversationId = conversationId;
        }

        // TODO: Ensure that the above two values are assigned early on

        reloadPage(true);
    }

    @Override
    public void onClickSendInReplyView(String message) {
        // TODO: Optimisitc Sending? Show it in messagelist?
        // TODO: Should both MessageListContainerView and MessageListView determine if it's a new conversation separately?

        // Collapse toolbar once a message has been sent!
        mView.collapseToolbar();

        if (mNewConversationHelper.isNewConversation()) {
            mNewConversationHelper.onClickSendInReplyView(message);

        } else {

            if (mConversationId == null) {
                throw new AssertionError("If it is NOT a new conversation, conversation id should NOT be null");
            }

            mData.postNewMessage(mConversationId, message, new MessageListContainerContract.PostNewMessageCallback() {
                @Override
                public void onSuccess(Message message) {
                    mExistingConversationHelper.reloadMessagesOfConversation(mConversationId);
                }

                @Override
                public void onFailure(final String errorMessage) {
                    // TODO: show error message
                    mView.showToastMessage(errorMessage);
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

        if (mNewConversationHelper.isNewConversation()) {
            // Show expanded toolbar when it's a new conversation
            mView.expandToolbar();

            // Load view
            mOnboardingHelper.reloadOnboardingMessages();
        } else {
            mExistingConversationHelper.reloadMessagesOfConversation(mConversationId);
            mExistingConversationHelper.reloadConversation(mConversationId);
        }

        mReplyBoxHelper.configureReplyBoxVisibility();
    }


    MessageListContainerContract.OnLoadConversationListener onLoadConversationListener = new MessageListContainerContract.OnLoadConversationListener() {
        @Override
        public void onSuccess(Conversation conversation) {
            // Update global variables
            mConversation = conversation;
            mConversationId = conversation.getId();

            // Update user details (creator of new conversation) - This has been done EVERY TIME to ensure that even if the current userid is lost due to any reason, a new conversation will get it back! - eg: using the same fingerprint for multiple users
            mMessengerPrefHelper
                    .setUserInfo(
                            conversation.getCreator().getId(), // Assuming the current user is always the creator of conversation,
                            conversation.getCreator().getFullName());


            // If originally new conversation, set as existing conversation
            if (mNewConversationHelper.isNewConversation()) {
                mNewConversationHelper.setIsNewConversation(false);
            }

            // Reload the messages of existing conversation
            mExistingConversationHelper.reloadMessagesOfConversation(mConversationId);
        }

        @Override
        public void onFailure(String message) {
            // TODO: Show error mesasage?
        }
    };

    private class MarkReadHelper {

        private AtomicLong lastMessageMarkedRead = new AtomicLong(0);

        private synchronized boolean setLastMessageMarkedRead(final Long lastMessageMarkedRead, final Long conversationId) {
            // If the argument is null, skip
            if (lastMessageMarkedRead == null || conversationId == null) {
                return false;
            }

            // If there is nothing new to update, skip to prevent multiple API calls to mark same message as read
            if (this.lastMessageMarkedRead.get() == lastMessageMarkedRead) {
                return false;
            }

            // Call API to mark message read
            mData.markMessageAsRead(conversationId, lastMessageMarkedRead, new MessageListContainerContract.OnMarkMessageAsReadListener() {
                @Override
                public void onSuccess() {
                    // Set last read message
                    MarkReadHelper.this.lastMessageMarkedRead.set(lastMessageMarkedRead);
                }

                @Override
                public void onFailure(String message) {
                }
            });

            return true;
        }

        private Long extractLastMessageId(List<Message> messageList) {
            if (messageList == null || messageList.size() == 0) {
                return null;
            }

            Message message = messageList.get(messageList.size() - 1);
            return message.getId();
        }

    }

    private class ExistingConversationHelper {

        public void reloadConversation(final long conversationId) {
            mData.getConversation(conversationId, onLoadConversationListener);
        }

        public void reloadMessagesOfConversation(final long conversationId) {
            mData.getMessages(new MessageListContainerContract.OnLoadMessagesListener() {
                @Override
                public void onSuccess(List<Message> messageList) {
                    List<DataItem> dataItems = convertMessagesToDataItems(messageList);

                    if (dataItems.size() == 0) {
                        mView.showEmptyViewInMessageListingView();
                    } else {
                        // TODO: mMessageList.addOrReplaceIfExisting(messageList);

                        Collections.reverse(dataItems);
                        List<BaseListItem> baseListItems = DataItemHelper.getInstance().convertDataItemToListItems(dataItems);

                        if (mOnboardingHelper.getOnboardingItems() != null && mOnboardingHelper.getOnboardingItems().size() != 0) {
                            baseListItems.addAll(0, mOnboardingHelper.getOnboardingItems());
                        }

                        mView.setupListInMessageListingView(baseListItems);

                        // Once messages have been loaded AND displayed in view, mark the last message as read
                        mMarkReadHelper.setLastMessageMarkedRead(
                                mMarkReadHelper.extractLastMessageId(messageList),
                                conversationId);
                    }
                }

                @Override
                public void onFailure(String message) {
                    // TODO: Add conditions to show toast if load-more and show error view if offset=0
                    mView.showErrorViewInMessageListingView();
                }
            }, conversationId, 0, 10);
        }

        private List<DataItem> convertMessagesToDataItems(List<Message> messageList) {
            List<DataItem> dataItems = new ArrayList<>();

            for (Message message : messageList) {

                boolean isRead = false;
                if (mConversation != null
                        && mConversation.getReadMarker() != null
                        && mConversation.getReadMarker().getLastReadPostId() > message.getId()) {
                    isRead = true;
                } else {
                    isRead = false;
                }

                // TODO: Leverage client_ids for optimistic sending
                dataItems.add(
                        new DataItem(
                                message.getId(),
                                null,
                                new UserDecoration(
                                        message.getCreator().getFullName(),
                                        message.getCreator().getAvatarUrl(),
                                        message.getCreator().getId(),
                                        mMessengerPrefHelper.getUserId() != null && message.getCreator().getId().equals(mMessengerPrefHelper.getUserId())),
                                null, // no channelDecoration
                                message.getContentText(),
                                message.getCreatedAt(),
                                Collections.EMPTY_LIST, // TODO: Attachments
                                isRead
                        )
                );
            }

            return dataItems;
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
            mOnboardingHelper.reloadOnboardingMessages();
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

        private boolean mWasEmailAskedInThisConversation; // Where email was not known before, but was set by user in this conversation
        private List<BaseListItem> mOnboardingItems = new ArrayList<>();

        public List<BaseListItem> getOnboardingItems() {
            return mOnboardingItems;
        }

        /**
         * Decides what kind of onboarding messages to show
         */
        // TODO: There should only be RELOAD MESSAGES and getOnboardingitems should be used
        public void reloadOnboardingMessages() {
            if (mNewConversationHelper.isNewConversation() && mMessengerPrefHelper.getEmail() == null) { // New conversation (first time) where email is unknown
                mOnboardingItems = generateOnboardingMessagesAskingForEmail();
                mWasEmailAskedInThisConversation = true;
            } else if (mNewConversationHelper.isNewConversation() && mWasEmailAskedInThisConversation) { // New conversation (first time) where email is now known (but was asked in this conversation)
                mOnboardingItems = generateOnboardingMessagesWithPrefilledEmail();
            } else { // New conversation (not the first time)
                mOnboardingItems = generateOnboardingMessagesWithoutEmail();
            }

            mView.setupListInMessageListingView(mOnboardingItems);
        }

        private List<BaseListItem> generateOnboardingMessagesAskingForEmail() {
            List<BaseListItem> baseListItems = new ArrayList<>();

            baseListItems.add(new InputEmailListItem(new InputEmailListItem.OnClickSubmitListener() {
                @Override
                public void onClickSubmit(String email) {
                    mMessengerPrefHelper.setEmail(email);

                    mReplyBoxHelper.configureReplyBoxVisibility();
                    mView.focusOnReplyBox();

                    reloadOnboardingMessages();
                    // TODO: Some loading indicator - Optimistic Sending
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
        private void configureReplyBoxVisibility() {
            if (mListPageState != null) {
                switch (mListPageState) {
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
    }
}
