package com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.presence;

import android.support.annotation.NonNull;

import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.HomeScreenListType;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.BaseWidgetListItem;

import java.util.HashMap;
import java.util.Map;

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

    @Override
    public Map<String, String> getContents() {
        Map<String, String> map = new HashMap<>();
        map.put("title", String.valueOf(getTitle()));
        map.put("presenceCaption", String.valueOf(presenceCaption));
        map.put("avatarUrl1", String.valueOf(avatarUrl1));
        map.put("avatarUrl2", String.valueOf(avatarUrl2));
        map.put("avatarUrl3", String.valueOf(avatarUrl3));
        return map;
    }

}
