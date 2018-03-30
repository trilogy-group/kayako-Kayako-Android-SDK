package com.kayako.sdk.android.k5.helpcenter.sectionbycategorypage;

import com.kayako.sdk.android.k5.core.HelpCenterPref;
import com.kayako.sdk.error.KayakoException;
import com.kayako.sdk.helpcenter.HelpCenter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.Generated;
import org.junit.Test;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.validation.Validator;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.aurea.unittest.commons.pojo.Testers;

@Generated("GeneralPatterns")
public class SectionByCategoryContainerRepositoryPojoTest {

    @Test
    public void test_validate_SectionByCategoryContainerRepository_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(SectionByCategoryContainerRepository.class));
    }
}
