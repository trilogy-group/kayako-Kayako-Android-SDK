package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.ClientDeliveryStatus;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.OptimisticSendingHelper;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.UnsentMessage;
import com.kayako.sdk.android.k5.common.utils.file.FileAttachment;
import com.kayako.sdk.messenger.message.Message;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Generated;
import org.junit.Test;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.validation.Validator;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.aurea.unittest.commons.pojo.Testers;

@Generated("GeneralPatterns")
public class OptimisticSendingViewHelperPojoTest {

    @Test
    public void test_validate_OptimisticSendingViewHelper_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(OptimisticSendingViewHelper.class));
    }
}
