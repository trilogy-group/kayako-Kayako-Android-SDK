package com.kayako.sdk.android.k5.common.adapter;

import android.support.annotation.Nullable;

import java.util.Map;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public abstract class BaseDataListItem extends BaseIdentityListItem {

    @Nullable
    private Map<String, Object> data;

    public BaseDataListItem(int type, Long id, Map<String, Object> data) {
        super(type, id);
        this.data = data;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
