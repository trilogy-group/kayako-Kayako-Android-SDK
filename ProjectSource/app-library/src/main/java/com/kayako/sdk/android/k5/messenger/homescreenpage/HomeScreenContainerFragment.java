package com.kayako.sdk.android.k5.messenger.homescreenpage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.activities.KayakoSelectConversationActivity;
import com.kayako.sdk.android.k5.messenger.style.MessengerTemplateHelper;

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
        MessengerTemplateHelper.applyBackgroundTheme(mRoot);
        return mRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRoot.findViewById(R.id.ko__new_conversation_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onClickNewConversationButton();
            }
        });
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
        if (!hasPageLoaded()) {
            return;
        }

        mRoot.findViewById(R.id.ko__new_conversation_button).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNewConversationButton() {
        if (!hasPageLoaded()) {
            return;
        }

        mRoot.findViewById(R.id.ko__new_conversation_button).setVisibility(View.GONE);
    }

    @Override
    public void openNewConversationPage() {
        if (!hasPageLoaded()) {
            return;
        }

        startActivity(KayakoSelectConversationActivity.getIntent(getContext()));
    }
}
