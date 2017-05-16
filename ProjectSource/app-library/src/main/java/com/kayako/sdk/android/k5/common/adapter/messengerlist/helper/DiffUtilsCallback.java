package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import android.support.v7.util.DiffUtil;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;

import java.util.List;

public class DiffUtilsCallback extends DiffUtil.Callback {

    private List<BaseListItem> mOldItems;
    private List<BaseListItem> mNewItems;


    public DiffUtilsCallback(List<BaseListItem> oldItems, List<BaseListItem> newItems) {
        mOldItems = oldItems;
        mNewItems = newItems;
    }

    @Override
    public int getOldListSize() {
        return mOldItems.size();
    }

    @Override
    public int getNewListSize() {
        return mNewItems.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldItems.get(oldItemPosition).getListId() == mNewItems.get(newItemPosition).getListId()
                && mOldItems.get(oldItemPosition).getItemType() == mNewItems.get(newItemPosition).getItemType();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldItems.get(oldItemPosition).getContents() != null
                && mOldItems.get(oldItemPosition).getContents().equals(mNewItems.get(newItemPosition).getContents());
    }
}
