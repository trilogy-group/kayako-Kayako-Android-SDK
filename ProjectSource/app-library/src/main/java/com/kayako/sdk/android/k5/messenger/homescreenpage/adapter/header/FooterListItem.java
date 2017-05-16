package com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.header;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.HomeScreenListType;

import java.util.HashMap;
import java.util.Map;

public class FooterListItem extends BaseListItem{

    public FooterListItem() {
        super(HomeScreenListType.FOOTER);
    }

    @Override
    public Map<String, String> getContents() {
        Map<String, String> map = new HashMap<>();
        return map;
    }
}
