package com.kayako.sdk.android.k5.welcomepage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.searcharticlepage.SearchArticleContainerFragment;

public class KayakoSearchArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ko__activity_search);

        Fragment newFragment = SearchArticleContainerFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, newFragment);
        transaction.commit();
    }
}
