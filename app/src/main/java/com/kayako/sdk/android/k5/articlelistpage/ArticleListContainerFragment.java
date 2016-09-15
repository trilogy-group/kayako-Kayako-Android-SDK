package com.kayako.sdk.android.k5.articlelistpage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.activities.KayakoSearchArticleActivity;
import com.kayako.sdk.helpcenter.section.Section;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class ArticleListContainerFragment extends Fragment implements ArticleListContainerContract.View {

    private static final String ARG_RESOURCE = "resource";

    private View mRoot;
    private Toolbar mToolbar;
    private ArticleListContainerContract.Presenter mPresenter;

    public static ArticleListContainerFragment newInstance(Section section) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_RESOURCE, section);

        ArticleListContainerFragment articleListFragment = new ArticleListContainerFragment();
        articleListFragment.setArguments(bundle);
        return articleListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        mPresenter = ArticleListContainerFactory.getPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.ko__fragment_default, null);
        setUpToolbar();
        return mRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Section section = (Section) getArguments().getSerializable(ARG_RESOURCE);
        ArticleListFragment articleListFragment = ArticleListFragment.newInstance(section);
        getChildFragmentManager().beginTransaction().replace(R.id.container, articleListFragment).commit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void setUpToolbar() {
        mToolbar = (Toolbar) mRoot.findViewById(R.id.ko__toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle(null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.ko__menu_default, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;

            case R.id.ko__action_search:
                mPresenter.clickSearchAction();
                return true;

            case R.id.ko__action_contact:
                mPresenter.clickContactPage();
                return true;
        }
        return false;
    }

    @Override
    public void openSearchPage() {
        startActivity(new Intent(getContext(), KayakoSearchArticleActivity.class));
    }

    @Override
    public void openContactPage() {
        // TODO: Open Email or callback specified by Developer
    }
}
