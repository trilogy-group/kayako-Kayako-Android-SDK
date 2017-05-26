package com.kayako.sdk.android.k5.messenger.toolbarview;

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
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.core.KayakoLogHelper;
import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.AssignedAgentData;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.LastActiveAgentsData;
import com.kayako.sdk.android.k5.messenger.style.MessengerTemplateHelper;
import com.kayako.sdk.android.k5.messenger.toolbarview.child.MessengerToolbarCollapsedFragment;
import com.kayako.sdk.android.k5.messenger.toolbarview.child.MessengerToolbarExpandedFragment;

/**
 * One single fragment for all the forms that a toolbar may exist as.
 * <p>
 * Why is it built this way?
 * - Because a new conversation becomes an existing conversation which becomes an existing conversation with an assigned agent.
 * - The toolbar should be capable of changing forms.
 */
public class MessengerToolbarFragment extends Fragment implements MessengerToolbarContract.ConfigureView {

    private static final String FRAGMENT_TAG = "KayakoToolbarFragmentTag";

    private View mRoot;
    private ViewGroup mContainer;

    private Fragment mLastAddedChildFragment;
    private MessengerToolbarContract.MessengerToolbarType mToolbarType;
    private boolean mIsExpanded;
    private AssignedAgentData mAssignedAgentData;
    private LastActiveAgentsData mLastActiveAgentsData;

    private String mTitle;
    private MessengerToolbarContract.Presenter mPresenter;

    private boolean showAnimations = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = MessengerToolbarFactory.getPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.ko__messenger_toolbar, container);
        MessengerTemplateHelper.applyBackgroundTheme(mRoot);
        return mRoot;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.closePage();
    }

    private boolean isViewReady() {
        return getActivity() != null
                && !getActivity().isFinishing()
                && isAdded();
    }

    private void setupToolbar() {
        if (mToolbarType == null) { // Default toolbar type
            throw new IllegalStateException("The necessary fields are not initialized!");
        }

        /**
         * Weird Issue in Android Support Library that causes the following crash (happens only on the Agent App - not sure why)
         * - java.lang.NullPointerException: Attempt to invoke virtual method 'boolean java.lang.String.equals(java.lang.Object)' on a null object reference at android.support.v4.app.FragmentTransitionCompat21.setNameOverridesOptimized(FragmentTransitionCompat21.java:339)
         * - java.lang.IndexOutOfBoundsException: Index: 3, Size: 3 at java.util.ArrayList.get(ArrayList.java:411) at android.support.v4.app.FragmentTransitionCompat21.setNameOverridesOptimized(FragmentTransitionCompat21.java:330)
         *
         * Crash avoided when shared transitions are not added. Therefore, reluctantly, disabling animations for now
         */
        Fragment childFragment = commitTransactionWithoutAnimation();

        // Child fragments must be configured ONLY once it's added to activity
        configureChildFragment(childFragment);

        mContainer.setVisibility(View.VISIBLE);
        mLastAddedChildFragment = childFragment;
    }

    private Fragment generateChildFragment() {
        Fragment childFragment;

        switch (mToolbarType) {
            case ASSIGNED_AGENT:
                // Only collapsed view supported
                childFragment = new MessengerToolbarCollapsedFragment();
                mIsExpanded = false;
                break;

            case LAST_ACTIVE_AGENTS:
                if (mIsExpanded) {
                    childFragment = new MessengerToolbarExpandedFragment();
                } else {
                    childFragment = new MessengerToolbarCollapsedFragment();
                }
                break;

            case SIMPLE_TITLE:
                // Only collapsed view supported
                childFragment = new MessengerToolbarCollapsedFragment();
                mIsExpanded = false;
                break;

            default:
                throw new IllegalStateException("Unhandled type!");
        }

        return childFragment;
    }

    private Fragment commitTransactionWithoutAnimation() {
        Fragment childFragment = generateChildFragment();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction().replace(R.id.ko__messenger_child_toolbar_container, childFragment, FRAGMENT_TAG);
        fragmentTransaction.commitNowAllowingStateLoss(); // ** NEEDS TO BE SYNCHRONOUS FOR CURRENT DESIGN
        return childFragment;
    }

    private void removeAddedTransactedFragment() {
        Fragment previouoslyTransanctedFragment = getChildFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (previouoslyTransanctedFragment != null) {
            getChildFragmentManager().beginTransaction().remove(previouoslyTransanctedFragment).commitAllowingStateLoss();
        }

        ViewGroup viewGroup = (ViewGroup) mRoot.findViewById(R.id.ko__messenger_child_toolbar_container);
        viewGroup.removeAllViews();
    }

    private Fragment commitTransactionWithAnimation() {
        Fragment childFragment = generateChildFragment();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction().replace(R.id.ko__messenger_child_toolbar_container, childFragment, FRAGMENT_TAG);
        setupSharedTransitions(mLastAddedChildFragment, childFragment, fragmentTransaction);
        fragmentTransaction.commitNowAllowingStateLoss(); // ** NEEDS TO BE SYNCHRONOUS FOR CURRENT DESIGN
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
                case LAST_ACTIVE_AGENTS:
                    fragmentTransaction
                            .addSharedElement(configureView.getView().findViewById(R.id.ko__messenger_toolbar_avatar3), "ko__messenger_toolbar_avatar3")
                            .addSharedElement(configureView.getView().findViewById(R.id.ko__messenger_toolbar_avatar2), "ko__messenger_toolbar_avatar2")
                            .addSharedElement(configureView.getView().findViewById(R.id.ko__messenger_toolbar_avatar1), "ko__messenger_toolbar_avatar1");
                    return;

                case ASSIGNED_AGENT:
                    // no expanded view - not relevant
                    return;

                case SIMPLE_TITLE:
                    // No avatars shown - not relevant
                    return;

                default:
                    throw new IllegalStateException("Unhandled type!");
            }
        } else {
            return;
        }
    }

    private MessengerToolbarContract.ChildToolbarConfigureView configureChildFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            throw new IllegalStateException("This method should be called once the fragment is added!");
        }

        MessengerToolbarContract.ChildToolbarConfigureView configureView = ((MessengerToolbarContract.ChildToolbarConfigureView) fragment);
        configureView.setExpandCollapseButtonClicked(new MessengerToolbarContract.OnExpandOrCollapseListener() {
            @Override
            public synchronized void onCollapseOrExpand() {
                mIsExpanded = !mIsExpanded;
                setupToolbar();
            }
        });

        int unreadCount = mPresenter.getUnreadCount();

        switch (mToolbarType) {
            case ASSIGNED_AGENT:
                if (mAssignedAgentData == null) {
                    throw new IllegalStateException("Null AssignedAgent Data!");
                }

                configureView.update(mAssignedAgentData, unreadCount);
                break;

            case LAST_ACTIVE_AGENTS:
                if (mLastActiveAgentsData == null) {
                    throw new IllegalStateException("Null LastActiveAgents Data!");
                }

                configureView.update(mLastActiveAgentsData, unreadCount);
                break;

            case SIMPLE_TITLE:
                if (mTitle == null) {
                    throw new IllegalStateException("Null LastActiveAgents Data!");
                }

                configureView.update(mTitle, unreadCount);
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
    public synchronized void configureForLastActiveUsersView(@NonNull LastActiveAgentsData data, boolean showUnreadCount) {
        if (!isViewReady()) {
            return;
        }

        if (data == null) {
            throw new IllegalArgumentException("Null not allowed!");
        }


        mPresenter.configureOtherView(showUnreadCount);
        mLastActiveAgentsData = data;
        mToolbarType = MessengerToolbarContract.MessengerToolbarType.LAST_ACTIVE_AGENTS;
        setupToolbar();
    }

    @Override
    public synchronized void configureForAssignedAgentView(@NonNull AssignedAgentData data, boolean showUnreadCount) {
        if (!isViewReady()) {
            return;
        }

        if (data == null) {
            throw new IllegalArgumentException("Null not allowed!");
        }

        mPresenter.configureOtherView(showUnreadCount);
        mAssignedAgentData = data;
        mToolbarType = MessengerToolbarContract.MessengerToolbarType.ASSIGNED_AGENT;
        setupToolbar();
    }

    @Override
    public void configureForSimpleTitle(@NonNull String title, boolean showUnreadCount) {
        if (!isViewReady()) {
            return;
        }

        if (title == null) {
            throw new IllegalArgumentException("Null not allowed!");
        }

        mPresenter.configureOtherView(showUnreadCount);
        mTitle = title;
        mToolbarType = MessengerToolbarContract.MessengerToolbarType.SIMPLE_TITLE;
        setupToolbar();
    }

    @Override
    public synchronized void expandToolbarView() {
        if (!isViewReady() || mToolbarType == null) {
            return;
        } else if (isToolbarCollapsed()) { // Prevent multiple UI changes if already expanded
            mIsExpanded = true;
            setupToolbar();
        }
    }

    @Override
    public synchronized void collapseToolbarView() {
        if (!isViewReady() || mToolbarType == null) {
            return;
        } else if (isToolbarExpanded()) { // Prevent multiple UI changes if already collapsed
            mIsExpanded = false;
            setupToolbar();
        }
    }

    @Override
    public boolean isToolbarCollapsed() {
        return mLastAddedChildFragment != null && mLastAddedChildFragment instanceof MessengerToolbarCollapsedFragment;
    }

    @Override
    public boolean isToolbarExpanded() {
        return mLastAddedChildFragment != null && mLastAddedChildFragment instanceof MessengerToolbarExpandedFragment;
    }

    @Override
    public boolean isToolbarAreadyConfigured() {
        return mLastAddedChildFragment != null && mToolbarType != null;
    }

    @Override
    public void refreshUnreadCounter(int newUnreadCount) {
        if (!isViewReady() || mToolbarType == null) {
            return;
        }

        setupToolbar(); // refresh toolbar
    }
}
