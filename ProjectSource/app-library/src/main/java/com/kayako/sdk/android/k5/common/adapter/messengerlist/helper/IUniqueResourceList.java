package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public interface IUniqueResourceList<T> {

    boolean addElement(long id, T t);

    List<T> getList();

    int getSize();

    boolean exists(long id);

    void removeElement(long id);

    void setSortComparator(Comparator<T> comparator);

    Set<Long> getIds();
}
