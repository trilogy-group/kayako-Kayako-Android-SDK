package com.kayako.sdk.android.k5.common.adapter.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;

public class SearchSectionViewHolder extends RecyclerView.ViewHolder {
    public TextView mSearchEditText;
    public View mView;

    public SearchSectionViewHolder(View view) {
        super(view);
        mView = view;
        mSearchEditText = (TextView) view.findViewById(R.id.ko__search_bar);
    }
}