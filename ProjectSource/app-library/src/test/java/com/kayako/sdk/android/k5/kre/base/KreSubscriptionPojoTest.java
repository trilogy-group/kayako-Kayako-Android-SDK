package com.kayako.sdk.android.k5.kre.base;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kayako.sdk.android.k5.core.KayakoLogHelper;
import com.kayako.sdk.android.k5.kre.base.credentials.KreCredentials;
import com.kayako.sdk.android.k5.kre.data.Payload;
import com.kayako.sdk.android.k5.kre.data.PushData;
import org.phoenixframework.channels.Channel;
import org.phoenixframework.channels.Envelope;
import org.phoenixframework.channels.IMessageCallback;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.Generated;
import org.junit.Test;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.validation.Validator;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.aurea.unittest.commons.pojo.Testers;

@Generated("GeneralPatterns")
public class KreSubscriptionPojoTest {

    @Test
    public void test_validate_KreSubscription_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(KreSubscription.class));
    }
}
