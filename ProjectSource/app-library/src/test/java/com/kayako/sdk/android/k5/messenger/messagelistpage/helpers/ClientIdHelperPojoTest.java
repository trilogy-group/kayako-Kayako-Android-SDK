package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Generated;
import org.junit.Test;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.validation.Validator;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.aurea.unittest.commons.pojo.Testers;

@Generated("GeneralPatterns")
public class ClientIdHelperPojoTest {

    @Test
    public void test_validate_ClientIdHelper_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(ClientIdHelper.class));
    }
}
