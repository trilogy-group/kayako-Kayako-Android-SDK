package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import com.aurea.unittest.commons.pojo.Testers;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;

import org.junit.Test;

import javax.annotation.Generated;

@Generated("GeneralPatterns")
public class AttachmentUrlTypePojoTest {

    @Test
    public void test_validate_AttachmentUrlType_Getters() {
        Validator validator = TestChain.startWith(Testers.getterTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(AttachmentUrlType.class));
    }

    @Test
    public void test_validate_AttachmentUrlType_Setters() {
        Validator validator = TestChain.startWith(Testers.setterTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(AttachmentUrlType.class));
    }

    @Test
    public void test_validate_AttachmentUrlType_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(AttachmentUrlType.class));
    }
}
