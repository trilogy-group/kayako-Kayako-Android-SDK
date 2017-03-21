package com.kayako.sdk.android.k5.messenger.homescreenpage.adapter;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;

public class HeaderListItem extends BaseListItem {

    public String title;
    public String subtitle;

    public HeaderListItem(String title, String subtitle) {
        super(HomeScreenListType.WIDGET_HEADER);
        this.title = title;
        this.subtitle = subtitle;

        if (title == null || subtitle == null) {
            throw new IllegalArgumentException("Title and Subtitle can not be null!");
        }
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }
}
