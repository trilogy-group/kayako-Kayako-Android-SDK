package com.kayako.sdk.android.k5.messenger.toolbarview;

import com.kayako.sdk.android.k5.core.KayakoLogHelper;
import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.IConversationStarterRepository;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.LastActiveAgentsData;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.ConversationStarterHelper;
import com.kayako.sdk.error.KayakoException;
import com.kayako.sdk.messenger.conversationstarter.ConversationStarter;

public class MessengerToolbarPresenter implements MessengerToolbarContract.Presenter, IConversationStarterRepository.OnLoadConversationStarterListener {

    // TODO: Handle situation where toolbar is configured to stay expanded - BUT there is not data to show in expanded view. Collapse then?

    private MessengerToolbarContract.ConfigureView mView;
    private IConversationStarterRepository mData;

    private boolean mIsLastActiveAgentView;

    public MessengerToolbarPresenter(MessengerToolbarContract.ConfigureView view, IConversationStarterRepository data) {
        mView = view;
        mData = data;
    }

    @Override
    public void initPage() {
        if (!mView.isToolbarAreadyConfigured()) {
            // Set in case there's a network error - otherwise toolbar covers entire screen
            String brand = MessengerPref.getInstance().getBrandName();
            mView.configureForLastActiveUsersView(new LastActiveAgentsData(
                    brand,
                    -1L, // Default average response time // TODO: Change it to a vague answer or remove line?
                    null, null, null));

            // TODO: Show this version of the toolbar ONLY
            // TODO: Set the default toolbar version
            mData.getConversationStarter(null);
        }
    }

    @Override
    public void configureDefaultView() {
        mIsLastActiveAgentView = true;
        mData.getConversationStarter(this);
    }

    @Override
    public void configureOtherView() {
        mIsLastActiveAgentView = false;
    }

    @Override
    public synchronized void onLoadConversationMetrics(ConversationStarter conversationStarter) {
        if (!mIsLastActiveAgentView) {
            return; // Skip if not set to conversation metric view
        }

        try {
            LastActiveAgentsData lastActiveAgentsData = ConversationStarterHelper.convertToLastActiveAgentsData(conversationStarter);
            if (lastActiveAgentsData != null) {
                mView.configureForLastActiveUsersView(lastActiveAgentsData);
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
