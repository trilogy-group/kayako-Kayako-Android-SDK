package com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;

public class PresenceWidgetViewHolder extends BaseWidgetViewHolder {

    public ImageView avatar1;
    public ImageView avatar2;
    public ImageView avatar3;
    public TextView presenceCaptionTextView;

    public PresenceWidgetViewHolder(View itemView, int contentViewResId) {
        super(itemView, contentViewResId);
        avatar1 = (ImageView) itemView.findViewById(R.id.ko__messenger_home_screen_widget_presence_avatar1);
        avatar2 = (ImageView) itemView.findViewById(R.id.ko__messenger_home_screen_widget_presence_avatar2);
        avatar3 = (ImageView) itemView.findViewById(R.id.ko__messenger_home_screen_widget_presence_avatar3);
        presenceCaptionTextView = (TextView) itemView.findViewById(R.id.ko__messenger_home_screen_widget_presence_caption);
    }
}
