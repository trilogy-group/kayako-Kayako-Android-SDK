package com.kayako.sdk.android.k5.toolbarview;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.transition.AutoTransition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.toolbarview.child.AssignedAgentData;
import com.kayako.sdk.android.k5.toolbarview.child.LastActiveAgentsData;
import com.kayako.sdk.android.k5.toolbarview.child.MessengerToolbarCollapsedFragment;
import com.kayako.sdk.android.k5.toolbarview.child.MessengerToolbarExpandedFragment;

/**
 * One single fragment for all the forms that a toolbar may exist as.
 * <p>
 * Why is it built this way?
 * - Because a new conversation becomes an existing conversation which becomes an existing conversation with an assigned agent.
 * - The toolbar should be capable of changing forms.
 */
public class MessengerToolbarFragment extends Fragment implements MessengerToolbarContract.ConfigureView {

    private View mRoot;
    private ViewGroup mContainer;
    private Fragment mLastAddedChildFragment;

    private MessengerToolbarContract.MessengerToolbarType mToolbarType;
    private boolean mIsExpanded;
    private AssignedAgentData mAssignedAgentData;
    private LastActiveAgentsData mLastActiveAgentsData;

    private MessengerToolbarContract.Presenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = MessengerToolbarFactory.getPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mRoot = inflater.inflate(R.layout.ko__messenger_toolbar, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContainer = (ViewGroup) mRoot.findViewById(R.id.ko__messenger_child_toolbar_container);
        mContainer.setVisibility(View.GONE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.initPage();
    }

    private boolean isViewReady() {
        return getActivity() != null
                && !getActivity().isFinishing()
                && isAdded();
    }

    private Fragment generateChildFragment() {
        Fragment childFragment;

        switch (mToolbarType) {
            case ASSIGNED_AGENT:
                if (mIsExpanded) {
                    childFragment = new MessengerToolbarExpandedFragment();
                } else {
                    childFragment = new MessengerToolbarCollapsedFragment();
                }
                break;

            case LAST_ACTIVE_AGENTS:
                if (mIsExpanded) {
                    childFragment = new MessengerToolbarExpandedFragment();
                } else {
                    childFragment = new MessengerToolbarCollapsedFragment();
                }
                break;

            default:
                throw new IllegalStateException("Unhandled type!");
        }

        return childFragment;
    }

    private void setupSharedTransitions(Fragment lastFragment, Fragment newFragment, FragmentTransaction fragmentTransaction) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                && mLastAddedChildFragment != null) {

            MessengerToolbarContract.ChildToolbarConfigureView configureView = ((MessengerToolbarContract.ChildToolbarConfigureView) lastFragment);
            if (configureView.getView() == null) {
                return;
            }

            // Specify the transitions to apply
            newFragment.setSharedElementEnterTransition(new AutoTransition());
            newFragment.setSharedElementReturnTransition(new AutoTransition());
            newFragment.setEnterTransition(new AutoTransition());
            lastFragment.setExitTransition(new AutoTransition());

            // Common Transitions
            fragmentTransaction.addSharedElement(configureView.getView().findViewById(R.id.ko__messenger_toolbar_back_button), "ko__messenger_toolbar_back_button");
            fragmentTransaction.addSharedElement(configureView.getView().findViewById(R.id.ko__messenger_toolbar_title), "ko__messenger_toolbar_title");
            fragmentTransaction.addSharedElement(configureView.getView().findViewById(R.id.ko__messenger_toolbar_subtitle), "ko__messenger_toolbar_subtitle");

            // Specific Transitions
            switch (mToolbarType) {
                case ASSIGNED_AGENT:
                    fragmentTransaction
                            .addSharedElement(configureView.getView().findViewById(R.id.ko__messenger_toolbar_avatar3), "ko__messenger_toolbar_avatar3");
                    return;

                case LAST_ACTIVE_AGENTS:
                    fragmentTransaction
                            .addSharedElement(configureView.getView().findViewById(R.id.ko__messenger_toolbar_avatar3), "ko__messenger_toolbar_avatar3")
                            .addSharedElement(configureView.getView().findViewById(R.id.ko__messenger_toolbar_avatar2), "ko__messenger_toolbar_avatar2")
                            .addSharedElement(configureView.getView().findViewById(R.id.ko__messenger_toolbar_avatar1), "ko__messenger_toolbar_avatar1");
                    return;

                default:
                    throw new IllegalStateException("Unhandled type!");
            }
        } else {
            return;
        }
    }

    private void setupToolbar(MessengerToolbarContract.MessengerToolbarType toolbarType,
                              boolean isExpanded) {

        if (mToolbarType == null) {
            throw new IllegalStateException("The necessary fields are not initialized!");
        }

        Fragment childFragment = generateChildFragment();

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction().replace(R.id.ko__messenger_child_toolbar_container, childFragment);
        setupSharedTransitions(mLastAddedChildFragment, childFragment, fragmentTransaction);
        fragmentTransaction.commitNowAllowingStateLoss(); // ** NEEDS TO BE SYNCHRONOUS FOR CURRENT DESIGN

        // Child fragments must be configured ONLY once it's added to activity
        configureChildFragment(childFragment);

        mContainer.setVisibility(View.VISIBLE);
        mLastAddedChildFragment = childFragment;
    }

    private MessengerToolbarContract.ChildToolbarConfigureView configureChildFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            throw new IllegalStateException("This method should be called once the fragment is added!");
        }

        MessengerToolbarContract.ChildToolbarConfigureView configureView = ((MessengerToolbarContract.ChildToolbarConfigureView) fragment);
        configureView.setExpandCollapseButtonClicked(new MessengerToolbarContract.OnExpandOrCollapseListener() {
            @Override
            public void onCollapseOrExpand() {
                setupToolbar(mToolbarType, mIsExpanded = !mIsExpanded);
            }
        });

        switch (mToolbarType) {
            case ASSIGNED_AGENT:
                if (mAssignedAgentData == null) {
                    throw new IllegalStateException("Null AssignedAgent Data!");
                }

                configureView.update(mAssignedAgentData);
                break;
            case LAST_ACTIVE_AGENTS:
                if (mLastActiveAgentsData == null) {
                    throw new IllegalStateException("Null LastActiveAgents Data!");
                }

                configureView.update(mLastActiveAgentsData);
                break;

            default:
                throw new IllegalStateException("Unhandled type!");
        }

        return configureView;
    }

    @Override
    public void configureDefaultView() {
        mPresenter.configureDefaultView();
    }

    @Override
    public synchronized void configureForLastActiveUsersView(@NonNull LastActiveAgentsData data) {
        if (!isViewReady()) {
            return;
        }

        if (data == null) {
            throw new IllegalArgumentException("Null not allowed!");
        }

        mLastActiveAgentsData = data;
        setupToolbar(mToolbarType = MessengerToolbarContract.MessengerToolbarType.LAST_ACTIVE_AGENTS, mIsExpanded);
    }

    @Override
    public synchronized void configureForAssignedAgentView(@NonNull AssignedAgentData data) {
        if (!isViewReady()) {
            return;
        }

        if (data == null) {
            throw new IllegalArgumentException("Null not allowed!");
        }

        mAssignedAgentData = data;
        setupToolbar(mToolbarType = MessengerToolbarContract.MessengerToolbarType.ASSIGNED_AGENT,
                mIsExpanded);
    }

    @Override
    public synchronized void expandToolbarView() {
        setupToolbar(mToolbarType, mIsExpanded = true);
    }

    @Override
    public synchronized void collapseToolbarView() {
        setupToolbar(mToolbarType, mIsExpanded = false);
    }

}
