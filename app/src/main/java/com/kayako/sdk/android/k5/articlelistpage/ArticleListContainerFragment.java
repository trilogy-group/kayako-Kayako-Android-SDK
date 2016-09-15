package com.kayako.sdk.android.k5.articlelistpage;

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
import com.kayako.sdk.helpcenter.base.Resource;
import com.kayako.sdk.helpcenter.section.Section;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class ArticleListContainerFragment extends Fragment {

    private static final String ARG_ID = "id";
    private static final String ARG_RESOURCE = "resource";

    private View mRoot;
    private Toolbar mToolbar;

//    public static Fragment newInstance(long id) {
//        Bundle bundle = new Bundle();
//        bundle.putLong(ARG_ID, id);
//        ArticleListContainerFragment articleListContainerFragment = new ArticleListContainerFragment();
//        articleListContainerFragment.setArguments(bundle);
//        return articleListContainerFragment;
//    }

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }
        return false;
    }
}
