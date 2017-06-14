package com.kayako.sdk.android.k5.messenger.homescreenpage;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.utils.FailsafePollingHelper;
import com.kayako.sdk.android.k5.core.KayakoLogHelper;
import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.android.k5.messenger.data.MessengerRepoFactory;
import com.kayako.sdk.android.k5.messenger.data.conversation.ConversationStore;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.ClientTypingActivity;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.ConversationViewModel;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.ConversationViewModelHelper;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.UserViewModel;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.IConversationStarterRepository;
import com.kayako.sdk.android.k5.messenger.data.realtime.OnConversationChangeListener;
import com.kayako.sdk.android.k5.messenger.data.realtime.OnConversationClientActivityListener;
import com.kayako.sdk.android.k5.messenger.data.realtime.RealtimeConversationHelper;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.header.FooterListItem;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.header.HeaderListItem;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.helper.RecentConversationHelper;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.helper.WidgetFactory;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.BaseWidgetListItem;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.presence.PresenceWidgetListItem;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.recentcases.OnClickRecentConversationListener;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.recentcases.RecentConversationsWidgetListItem;
import com.kayako.sdk.error.KayakoException;
import com.kayako.sdk.messenger.conversation.Conversation;
import com.kayako.sdk.messenger.conversationstarter.ConversationStarter;

import java.util.ArrayList;
import java.util.List;

public class HomeScreenListPresenter implements HomeScreenListContract.Presenter, OnConversationChangeListener, OnConversationClientActivityListener {

    private HomeScreenListContract.View mView;
    private PresenceWidgetListItem mPresenceWidgetListItem;
    private RecentConversationsWidgetListItem mRecentCasesWidgetListItem;
    private ConversationViewModelHelper mConversationViewModelHelper = new ConversationViewModelHelper();
    private FailsafePollingHelper mFailsafePollingHelper = new FailsafePollingHelper();

    public HomeScreenListPresenter(HomeScreenListContract.View view) {
        mView = view;
    }

    @Override
    public void initPage() {
        setupList();
        // load conversation started in onResume()

        mFailsafePollingHelper.startPolling(new FailsafePollingHelper.PollingListener() {
            @Override
            public void onPoll() {
                loadConversationStarter();
            }
        });
    }

    @Override
    public void closePage() {
        // unsubscribe from realtime events
        RealtimeConversationHelper.untrack((OnConversationChangeListener) this);
        RealtimeConversationHelper.untrack((OnConversationClientActivityListener) this);

        mFailsafePollingHelper.stopPolling();
    }

    @Override
    public void onResume() {
        loadConversationStarter();
    }

    private void loadConversationStarter() {
        MessengerRepoFactory.getConversationStarterRepository().getConversationStarter(mOnLoadConversationStarterListener);
        ConversationStore.getInstance().getConversations(0, 3, mOnLoadConversationListListener);
    }

    private List<ConversationViewModel> generateConversationViewModels(List<Conversation> conversations) {
        if (conversations == null || conversations.size() == 0) {
            throw new IllegalArgumentException("No conversations to show! Can not generate this widget!");
        }

        // Update the Recent Conversations and remove and unsubscribe from unused conversation
        RecentConversationHelper.updateRecentConversations(mConversationViewModelHelper, conversations, null);

        // Register for realtime updates on case
        for (Conversation conversation : conversations) {
            RealtimeConversationHelper.trackChange(conversation.getRealtimeChannel(), conversation.getId(), this);
            RealtimeConversationHelper.trackClientActivity(conversation.getRealtimeChannel(), conversation.getId(), this);
        }

        return mConversationViewModelHelper.getConversationList();
    }

    private void refreshRecentConversationsWidget() {
        mRecentCasesWidgetListItem = WidgetFactory.generateRecentCasesWidgetListItem(
                mConversationViewModelHelper.getConversationList(),
                new BaseWidgetListItem.OnClickActionListener() {
                    @Override
                    public void onClickActionButton() {
                        mView.openConversationListingPage();
                    }
                },
                new OnClickRecentConversationListener() {
                    @Override
                    public void onClickRecentConversation(long conversationId) {
                        mView.openSelectConversationPage(conversationId);
                    }
                });
    }

    private synchronized void setupList() {
        List<BaseListItem> baseListItems = new ArrayList<>();

        // Show Recent Cases widget first!
        if (mRecentCasesWidgetListItem != null) {
            baseListItems.add(mRecentCasesWidgetListItem);
        }

        // Show Presence always after Cases (since the list of cases is most relevant)
        if (mPresenceWidgetListItem != null) {
            baseListItems.add(mPresenceWidgetListItem);
        }

        baseListItems.add(0, new HeaderListItem(
                MessengerPref.getInstance().getTitle(),
                MessengerPref.getInstance().getDescription()));

        baseListItems.add(new FooterListItem());

        mView.setupList(baseListItems);
    }

    private IConversationStarterRepository.OnLoadConversationStarterListener mOnLoadConversationStarterListener = new IConversationStarterRepository.OnLoadConversationStarterListener() {
        @Override
        public void onLoadConversationMetrics(ConversationStarter conversationStarter) {
            if (conversationStarter != null) {

                // If successful, generate the Presence Widget
                try {
                    mPresenceWidgetListItem = WidgetFactory.generatePresenceWidgetListItem(conversationStarter);
                } catch (IllegalArgumentException e) {
                    KayakoLogHelper.printStackTrace(HomeScreenListPresenter.class.getName(), e);
                }

                setupList();
            }
        }

        @Override
        public void onFailure(KayakoException exception) {
            setupList(); // show list without loaded content then
        }
    };

    private ConversationStore.ConversationListLoaderCallback mOnLoadConversationListListener = new ConversationStore.ConversationListLoaderCallback() {
        @Override
        public void onLoadConversations(List<Conversation> conversationList) {
            // If successful, generate the Recent Cases Widget
            try {
                generateConversationViewModels(conversationList);
                refreshRecentConversationsWidget();
            } catch (IllegalArgumentException e) {
                KayakoLogHelper.printStackTrace(HomeScreenListPresenter.class.getName(), e);
            }

            setupList();
        }

        @Override
        public void onFailure(KayakoException e) {
            setupList(); // show list without loaded content then
        }
    };

    @Override
    public void onChange(Conversation conversation) {
        // Check to see if the changes are for conversations relevant to this view
        if (!mConversationViewModelHelper.exists(conversation.getId())) {
            return;
        }

        boolean isUpdated = mConversationViewModelHelper.updateConversationProperty(conversation.getId(), conversation);
        if (isUpdated) {
            refreshRecentConversationsWidget();
            setupList();
        }
    }

    @Override
    public void onTyping(long conversationId, UserViewModel userTyping, boolean isTyping) {
        // Check to see if the changes are for conversations relevant to this view
        if (mConversationViewModelHelper.exists(conversationId)) {
            boolean isUpdated = mConversationViewModelHelper.updateRealtimeProperty(conversationId, new ClientTypingActivity(isTyping, userTyping));
            if (isUpdated) { // Prevent multiple refreshes of UI for the same value
                refreshRecentConversationsWidget();
                setupList();
            }
        }
    }
}
