package com.kayako.sdk.android.k5.kre.helpers.presence;

import com.kayako.sdk.android.k5.kre.base.KreSubscription;
import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.Generated;
import org.junit.Test;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.validation.Validator;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.aurea.unittest.commons.pojo.Testers;

@Generated("GeneralPatterns")
public class KrePresencePushDataConservativelyHelperPojoTest {

    @Test
    public void test_validate_KrePresencePushDataConservativelyHelper_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(KrePresencePushDataConservativelyHelper.class));
    }
}
