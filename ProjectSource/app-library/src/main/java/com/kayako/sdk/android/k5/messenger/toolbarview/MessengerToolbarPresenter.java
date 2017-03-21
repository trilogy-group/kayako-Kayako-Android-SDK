package com.kayako.sdk.android.k5.messenger.toolbarview;

import com.kayako.sdk.android.k5.messenger.toolbarview.child.ActiveUser;
import com.kayako.sdk.android.k5.messenger.toolbarview.child.LastActiveAgentsData;
import com.kayako.sdk.error.KayakoException;
import com.kayako.sdk.helpcenter.user.UserMinimal;
import com.kayako.sdk.messenger.conversationstarter.ConversationStarter;
import com.kayako.sdk.utils.LogUtils;

public class MessengerToolbarPresenter implements MessengerToolbarContract.Presenter, MessengerToolbarContract.OnLoadConversationStarterListener {

    private MessengerToolbarContract.ConfigureView mView;
    private MessengerToolbarContract.Data mData;

    public MessengerToolbarPresenter(MessengerToolbarContract.ConfigureView view, MessengerToolbarContract.Data data) {
        mView = view;
        mData = data;
    }

    @Override
    public void initPage() {
        // TODO: Show this version of the toolbar ONLY
        // TODO: Set the default toolbar version
        mData.getConversationStarter(null);
    }

    @Override
    public void configureDefaultView() {
         mData.getConversationStarter(this);

        // TODO: Testing:
        /*mView.configureForLastActiveUsersView(new LastActiveAgentsData(
                "Test",
                1000L,
                null,
                null,
                null
        ));*/
    }

    @Override
    public synchronized void onLoadConversationMetrics(ConversationStarter conversationStarter) {
        try {
            LastActiveAgentsData lastActiveAgentsData = convert(conversationStarter);
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
        // TODO: Show default view?
    }

    private long convert(double minutes) {
        long seconds = (long) (minutes * 60 * 1000);
        return seconds;
    }

    private ActiveUser convert(UserMinimal userMinimal) {
        return new ActiveUser(
                userMinimal.getAvatarUrl(),
                userMinimal.getFullName(),
                userMinimal.getLastActiveAt()
        );
    }

    private LastActiveAgentsData convert(ConversationStarter conversationStarter) {
        String brand = "Kayako"; // TODO: brand name?
        long averageReplyTimeInMilliseconds;
        ActiveUser user1 = null;
        ActiveUser user2 = null;
        ActiveUser user3 = null;

        if (conversationStarter == null) {
            throw new IllegalArgumentException("Null unacceptable!");
        }

        if (conversationStarter.getAverageReplyTime() != null) {
            averageReplyTimeInMilliseconds = convert(conversationStarter.getAverageReplyTime());
        } else {
            averageReplyTimeInMilliseconds = -1L;
        }

        if (conversationStarter.getLastActiveAgents() != null) {
            final int size = conversationStarter.getLastActiveAgents().size();
            switch (size) {
                case 3:
                    user3 = convert(conversationStarter.getLastActiveAgents().get(2));
                case 2:
                    user2 = convert(conversationStarter.getLastActiveAgents().get(1));
                case 1:
                    user1 = convert(conversationStarter.getLastActiveAgents().get(0));
                    break;
            }
        }

        LastActiveAgentsData lastActiveAgentsData = new LastActiveAgentsData(
                brand,
                averageReplyTimeInMilliseconds,
                user1,
                user2,
                user3
        );

        return lastActiveAgentsData;
    }

}
