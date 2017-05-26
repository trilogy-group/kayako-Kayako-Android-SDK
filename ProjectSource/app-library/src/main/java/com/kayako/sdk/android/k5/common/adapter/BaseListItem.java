package com.kayako.sdk.android.k5.common.adapter;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public abstract class BaseListItem implements ContentComparable {

    private int listItemType;

    public BaseListItem(int type) {
        listItemType = type;
    }

    public int getItemType() {
        return listItemType;
    }

    @Override
    public boolean equals(Object o) {
        try {
            BaseListItem other = (BaseListItem) o;
            return this.getItemType() == other.getItemType()
                    && this.getContents().equals(other.getContents());

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        if (getContents() != null) {
            return getContents().hashCode();
        } else {
            return super.hashCode();
        }
    }
}

