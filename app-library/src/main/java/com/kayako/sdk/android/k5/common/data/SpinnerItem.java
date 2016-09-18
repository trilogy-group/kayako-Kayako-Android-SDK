package com.kayako.sdk.android.k5.common.data;


import com.kayako.sdk.helpcenter.base.Resource;

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
}