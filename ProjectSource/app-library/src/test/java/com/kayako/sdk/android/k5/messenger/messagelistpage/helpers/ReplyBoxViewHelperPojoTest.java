package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.fragments.ListPageState;
import com.kayako.sdk.android.k5.core.Kayako;
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
import org.junit.Test;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.validation.Validator;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.aurea.unittest.commons.pojo.Testers;

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
