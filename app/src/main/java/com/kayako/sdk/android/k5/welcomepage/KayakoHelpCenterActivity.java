package com.kayako.sdk.android.k5.welcomepage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.articlelistpage.ArticleListFragment;
import com.kayako.sdk.android.k5.sectionbycategorypage.SectionByCategoryListFragment;

public class KayakoHelpCenterActivity extends AppCompatActivity implements ActivityNavigationInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center_main);

        Fragment newFragment = SectionByCategoryListFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_listing, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void openNextPage(long id) {
        Fragment newFragment = ArticleListFragment.newInstance(id);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_listing, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // TODO: Check backstack and close (empty blank page seen)
    }

    // TODO: New Presenter for this page
    // TODO: Presenter-View #1: Search Section - hide and show menu options
    // TODO: Presenter-View #2: Toolbar to select Locales
}
