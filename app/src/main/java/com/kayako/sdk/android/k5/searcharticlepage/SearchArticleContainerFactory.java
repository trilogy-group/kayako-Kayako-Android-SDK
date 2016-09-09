package com.kayako.sdk.android.k5.searcharticlepage;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SearchArticleContainerFactory {
    private static SearchArticleContainerContract.Presenter sPresenter;

    public static SearchArticleContainerContract.Presenter getPresenter(SearchArticleContainerContract.View view) {
        if (sPresenter == null) {
            return sPresenter = new SearchArticleContainerPresenter(view);
        } else {
            sPresenter.setView(view);
            return sPresenter;
        }
    }
}
