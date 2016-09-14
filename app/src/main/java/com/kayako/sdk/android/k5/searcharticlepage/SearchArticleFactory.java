package com.kayako.sdk.android.k5.searcharticlepage;

import com.kayako.sdk.android.k5.articlelistpage.ArticleListContract;
import com.kayako.sdk.android.k5.articlelistpage.ArticleListPresenter;
import com.kayako.sdk.android.k5.articlelistpage.ArticleListRepository;

import java.util.Locale;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SearchArticleFactory {

    public static SearchArticleContract.Presenter getPresenter(SearchArticleContract.View view) {
        return new SearchArticlePresenter(view);
    }

    public static SearchArticleContract.Data getDataSource(String helpCenterUrl, Locale locale) {
        return new SearchArticleRepository(helpCenterUrl, locale);
    }
}
