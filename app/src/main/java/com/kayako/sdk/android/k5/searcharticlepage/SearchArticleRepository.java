package com.kayako.sdk.android.k5.searcharticlepage;

import com.kayako.sdk.helpcenter.HelpCenter;
import com.kayako.sdk.helpcenter.search.SearchArticle;

import java.util.List;
import java.util.Locale;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SearchArticleRepository implements SearchArticleContract.Data {

    private HelpCenter mHelpCenter;

    public SearchArticleRepository(String helpCenterUrl, Locale locale) {
        mHelpCenter = new HelpCenter(helpCenterUrl, locale);
    }

    @Override
    public List<SearchArticle> searchArticles(String query, int offset, int limit) {
        return mHelpCenter.getSearchArticles(query, offset, limit);
    }
}
