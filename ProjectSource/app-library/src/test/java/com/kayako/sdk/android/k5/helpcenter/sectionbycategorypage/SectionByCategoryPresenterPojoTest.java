package com.kayako.sdk.android.k5.helpcenter.sectionbycategorypage;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.list.HeaderListItem;
import com.kayako.sdk.android.k5.common.adapter.list.ListItem;
import com.kayako.sdk.base.parser.Resource;
import com.kayako.sdk.helpcenter.category.Category;
import com.kayako.sdk.helpcenter.section.Section;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import org.junit.Test;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.validation.Validator;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.aurea.unittest.commons.pojo.Testers;

@Generated("GeneralPatterns")
public class SectionByCategoryPresenterPojoTest {

    @Test
    public void test_validate_SectionByCategoryPresenter_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(SectionByCategoryPresenter.class));
    }
}
