package com.kayako.sdk.android.k5.messenger.messagelistpage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.activities.KayakoSelectConversationActivity;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.fragments.ListPageState;
import com.kayako.sdk.android.k5.common.fragments.MessengerListFragment;
import com.kayako.sdk.android.k5.common.fragments.OnListPageStateChangeListener;
import com.kayako.sdk.android.k5.common.fragments.OnScrollListListener;
import com.kayako.sdk.android.k5.core.Kayako;

import java.util.List;

public class MessageListFragment extends MessengerListFragment implements MessageListContract.View, MessageListContract.ConfigureView {

    private MessageListContract.Presenter mPresenter;
    private OnListPageStateChangeListener mOnListPageStateChangeListener;
    private MessageListContract.OnErrorListener mErrorListener;
    private OnScrollListListener mOnScrollListener;

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

    @Override
    public void setupList(List<BaseListItem> messageList) {
        // TODO: Test pre-submitted value
        // messageList.add(new InputEmailListItem("prefilled@yeah.com"));

        // TODO: Testing Submit button
        /*messageList.add(new InputEmailListItem(new InputEmailListItem.OnClickSubmitListener() {
            @Override
            public void onClickSubmit(String email) {
                Toast.makeText(getContext(), "SUBMIT WORKS", Toast.LENGTH_SHORT).show();
            }
        }));*/

        // TODO: Testing BOT Message
        /*messageList.add(new BotMessageListItem("What would you like to talk about?", 0, null));*/

        initMessengerList(messageList);
        showListViewAndHideOthers();

        super.addScrollListListener(mOnScrollListener);
        // TODO: Pagination
    }

    @Override
    public void showEmptyView() {
        showEmptyViewAndHideOthers();
    }

    @Override
    public void showErrorView() {
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
    public void setOnErrorListener(MessageListContract.OnErrorListener listener) {
        mErrorListener = listener;
    }

}
