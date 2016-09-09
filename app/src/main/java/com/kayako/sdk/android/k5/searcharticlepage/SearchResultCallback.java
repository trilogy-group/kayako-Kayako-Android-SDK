package com.kayako.sdk.android.k5.searcharticlepage;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public interface SearchResultCallback {
    void showSearchResults(String query);
    void clearSearchResults();
}
