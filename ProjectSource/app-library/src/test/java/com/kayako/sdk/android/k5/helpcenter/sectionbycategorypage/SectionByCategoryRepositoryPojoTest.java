package com.kayako.sdk.android.k5.helpcenter.sectionbycategorypage;

import com.kayako.sdk.android.k5.core.HelpCenterPref;
import com.kayako.sdk.error.KayakoException;
import com.kayako.sdk.helpcenter.HelpCenter;
import com.kayako.sdk.helpcenter.category.Category;
import com.kayako.sdk.helpcenter.section.Section;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Generated;
import org.junit.Test;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.validation.Validator;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.aurea.unittest.commons.pojo.Testers;

@Generated("GeneralPatterns")
public class SectionByCategoryRepositoryPojoTest {

    @Test
    public void test_validate_SectionByCategoryRepository_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(SectionByCategoryRepository.class));
    }
}
