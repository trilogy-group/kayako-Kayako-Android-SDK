package com.kayako.sdk.android.k5.messenger.homescreenpage;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.IConversationStarterRepository;
import com.kayako.sdk.android.k5.messenger.data.RepoFactory;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.header.HeaderListItem;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.helper.WidgetFactory;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.PresenceWidgetListItem;
import com.kayako.sdk.error.KayakoException;
import com.kayako.sdk.messenger.conversationstarter.ConversationStarter;

import java.util.ArrayList;
import java.util.List;

public class HomeScreenListPresenter implements HomeScreenListContract.Presenter {

    private HomeScreenListContract.View mView;
    private PresenceWidgetListItem mPresenceWidgetListItem;

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
                    mPresenceWidgetListItem = WidgetFactory.generatePresenceWidgetListItem(conversationStarter);
                    setupList();
                }
            }

            @Override
            public void onFailure(KayakoException exception) {
                setupList(); // show list without loaded content then
                // TODO: show toast message indicating an error?!
            }
        });

        // TODO: TEST the action button and clicking it
/*
        baseListItems.add(new BaseWidgetListItem(HomeScreenListType.WIDGET_RECENT_CONVERSATIONS,
                "Recent Conversations",
                "Click Me",
                new BaseWidgetListItem.OnClickActionListener() {
                    @Override
                    public void onClickActionButton() {
                        // TODO: REMOVE THIS FROM PRESENTER
                        Kayako.getApplicationContext().startActivity(KayakoConversationListActivity.getIntent(Kayako.getApplicationContext()));
                    }
                }) {
        });
*/
    }

    private synchronized void setupList() {
        List<BaseListItem> baseListItems = new ArrayList<>();
        baseListItems.add(new HeaderListItem("Howdy Taylor", "Welcome back to Kayako support. Start a new conversation using button below...")); // TODO: Default from strings.xml

        // TODO: Define order and whether a widget is enabled or disabled
        if (mPresenceWidgetListItem != null) {
            baseListItems.add(mPresenceWidgetListItem);
        }

        mView.setupList(baseListItems);
    }
}
