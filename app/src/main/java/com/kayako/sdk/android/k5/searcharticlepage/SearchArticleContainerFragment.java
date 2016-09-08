package com.kayako.sdk.android.k5.searcharticlepage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.articlelistpage.ArticleListFragment;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SearchArticleContainerFragment extends Fragment {

    private View mRoot;
    private Toolbar mToolbar;

    public static Fragment newInstance() {
        return new SearchArticleContainerFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.ko__fragment_search, null);
        setUpToolbar();
        return mRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showSearchResultFragment();
    }

    private void setUpToolbar() {
        mToolbar = (Toolbar) mRoot.findViewById(R.id.ko__search_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle(null);
    }

    private void showSearchResultFragment() {
        getChildFragmentManager().beginTransaction().replace(R.id.container, ArticleListFragment.newInstance(243)).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }
        return false;
    }

    // TODO: Inflate view
    // TODO: Set up listeners for the edit text in search
}
