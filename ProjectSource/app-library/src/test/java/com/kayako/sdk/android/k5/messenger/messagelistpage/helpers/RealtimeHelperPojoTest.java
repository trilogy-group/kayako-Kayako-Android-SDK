package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import com.kayako.sdk.android.k5.core.MessengerUserPref;
import com.kayako.sdk.android.k5.messenger.data.realtime.OnConversationChangeListener;
import com.kayako.sdk.android.k5.messenger.data.realtime.OnConversationClientActivityListener;
import com.kayako.sdk.android.k5.messenger.data.realtime.OnConversationMessagesChangeListener;
import com.kayako.sdk.android.k5.messenger.data.realtime.OnConversationUserOnlineListener;
import com.kayako.sdk.android.k5.messenger.data.realtime.RealtimeConversationHelper;
import com.kayako.sdk.android.k5.messenger.data.realtime.RealtimeCurrentUserTrackerHelper;
import com.kayako.sdk.messenger.conversation.Conversation;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Generated;
import org.junit.Test;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.validation.Validator;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.aurea.unittest.commons.pojo.Testers;

@Generated("GeneralPatterns")
public class RealtimeHelperPojoTest {

    @Test
    public void test_validate_RealtimeHelper_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(RealtimeHelper.class));
    }
}
