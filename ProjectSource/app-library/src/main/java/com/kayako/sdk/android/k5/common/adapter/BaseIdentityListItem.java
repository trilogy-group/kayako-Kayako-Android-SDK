package com.kayako.sdk.android.k5.common.adapter;

import android.support.annotation.Nullable;

public abstract class BaseIdentityListItem extends BaseListItem {

    @Nullable
    private Long id;

    public BaseIdentityListItem(int type, Long id) {
        super(type);
        this.id = id;
    }

    public BaseIdentityListItem(int type) {
        super(type);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        BaseDataListItem that = (BaseDataListItem) o;

        return id != null ? id.equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
