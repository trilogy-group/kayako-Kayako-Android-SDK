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
import javax.annotation.Generated;
import org.junit.Test;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.validation.Validator;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.aurea.unittest.commons.pojo.Testers;

@Generated("GeneralPatterns")
public class HomeScreenListPresenterPojoTest {

    @Test
    public void test_validate_HomeScreenListPresenter_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(HomeScreenListPresenter.class));
    }
}
