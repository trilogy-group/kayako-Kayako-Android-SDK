package com.kayako.sdk.android.k5.messagelistpage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.kayako.sdk.android.k5.activities.KayakoSelectConversationActivity;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputEmailListItem;
import com.kayako.sdk.android.k5.common.fragments.MessengerListFragment;

import java.util.List;

public class MessageListView extends MessengerListFragment implements MessageListContract.View {

    private MessageListContract.Presenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = MessageListFactory.getPresenter(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity().getClass() != KayakoSelectConversationActivity.class) {
            throw new AssertionError("This fragment was intended to be used with KayakoSelectConversationActivity!");
        }

        Bundle bundle = getActivity().getIntent().getExtras();
        if (!bundle.containsKey(KayakoSelectConversationActivity.ARG_CONVERSATION_ID)) {
            throw new AssertionError("This fragment needs the containing activity to be passed a conversation id!");
        }

        mPresenter.initPage(bundle.getLong(KayakoSelectConversationActivity.ARG_CONVERSATION_ID));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.closePage();
    }

    @Override
    public void setupList(List<BaseListItem> messageList) {
        // TODO: Testing Submit button
        messageList.add(new InputEmailListItem(new InputEmailListItem.OnClickSubmitListener() {
            @Override
            public void onClickSubmit(String email) {
                Toast.makeText(getContext(), "SUBMIT WORKS", Toast.LENGTH_SHORT).show();
            }
        }));

        showListViewAndHideOthers();
        initMessengerList(messageList);
        // TODO: Pagination
    }

    @Override
    public void showEmptyView() {
        showEmptyViewAndHideOthers();
    }

    @Override
    public void showErrorView() {
        showErrorViewAndHideOthers();
    }

    @Override
    public void showLoadingView() {
        showLoadingViewAndHideOthers();
    }
}
