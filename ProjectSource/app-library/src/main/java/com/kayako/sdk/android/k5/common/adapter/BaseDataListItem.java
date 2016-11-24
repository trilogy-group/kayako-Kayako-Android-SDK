package com.kayako.sdk.android.k5.common.adapter;

import java.util.Map;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public abstract class BaseDataListItem extends BaseListItem {

    private Map<String, Object> data;

    public BaseDataListItem(int type, Map<String, Object> data) {
        super(type);
        this.data = data;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

}
