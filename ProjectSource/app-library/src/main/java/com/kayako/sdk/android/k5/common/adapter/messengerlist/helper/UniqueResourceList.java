package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A list that has 3 conditions:
 * 1. Sorted (by indentifier)
 * 2. No Duplicates (of same identifer)
 * 3. Elements with new values should replace elements with old values (Elements of same identifier)
 */
public class UniqueResourceList<T> implements IUniqueResourceList<T> {

    Map<Long, T> mapResources = new HashMap<>();

    public synchronized boolean addElement(long id, T t) {
        // (2) No duplicates since map ensures a single value for a single identifier
        // (3) Map replaces old value with new value based on identifier
        mapResources.put(id, t);
        return true;
    }

    @Override
    public synchronized List<T> getList() {
        // (1) sort list by identifiers
        List<Long> keys = new ArrayList<Long>(mapResources.keySet());
        Collections.sort(keys, new Comparator<Long>() {
            @Override
            public int compare(Long lhs, Long rhs) {
                return lhs.compareTo(rhs);
            }
        });

        List<T> values = new ArrayList<>();
        for (Long key : keys) {
            values.add(mapResources.get(key));
        }
        return values;
    }

    @Override
    public int getSize() {
        return mapResources.keySet().size();
    }
}
