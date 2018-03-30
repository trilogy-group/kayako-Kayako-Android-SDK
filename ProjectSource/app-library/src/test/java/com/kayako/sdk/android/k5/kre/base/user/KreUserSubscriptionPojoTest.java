package com.kayako.sdk.android.k5.kre.base.user;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.kayako.sdk.android.k5.kre.base.KreConnection;
import com.kayako.sdk.android.k5.kre.base.KreConnectionFactory;
import com.kayako.sdk.android.k5.kre.base.KreSubscription;
import com.kayako.sdk.android.k5.kre.data.Payload;
import com.kayako.sdk.android.k5.kre.base.credentials.KreCredentials;
import com.kayako.sdk.android.k5.kre.helpers.KreOnlinePresenceHelper;
import com.kayako.sdk.android.k5.kre.helpers.RawUserOnlinePresenceListener;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.junit.Test;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.validation.Validator;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.aurea.unittest.commons.pojo.Testers;

@Generated("GeneralPatterns")
public class KreUserSubscriptionPojoTest {

    @Test
    public void test_validate_KreUserSubscription_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(KreUserSubscription.class));
    }
}
