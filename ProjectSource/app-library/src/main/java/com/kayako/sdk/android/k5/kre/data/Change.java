package com.kayako.sdk.android.k5.kre.data;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class Change extends PushData {
    public Long resource_id;
    public String resource_type;
    public String resource_url;
    public ChangedProperties changed_properties;

    public Change(Long resource_id, String resource_type, String resource_url, ChangedProperties changed_properties) {
        this.resource_id = resource_id;
        this.resource_type = resource_type;
        this.resource_url = resource_url;
        this.changed_properties = changed_properties;
    }

    public Long getResourceId() {
        return resource_id;
    }

    public String getResourceType() {
        return resource_type;
    }

    public String getResourceUrl() {
        return resource_url;
    }

    public ChangedProperties getChangedProperties() {
        return changed_properties;
    }
}
