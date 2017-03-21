package com.kayako.sdk.android.k5.helpcenter.searcharticlepage;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public interface SearchResultCallback {
    void showSearchResults(String query);
    void clearSearchResults();
}
