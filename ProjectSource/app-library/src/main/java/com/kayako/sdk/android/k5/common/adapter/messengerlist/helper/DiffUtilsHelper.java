package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import com.kayako.sdk.android.k5.common.adapter.BaseIdentityListItem;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;

import java.util.Map;

public class DiffUtilsHelper {

    private DiffUtilsHelper() {
    }

    public static boolean areItemsSame(BaseListItem item1, BaseListItem item2) {
        // if of different list item types, they are different
        if (item1.getItemType() != item2.getItemType()) {
            return false;
        }

        // if of BaseIdentityListItem and have the same ids, they are same
        else if (item1 instanceof BaseIdentityListItem
                && item2 instanceof BaseIdentityListItem
                && ((BaseIdentityListItem) item1).getId() != null
                && ((BaseIdentityListItem) item2).getId() != null
                && ((BaseIdentityListItem) item1).getId().equals((((BaseIdentityListItem) item2).getId()))) {
            return true; // Check ids of BaseDataListItems because they're represent actual API resources with valid ids
        }

        // if dummy views, then they are the same if they have the same content
        else if (item1.getContents() != null && item1.getContents().size() > 0
                && item2.getContents() != null && item2.getContents().size() > 0
                && item1.getContents().equals(item2.getContents())) {
            return true;
        }

        // if they can not be compared in any of the above ways, then they are not the same
        else {
            return false;
        }
    }

    public static boolean areContentsSame(BaseListItem item1, BaseListItem item2) {
        return item1.getContents() != null && item1.getContents().equals(item2.getContents());
    }

    public static String convertToString(Map<String, String> map) {
        if (map == null) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String key : map.keySet()) {
            String value = map.get(key);
            if (value != null) {
                stringBuilder.append(key);
                stringBuilder.append("=");
                stringBuilder.append(value);
                stringBuilder.append("\n");
            }
        }

        return stringBuilder.toString();
    }
}
