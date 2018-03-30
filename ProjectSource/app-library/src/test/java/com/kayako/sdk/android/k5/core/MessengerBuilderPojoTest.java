package com.kayako.sdk.android.k5.core;

import com.aurea.unittest.commons.pojo.Testers;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;

import org.junit.Test;

import javax.annotation.Generated;

@Generated("GeneralPatterns")
public class MessengerBuilderPojoTest {

    @Test
    public void test_validate_MessengerBuilder_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(MessengerBuilder.class));
    }
}
