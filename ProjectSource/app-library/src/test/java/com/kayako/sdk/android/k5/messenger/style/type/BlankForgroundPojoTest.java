package com.kayako.sdk.android.k5.messenger.style.type;

import android.graphics.drawable.Drawable;
import javax.annotation.Generated;
import org.junit.Test;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.validation.Validator;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.aurea.unittest.commons.pojo.Testers;

@Generated("GeneralPatterns")
public class BlankForgroundPojoTest {

    @Test
    public void test_validate_BlankForground_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(BlankForground.class));
    }
}
