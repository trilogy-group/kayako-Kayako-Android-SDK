package com.kayako.sdk.android.k5.messenger.messagelistpage;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.activities.KayakoSelectConversationActivity;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.loadmorelist.EndlessRecyclerViewScrollAdapter;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerAdapter;
import com.kayako.sdk.android.k5.common.fragments.ListPageState;
import com.kayako.sdk.android.k5.common.fragments.OnListPageStateChangeListener;
import com.kayako.sdk.android.k5.common.fragments.OnScrollListListener;
import com.kayako.sdk.android.k5.common.utils.file.FileAttachmentUtil;
import com.kayako.sdk.android.k5.core.KayakoLogHelper;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.AssignedAgentData;
import com.kayako.sdk.android.k5.messenger.replyboxview.ReplyBoxContract;
import com.kayako.sdk.android.k5.messenger.toolbarview.MessengerToolbarContract;

import java.io.File;
import java.util.List;
import java.util.Map;

public class MessageListContainerFragment extends Fragment implements MessageListContainerContract.View {

    private MessageListContainerContract.Presenter mPresenter;
    private MessageListContract.ConfigureView mMessageListView;
    private ReplyBoxContract.ConfigureView mReplyBoxView;
    private MessengerToolbarContract.ConfigureView mToolbarView;

    private static final int REQUEST_CODE_ATTACHMENT = 100;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mPresenter = MessageListContainerFactory.getPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ko__fragment_message_list_container, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMessageListView = (MessageListContract.ConfigureView) getChildFragmentManager().findFragmentById(R.id.ko__message_list);
        mReplyBoxView = (ReplyBoxContract.ConfigureView) getChildFragmentManager().findFragmentById(R.id.ko__reply_box);
        mToolbarView = (MessengerToolbarContract.ConfigureView) getChildFragmentManager().findFragmentById(R.id.ko__messenger_toolbar);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Call on Activity Created
        // Call before initPage()
        mToolbarView.configureDefaultView();

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null && bundle.containsKey(KayakoSelectConversationActivity.ARG_CONVERSATION_ID)) {
            mPresenter.initPage(false, bundle.getLong(KayakoSelectConversationActivity.ARG_CONVERSATION_ID));
        } else {
            mPresenter.initPage(true, null);
        }

        // Ensure that all methods that use mPresenter should be used only AFTER initPage() is called - this is to ensure resetValues() is called before any mPresenter method is called if the view is recreated
        mMessageListView.setOnErrorListener(new MessageListContract.OnErrorListener() {
            @Override
            public void onClickRetry() {
                mPresenter.onClickRetryInErrorView();
            }
        });

        mMessageListView.setOnListPageStateChangeListener(new OnListPageStateChangeListener() {
            @Override
            public void onListPageStateChanged(ListPageState state) {
                mPresenter.onPageStateChange(state);
            }
        });

        mMessageListView.setOnListScrollListener(new OnScrollListListener() {
            @Override
            public void onScrollList(boolean isScrolling, ScrollDirection direction) {
                mPresenter.onScrollList(isScrolling, direction);
            }
        });

        mMessageListView.setOnListItemClickListener(new MessengerAdapter.OnItemClickListener() {
            @Override
            public void onClickItem(int messageType, Long id, Map<String, Object> messageData) {
                mPresenter.onListItemClick(messageType, id, messageData);
            }
        });

        mMessageListView.setOnLoadMoreListener(new EndlessRecyclerViewScrollAdapter.OnLoadMoreListener() {
            @Override
            public void loadMoreItems() {
                mPresenter.onLoadMoreItems();
            }
        });

        mReplyBoxView.setReplyBoxListener(new ReplyBoxContract.ReplyBoxListener() {
            @Override
            public void onClickSend(String message) {
                mPresenter.onClickSendInReplyView(message);
            }

            @Override
            public void onClickAddAttachment() {
                mPresenter.onClickAddAttachment();
            }

            @Override
            public void onTypeReply(String typedMessage) {
                mPresenter.onTypeReply(typedMessage);
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == REQUEST_CODE_ATTACHMENT) {
                File file = FileAttachmentUtil.getFileOnActivityResult(resultCode, data);
                if (file != null) {
                    mPresenter.onAttachmentAttached(FileAttachmentUtil.generateFileAttachment("attachment", file));
                }
            }
        } catch (Exception e) {
            String TAG = getClass().getName();
            KayakoLogHelper.e(TAG, "Unable to attach file in Messenger");
            KayakoLogHelper.logException(TAG, e);
            showToastMessage(getString(R.string.ko__attachment_msg_unable_to_attach_file));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.closePage();
    }

    private boolean hasPageLoaded() {
        return isAdded() && getActivity() != null;
    }

    @Override
    public void showToastMessage(String errorMessage) {
        // TODO: See if this method is really required
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setupListInMessageListingView(List<BaseListItem> baseListItems) {
        if (!hasPageLoaded()) {
            return;
        }

        synchronized (this) { // TODO: Ensure the message listing is updated at a time, shift this logic to library for all view rendering logic!
            mMessageListView.setupList(baseListItems);
        }
    }

    @Override
    public void showEmptyViewInMessageListingView() {
        if (!hasPageLoaded()) {
            return;
        }

        mMessageListView.showEmptyView();
    }

    @Override
    public void showErrorViewInMessageListingView() {
        if (!hasPageLoaded()) {
            return;
        }

        mMessageListView.showErrorView();
    }

    @Override
    public void showLoadingViewInMessageListingView() {
        if (!hasPageLoaded()) {
            return;
        }

        mMessageListView.showLoadingView();
    }

    @Override
    public void hideReplyBox() {
        if (!hasPageLoaded()) {
            return;
        }

        mReplyBoxView.hideReplyBox();
    }

    @Override
    public void showReplyBox() {
        if (!hasPageLoaded()) {
            return;
        }

        mReplyBoxView.showReplyBox();
    }

    @Override
    public void focusOnReplyBox() {
        if (!hasPageLoaded()) {
            return;
        }

        mReplyBoxView.focusOnReplyBox();
    }

    @Override
    public void collapseToolbar() {
        if (!hasPageLoaded()) {
            return;
        }

        mToolbarView.collapseToolbarView();
    }

    @Override
    public void expandToolbar() {
        if (!hasPageLoaded()) {
            return;
        }

        mToolbarView.expandToolbarView();
    }

    @Override
    public void setHasMoreItems(boolean hasMoreItems) {
        if (!hasPageLoaded()) {
            return;
        }

        mMessageListView.setHasMoreItemsToLoad(hasMoreItems);
    }

    @Override
    public void showLoadMoreView() {
        if (!hasPageLoaded()) {
            return;
        }

        mMessageListView.showLoadMoreView();
    }

    @Override
    public void hideLoadMoreView() {
        if (!hasPageLoaded()) {
            return;
        }

        mMessageListView.hideLoadMoreView();
    }

    @Override
    public void scrollToBottomOfList() {
        if (!hasPageLoaded()) {
            return;
        }


        mMessageListView.scrollToBottomOfList();
    }

    @Override
    public void openFilePickerForAttachments() {
        if (!hasPageLoaded()) {
            return;
        }

        FileAttachmentUtil.openFileChooserActivityFromFragment(this, REQUEST_CODE_ATTACHMENT);
    }

    @Override
    public void setAttachmentButtonVisibilityInReplyBox(boolean showAttachment) {
        if (!hasPageLoaded()) {
            return;
        }

        mReplyBoxView.setAttachmentButtonVisibility(showAttachment);
    }

    @Override
    public void configureToolbarForAssignedAgent(AssignedAgentData assignedAgentData) {
        if (!hasPageLoaded()) {
            return;
        }

        mToolbarView.configureForAssignedAgentView(assignedAgentData, true);
    }

    @Override
    public void configureToolbarForLastActiveAgents() {
        if (!hasPageLoaded()) {
            return;
        }

        mToolbarView.configureDefaultView();
    }
}
