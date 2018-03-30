package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.BotMessageListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputEmailListItem;
import com.kayako.sdk.android.k5.core.Kayako;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Generated;
import org.junit.Test;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.validation.Validator;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.aurea.unittest.commons.pojo.Testers;

@Generated("GeneralPatterns")
public class OnboardingHelperPojoTest {

    @Test
    public void test_validate_OnboardingHelper_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(OnboardingHelper.class));
    }
}
