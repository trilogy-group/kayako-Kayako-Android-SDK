package com.kayako.sdk.android.k5.common.data;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class LoadingItem {

    private boolean isLoading;

    public LoadingItem(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }
}