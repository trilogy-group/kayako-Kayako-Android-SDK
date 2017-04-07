package com.kayako.sdk.android.k5.messenger.messagelistpage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.activities.KayakoSelectConversationActivity;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.loadmorelist.EndlessRecyclerViewScrollAdapter;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerAdapter;
import com.kayako.sdk.android.k5.common.fragments.ListPageState;
import com.kayako.sdk.android.k5.common.fragments.MessengerListFragment;
import com.kayako.sdk.android.k5.common.fragments.OnListPageStateChangeListener;
import com.kayako.sdk.android.k5.common.fragments.OnScrollListListener;
import com.kayako.sdk.android.k5.core.Kayako;

import java.util.List;

public class MessageListFragment extends MessengerListFragment implements MessageListContract.View, MessageListContract.ConfigureView {

    private MessageListContract.Presenter mPresenter;
    private MessengerAdapter.OnItemClickListener mOnItemClickListener;
    private OnListPageStateChangeListener mOnListPageStateChangeListener;
    private MessageListContract.OnErrorListener mErrorListener;
    private EndlessRecyclerViewScrollAdapter.OnLoadMoreListener mLoadMoreListener;
    private OnScrollListListener mOnScrollListener;
    private boolean isListAlreadyInitialized;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = MessageListFactory.getPresenter(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.setOnListPageChangeStateListener(new OnListPageStateChangeListener() {
            @Override
            public void onListPageStateChanged(ListPageState state) {
                if (mOnListPageStateChangeListener != null) {
                    mOnListPageStateChangeListener.onListPageStateChanged(state);
                }
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity().getClass() != KayakoSelectConversationActivity.class) {
            throw new AssertionError("This fragment was intended to be used with KayakoSelectConversationActivity!");
        }

        mPresenter.initPage();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.closePage();
    }

    private boolean hasPageLoaded(){
        return isAdded();
    }

    @Override
    public void setupList(List<BaseListItem> messageList) {
        if(!hasPageLoaded()){
            return;
        }

        if (messageList == null) {
            throw new IllegalStateException("Null argument unacceptable!");
        }

        if (isListAlreadyInitialized) {
            replaceMessengerList(messageList);
        } else {
            initMessengerList(messageList);
            if (mOnItemClickListener != null) {
                super.setOnItemClickListener(mOnItemClickListener);
            }
            if (mOnScrollListener != null) {
                super.addScrollListListener(mOnScrollListener);
            }
            if (mLoadMoreListener != null) {
                super.setLoadMoreListener(mLoadMoreListener);
            }
            isListAlreadyInitialized = true;
        }

        showListViewAndHideOthers();
    }

    @Override
    public void setHasMoreItemsToLoad(boolean hasMoreItems) {
        if(!hasPageLoaded()){
            return;
        }

        super.setHasMoreItems(hasMoreItems);
    }

    @Override
    public void showLoadMoreView() {
        if(!hasPageLoaded()){
            return;
        }

        super.showLoadMoreProgress();

    }

    @Override
    public void hideLoadMoreView() {
        if(!hasPageLoaded()){
            return;
        }

        super.hideLoadMoreProgress();
    }

    @Override
    public void scrollToBottomOfList() {
        if(!hasPageLoaded()){
            return;
        }

        super.scrollToEndOfList();
    }


    @Override
    public void showEmptyView() {
        if(!hasPageLoaded()){
            return;
        }

        showEmptyViewAndHideOthers();
    }

    @Override
    public void showErrorView() {
        if(!hasPageLoaded()){
            return;
        }

        Context context = Kayako.getApplicationContext();

        if (mErrorListener != null) {
            getDefaultStateViewHelper().setupErrorView(context.getResources().getString(R.string.ko__label_error_view_title), context.getResources().getString(R.string.ko__label_error_view_description), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mErrorListener.onClickRetry();
                }
            });
        }
        showErrorViewAndHideOthers();
    }

    @Override
    public void showLoadingView() {
        if(!hasPageLoaded()){
            return;
        }

        showLoadingViewAndHideOthers();
    }

    @Override
    public void setOnListPageStateChangeListener(OnListPageStateChangeListener listener) {
        mOnListPageStateChangeListener = listener;
    }

    @Override
    public void setOnListScrollListener(OnScrollListListener listener) {
        mOnScrollListener = listener;
    }

    @Override
    public void setOnListItemClickListener(MessengerAdapter.OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    @Override
    public void setOnLoadMoreListener(EndlessRecyclerViewScrollAdapter.OnLoadMoreListener listener) {
        mLoadMoreListener = listener;
    }

    @Override
    public void setOnErrorListener(MessageListContract.OnErrorListener listener) {
        mErrorListener = listener;
    }

}
