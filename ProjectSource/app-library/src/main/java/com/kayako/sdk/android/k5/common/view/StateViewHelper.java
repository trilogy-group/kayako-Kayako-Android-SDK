package com.kayako.sdk.android.k5.common.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;

/**
 * This helper class can be used with any fragment/activity that includes R.layout.ko__include_state_stubs in their layout xml file.
 */
public class StateViewHelper {

    private ViewStub mEmptyStubView;
    private ViewStub mErrorStubView;
    private ViewStub mLoadingStubView;
    private View mRootView;

    public StateViewHelper(View rootView) {
        if (rootView.findViewById(R.id.ko__stub_empty_state) == null
                || rootView.findViewById(R.id.ko__stub_loading_state) == null
                || rootView.findViewById(R.id.ko__stub_error_state) == null) {

            throw new AssertionError("Make sure the main layout xml is including R.layout.ko__include_state_stubs");
        }

        initStateViews(rootView);
    }

    private void initStateViews(View rootView) {
        mRootView = rootView;
        mEmptyStubView = (ViewStub) mRootView.findViewById(R.id.ko__stub_empty_state);
        mLoadingStubView = (ViewStub) mRootView.findViewById(R.id.ko__stub_loading_state);
        mErrorStubView = (ViewStub) mRootView.findViewById(R.id.ko__stub_error_state);
    }

    public void showEmptyView(@Nullable String title, @Nullable String description, @NonNull Context context) {

        if (mEmptyStubView != null) {
            // After the stub is inflated, the stub is removed from the view hierarchy.
            mEmptyStubView.setVisibility(View.VISIBLE);
        }

        if (title == null) title = context.getString(R.string.ko__label_empty_view_title);
        if (description == null)
            description = context.getString(R.string.ko__label_empty_view_description);

        mRootView.findViewById(R.id.ko__inflated_stub_empty_state).setVisibility(View.VISIBLE);
        ((TextView) mRootView.findViewById(R.id.ko__empty_state_title)).setText(title);
        ((TextView) mRootView.findViewById(R.id.ko__empty_state_description)).setText(description);
    }

    public void hideEmptyView() {
        if (mEmptyStubView != null) {
            mEmptyStubView.setVisibility(View.GONE);
        }
        View inflatedView = mRootView.findViewById(R.id.ko__inflated_stub_empty_state);
        if (inflatedView != null) {
            inflatedView.setVisibility(View.GONE);
        }
    }

    public void showErrorView(@Nullable String title, @Nullable String description, @NonNull Context context, @NonNull View.OnClickListener onClickListener) {
        if (mErrorStubView != null) {
            mErrorStubView.setVisibility(View.VISIBLE);
        }

        if (title == null) title = context.getString(R.string.ko__label_error_view_title);
        if (description == null)
            description = context.getString(R.string.ko__label_error_view_description);

        mRootView.findViewById(R.id.ko__inflated_stub_error_state).setVisibility(View.VISIBLE);
        ((TextView) mRootView.findViewById(R.id.ko__error_state_title)).setText(title);
        ((TextView) mRootView.findViewById(R.id.ko__error_state_description)).setText(description);
        ((Button) mRootView.findViewById(R.id.ko__error_retry_button)).setOnClickListener(onClickListener);
    }

    public void hideErrorView() {
        if (mErrorStubView != null) {
            mErrorStubView.setVisibility(View.GONE);
        }

        View inflatedView = mRootView.findViewById(R.id.ko__inflated_stub_error_state);
        if (inflatedView != null) {
            inflatedView.setVisibility(View.GONE);
        }
    }

    public void showLoadingView() {
        if (mLoadingStubView != null) {
            mLoadingStubView.setVisibility(View.VISIBLE);
        }
        mRootView.findViewById(R.id.ko__inflated_stub_loading_state).setVisibility(View.VISIBLE);
    }

    public void hideLoadingView() {
        if (mLoadingStubView != null) {
            mLoadingStubView.setVisibility(View.GONE);
        }

        View inflatedView = mRootView.findViewById(R.id.ko__inflated_stub_loading_state);
        if (inflatedView != null) {
            inflatedView.setVisibility(View.GONE);
        }
    }
}
