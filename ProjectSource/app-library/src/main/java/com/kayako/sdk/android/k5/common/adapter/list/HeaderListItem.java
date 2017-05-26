package com.kayako.sdk.android.k5.common.adapter.list;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.base.parser.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class HeaderListItem extends BaseListItem {

    private String title;
    private Resource resource;

    public HeaderListItem(String title, Resource resource) {
        super(ListType.HEADER_ITEM);
        this.title = title;
        this.resource = resource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public Map<String, String> getContents() {
        Map<String, String> map = new HashMap<>();
        map.put("title", String.valueOf(title));
        return map;
    }
}
