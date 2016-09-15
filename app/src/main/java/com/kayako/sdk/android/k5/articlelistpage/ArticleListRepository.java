package com.kayako.sdk.android.k5.articlelistpage;

import com.kayako.sdk.helpcenter.HelpCenter;
import com.kayako.sdk.helpcenter.articles.Article;

import java.util.List;
import java.util.Locale;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class ArticleListRepository implements ArticleListContract.Data {

    private HelpCenter mHelpCenter;

    public ArticleListRepository(String helpCenterUrl, Locale locale) {
        mHelpCenter = new HelpCenter(helpCenterUrl, locale);
    }

    @Override
    public List<Article> getArticles(long sectionId, int offset, int limit) {
        // TODO: Force Network
        return mHelpCenter.getArticles(sectionId, offset, limit);
    }

    @Override
    public boolean isCached() {
        return false; // TODO:
    }
}
