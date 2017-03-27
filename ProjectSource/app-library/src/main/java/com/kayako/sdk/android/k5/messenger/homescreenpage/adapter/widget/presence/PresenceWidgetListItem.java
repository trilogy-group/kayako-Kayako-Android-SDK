package com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.presence;

import android.support.annotation.NonNull;

import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.HomeScreenListType;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.BaseWidgetListItem;

public class PresenceWidgetListItem extends BaseWidgetListItem {

    private String presenceCaption;

    private String avatarUrl1;
    private String avatarUrl2;
    private String avatarUrl3;

    public PresenceWidgetListItem(@NonNull String title, String presenceCaption, String avatarUrl1, String avatarUrl2, String avatarUrl3) {
        super(HomeScreenListType.WIDGET_PRESENCE, title);
        this.avatarUrl1 = avatarUrl1;
        this.avatarUrl2 = avatarUrl2;
        this.avatarUrl3 = avatarUrl3;
        this.presenceCaption = presenceCaption;
    }

    public String getPresenceCaption() {
        return presenceCaption;
    }

    public String getAvatarUrl1() {
        return avatarUrl1;
    }

    public String getAvatarUrl2() {
        return avatarUrl2;
    }

    public String getAvatarUrl3() {
        return avatarUrl3;
    }
}
