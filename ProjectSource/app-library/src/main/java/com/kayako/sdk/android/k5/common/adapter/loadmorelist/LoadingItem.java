package com.kayako.sdk.android.k5.common.adapter.loadmorelist;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.list.ListType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class LoadingItem extends BaseListItem {

    public LoadingItem() {
        super(ListType.LOADING_ITEM);
    }

    @Override
    public Map<String, String> getContents() {
        Map<String, String> map = new HashMap<>();
        return map;
    }
}