package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import java.util.List;

public interface IUniqueResourceList<T> {

    boolean addElement(long id, T t);

    List<T> getList();

    int getSize();

    boolean exists(long id);

    void removeElement(long id);
}
