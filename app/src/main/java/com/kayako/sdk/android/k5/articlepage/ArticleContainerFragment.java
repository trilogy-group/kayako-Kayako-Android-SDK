package com.kayako.sdk.android.k5.articlepage;

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
import com.kayako.sdk.helpcenter.articles.Article;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class ArticleContainerFragment extends Fragment {

    private static final String ARG_ARTICLE = "article";
    private View mRoot;
    private Toolbar mToolbar;

    public static Fragment newInstance(Article article) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_ARTICLE, article);
        ArticleContainerFragment articleContainerFragment = new ArticleContainerFragment();
        articleContainerFragment.setArguments(bundle);
        return articleContainerFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.ko__fragment_default, null);
        Article article = (Article) getArguments().getSerializable(ARG_ARTICLE);
        setUpToolbar(String.valueOf(article.getId()));
        return mRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Article article = (Article) getArguments().getSerializable(ARG_ARTICLE);
        ArticleFragment articleFragment = ArticleFragment.newInstance(article);
        getChildFragmentManager().beginTransaction().replace(R.id.container, articleFragment).commit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void setUpToolbar(String title) {
        mToolbar = (Toolbar) mRoot.findViewById(R.id.ko__toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle(title);
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
}
