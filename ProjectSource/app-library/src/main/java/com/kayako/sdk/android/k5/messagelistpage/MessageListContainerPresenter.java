package com.kayako.sdk.android.k5.messagelistpage;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.ChannelDecoration;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.DataItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.DataItemHelper;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.UserDecoration;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.BotMessageListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputEmailListItem;
import com.kayako.sdk.android.k5.common.fragments.ListPageState;
import com.kayako.sdk.android.k5.common.fragments.OnScrollListListener;
import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.messenger.conversation.Conversation;
import com.kayako.sdk.messenger.conversation.PostConversationBodyParams;
import com.kayako.sdk.messenger.message.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessageListContainerPresenter implements MessageListContainerContract.Presenter {

    private MessageListContainerContract.View mView;
    private MessageListContainerContract.Data mData;

    private String mEmail; // TODO: Make this a SharedPref - should be global - not specific to a conversation
    private Long mCurrentUserId; // TODO: This too has to be saved

    private Long mConversationId;
    private Conversation mConversation;
    private boolean mIsNewConversation;
    private boolean mIsEmailAskedInThisConversation;
    private List<BaseListItem> mOnboardingItems = new ArrayList<>();
    private ListPageState mListPageState;

    // TODO: Group onboarding messages logic in a private class? - confusing otherwise as more and more methods are used

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
        configureReplyBoxVisibility();
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
        mIsNewConversation = isNewConversation;
        if (conversationId != null) {
            mConversationId = conversationId;
        }

        // Retrieve existing email if available - so that user doesn't retry the same steps
        mEmail = MessengerPref.getInstance().getEmailId();
        if (mEmail == null) {
            mIsEmailAskedInThisConversation = true;
        } else {
            mIsEmailAskedInThisConversation = false;
        }

        mCurrentUserId = MessengerPref.getInstance().getUserId();
        // TODO: Ensure that the above two values are assigned early on

        reloadPage(true);
    }

    @Override
    public void onClickSendInReplyView(String message) {
        // TODO: Optimisitc Sending? Show it in messagelist?
        // TODO: Should both MessageListContainerView and MessageListView determine if it's a new conversation separately?
        if (mIsNewConversation) {
            if (mEmail == null) {
                throw new AssertionError("If it's a new conversation and email is null, the user should not have had the chance to send a reply!");
            }

            String email = mEmail;
            String name = mEmail.substring(0, mEmail.indexOf("@"));
            String subject = message;
            String contents = message;

            // This method should only be called (during onboarding) when a new conversation needs to be made
            mData.startNewConversation(new PostConversationBodyParams(name, email, subject, contents), new MessageListContainerContract.OnLoadConversationListener() {
                @Override
                public void onSuccess(Conversation conversation) {
                    mConversation = conversation;
                    setCurrentConversation(conversation);
                    reloadMessagesOfConversation();
                }

                @Override
                public void onFailure(String message) {
                    // TODO: Show error mesasage?
                }
            });
            reloadOnboardingMessages();

        } else {
            // TODO: mConversationId can be null
            mData.postNewMessage(mConversationId, message, new MessageListContainerContract.PostNewMessageCallback() {
                @Override
                public void onSuccess(Message message) {
                    reloadMessagesOfConversation();
                }

                @Override
                public void onFailure(final String errorMessage) {
                    // TODO: show error message
                    mView.showToastMessage(errorMessage);
                }
            });
        }
    }

    /**
     * All logic for showing and hiding reply box should be in this method
     */
    private void configureReplyBoxVisibility() {
        if (mListPageState != null) {
            switch (mListPageState) {
                case LIST:
                    if (mIsNewConversation) { // Starting a new Conversation
                        if (mEmail == null) { // Email step is yet to be entered (Reply box shouldn't be shown until the email is known
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

    private void reloadPage(boolean resetView) {
        if (resetView) {
            mView.showLoadingViewInMessageListingView();
        }

        if (mIsNewConversation) {
            // Load view
            reloadOnboardingMessages();
        } else {
            reloadMessagesOfConversation();
            reloadConversation(mConversationId);
        }

        configureReplyBoxVisibility();
    }

    private void reloadConversation(final long conversationId) {
        mData.getConversation(conversationId, new MessageListContainerContract.OnLoadConversationListener() {
            @Override
            public void onSuccess(Conversation conversation) {
                setCurrentConversation(conversation);
                // TODO: Check for status - completed/closed to hide replybox
            }

            @Override
            public void onFailure(String message) {
                // TODO: show message?
            }
        });
    }

    private void setCurrentConversation(Conversation conversation) {
        if (mIsNewConversation) {
            setMessengerPrefsOnCreateConversation(
                    mCurrentUserId = conversation.getCreator().getId(), // Assuming the current user is always the creator of conversation,
                    mEmail,
                    conversation.getCreator().getFullName());
        }

        mIsNewConversation = false;
        mConversation = conversation;
        mConversationId = conversation.getId();
        // TODO: At the same time, you need to RETRIEVE these values when a new conversation is made
        // TODO: Method to configure page based on conversation - eg: reply box visibility, etc

        // Subscribe for realtime changes:
        // TODO
//        mData.registerCaseChangeListener(mCurrentUserId, conversation.getRealtimeChannel(), new MessageListContainerContract.OnConversationChangeListener() {
//            @Override
//            public void onConversationChange(Conversation conversation) {
//                setCurrentConversation(conversation);
//                // TODO: Method to configure page based on conversation - eg: reply box visibility, etc
//            }
//
//            @Override
//            public void onNewMessage(long messageId) {
//                reloadMessagesOfConversation();
//            }
//
//            @Override
//            public void onUpdateMessage(long messageId) {
//                reloadMessagesOfConversation();
//            }
//        });
    }

    private void setMessengerPrefsOnCreateConversation(Long currentUserId, String email, String fullName) {
        if (email == null || currentUserId == null || currentUserId == 0 || fullName == null) {
            throw new IllegalArgumentException("Values can not be null!");
        }

        MessengerPref.getInstance().setEmailId(email);
        MessengerPref.getInstance().setUserId(currentUserId);
        MessengerPref.getInstance().setFullName(fullName);
    }

    private void reloadOnboardingMessages() {
        // TODO: Synchronize these methods?

        // Decide what onboarding messages to show
        if (mEmail == null) { // New conversation (first time) where email is unknown
            mOnboardingItems = reloadOnboardingMessagesAskingForEmail();
        } else if (mIsEmailAskedInThisConversation) { // New conversation (first time) where email is now known (but was asked in this conversation)
            mOnboardingItems = reloadOnboardingMessagesWithPrefilledEmail();
        } else { // New conversation (not the first time)
            mOnboardingItems = reloadOnboardingMessagesWithoutEmail();
        }

        mView.setupListInMessageListingView(mOnboardingItems);
    }

    private List<BaseListItem> reloadOnboardingMessagesAskingForEmail() {
        mIsEmailAskedInThisConversation = true;
        List<BaseListItem> baseListItems = new ArrayList<>();
        baseListItems.add(new InputEmailListItem(new InputEmailListItem.OnClickSubmitListener() {
            @Override
            public void onClickSubmit(String email) {
                mEmail = email;
                configureReplyBoxVisibility();
                mView.focusOnReplyBox();
                reloadOnboardingMessages();
                // TODO: Some loading indicator - Optimistic Sending
            }
        }));
        return baseListItems;
    }

    private List<BaseListItem> reloadOnboardingMessagesWithPrefilledEmail() {
        List<BaseListItem> baseListItems = new ArrayList<>();
        baseListItems.add(new InputEmailListItem(mEmail));
        baseListItems.add(new BotMessageListItem("What would you like to talk about?", 0, null)); // TODO: Convert to resId instead of string msg
        return baseListItems;
    }

    private List<BaseListItem> reloadOnboardingMessagesWithoutEmail() {
        List<BaseListItem> baseListItems = new ArrayList<>();
        baseListItems.add(new BotMessageListItem("What would you like to talk about?", 0, null)); // TODO: Convert to resId instead of string msg
        return baseListItems;
    }

    private void reloadMessagesOfConversation() {
        mData.getMessages(new MessageListContainerContract.OnLoadMessagesListener() {
            @Override
            public void onSuccess(List<Message> messageList) {
                List<DataItem> dataItems = convertMessagesToDataItems(messageList);

                if (dataItems.size() == 0) {
                    mView.showEmptyViewInMessageListingView();
                } else {
                    Collections.reverse(dataItems);
                    List<BaseListItem> baseListItems = DataItemHelper.getInstance().convertDataItemToListItems(dataItems);

                    if (mOnboardingItems != null && mOnboardingItems.size() != 0) {
                        baseListItems.addAll(0, mOnboardingItems);
                    }

                    mView.setupListInMessageListingView(baseListItems);
                }
            }

            @Override
            public void onFailure(String message) {
                // TODO: Add conditions to show toast if load-more and show error view if offset=0
                mView.showErrorViewInMessageListingView();
            }
        }, mConversationId, 0, 10);

    }

    private List<DataItem> convertMessagesToDataItems(List<Message> messageList) {
        List<DataItem> dataItems = new ArrayList<>();
        for (Message message : messageList) {

            // TODO: Leverage client_ids for optimistic sending
            dataItems.add(
                    new DataItem(
                            message.getId(),
                            null,
                            new UserDecoration(
                                    message.getCreator().getFullName(),
                                    message.getCreator().getAvatarUrl(),
                                    message.getCreator().getId(),
                                    mCurrentUserId != null && message.getCreator().getId().equals(mCurrentUserId)),
                            null, // no channelDecoration
                            message.getContentText(),
                            message.getCreatedAt(),
                            Collections.EMPTY_LIST, // TODO: Attachments
                            false
                    )
            );
        }

        return dataItems;
    }

}
