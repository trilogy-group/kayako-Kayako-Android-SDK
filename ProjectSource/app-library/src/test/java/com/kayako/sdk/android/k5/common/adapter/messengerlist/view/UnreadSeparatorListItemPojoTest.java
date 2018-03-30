package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import android.support.annotation.Nullable;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.DiffUtilsCallback;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.DiffUtilsHelper;
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
public class UnreadSeparatorListItemPojoTest {

    @Test
    public void test_validate_UnreadSeparatorListItem_Getters() {
        Validator validator = TestChain.startWith(Testers.getterTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(UnreadSeparatorListItem.class));
    }

    @Test
    public void test_validate_UnreadSeparatorListItem_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(UnreadSeparatorListItem.class));
    }
}
