package com.kayako.sdk.android.k5.kre.base.kase;

import android.support.annotation.NonNull;
import com.kayako.sdk.android.k5.kre.base.KreConnection;
import com.kayako.sdk.android.k5.kre.base.KreConnectionFactory;
import com.kayako.sdk.android.k5.kre.base.KreSubscription;
import com.kayako.sdk.android.k5.kre.base.credentials.KreCredentials;
import com.kayako.sdk.android.k5.kre.data.Change;
import com.kayako.sdk.android.k5.kre.helpers.KreCaseChangeHelper;
import com.kayako.sdk.android.k5.kre.helpers.KreCasePostChangeHelper;
import com.kayako.sdk.android.k5.kre.helpers.MinimalClientTypingListener;
import com.kayako.sdk.android.k5.kre.helpers.RawCaseChangeListener;
import com.kayako.sdk.android.k5.kre.helpers.RawCasePostChangeListener;
import com.kayako.sdk.android.k5.kre.helpers.RawClientActivityListener;
import com.kayako.sdk.android.k5.kre.helpers.RawClientTypingListener;
import com.kayako.sdk.android.k5.kre.helpers.RawUserOnCasePresenceListener;
import com.kayako.sdk.android.k5.kre.helpers.presence.KrePresenceHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Generated;
import org.junit.Test;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.validation.Validator;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.aurea.unittest.commons.pojo.Testers;

@Generated("GeneralPatterns")
public class KreCaseSubscriptionPojoTest {

    @Test
    public void test_validate_KreCaseSubscription_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(KreCaseSubscription.class));
    }
}
