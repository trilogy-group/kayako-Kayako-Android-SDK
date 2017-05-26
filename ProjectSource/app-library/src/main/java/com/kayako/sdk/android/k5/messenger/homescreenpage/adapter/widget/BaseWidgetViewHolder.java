package com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;

public abstract class BaseWidgetViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView actionButton;
    public ViewGroup container;

    public BaseWidgetViewHolder(View itemView, int contentViewResId) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.ko__messenger_home_screen_widget_title);
        actionButton = (TextView) itemView.findViewById(R.id.ko__messenger_home_screen_widget_action_button);

        container = (ViewGroup) itemView.findViewById(R.id.ko__messenger_home_screen_widget_container);
        View presenceWidgetContent = LayoutInflater.from(itemView.getContext()).inflate(contentViewResId, container, false);
        container.removeAllViews();
        container.addView(presenceWidgetContent);
    }
}
