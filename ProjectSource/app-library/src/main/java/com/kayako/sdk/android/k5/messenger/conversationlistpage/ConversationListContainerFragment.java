package com.kayako.sdk.android.k5.messenger.conversationlistpage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.activities.KayakoSelectConversationActivity;
import com.kayako.sdk.android.k5.common.fragments.ListPageState;
import com.kayako.sdk.android.k5.common.fragments.OnListPageStateChangeListener;
import com.kayako.sdk.android.k5.messenger.toolbarview.MessengerToolbarContract;

public class ConversationListContainerFragment extends Fragment implements ConversationListContainerContract.View {

    private ConversationListContainerContract.Presenter mPresenter;
    private ConversationListContract.ConfigureView mConversationListView;
    private MessengerToolbarContract.ConfigureView mToolbarView;
    private View mRoot;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mPresenter = ConversationListContainerFactory.getPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mRoot = inflater.inflate(R.layout.ko__fragment_conversation_list_container, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View newConversationButton = mRoot.findViewById(R.id.ko__new_conversation_button);
        newConversationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onClickNewConversation();
            }
        });

        mConversationListView = (ConversationListContract.ConfigureView) getChildFragmentManager().findFragmentById(R.id.ko__conversation_list);
        mConversationListView.setOnScrollListener(new ConversationListContract.OnScrollListener() {
            @Override
            public void onScroll(boolean isScrolling) {
                mPresenter.onScrollConversationList(isScrolling);
            }
        });
        mConversationListView.setOnPageStateChangeListener(new OnListPageStateChangeListener() {
            @Override
            public void onListPageStateChanged(ListPageState state) {
                mPresenter.onPageStateChange(state);
            }
        });

        mToolbarView = (MessengerToolbarContract.ConfigureView) getChildFragmentManager().findFragmentById(R.id.ko__messenger_toolbar);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mToolbarView.configureForSimpleTitle(getString(R.string.ko__messenger_toolbar_title_conversations), false);
        mPresenter.onOpenPage();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onClosePage();
    }

    private boolean hasPageLoaded() {
        return isAdded() && getActivity() != null;
    }

    @Override
    public void hideNewConversationButton() {
        if (!hasPageLoaded()) {
            return;
        }

        View button = mRoot.findViewById(R.id.ko__new_conversation_button);
        button.setVisibility(View.GONE);
        // TODO: Animations?
    }

    @Override
    public void showNewConversationButton() {
        if (!hasPageLoaded()) {
            return;
        }

        View button = mRoot.findViewById(R.id.ko__new_conversation_button);
        button.setVisibility(View.VISIBLE);
        // TODO: Animations?
    }

    @Override
    public void openNewConversationPage(int requestCode) {
        if (!hasPageLoaded()) {
            return;
        }

        KayakoSelectConversationActivity.startActivityForResult((AppCompatActivity) getActivity(), this, requestCode);
    }

    @Override
    public void reloadConversations() {
        if (!hasPageLoaded()) {
            return;
        }

        mConversationListView.reloadConversations();
    }

}
