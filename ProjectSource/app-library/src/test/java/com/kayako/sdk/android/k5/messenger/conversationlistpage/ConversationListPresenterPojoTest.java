package com.kayako.sdk.android.k5.messenger.conversationlistpage;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.conversationlist.ConversationListItem;
import com.kayako.sdk.android.k5.common.utils.FailsafePollingHelper;
import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.android.k5.messenger.data.conversation.unreadcounter.UnreadCounterRepository;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.ClientTypingActivity;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.ConversationViewModel;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.ConversationViewModelHelper;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.UserViewModel;
import com.kayako.sdk.android.k5.messenger.data.realtime.OnConversationChangeListener;
import com.kayako.sdk.android.k5.messenger.data.realtime.OnConversationClientActivityListener;
import com.kayako.sdk.android.k5.messenger.data.realtime.RealtimeConversationHelper;
import com.kayako.sdk.messenger.conversation.Conversation;
import com.kayako.sdk.utils.FingerprintUtils;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.junit.Test;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.validation.Validator;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.aurea.unittest.commons.pojo.Testers;

@Generated("GeneralPatterns")
public class ConversationListPresenterPojoTest {

    @Test
    public void test_validate_ConversationListPresenter_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(ConversationListPresenter.class));
    }
}
