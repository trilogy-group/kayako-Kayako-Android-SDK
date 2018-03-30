package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.UserViewModel;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.AssignedAgentData;
import com.kayako.sdk.helpcenter.user.UserMinimal;
import com.kayako.sdk.messenger.conversation.Conversation;
import javax.annotation.Generated;
import org.junit.Test;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.validation.Validator;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.aurea.unittest.commons.pojo.Testers;

@Generated("GeneralPatterns")
public class AssignedAgentToolbarHelperPojoTest {

    @Test
    public void test_validate_AssignedAgentToolbarHelper_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(AssignedAgentToolbarHelper.class));
    }
}
