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
import com.kayako.sdk.messenger.conversation.Conversation;
import com.kayako.sdk.messenger.conversation.PostConversationBodyParams;
import com.kayako.sdk.messenger.message.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessageListContainerPresenter implements MessageListContainerContract.Presenter {

    private MessageListContainerContract.View mView;
    private MessageListContainerContract.Data mData;

    private Long mConversationId;
    private boolean mIsNewConversation;
    private String mEmail; // TODO: Make this a SharedPref - should be global - not specific to a conversation
    private List<BaseListItem> mOnboardingItems = new ArrayList<>();
    private ListPageState mListPageState;


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
    public void setView(MessageListContainerContract.View view) {
        mView = view;
    }

    @Override
    public void initPage(boolean isNewConversation, Long conversationId) {
        mIsNewConversation = isNewConversation;
        if (conversationId != null) {
            mConversationId = conversationId;
        }

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
                    convertNewConversationToExistingConversation(conversation);
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

    private void convertNewConversationToExistingConversation(Conversation conversation) {
        mConversationId = conversation.getId();
        mIsNewConversation = false;
    }

    private void reloadPage(boolean resetView) {
        if (resetView) {
            mView.showLoadingViewInMessageListingView();
        }

        if (mIsNewConversation) {
            reloadOnboardingMessages();
        } else {
            reloadMessagesOfConversation();
        }

        configureReplyBoxVisibility();
    }

    private void reloadOnboardingMessages() {
        List<BaseListItem> baseListItems = new ArrayList<>();

        if (mEmail == null) {// TODO: Condition - if email not already available - from sharedPref
            baseListItems.add(new InputEmailListItem(new InputEmailListItem.OnClickSubmitListener() {
                @Override
                public void onClickSubmit(String email) {
                    mEmail = email;
                    configureReplyBoxVisibility();
                    reloadOnboardingMessages();
                    // TODO: Create new conversation?
                    // TODO: Some loading indicator

                }
            }));
        } else {
            baseListItems.add(new InputEmailListItem(mEmail));
            baseListItems.add(new BotMessageListItem("What would you like to talk about?", 0, null)); // TODO: Convert to resId instead of string msg
        }

        mOnboardingItems = baseListItems;
        mView.setupListInMessageListingView(baseListItems);
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
            dataItems.add(
                    new DataItem(
                            message.getId(),
                            null,
                            new UserDecoration(
                                    message.getCreator().getFullName(),
                                    message.getCreator().getAvatarUrl(),
                                    message.getCreator().getId(),
                                    false), // TODO: On creating a conversation for the first time - save the userId of the creator of the conversation and check if it matches
                            new ChannelDecoration(
                                    R.drawable.ko__img_helpcenter),
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
