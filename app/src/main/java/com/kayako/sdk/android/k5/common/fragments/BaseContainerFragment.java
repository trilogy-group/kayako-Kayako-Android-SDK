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

    private boolean showContact;
    private OnMenuClickListener mClickContactListener;

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

        if (showContact) {
            showContactIcon();
        } else {
            hideContactIcon();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;

            case R.id.ko__action_search:
                if (mClickSearchListener != null) {
                    mClickSearchListener.OnMenuClick(item);
                }
                return true;

            case R.id.ko__action_contact:
                if (mClickContactListener != null) {
                    mClickContactListener.OnMenuClick(item);
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

    protected void showContactIcon() {
        if (mMenu != null) {
            MenuItem item = mMenu.findItem(R.id.ko__action_contact);
            item.setVisible(showContact = true);
        }
    }

    protected void hideContactIcon() {
        if (mMenu != null) {
            MenuItem item = mMenu.findItem(R.id.ko__action_contact);
            item.setVisible(showContact = false);
        }
    }

    protected void refreshOptionsMenu() {
        if (getActivity() != null) {
            getActivity().invalidateOptionsMenu();
        }
    }

    protected void setContactClickListener(OnMenuClickListener listener) {
        mClickContactListener = listener;
    }

    protected void openSearchPage() {
        startActivity(new Intent(getContext(), KayakoSearchArticleActivity.class));
    }

    protected void openContactPage() {
        // TODO: Email Intent
    }

    public interface OnMenuClickListener {
        void OnMenuClick(MenuItem menuItem);
    }
}
