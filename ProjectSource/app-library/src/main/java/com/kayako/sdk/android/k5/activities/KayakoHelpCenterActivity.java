package com.kayako.sdk.android.k5.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.helpcenter.articlelistpage.ArticleListContainerFragment;
import com.kayako.sdk.android.k5.common.fragments.ActivityNavigationResourceCallback;
import com.kayako.sdk.android.k5.helpcenter.sectionbycategorypage.SectionByCategoryContainerFragment;
import com.kayako.sdk.base.parser.Resource;
import com.kayako.sdk.helpcenter.section.Section;

public class KayakoHelpCenterActivity extends AppCompatActivity implements ActivityNavigationResourceCallback {

    private static final String FRAGMENT_TAG = "current_fragment";

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, KayakoHelpCenterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ko__activity_help_center);

        if (savedInstanceState == null) {
            openFirstPage();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        openFirstPage();
    }

    private void openFirstPage() {
        showSectionsByCategoryFragment();
    }

    @Override
    public void openNextPage(Resource resource) {
        showArticleListFragment(resource);
    }

    private void showArticleListFragment(Resource resource) {
        Fragment newFragment = ArticleListContainerFragment.newInstance((Section) resource);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.ko__fragment_listing, newFragment, FRAGMENT_TAG);
        transaction.addToBackStack(null); // enable back stack to return to first fragment
        transaction.commit();
    }

    private void showSectionsByCategoryFragment() {
        Fragment newFragment = SectionByCategoryContainerFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.ko__fragment_listing, newFragment, FRAGMENT_TAG);
        transaction.commit();
    }

}
