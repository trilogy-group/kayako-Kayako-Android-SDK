package com.kayako.sdk.android.k5.helpcenter.articlelistpage;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.kayako.sdk.android.k5.common.fragments.BaseContainerFragment;
import com.kayako.sdk.helpcenter.section.Section;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class ArticleListContainerFragment extends BaseContainerFragment implements ArticleListContainerContract.View {

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
        setRetainInstance(true);
        mPresenter = ArticleListContainerFactory.getPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.ko__fragment_default, container, false); // parent view added to ensure fragment fills LinearLayout
        setUpToolbar();
        return mRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Section section = (Section) getArguments().getSerializable(ARG_RESOURCE);
        ArticleListFragment articleListFragment = ArticleListFragment.newInstance(section);
        getChildFragmentManager().beginTransaction().replace(R.id.ko__container, articleListFragment).commit();
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
        super.onCreateOptionsMenu(menu, inflater);
        showSearchIcon();
        setSearchIconClickListener(new OnMenuClickListener() {
            @Override
            public void OnMenuClick(MenuItem menuItem) {
                mPresenter.clickSearchAction();
            }
        });
        refreshOptionsMenu();
    }

    @Override
    public void openSearchPage() {
        super.openSearchPage();
    }

}
