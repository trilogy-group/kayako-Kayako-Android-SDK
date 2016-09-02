package com.kayako.sdk.android.k5.welcomepage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.sectionbycategoryfragment.SectionByCategoryListFragment;

public class HelpCenterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_listing, SectionByCategoryListFragment.newInstance()).commit();
    }

    // TODO: New Presenter for this page
    // TODO: Presenter-View #1: Search Section - hide and show menu options
    // TODO: Presenter-View #2: Toolbar to select Locales
}
