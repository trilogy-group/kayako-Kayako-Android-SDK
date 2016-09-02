package com.kayako.sdk.android.k5.ui;

import com.kayako.sdk.helpcenter.base.Resource;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class ListItem {

    private boolean isHeader;
    private String title;
    private String subtitle;

    private Resource resource;

    public ListItem(boolean isHeader, String title, String subtitle, Resource resource) {
        this.isHeader = isHeader;
        this.title = title;
        this.subtitle = subtitle;
        this.resource = resource;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
