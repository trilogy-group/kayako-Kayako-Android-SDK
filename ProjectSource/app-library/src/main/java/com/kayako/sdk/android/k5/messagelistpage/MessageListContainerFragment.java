package com.kayako.sdk.android.k5.messagelistpage;


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
import com.kayako.sdk.android.k5.common.fragments.ListPageState;
import com.kayako.sdk.android.k5.common.fragments.OnListPageStateChangeListener;
import com.kayako.sdk.android.k5.common.fragments.OnScrollListListener;
import com.kayako.sdk.android.k5.toolbarview.child.LastActiveAgentsData;
import com.kayako.sdk.android.k5.toolbarview.MessengerToolbarContract;

import java.util.List;

public class MessageListContainerFragment extends Fragment implements MessageListContainerContract.View {

    private MessageListContainerContract.Presenter mPresenter;
    private MessageListContract.ConfigureView mMessageListView;
    private ReplyBoxContract.ConfigureView mReplyBoxView;
    private MessengerToolbarContract.ConfigureView mToolbarView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mReplyBoxView.setReplyBoxListener(new ReplyBoxContract.ReplyBoxListener() {
            @Override
            public void onClickSend(String message) {
                mPresenter.onClickSendInReplyView(message);
            }
        });

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

        mToolbarView = (MessengerToolbarContract.ConfigureView) getChildFragmentManager().findFragmentById(R.id.ko__messenger_toolbar);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null && bundle.containsKey(KayakoSelectConversationActivity.ARG_CONVERSATION_ID)) {
            mPresenter.initPage(false, bundle.getLong(KayakoSelectConversationActivity.ARG_CONVERSATION_ID));
        } else {
            mPresenter.initPage(true, null);
        }

        // Call on Activity Created
        mToolbarView.configureDefaultView();
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
}
