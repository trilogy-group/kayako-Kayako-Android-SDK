package com.kayako.sdk.android.k5.sectionbycategorypage;

import android.os.AsyncTask;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.data.SpinnerItem;
import com.kayako.sdk.android.k5.common.fragments.BaseContainerFragment;
import com.kayako.sdk.android.k5.common.task.BackgroundTask;

import java.util.List;
import java.util.Locale;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SectionByCategoryContainerFragment extends BaseContainerFragment implements AdapterView.OnItemSelectedListener, SectionByCategoryContainerContract.View {

    private View mRoot;
    private Toolbar mToolbar;
    private BackgroundTask mBackgroundTask;

    public static Fragment newInstance() {
        return new SectionByCategoryContainerFragment();
    }

    SectionByCategoryContainerContract.Presenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mPresenter = SectionByCategoryContainerFactory.getPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.ko__fragment_help_center, null);
        setUpToolbar();
        return mRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SectionByCategoryListFragment sectionByCategoryListFragment = SectionByCategoryListFragment.newInstance();
        getChildFragmentManager().beginTransaction().replace(R.id.ko__container, sectionByCategoryListFragment).commit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.initPage();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        showContactIcon();
        setContactClickListener(new OnMenuClickListener() {
            @Override
            public void OnMenuClick(MenuItem menuItem) {
                mPresenter.onClickContact();
            }
        });
        refreshOptionsMenu();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mBackgroundTask != null) {
            mBackgroundTask.cancelTask();
            mBackgroundTask = null;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mPresenter.onSpinnerItemSelected((SpinnerItem) adapterView.getSelectedItem());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public void setToolbarSpinner(List<SpinnerItem> items,int defaultPosition) {
        Spinner spinner = (Spinner) mToolbar.findViewById(R.id.ko__toolbar_spinner);
        spinner.setAdapter(new ArrayAdapter<>(getContext(), R.layout.ko__spinner_item, items));
        spinner.setSelection(defaultPosition);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void showToolbarSpinner() {
        (mToolbar.findViewById(R.id.ko__toolbar_spinner)).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideToolbarSpinner() {
        (mToolbar.findViewById(R.id.ko__toolbar_spinner)).setVisibility(View.GONE);
    }

    @Override
    public void showToolbarTitle() {
        (mToolbar.findViewById(R.id.ko__toolbar_title)).setVisibility(View.GONE);

    }

    @Override
    public void hideToolbarTitle() {
        (mToolbar.findViewById(R.id.ko__toolbar_title)).setVisibility(View.GONE);
    }

    @Override
    public void setToolbarTitle(String title) {
        ((TextView) mToolbar.findViewById(R.id.ko__toolbar_title)).setText(title);

    }

    @Override
    public void reloadSectionsByCategory() {
        SectionByCategoryListFragment sectionByCategoryListFragment = SectionByCategoryListFragment.newInstance();
        getChildFragmentManager().beginTransaction().replace(R.id.ko__container, sectionByCategoryListFragment).commitAllowingStateLoss();
    }

    @Override
    public void startBackgroundTask() {
        mBackgroundTask = (BackgroundTask) new BackgroundTask(getActivity()) {
            @Override
            protected boolean performInBackground() {
                return mPresenter.loadDataInBackground();
            }

            @Override
            protected void performOnCompletion(boolean isSuccessful) {
                mPresenter.onDataLoaded(isSuccessful);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void openContactPage() {
        super.openContactPage();
    }

    private void setUpToolbar() {
        mToolbar = (Toolbar) mRoot.findViewById(R.id.ko__toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle(null);
    }
}
