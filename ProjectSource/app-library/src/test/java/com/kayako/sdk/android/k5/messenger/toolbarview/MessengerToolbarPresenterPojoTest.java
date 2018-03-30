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
import javax.annotation.Generated;
import org.junit.Test;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.validation.Validator;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.aurea.unittest.commons.pojo.Testers;

@Generated("GeneralPatterns")
public class MessengerToolbarPresenterPojoTest {

    @Test
    public void test_validate_MessengerToolbarPresenter_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(MessengerToolbarPresenter.class));
    }
}
