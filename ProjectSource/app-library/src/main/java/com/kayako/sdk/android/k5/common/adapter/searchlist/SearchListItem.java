package com.kayako.sdk.android.k5.common.adapter.searchlist;

import com.kayako.sdk.android.k5.common.adapter.list.ListItem;
import com.kayako.sdk.helpcenter.base.Resource;

public class SearchListItem extends ListItem {

    public SearchListItem(String title, String subtitle, Resource resource) {
        super(SearchListType.SEARCH_ITEM, title, subtitle, resource);
    }
}
