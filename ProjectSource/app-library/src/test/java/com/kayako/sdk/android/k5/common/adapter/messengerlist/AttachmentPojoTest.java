package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import android.support.annotation.NonNull;
import com.kayako.sdk.android.k5.common.adapter.ContentComparable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import org.junit.Test;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.validation.Validator;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.aurea.unittest.commons.pojo.Testers;
import org.junit.Test;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.validation.Validator;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.aurea.unittest.commons.pojo.Testers;

@Generated("GeneralPatterns")
public class AttachmentPojoTest {

    @Test
    public void test_validate_Attachment_Getters() {
        Validator validator = TestChain.startWith(Testers.getterTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(Attachment.class));
    }

    @Test
    public void test_validate_Attachment_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(Attachment.class));
    }
}
