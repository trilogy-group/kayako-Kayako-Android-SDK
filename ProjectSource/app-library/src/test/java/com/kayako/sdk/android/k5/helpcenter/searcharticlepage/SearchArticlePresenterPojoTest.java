package com.kayako.sdk.android.k5.helpcenter.searcharticlepage;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.searchlist.SearchListItem;
import com.kayako.sdk.helpcenter.search.SearchArticle;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.junit.Test;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.validation.Validator;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.aurea.unittest.commons.pojo.Testers;

@Generated("GeneralPatterns")
public class SearchArticlePresenterPojoTest {

    @Test
    public void test_validate_SearchArticlePresenter_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(SearchArticlePresenter.class));
    }
}
