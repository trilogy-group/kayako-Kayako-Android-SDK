package com.kayako.sdk.android.k5.common.utils;

import android.os.Handler;
import java.util.Timer;
import java.util.TimerTask;
import javax.annotation.Generated;
import org.junit.Test;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.validation.Validator;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.aurea.unittest.commons.pojo.Testers;

@Generated("GeneralPatterns")
public class FailsafePollingHelperPojoTest {

    @Test
    public void test_validate_FailsafePollingHelper_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(FailsafePollingHelper.class));
    }
}
