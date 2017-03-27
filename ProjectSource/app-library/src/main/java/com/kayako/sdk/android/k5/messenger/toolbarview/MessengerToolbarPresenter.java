package com.kayako.sdk.android.k5.messenger.toolbarview;

import com.kayako.sdk.android.k5.messenger.data.conversationstarter.IConversationStarterRepository;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.LastActiveAgentsData;
import com.kayako.sdk.android.k5.messenger.style.ConversationStarterHelper;
import com.kayako.sdk.error.KayakoException;
import com.kayako.sdk.messenger.conversationstarter.ConversationStarter;
import com.kayako.sdk.utils.LogUtils;

public class MessengerToolbarPresenter implements MessengerToolbarContract.Presenter, IConversationStarterRepository.OnLoadConversationStarterListener {

    private MessengerToolbarContract.ConfigureView mView;
    private IConversationStarterRepository mData;

    public MessengerToolbarPresenter(MessengerToolbarContract.ConfigureView view, IConversationStarterRepository data) {
        mView = view;
        mData = data;
    }

    @Override
    public void initPage() {
        // Set in case there's a network error - otherwise toolbar covers entire screen
        String brand = "Kayako"; // TODO: brand name?
        mView.configureForLastActiveUsersView(new LastActiveAgentsData(
                brand,
                -1L, // Default average response time // TODO: Change it to a vague answer or remove line?
                null, null, null));

        // TODO: Show this version of the toolbar ONLY
        // TODO: Set the default toolbar version
        mData.getConversationStarter(null);
    }

    @Override
    public void configureDefaultView() {
        mData.getConversationStarter(this);
    }

    @Override
    public synchronized void onLoadConversationMetrics(ConversationStarter conversationStarter) {
        try {
            LastActiveAgentsData lastActiveAgentsData = ConversationStarterHelper.convert(conversationStarter);
            if (lastActiveAgentsData != null) {
                mView.configureForLastActiveUsersView(lastActiveAgentsData);
            }
        } catch (Exception e) {
            LogUtils.logError(getClass(), "Conversation starter failed to load correctly for Messenger Toolbar");
            LogUtils.logError(getClass(), e.getMessage());
        }
    }

    @Override
    public synchronized void onFailure(KayakoException exception) {
        // TODO: Show default view? - especially when there's no network
        // TODO: Show a toast or some message?
    }

}
