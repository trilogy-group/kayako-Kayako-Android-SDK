package com.kayako.sdk.android.k5.common.fragments;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public abstract class BaseStateFragment extends Fragment {

    private ViewStub mEmptyStubView;
    private ViewStub mErrorStubView;
    private ViewStub mLoadingStubView;
    private View mRootView;

    public void initStateViews(View rootView) {
        mRootView = rootView;
        mEmptyStubView = (ViewStub) mRootView.findViewById(R.id.ko__stub_empty_state);
        mLoadingStubView = (ViewStub) mRootView.findViewById(R.id.ko__stub_loading_state);
        mErrorStubView = (ViewStub) mRootView.findViewById(R.id.ko__stub_error_state);
    }

    protected void showEmptyView(@Nullable String title, @Nullable String description) {
        if (mEmptyStubView != null) {
            // After the stub is inflated, the stub is removed from the view hierarchy.
            mEmptyStubView.setVisibility(View.VISIBLE);
        }

        if (title == null) {
            title = getString(R.string.ko__label_empty_view_title);
        }

        if (description == null) {
            description = getString(R.string.ko__label_empty_view_description);
        }

        mRootView.findViewById(R.id.ko__inflated_stub_empty_state).setVisibility(View.VISIBLE);
        ((TextView) mRootView.findViewById(R.id.ko__empty_state_title)).setText(title);
        ((TextView) mRootView.findViewById(R.id.ko__empty_state_description)).setText(description);
    }

    protected void hideEmptyView() {
        if (mEmptyStubView != null) {
            mEmptyStubView.setVisibility(View.GONE);
        }
        View inflatedView = mRootView.findViewById(R.id.ko__inflated_stub_empty_state);
        if (inflatedView != null) {
            inflatedView.setVisibility(View.GONE);
        }
    }

    protected void showErrorView(@Nullable String title, @Nullable String description, @NonNull View.OnClickListener onClickListener) {
        if (mErrorStubView != null) {
            mErrorStubView.setVisibility(View.VISIBLE);
        }

        if (title == null) title = getString(R.string.ko__label_error_view_title);
        if (description == null) description = getString(R.string.ko__label_error_view_description);

        mRootView.findViewById(R.id.ko__inflated_stub_error_state).setVisibility(View.VISIBLE);
        ((TextView) mRootView.findViewById(R.id.ko__error_state_title)).setText(title);
        ((TextView) mRootView.findViewById(R.id.ko__error_state_description)).setText(description);
        ((Button) mRootView.findViewById(R.id.ko__error_retry_button)).setOnClickListener(onClickListener);
    }

    protected void hideErrorView() {
        if (mErrorStubView != null) {
            mErrorStubView.setVisibility(View.GONE);
        }

        View inflatedView = mRootView.findViewById(R.id.ko__inflated_stub_error_state);
        if (inflatedView != null) {
            inflatedView.setVisibility(View.GONE);
        }
    }

    protected void showLoadingView() {
        if (mLoadingStubView != null) {
            mLoadingStubView.setVisibility(View.VISIBLE);
        }
        mRootView.findViewById(R.id.ko__inflated_stub_loading_state).setVisibility(View.VISIBLE);
    }

    protected void hideLoadingView() {
        if (mLoadingStubView != null) {
            mLoadingStubView.setVisibility(View.GONE);
        }

        View inflatedView = mRootView.findViewById(R.id.ko__inflated_stub_loading_state);
        if (inflatedView != null) {
            inflatedView.setVisibility(View.GONE);
        }
    }
}
