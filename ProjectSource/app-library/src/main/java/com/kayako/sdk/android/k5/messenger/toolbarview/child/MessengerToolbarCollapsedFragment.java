package com.kayako.sdk.android.k5.messenger.toolbarview.child;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.AssignedAgentData;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.LastActiveAgentsData;
import com.kayako.sdk.android.k5.messenger.toolbarview.MessengerToolbarContract;

public class MessengerToolbarCollapsedFragment extends BaseToolbarFragment implements MessengerToolbarContract.ChildToolbarConfigureView {

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
                },
                0);

        CommonToolbarViewUtil.customizeColorsToMatchMessengerStyle(mRoot);
    }

    private boolean isPageReady() {
        return isAdded() && getActivity() != null && !getActivity().isFinishing() && mRoot != null;
    }

    @Override
    public synchronized void update(@NonNull final LastActiveAgentsData data, final int unreadCount) {
        if (!isPageReadyButView()) {
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
                CommonToolbarViewUtil.setUnreadCount(mRoot, unreadCount);
            }
        });
    }

    @Override
    public synchronized void update(@NonNull final AssignedAgentData data, final int unreadCount) {
        if (!isPageReadyButView()) {
            return;
        }

        setOnViewLoadedListener(new OnViewLoadedListener() {
            @Override
            public void onViewLoaded() {
                if (!isPageReady()) {
                    return;
                }

                CommonToolbarViewUtil.setTitle(mRoot, data.getUser().getFullName());
                CommonToolbarViewUtil.setAssignedAgentAvatar(mRoot, data);
                CommonToolbarViewUtil.setSubtitleForUserLastActiveTime(mRoot, data);
                CommonToolbarViewUtil.setUnreadCount(mRoot, unreadCount);
            }
        });
    }

    @Override
    public synchronized void update(@NonNull final String title, final int unreadCount) {
        if (!isPageReadyButView()) {
            return;
        }

        setOnViewLoadedListener(new OnViewLoadedListener() {
            @Override
            public void onViewLoaded() {
                if (!isPageReady()) {
                    return;
                }

                CommonToolbarViewUtil.setOnlyTitle(mRoot, title);
                CommonToolbarViewUtil.customizeColorsToMatchMessengerStyle(mRoot);
                CommonToolbarViewUtil.setUnreadCount(mRoot, unreadCount);
            }
        });
    }

    @Override
    public void setExpandCollapseButtonClicked(MessengerToolbarContract.OnExpandOrCollapseListener listener) {
        mListener = listener;
    }
}
