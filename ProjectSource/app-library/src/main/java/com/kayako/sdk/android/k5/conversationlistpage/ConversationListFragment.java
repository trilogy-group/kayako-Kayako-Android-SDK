package com.kayako.sdk.android.k5.conversationlistpage;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.activities.KayakoConversationListActivity;
import com.kayako.sdk.android.k5.activities.KayakoSelectConversationActivity;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.conversationlist.ConversationListAdapter;
import com.kayako.sdk.android.k5.common.adapter.loadmorelist.EndlessRecyclerViewScrollAdapter;
import com.kayako.sdk.android.k5.common.fragments.BaseListFragment;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.messenger.conversation.Conversation;

import java.util.List;

public class ConversationListFragment extends BaseListFragment implements ConversationListContract.View {

    private ConversationListContract.Presenter mPresenter;
    private Handler mHandler;
    // TODO: Receive fingerprintId
    // TODO: Redesign the Views - placeholders for loading

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = ConversationListFactory.getPresenter(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mHandler = new Handler();
        mPresenter.initPage();
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
    public void setupList(final List<BaseListItem> conversations) {
        if (!hasPageLoaded()) {
            return;
        }

        ConversationListAdapter conversationListAdapter = new ConversationListAdapter(conversations, new ConversationListAdapter.OnClickConversationListener() {
            @Override
            public void onClickConversation(Conversation conversation) {
                mPresenter.onClickConversation(conversation);
            }
        });

        initList(conversationListAdapter);
        setLoadMoreListener(new EndlessRecyclerViewScrollAdapter.OnLoadMoreListener() {
            @Override
            public void loadMoreItems() {
                mPresenter.onLoadMoreItems();
            }
        });

        showListViewAndHideOthers();
    }

    @Override
    public void appendToEndOfListAndStopLoadMoreProgress(List<BaseListItem> conversations) {
        if (!hasPageLoaded()) {
            return;
        }

        addItemsToEndOfList(conversations);
    }

    @Override
    public void showEmptyView() {
        if (!hasPageLoaded()) {
            return;
        }

        Context context = Kayako.getApplicationContext();
        getDefaultStateViewHelper().setupErrorView(context.getString(R.string.ko__label_error_view_title), context.getString(R.string.ko__label_error_view_description), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onClickRetryOnError();
            }
        });
        showEmptyViewAndHideOthers();
    }

    @Override
    public void showErrorView() {
        if (!hasPageLoaded()) {
            return;
        }

        showErrorViewAndHideOthers();
    }

    @Override
    public void showLoadingView() {
        if (!hasPageLoaded()) {
            return;
        }

        showLoadingViewAndHideOthers();
    }

    @Override
    public void openMessageListPage(final Long id) {
        if (!hasPageLoaded()) {
            return;
        }

        startActivity(KayakoSelectConversationActivity.getIntent(getActivity(), id));
    }

    @Override
    public void showMessage(String message) {
        if (!hasPageLoaded()) {
            return;
        }

        Toast.makeText(Kayako.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setListHasMoreItems(boolean hasMore) {
        if (!hasPageLoaded()) {
            return;
        }

        setHasMoreItems(hasMore);
    }

    @Override
    public void configureLoadMoreView(boolean showLoadMoreProgress) {
        if (!hasPageLoaded()) {
            return;
        }

        if (showLoadMoreProgress) {
            showLoadMoreProgress();
        } else {
            hideLoadMoreProgress();
        }
    }

    @Override
    public boolean getIfListHasMoreItems() {
        if (!hasPageLoaded()) {
            return false;
        }

        return hasMoreItems();
    }
}
