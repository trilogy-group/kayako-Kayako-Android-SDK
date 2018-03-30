package com.kayako.sdk.android.k5.messenger.conversationlistpage;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.kayako.sdk.android.k5.messenger.conversationlistpage.ConversationListContract.Data;
import com.kayako.sdk.android.k5.messenger.data.conversation.ConversationStore;
import com.kayako.sdk.auth.FingerprintAuth;
import com.kayako.sdk.base.callback.ListCallback;
import com.kayako.sdk.error.KayakoException;
import com.kayako.sdk.messenger.Messenger;
import com.kayako.sdk.messenger.conversation.Conversation;
import java.util.List;
import javax.annotation.Generated;
import org.junit.Test;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.validation.Validator;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.aurea.unittest.commons.pojo.Testers;

@Generated("GeneralPatterns")
public class ConversationListRepositoryPojoTest {

    @Test
    public void test_validate_ConversationListRepository_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(ConversationListRepository.class));
    }
}
