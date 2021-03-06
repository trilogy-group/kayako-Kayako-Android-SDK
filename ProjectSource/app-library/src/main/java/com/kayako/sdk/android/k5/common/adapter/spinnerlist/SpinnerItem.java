package com.kayako.sdk.android.k5.common.adapter.spinnerlist;


import com.kayako.sdk.base.parser.Resource;

public class SpinnerItem {

    private String label;
    private Resource resource;

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SpinnerItem) {
            SpinnerItem otherItem = (SpinnerItem) obj;
            return resource != null && otherItem.getResource() != null && resource.equals(otherItem.getResource());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return resource.hashCode();
    }
}