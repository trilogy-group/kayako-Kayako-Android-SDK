package com.kayako.sdk.android.k5.common.adapter;

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

    @Override
    public boolean equals(Object o) {
        try {
            BaseListItem other = (BaseListItem) o;

            return this.getItemType() == other.getItemType()
                    && this.getListId() == other.getListId()
                    && this.getContents().equals(other.getContents());

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return getContents().hashCode();
    }
}

