package com.kayako.sdk.android.k5.common.viewhelpers;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * This helper class can be used with any fragment/activity that includes R.layout.ko__include_state_stubs in their layout xml file.
 */
public class CustomStateViewHelper {

    DefaultStateViewHelper mStateViewHelper;

    private LinearLayout mStateContainerView;

    private View mEmptyStateView;
    private View mErrorStateView;
    private View mLoadingStateView;

    public CustomStateViewHelper(@NonNull LinearLayout stateViewContainer) {
        mStateContainerView = stateViewContainer;
    }

    /**
     * Setter commands return the inflated view for the calling activity/fragment to perform additional click listeners, customize text, etc
     *
     * @param layoutId
     * @param context
     * @return
     */
    public View setEmptyView(@LayoutRes int layoutId, @NonNull Context context) {
        return mEmptyStateView = inflateStateView(context, layoutId);
    }

    public View setErrorView(@LayoutRes int layoutId, @NonNull Context context) {
        return mErrorStateView = inflateStateView(context, layoutId);
    }

    public View setLoadingView(@LayoutRes int layoutId, @NonNull Context context) {
        return mLoadingStateView = inflateStateView(context, layoutId);
    }

    public void showEmptyView() {
        if (mEmptyStateView == null) {
            throw new AssertionError("Please setEmptyView() before calling this method");
        }
        replaceContainer(mEmptyStateView);
    }

    public void showLoadingView() {
        if (mLoadingStateView == null) {
            throw new AssertionError("Please setLoadingView() before calling this method");
        }
        replaceContainer(mLoadingStateView);
    }

    public void showErrorView() {
        if (mErrorStateView == null) {
            throw new AssertionError("Please setErrorView() before calling this method");
        }
        replaceContainer(mErrorStateView);
    }

    public void hideEmptyView() {
        clearContainer();
    }

    public void hideLoadingView() {
        clearContainer();
    }

    public void hideErrorView() {
        clearContainer();
    }

    public void hideAll() {
        clearContainer();
    }

    public boolean hasEmptyView() {
        return mEmptyStateView != null;
    }

    public boolean hasErrorView() {
        return mErrorStateView != null;
    }

    public boolean hasLoadingView() {
        return mLoadingStateView != null;
    }

    private void clearContainer() {
        mStateContainerView.removeAllViews();
    }

    private void replaceContainer(View view) {
        if (view == null) {
            return;
        }
        mStateContainerView.removeAllViews();
        mStateContainerView.addView(view);
    }

    private View inflateStateView(Context context, int viewId) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return layoutInflater.inflate(viewId, mStateContainerView, false);
    }
}
