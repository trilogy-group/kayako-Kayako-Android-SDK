package com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.UniqueSortedUpdatableResourceList;
import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.messenger.conversation.Conversation;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import static com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.BotMessageHelper.getDefaultDrawableForConversation;
import javax.annotation.Generated;
import org.junit.Test;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.validation.Validator;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.aurea.unittest.commons.pojo.Testers;

@Generated("GeneralPatterns")
public class ConversationViewModelHelperPojoTest {

    @Test
    public void test_validate_ConversationViewModelHelper_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(ConversationViewModelHelper.class));
    }
}
