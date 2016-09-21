package com.kayako.sdk.android.k5.common.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.activities.KayakoSearchArticleActivity;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public abstract class BaseContainerFragment extends Fragment {

    private Menu mMenu;

    private boolean showSearch;
    private OnMenuClickListener mClickSearchListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        mMenu = menu;
        inflater.inflate(R.menu.ko__menu_default, menu);

        if (showSearch) {
            showSearchIcon();
        } else {
            hideSearchIcon();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            getActivity().onBackPressed();
            return true;
        } else if (i == R.id.ko__action_search) {
            if (mClickSearchListener != null) {
                mClickSearchListener.OnMenuClick(item);
            }
            return true;
        }
        return false;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        mMenu = menu;
    }

    protected void showSearchIcon() {
        if (mMenu != null) {
            MenuItem item = mMenu.findItem(R.id.ko__action_search);
            item.setVisible(showSearch = true);
        }
    }

    protected void hideSearchIcon() {
        if (mMenu != null) {
            MenuItem item = mMenu.findItem(R.id.ko__action_search);
            item.setVisible(showSearch = false);
        }
    }

    protected void setSearchIconClickListener(OnMenuClickListener listener) {
        mClickSearchListener = listener;
    }

    protected void refreshOptionsMenu() {
        if (getActivity() != null) {
            getActivity().invalidateOptionsMenu();
        }
    }

    protected void openSearchPage() {
        startActivity(KayakoSearchArticleActivity.getIntent(getContext()));
    }

    public interface OnMenuClickListener {
        void OnMenuClick(MenuItem menuItem);
    }
}
