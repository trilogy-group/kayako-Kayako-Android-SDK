package com.kayako.sdk.android.k5.common.adapter.loadmorelist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.kayako.sdk.android.k5.R;

public class LoadingViewHolder extends RecyclerView.ViewHolder {
    public ProgressBar mProgressBar;

    public LoadingViewHolder(View view) {
        super(view);
        mProgressBar = (ProgressBar) view.findViewById(R.id.ko__view_loading);
    }
}
