package com.kayako.sdk.android.k5.kre.helpers.presence;

import android.support.annotation.NonNull;
import com.kayako.sdk.android.k5.core.KayakoLogHelper;
import com.kayako.sdk.android.k5.kre.base.KreSubscription;
import com.kayako.sdk.android.k5.kre.helpers.MinimalClientTypingListener;
import com.kayako.sdk.android.k5.kre.helpers.RawClientActivityListener;
import com.kayako.sdk.android.k5.kre.helpers.RawClientTypingListener;
import com.kayako.sdk.android.k5.kre.helpers.RawUserOnCasePresenceListener;
import com.kayako.sdk.android.k5.kre.helpers.RawUserSubscribedPresenceListener;
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
public class KrePresenceHelperPojoTest {

    @Test
    public void test_validate_KrePresenceHelper_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(KrePresenceHelper.class));
    }
}
