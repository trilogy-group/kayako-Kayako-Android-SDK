package com.kayako.sdk.android.k5.messenger.homescreenpage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayako.sdk.android.k5.R;

public class HomeScreenContainerFragment extends Fragment implements HomeScreenContainerContract.View {

    private HomeScreenContainerContract.Presenter mPresenter;
    private View mRoot;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = HomeScreenContainerFactory.getPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.ko__fragment_home_screen_container, container, false);
        return mRoot;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.initPage();
    }

    private boolean hasPageLoaded() {
        return isAdded() && getActivity() != null;
    }

    @Override
    public void showNewConversationButton() {
        mRoot.findViewById(R.id.ko__new_conversation_button).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNewConversationButton() {
        mRoot.findViewById(R.id.ko__new_conversation_button).setVisibility(View.GONE);
    }

    @Override
    public void showReplyBoxButton() {
        if (!hasPageLoaded()) {
            return;
        }

        Fragment replyBoxFragment = getChildFragmentManager().findFragmentById(R.id.ko__home_screen_reply_box_fragment);
        getChildFragmentManager().beginTransaction().show(replyBoxFragment).commit();
    }

    @Override
    public void hideReplyBoxButton() {
        if (!hasPageLoaded()) {
            return;
        }

        Fragment replyBoxFragment = getChildFragmentManager().findFragmentById(R.id.ko__home_screen_reply_box_fragment);
        getChildFragmentManager().beginTransaction().hide(replyBoxFragment).commit();
    }
}
