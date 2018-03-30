package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import android.support.annotation.NonNull;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;
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
public class InputFeedbackRatingListItemPojoTest {

    @Test
    public void test_validate_InputFeedbackRatingListItem_Getters() {
        Validator validator = TestChain.startWith(Testers.getterTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(InputFeedbackRatingListItem.class));
    }

    @Test
    public void test_validate_InputFeedbackRatingListItem_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(InputFeedbackRatingListItem.class));
    }
}
