package com.kayako.sdk.android.k5.helpcenter.searcharticlepage;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.activities.KayakoArticleActivity;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.loadmorelist.EndlessRecyclerViewScrollAdapter;
import com.kayako.sdk.android.k5.common.adapter.searchlist.SearchListAdapter;
import com.kayako.sdk.android.k5.common.adapter.searchlist.SearchListItem;
import com.kayako.sdk.android.k5.common.fragments.BaseListFragment;
import com.kayako.sdk.android.k5.common.task.BackgroundTask;
import com.kayako.sdk.android.k5.common.utils.ViewUtils;
import com.kayako.sdk.helpcenter.articles.Article;
import java.util.List;
import javax.annotation.Generated;
import org.junit.Test;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.validation.Validator;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.aurea.unittest.commons.pojo.Testers;

@Generated("GeneralPatterns")
public class SearchArticleFragmentPojoTest {

    @Test
    public void test_validate_SearchArticleFragment_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(SearchArticleFragment.class));
    }
}
