package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A data structure that has 3 conditions:
 * 1. Sorted (by identifier)
 * 2. No Duplicates (of same identifer)
 * 3. Elements with new values should replace elements with old values (Elements of same identifier)
 * <p>
 * Plus Points:
 * 1. Allows random access
 * 2. Allows custom sorting of the resources via Comparator (default sorting by Long-type ids)
 * <p>
 * Note:
 * - Realized one issue of relying on this list is that if an item is no longer being returned via API (say a deleted conversation), it will continue to show until this object is recreated - page reopened)
 */
public class UniqueSortedUpdatableResourceList<T> implements IUniqueResourceList<T> {

    private static final Comparator DEFAULT_ID_COMPARATOR = new Comparator<Long>() {
        @Override
        public int compare(Long lhs, Long rhs) {
            return lhs.compareTo(rhs);
        }
    };

    private Map<Long, T> mapResources = new HashMap<>();
    private Comparator<T> comparator = null;

    public synchronized boolean addElement(long id, T t) {
        // (2) No duplicates since map ensures a single value for a single identifier
        // (3) Map replaces old value with new value based on identifier
        mapResources.put(id, t);
        return true;
    }

    public synchronized T getElement(long id) {
        // (4) Map replaces old value with new value based on identifier
        if (mapResources.containsKey(id)) {
            return mapResources.get(id);
        } else {
            return null;
        }
    }

    @Override
    public synchronized boolean exists(long id) {
        return mapResources.containsKey(id);
    }

    @Override
    public synchronized void removeElement(long id) {
        mapResources.remove(id);
    }

    @Override
    public void setSortComparator(Comparator<T> comparator) {
        if (comparator == null) {
            this.comparator = DEFAULT_ID_COMPARATOR;
        } else {
            this.comparator = comparator;
        }
    }

    @Override
    public synchronized int getSize() {
        return mapResources.keySet().size();
    }

    @Override
    public synchronized List<T> getList() {
        // (1) sort list by identifiers
        if (comparator == null) {
            List<Long> keys = new ArrayList<Long>(mapResources.keySet());
            Collections.sort(keys, DEFAULT_ID_COMPARATOR);

            List<T> values = new ArrayList<>();
            for (Long key : keys) {
                values.add(mapResources.get(key));
            }
            return values;
        } else {
            List<T> values = new ArrayList<>();
            for (Long key : mapResources.keySet()) {
                values.add(mapResources.get(key));
            }

            Collections.sort(values, comparator);
            return values;
        }
    }
}
