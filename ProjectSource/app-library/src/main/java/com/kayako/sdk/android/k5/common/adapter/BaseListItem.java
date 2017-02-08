package com.kayako.sdk.android.k5.common.adapter;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public abstract class BaseListItem {

    private int mListItemType;

    public BaseListItem(int type) {
        mListItemType = type;
    }

    public int getItemType() {
        return mListItemType;
    }
}
