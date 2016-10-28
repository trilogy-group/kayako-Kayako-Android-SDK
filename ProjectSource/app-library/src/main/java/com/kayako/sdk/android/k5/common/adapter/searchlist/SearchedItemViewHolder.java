package com.kayako.sdk.android.k5.common.adapter.searchlist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;

public class SearchedItemViewHolder extends RecyclerView.ViewHolder {
    public TextView mTitle;
    public TextView mSubTitle;
    public View mView;

    public SearchedItemViewHolder(View view) {
        super(view);
        mView = view;
        mTitle = (TextView) view.findViewById(R.id.ko__list_item_title);
        mSubTitle = (TextView) view.findViewById(R.id.ko__list_item_subtitle);
    }
}
