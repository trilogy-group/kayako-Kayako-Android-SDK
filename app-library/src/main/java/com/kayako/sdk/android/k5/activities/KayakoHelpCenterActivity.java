package com.kayako.sdk.android.k5.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.articlelistpage.ArticleListContainerFragment;
import com.kayako.sdk.android.k5.core.HelpCenterPref;
import com.kayako.sdk.android.k5.common.fragments.ActivityNavigationResourceCallback;
import com.kayako.sdk.android.k5.sectionbycategorypage.SectionByCategoryContainerFragment;
import com.kayako.sdk.helpcenter.base.Resource;
import com.kayako.sdk.helpcenter.section.Section;

public class KayakoHelpCenterActivity extends AppCompatActivity implements ActivityNavigationResourceCallback {

    private static final String FRAGMENT_TAG = "current_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ko__activity_help_center);

        if (savedInstanceState == null) {
            openFirstPage();
        }
    }

    private void openFirstPage() {
        Fragment newFragment = SectionByCategoryContainerFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.ko__fragment_listing, newFragment, FRAGMENT_TAG);
        transaction.commit();
    }

    @Override
    public void openNextPage(Resource resource) {
        Fragment newFragment = ArticleListContainerFragment.newInstance((Section) resource);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.ko__fragment_listing, newFragment, FRAGMENT_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
