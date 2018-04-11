package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import com.aurea.unittest.commons.pojo.Testers;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;

import org.junit.Test;

import javax.annotation.Generated;

@Generated("GeneralPatterns")
public class ReplyBoxViewHelperPojoTest {

    @Test
    public void test_validate_ReplyBoxViewHelper_Getters() {
        Validator validator = TestChain.startWith(Testers.getterTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(ReplyBoxViewHelper.class));
    }

    @Test
    public void test_validate_ReplyBoxViewHelper_Setters() {
        Validator validator = TestChain.startWith(Testers.setterTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(ReplyBoxViewHelper.class));
    }

    @Test
    public void test_validate_ReplyBoxViewHelper_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(ReplyBoxViewHelper.class));
    }
}
