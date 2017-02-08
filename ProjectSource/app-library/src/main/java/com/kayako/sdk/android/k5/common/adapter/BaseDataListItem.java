package com.kayako.sdk.android.k5.common.adapter;

import java.util.Map;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public abstract class BaseDataListItem extends BaseListItem {

    private Long id;
    private Map<String, Object> data;

    public BaseDataListItem(int type, Long id, Map<String, Object> data) {
        super(type);
        this.id = id;
        this.data = data;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
