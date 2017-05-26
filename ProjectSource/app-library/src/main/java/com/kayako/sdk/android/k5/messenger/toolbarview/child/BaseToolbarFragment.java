package com.kayako.sdk.android.k5.messenger.toolbarview.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * This base fragment has been created so that when updates to the view is made, it is ensured that the task is carried out.
 * This is irrespective of how far behind or far ahead the fragment lifecycle state is
 */
public abstract class BaseToolbarFragment extends Fragment {

    private OnViewLoadedListener mOnViewLoadedListener;
    private boolean hasActivityBeenCreated = false;

    @Override
    final public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Callback added to ensure view configuration code is called and not lost due to inconsistent fragment & activity life cycles
        if (mOnViewLoadedListener != null) {
            mOnViewLoadedListener.onViewLoaded();
        }

        hasActivityBeenCreated = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        removeOnViewLoadedListener();
    }

    final protected boolean isPageReadyButView() {
        return isAdded() && getActivity() != null && !getActivity().isFinishing();
    }


    final public void setOnViewLoadedListener(OnViewLoadedListener onViewLoadedListener) {
        this.mOnViewLoadedListener = onViewLoadedListener;

        if (hasActivityBeenCreated && onViewLoadedListener != null) {
            onViewLoadedListener.onViewLoaded();
        }
    }

    final public void removeOnViewLoadedListener() {
        this.mOnViewLoadedListener = null;
    }

}
