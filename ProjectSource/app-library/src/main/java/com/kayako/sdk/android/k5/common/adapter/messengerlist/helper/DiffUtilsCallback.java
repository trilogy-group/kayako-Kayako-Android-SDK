package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import android.support.v7.util.DiffUtil;

import com.kayako.sdk.android.k5.common.adapter.BaseDataListItem;
import com.kayako.sdk.android.k5.common.adapter.BaseIdentityListItem;
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
        return DiffUtilsHelper.areItemsSame(mOldItems.get(oldItemPosition), mNewItems.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return DiffUtilsHelper.areContentsSame(mOldItems.get(oldItemPosition), mNewItems.get(newItemPosition));
    }
}
