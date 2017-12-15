package com.kayako.sdk.android.k5.kre.data;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class ViewCountChange extends PushData {
    public Long resource_id;
    public String resource_type;
    public String resource_url;
    public ViewCountChangedProperties changed_properties;

    public Long getResourceId() {
        return resource_id;
    }

    public String getResourceType() {
        return resource_type;
    }

    public String getResourceUrl() {
        return resource_url;
    }

    public ViewCountChangedProperties getChangedProperties() {
        return changed_properties;
    }
}
