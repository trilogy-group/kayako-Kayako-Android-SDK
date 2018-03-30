package com.kayako.sdk.android.k5.common.adapter.searchlist;

import com.kayako.sdk.android.k5.common.adapter.list.ListItem;
import com.kayako.sdk.base.parser.Resource;
import javax.annotation.Generated;
import org.junit.Test;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.validation.Validator;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.aurea.unittest.commons.pojo.Testers;

@Generated("GeneralPatterns")
public class SearchListItemPojoTest {

    @Test
    public void test_validate_SearchListItem_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(SearchListItem.class));
    }
}
