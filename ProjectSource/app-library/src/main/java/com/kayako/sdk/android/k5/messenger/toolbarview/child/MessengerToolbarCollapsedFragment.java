package com.kayako.sdk.android.k5.messenger.toolbarview.child;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.messenger.toolbarview.MessengerToolbarContract;

public class MessengerToolbarCollapsedFragment extends Fragment implements MessengerToolbarContract.ChildToolbarConfigureView {

    private View mRoot;
    private MessengerToolbarContract.OnExpandOrCollapseListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mRoot = inflater.inflate(R.layout.ko__messenger_toolbar_collapsed, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CommonToolbarViewUtil.setupCommonToolbar(
                mRoot,
                getActivity(),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.onCollapseOrExpand();
                        }
                    }
                });

        CommonToolbarViewUtil.customizeColorsToMatchMessengerStyle(mRoot);
    }

    private boolean isPageReady() {
        return isAdded() && getActivity() != null && !getActivity().isFinishing() && mRoot != null;
    }


    @Override
    public synchronized void update(@NonNull LastActiveAgentsData data) {
        if (!isPageReady()) {
            return;
        }

        CommonToolbarViewUtil.setTitle(mRoot, data.getBrandName());
        CommonToolbarViewUtil.setSubtitleForAverageResponseTime(mRoot, data.getAverageReplyTime());
        CommonToolbarViewUtil.setLastActiveAgentAvatars(mRoot, data.getUser1(), data.getUser2(), data.getUser3());
    }

    @Override
    public synchronized void update(@NonNull AssignedAgentData data) {
        if (!isPageReady()) {
            return;
        }


    }


    @Override
    public void setExpandCollapseButtonClicked(MessengerToolbarContract.OnExpandOrCollapseListener listener) {
        mListener = listener;
    }
}
