package com.kayako.sdk.android.k5.messenger.toolbarview;

import com.kayako.sdk.android.k5.core.KayakoLogHelper;
import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.android.k5.messenger.data.conversation.unreadcounter.OnUnreadCountChangeListener;
import com.kayako.sdk.android.k5.messenger.data.conversation.unreadcounter.UnreadCounterRepository;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.ConversationStarterHelper;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.IConversationStarterRepository;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.LastActiveAgentsData;
import com.kayako.sdk.error.KayakoException;
import com.kayako.sdk.messenger.conversationstarter.ConversationStarter;

public class MessengerToolbarPresenter implements MessengerToolbarContract.Presenter, IConversationStarterRepository.OnLoadConversationStarterListener {

    // TODO: Handle situation where toolbar is configured to stay expanded - BUT there is not data to show in expanded view. Collapse then?

    private MessengerToolbarContract.ConfigureView mView;
    private IConversationStarterRepository mData;

    private boolean mIsLastActiveAgentsView;
    private boolean mShowUnreadCounter = true; // default should be true

    public MessengerToolbarPresenter(MessengerToolbarContract.ConfigureView view, IConversationStarterRepository data) {
        mView = view;
        mData = data;
    }

    @Override
    public void initPage() {
        if (!mView.isToolbarAreadyConfigured()) {
            // Set in case there's a network error - otherwise toolbar covers entire screen
            String brand = MessengerPref.getInstance().getBrandName();
            mView.configureForLastActiveUsersView(
                    new LastActiveAgentsData(
                            brand,
                            -1L, // Default average response time // TODO: Change it to a vague answer or remove line?
                            null, null, null),
                    mShowUnreadCounter);

            // TODO: Show this version of the toolbar ONLY
            mData.getConversationStarter(null);
        }
    }

    @Override
    public void closePage() {
        unsubscribeToUnreadCounters();
    }

    @Override
    public void configureDefaultView() {
        mIsLastActiveAgentsView = true;
        mData.getConversationStarter(this);

        mShowUnreadCounter = true;
        configureToolbarForUnreadCounter(true);
    }

    @Override
    public void configureOtherView(boolean showUnreadCounter) {
        mIsLastActiveAgentsView = false;

        mShowUnreadCounter = showUnreadCounter;
        configureToolbarForUnreadCounter(showUnreadCounter);
    }

    // UNREAD COUNTER METHODS

    @Override
    public int getUnreadCount() {
        if (mShowUnreadCounter) {
            return UnreadCounterRepository.getsUnreadCounter();
        } else {
            return 0;
        }
    }

    private void configureToolbarForUnreadCounter(boolean showUnreadCount) {
        if (showUnreadCount) {
            subscribeToUnreadCounters();
        } else {
            unsubscribeToUnreadCounters();
        }
    }

    private final OnUnreadCountChangeListener mOnUnreadCounterChangeListener = new OnUnreadCountChangeListener() {
        @Override
        public void onUnreadCountChanged(int newUnreadCount) {
            if (mShowUnreadCounter) {
                mView.refreshUnreadCounter(newUnreadCount);
            }
        }
    };

    private void subscribeToUnreadCounters() {
        UnreadCounterRepository.addListener(mOnUnreadCounterChangeListener);
    }

    private void unsubscribeToUnreadCounters() {
        UnreadCounterRepository.removeListener(mOnUnreadCounterChangeListener);
    }


    // API CALLBACKS

    @Override
    public synchronized void onLoadConversationMetrics(ConversationStarter conversationStarter) {
        if (!mIsLastActiveAgentsView) {
            return; // Skip if not set to conversation metric view
        }

        try {
            LastActiveAgentsData lastActiveAgentsData = ConversationStarterHelper.convertToLastActiveAgentsData(conversationStarter);
            if (lastActiveAgentsData != null) {
                mView.configureForLastActiveUsersView(lastActiveAgentsData, mShowUnreadCounter);
            }
        } catch (Exception e) {
            KayakoLogHelper.e(getClass().getName(), "Conversation starter failed to load correctly for Messenger Toolbar");
            KayakoLogHelper.logException(getClass().getName(), e);
        }
    }

    @Override
    public synchronized void onFailure(KayakoException exception) {
        // TODO: Show default view? - especially when there's no network
        // TODO: Show a toast or some message?
    }

}
