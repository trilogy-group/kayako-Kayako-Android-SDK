package com.kayako.sdk.android.k5.messenger.toolbarview.child;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.AssignedAgentData;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.LastActiveAgentsData;
import com.kayako.sdk.android.k5.messenger.toolbarview.MessengerToolbarContract;

public class MessengerToolbarExpandedFragment extends BaseToolbarFragment implements MessengerToolbarContract.ChildToolbarConfigureView {

    private View mRoot;
    private MessengerToolbarContract.OnExpandOrCollapseListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mRoot = inflater.inflate(R.layout.ko__messenger_toolbar_expanded, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CommonToolbarViewUtil.setupCommonToolbar(mRoot, getActivity(),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.onCollapseOrExpand();
                        }
                    }
                },
                0);
    }

    private boolean isPageReady() {
        return isAdded() && getActivity() != null && !getActivity().isFinishing() && mRoot != null;
    }

    @Override
    public void update(@NonNull final LastActiveAgentsData data, final int unreadCount) {
        if (!isPageReadyButView() && data != null) {
            return;
        }

        setOnViewLoadedListener(new OnViewLoadedListener() {
            @Override
            public void onViewLoaded() {
                if (!isPageReady()) {
                    return;
                }

                CommonToolbarViewUtil.setTitle(mRoot, data.getBrandName());
                CommonToolbarViewUtil.setSubtitleForAverageResponseTime(mRoot, data.getAverageReplyTime());
                CommonToolbarViewUtil.setLastActiveAgentAvatars(mRoot, data);

                CommonToolbarViewUtil.customizeColorsToMatchMessengerStyle(mRoot);
                CommonToolbarViewUtil.customizeColorsToMatchMessengerStyleForExpandedToolbar(mRoot);
                CommonToolbarViewUtil.setUnreadCount(mRoot, unreadCount);
            }
        });
    }

    @Override
    public void update(@NonNull AssignedAgentData data, int unreadCount) {
        throw new IllegalStateException("This method should never be called. For Assigned Agent view - expanded view should not exist!");
    }

    @Override
    public void update(@NonNull String title, int unreadCount) {
        throw new IllegalStateException("This method should never be called. For simple view - expanded view should not exist!");
    }

    @Override
    public void setExpandCollapseButtonClicked(MessengerToolbarContract.OnExpandOrCollapseListener listener) {
        mListener = listener;
    }

}
