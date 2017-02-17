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

public class MessageListContainerFragment extends Fragment implements MessageListContainerContract.View {

    private MessageListContainerContract.Presenter mPresenter;
    private MessageListContract.ConfigureView mMessageListView;
    private ReplyBoxContract.ConfigureView mReplyBoxView;

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

                // TODO: Testing
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (!bundle.containsKey(KayakoSelectConversationActivity.ARG_CONVERSATION_ID)) {
            throw new AssertionError("This fragment needs the containing activity to be passed a conversation id!");
        }
        mPresenter.initPage(bundle.getLong(KayakoSelectConversationActivity.ARG_CONVERSATION_ID));
    }

    @Override
    public void refreshMessageListing() {
        mMessageListView.refreshList();
    }

    @Override
    public void showToastMessage(String errorMessage) {
        // TODO: See if this method is really required
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }
}
