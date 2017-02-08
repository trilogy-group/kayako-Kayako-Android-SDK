package com.kayako.sdk.android.k5.common.adapter.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;

public class ListItemViewHolder extends RecyclerView.ViewHolder {
    public final TextView mTitle;
    public final TextView mSubTitle;
    public ListItem mItem;
    public View mView;

    public ListItemViewHolder(View view) {
        super(view);
        mView = view;
        mTitle = (TextView) view.findViewById(R.id.ko__list_item_title);
        mSubTitle = (TextView) view.findViewById(R.id.ko__list_item_subtitle);
    }
}