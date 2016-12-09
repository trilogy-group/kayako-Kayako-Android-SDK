package com.kayako.sdk.android.k5.common.viewhelpers;

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
public class DefaultStateViewHelper {

    private ViewStub mEmptyStubView;
    private ViewStub mErrorStubView;
    private ViewStub mLoadingStubView;
    private View mRootView;

    private String mEmptyViewTitle;
    private String mEmptyViewDescription;

    private String mErrorViewTitle;
    private String mErrorViewDescription;
    private View.OnClickListener mErrorViewClickListener;

    public DefaultStateViewHelper(View rootView) {
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

    public void setupEmptyView(@Nullable String title, @Nullable String description) {
        mEmptyViewTitle = title;
        mEmptyViewDescription = description;
    }

    public void setupErrorView(@Nullable String title, @Nullable String description, @NonNull View.OnClickListener listener) {
        mErrorViewTitle = title;
        mErrorViewDescription = description;
        mErrorViewClickListener = listener;
    }

    public void showEmptyView(@NonNull Context context) {
        if (mEmptyStubView != null) {
            // After the stub is inflated, the stub is removed from the view hierarchy.
            mEmptyStubView.setVisibility(View.VISIBLE);
        }

        mRootView.findViewById(R.id.ko__inflated_stub_empty_state).setVisibility(View.VISIBLE);

        TextView description = ((TextView) mRootView.findViewById(R.id.ko__empty_state_description));
        if (mEmptyViewDescription == null) {
            description.setText(context.getString(R.string.ko__label_empty_view_description));
        } else {
            description.setText(mEmptyViewDescription);
        }

        TextView title = ((TextView) mRootView.findViewById(R.id.ko__empty_state_title));
        if (mEmptyViewTitle == null) {
            title.setText(context.getString(R.string.ko__label_empty_view_title));
        } else {
            title.setText(mEmptyViewTitle);
        }
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

    public void showErrorView(@NonNull Context context) {
        if (mErrorStubView != null) {
            mErrorStubView.setVisibility(View.VISIBLE);
        }

        mRootView.findViewById(R.id.ko__inflated_stub_error_state).setVisibility(View.VISIBLE);

        TextView title = ((TextView) mRootView.findViewById(R.id.ko__error_state_title));
        if (mErrorViewTitle == null) {
            title.setText(context.getString(R.string.ko__label_error_view_title));
        } else {
            title.setText(mErrorViewTitle);
        }

        TextView description = ((TextView) mRootView.findViewById(R.id.ko__error_state_description));
        if (mErrorViewDescription == null) {
            description.setText(context.getString(R.string.ko__label_error_view_description));
        } else {
            description.setText(mErrorViewDescription);
        }

        View button = (mRootView.findViewById(R.id.ko__error_retry_button));
        if (mErrorViewClickListener == null) {
            button.setVisibility(View.GONE); // Hide retry button
        } else {
            button.setOnClickListener(mErrorViewClickListener);
            button.setVisibility(View.VISIBLE);
        }
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
