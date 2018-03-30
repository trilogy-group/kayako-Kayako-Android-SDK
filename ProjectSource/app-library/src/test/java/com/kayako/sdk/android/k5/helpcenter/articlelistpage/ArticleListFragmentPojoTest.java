package com.kayako.sdk.android.k5.helpcenter.articlelistpage;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;
import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.activities.KayakoArticleActivity;
import com.kayako.sdk.android.k5.common.adapter.loadmorelist.EndlessRecyclerViewScrollAdapter;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.list.ListItemRecyclerViewAdapter;
import com.kayako.sdk.android.k5.common.adapter.list.ListItem;
import com.kayako.sdk.android.k5.common.adapter.sectioninfolist.SectionInfoAdapter;
import com.kayako.sdk.android.k5.common.fragments.BaseListFragment;
import com.kayako.sdk.android.k5.common.task.BackgroundTask;
import com.kayako.sdk.android.k5.common.utils.ViewUtils;
import com.kayako.sdk.helpcenter.articles.Article;
import com.kayako.sdk.helpcenter.section.Section;
import java.util.List;
import javax.annotation.Generated;
import org.junit.Test;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.validation.Validator;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.aurea.unittest.commons.pojo.Testers;

@Generated("GeneralPatterns")
public class ArticleListFragmentPojoTest {

    @Test
    public void test_validate_ArticleListFragment_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(ArticleListFragment.class));
    }
}
