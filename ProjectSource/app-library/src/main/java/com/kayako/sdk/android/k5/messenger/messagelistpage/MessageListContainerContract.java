package com.kayako.sdk.android.k5.messenger.messagelistpage;

import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.Attachment;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFeedback;
import com.kayako.sdk.android.k5.common.fragments.ListPageState;
import com.kayako.sdk.android.k5.common.fragments.OnScrollListListener;
import com.kayako.sdk.android.k5.common.mvp.BaseData;
import com.kayako.sdk.android.k5.common.mvp.BasePresenter;
import com.kayako.sdk.android.k5.common.mvp.BaseView;
import com.kayako.sdk.android.k5.common.utils.file.FileAttachment;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.AssignedAgentData;
import com.kayako.sdk.messenger.conversation.Conversation;
import com.kayako.sdk.messenger.conversation.PostConversationBodyParams;
import com.kayako.sdk.messenger.message.Message;
import com.kayako.sdk.messenger.message.PostMessageBodyParams;
import com.kayako.sdk.messenger.rating.PostRatingBodyParams;
import com.kayako.sdk.messenger.rating.PutRatingBodyParams;
import com.kayako.sdk.messenger.rating.Rating;

import java.io.File;
import java.util.List;
import java.util.Map;

public class MessageListContainerContract {

    public interface Data extends BaseData {

        void postNewMessage(long conversationId, PostMessageBodyParams postMessageBodyParams, final String clientId, final MessageListContainerContract.PostNewMessageCallback callback);

        void getMessages(final OnLoadMessagesListener listener, long conversationId, int offset, int limit);

        void startNewConversation(PostConversationBodyParams bodyParams, MessageListContainerContract.PostConversationCallback postConversationCallback);

        void getConversation(long conversationId, final OnLoadConversationListener onLoadConversationListener);

        void markMessageAsRead(long conversationId, long messageId, final OnMarkMessageAsReadListener onLoadConversationListener);

        void getConversationRatings(long conversationId, OnLoadRatingsListener onLoadRatingsListener);

        void addConversationRating(long conversationId, PostRatingBodyParams postRatingBodyParams, OnUpdateRatingListener onUpdateRatingListener);

        void updateConversationRating(long conversationId, long ratingId, PutRatingBodyParams putRatingBodyParams, OnUpdateRatingListener onUpdateRatingListener);

        void markMessageAsDelivered(long conversationId, long postId, OnMarkMessageAsReadListener onMarkMessageAsReadListener);
    }

    public interface View extends BaseView {

        boolean hasPageLoaded();

        void showToastMessage(int stringResId);

        void showToastMessage(String errorMessage);

        void setupListInMessageListingView(List<BaseListItem> baseListItems);

        void showEmptyViewInMessageListingView();

        void showErrorViewInMessageListingView();

        void showLoadingViewInMessageListingView();

        boolean hasUserInteractedWithList();

        void hideReplyBox();

        void showReplyBox();

        void focusOnReplyBox();

        void setReplyBoxHintMessage(String hintMessage);

        void collapseToolbar();

        void expandToolbar();

        void setHasMoreItems(boolean hasMoreItems);

        void showLoadMoreView();

        void hideLoadMoreView();

        void scrollToBottomOfList();

        void openFilePickerForAttachments();

        void setAttachmentButtonVisibilityInReplyBox(boolean showAttachment);

        void configureToolbarForAssignedAgent(AssignedAgentData assignedAgentData);

        void configureToolbarForLastActiveAgents();

        boolean isNearBottomOfList();

        void setKeyboardVisibility(boolean showKeyboard);

        void showAttachmentPreview(String imageUrl, String imageName, long time, String downloadUrl);
    }

    public interface Presenter extends BasePresenter<MessageListContainerContract.View> {

        void initPage(boolean isNewConversation, @Nullable Long conversationId);

        void closePage();

        void onClickSendInReplyView(String message);

        void onTypeReply(String messageInProcess);

        void onClickAddAttachment();

        void onConfirmSendingOfAttachment(FileAttachment file);

        void setData(Data data);

        void onClickRetryInErrorView();

        void onPageStateChange(ListPageState state);

        void onScrollList(boolean isScrolling, OnScrollListListener.ScrollDirection direction);

        void onListItemClick(int messageType, Long id, Map<String, Object> messageData);

        void onListAttachmentClick(Attachment attachment);

        void onLoadMoreItems();
    }


    public interface PostNewMessageCallback {
        void onSuccess(Message message); // message contains clientId

        void onFailure(String clientId);
    }

    public interface OnLoadMessagesListener {
        void onSuccess(List<Message> messageList, int offset);

        void onFailure(String errorMessage);
    }

    public interface PostConversationCallback {
        void onSuccess(String clientId, Conversation conversation);

        void onFailure(String clientId, String message);
    }


    public interface OnLoadConversationListener {
        void onSuccess(Conversation conversation);

        void onFailure(String message);
    }

    public interface OnConversationChangeListener {
        void onConversationChange(Conversation conversation);

        void onNewMessage(long messageId);

        void onUpdateMessage(long messageId);
    }

    public interface OnMarkMessageAsReadListener {

        void onSuccess(long messageId);

        void onFailure(String message);
    }

    public interface OnLoadRatingsListener {

        void onSuccess(List<Rating> ratings);

        void onFailure(String message);
    }


    public interface OnUpdateRatingListener {
        void onSuccess(Rating rating);

        void onFailure(Rating.SCORE score, String comment, String errorMessage);
    }
}
