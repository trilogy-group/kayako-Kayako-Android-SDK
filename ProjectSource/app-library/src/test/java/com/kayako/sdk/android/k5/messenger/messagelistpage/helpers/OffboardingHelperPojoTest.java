package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import android.content.Context;
import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFeedback;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFeedbackCommentListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFeedbackRatingListItem;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.messenger.rating.Rating;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.Generated;
import org.junit.Test;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.validation.Validator;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.aurea.unittest.commons.pojo.Testers;

@Generated("GeneralPatterns")
public class OffboardingHelperPojoTest {

    @Test
    public void test_validate_OffboardingHelper_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(OffboardingHelper.class));
    }
}
