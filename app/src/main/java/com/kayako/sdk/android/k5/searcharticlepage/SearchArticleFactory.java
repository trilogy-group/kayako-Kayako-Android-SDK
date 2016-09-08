package com.kayako.sdk.android.k5.searcharticlepage;

import com.kayako.sdk.android.k5.articlelistpage.ArticleListContract;
import com.kayako.sdk.android.k5.articlelistpage.ArticleListPresenter;
import com.kayako.sdk.android.k5.articlelistpage.ArticleListRepository;

import java.util.Locale;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SearchArticleFactory {

    private static SearchArticleContract.Presenter sPresenter;

    public static SearchArticleContract.Presenter getPresenter(SearchArticleContract.View view) {
        if (sPresenter == null) {
            return sPresenter = new SearchArticlePresenter(view);
        } else {
            sPresenter.setView(view);
            return sPresenter;
        }
    }

    public static SearchArticleContract.Data getDataSource(String helpCenterUrl, Locale locale) {
        return new SearchArticleRepository(helpCenterUrl, locale);
    }
}
