package com.kayako.sdk.android.k5.common.adapter.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;

public class HeaderViewHolder extends RecyclerView.ViewHolder {
    public final TextView mTitle;
    public HeaderListItem mItem;
    public View mView;

    public HeaderViewHolder(View view) {
        super(view);
        mView = view;
        mTitle = (TextView) view.findViewById(R.id.ko__list_header_title);
    }
}