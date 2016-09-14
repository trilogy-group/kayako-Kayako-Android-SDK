package com.kayako.sdk.android.k5.searcharticlepage;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SearchArticleContainerFactory {

    public static SearchArticleContainerContract.Presenter getPresenter(SearchArticleContainerContract.View view) {
        return new SearchArticleContainerPresenter(view);
    }
}
