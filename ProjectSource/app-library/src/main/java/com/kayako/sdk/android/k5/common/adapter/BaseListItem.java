package com.kayako.sdk.android.k5.common.adapter;

import java.util.Map;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public abstract class BaseListItem implements ContentComparable {

    private int listItemType;
    private long listId;

    public BaseListItem(int type) {
        listItemType = type;
    }

    public int getItemType() {
        return listItemType;
    }

    public long getListId() {
        return listId;
    }

    public void setListId(long listId) {
        this.listId = listId;
    }
}

