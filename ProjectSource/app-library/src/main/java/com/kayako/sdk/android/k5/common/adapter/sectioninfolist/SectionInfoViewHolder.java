package com.kayako.sdk.android.k5.common.adapter.sectioninfolist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;

public class SectionInfoViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public TextView description;
    public View mView;

    public SectionInfoViewHolder(View view) {
        super(view);
        mView = view;
        title = (TextView) view.findViewById(R.id.ko__section_title);
        description = (TextView) view.findViewById(R.id.ko__section_description);
    }
}
