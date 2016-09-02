package com.kayako.sdk.android.k5.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayako.sdk.android.k5.R;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public abstract class BaseListFragment extends Fragment {

    protected View mRoot;

    @Override
    final public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_item_list, container, false);
        return mRoot;
    }

    protected void showListView() {
        View view = mRoot.findViewById(R.id.ko__list);
        view.setVisibility(View.VISIBLE);
    }

    protected void hideListView() {
        View view = mRoot.findViewById(R.id.ko__list);
        view.setVisibility(View.GONE);
    }

    protected void showEmptyView() {
        View view = mRoot.findViewById(R.id.ko__view_state_empty);
        view.setVisibility(View.VISIBLE);
    }

    protected void hideEmptyView() {
        View view = mRoot.findViewById(R.id.ko__view_state_empty);
        view.setVisibility(View.GONE);
    }

    protected void showErrorView() {
        View view = mRoot.findViewById(R.id.ko__view_state_error);
        view.setVisibility(View.VISIBLE);
    }

    protected void hideErrorView() {
        View view = mRoot.findViewById(R.id.ko__view_state_error);
        view.setVisibility(View.GONE);
    }

    protected void showLoadingView() {
        View view = mRoot.findViewById(R.id.ko__view_state_loading);
        view.setVisibility(View.VISIBLE);
    }

    protected void hideLoadingView() {
        View view = mRoot.findViewById(R.id.ko__view_state_loading);
        view.setVisibility(View.GONE);
    }

    protected void showEmptyViewAndHideOthers() {
        showEmptyView();
        hideErrorView();
        hideLoadingView();
        hideListView();
    }

    protected void showLoadingViewAndHideOthers() {
        showLoadingView();
        hideEmptyView();
        hideErrorView();
        hideListView();
    }

    protected void showErrorViewAndHideOthers() {
        showErrorView();
        hideEmptyView();
        hideLoadingView();
        hideListView();
    }

    protected void showListViewAndHideOthers() {
        showListView();
        hideEmptyView();
        hideErrorView();
        hideLoadingView();
    }

    protected void initList(RecyclerView.Adapter adapter) {
        RecyclerView recyclerView = (RecyclerView) mRoot.findViewById(R.id.ko__list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mRoot.getContext()));
        recyclerView.setAdapter(adapter);
    }
}
