package com.kayako.sdk.android.k5.messenger.homescreenpage;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.IConversationStarterRepository;
import com.kayako.sdk.android.k5.messenger.data.RepoFactory;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.header.FooterListItem;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.header.HeaderListItem;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.helper.WidgetFactory;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.BaseWidgetListItem;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.presence.PresenceWidgetListItem;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.recentcases.OnClickRecentConversationListener;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.recentcases.RecentConversationsWidgetListItem;
import com.kayako.sdk.error.KayakoException;
import com.kayako.sdk.messenger.conversationstarter.ConversationStarter;
import com.kayako.sdk.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeScreenListPresenter implements HomeScreenListContract.Presenter {

    private HomeScreenListContract.View mView;
    private PresenceWidgetListItem mPresenceWidgetListItem;
    private RecentConversationsWidgetListItem mRecentCasesWidgetListItem;

    public HomeScreenListPresenter(HomeScreenListContract.View view) {
        mView = view;
    }

    @Override
    public void initPage() {
        setupList();

        RepoFactory.getConversationStarterRepository().getConversationStarter(new IConversationStarterRepository.OnLoadConversationStarterListener() {
            @Override
            public void onLoadConversationMetrics(ConversationStarter conversationStarter) {
                if (conversationStarter != null) {
                    // If successful, generate the Presence Widget
                    try {
                        mPresenceWidgetListItem = WidgetFactory.generatePresenceWidgetListItem(conversationStarter);
                    } catch (IllegalArgumentException e) {
                        LogUtils.logError(HomeScreenListPresenter.class, e.getMessage());
                    }

                    // If successful, generate the Recent Cases Widget
                    try {
                        mRecentCasesWidgetListItem = WidgetFactory.generateRecentCasesWidgetListItem(conversationStarter,
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
                    } catch (IllegalArgumentException e) {
                        LogUtils.logError(HomeScreenListPresenter.class, e.getMessage());
                    }

                    setupList();
                }
            }

            @Override
            public void onFailure(KayakoException exception) {
                setupList(); // show list without loaded content then
                // TODO: show toast message indicating an error?!
            }
        });
    }

    private synchronized void setupList() {
        List<BaseListItem> baseListItems = new ArrayList<>();

        // TODO: Define order and whether a widget is enabled or disabled
        if (mRecentCasesWidgetListItem != null) {
            baseListItems.add(mRecentCasesWidgetListItem);
        }

        // TODO: Define order and whether a widget is enabled or disabled
        if (mPresenceWidgetListItem != null) {
            baseListItems.add(mPresenceWidgetListItem);
        }

        baseListItems.add(0, new HeaderListItem("Howdy Taylor", "Welcome back to Kayako support. Start a new conversation using button below...")); // TODO: Default from strings.xml
        baseListItems.add(new FooterListItem());
        mView.setupList(baseListItems);
    }
}
