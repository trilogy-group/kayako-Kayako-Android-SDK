package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.AttachmentFileType;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.AttachmentMessageContinuedSelfListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.AttachmentMessageSelfListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.SimpleMessageContinuedSelfListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.SimpleMessageSelfListItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import org.junit.Test;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.validation.Validator;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.aurea.unittest.commons.pojo.Testers;

@Generated("GeneralPatterns")
public class OptimisticSendingHelperPojoTest {

    @Test
    public void test_validate_OptimisticSendingHelper_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(OptimisticSendingHelper.class));
    }
}
