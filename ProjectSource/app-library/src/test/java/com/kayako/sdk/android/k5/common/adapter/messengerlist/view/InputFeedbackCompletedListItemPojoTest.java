package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import com.aurea.unittest.commons.pojo.Testers;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;

import org.junit.Test;

import javax.annotation.Generated;

@Generated("GeneralPatterns")
public class InputFeedbackCompletedListItemPojoTest {

    @Test
    public void test_validate_InputFeedbackCompletedListItem_Getters() {
        Validator validator = TestChain.startWith(Testers.getterTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(InputFeedbackCompletedListItem.class));
    }

    @Test
    public void test_validate_InputFeedbackCompletedListItem_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(InputFeedbackCompletedListItem.class));
    }
}
