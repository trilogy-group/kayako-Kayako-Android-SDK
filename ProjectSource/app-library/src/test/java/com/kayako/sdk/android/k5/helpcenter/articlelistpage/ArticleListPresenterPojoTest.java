package com.kayako.sdk.android.k5.helpcenter.articlelistpage;

import android.text.Html;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.list.ListItem;
import com.kayako.sdk.helpcenter.articles.Article;
import com.kayako.sdk.helpcenter.section.Section;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.junit.Test;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.validation.Validator;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.aurea.unittest.commons.pojo.Testers;

@Generated("GeneralPatterns")
public class ArticleListPresenterPojoTest {

    @Test
    public void test_validate_ArticleListPresenter_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(ArticleListPresenter.class));
    }
}
