package com.kayako.sdk.android.k5.kre.base.view;

import com.kayako.sdk.android.k5.kre.base.KreConnection;
import com.kayako.sdk.android.k5.kre.base.KreConnectionFactory;
import com.kayako.sdk.android.k5.kre.base.KreSubscription;
import com.kayako.sdk.android.k5.kre.data.ViewCountChange;
import com.kayako.sdk.android.k5.kre.helpers.KreChangeHelper;
import com.kayako.sdk.android.k5.kre.helpers.RawChangeListener;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import org.junit.Test;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.validation.Validator;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.aurea.unittest.commons.pojo.Testers;

@Generated("GeneralPatterns")
public class KreViewCountSubscriptionPojoTest {

    @Test
    public void test_validate_KreViewCountSubscription_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(KreViewCountSubscription.class));
    }
}
