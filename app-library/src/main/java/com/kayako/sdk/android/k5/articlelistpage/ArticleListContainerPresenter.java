package com.kayako.sdk.android.k5.articlelistpage;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class ArticleListContainerPresenter implements ArticleListContainerContract.Presenter {

    private ArticleListContainerContract.View mView;

    public ArticleListContainerPresenter(ArticleListContainerContract.View view) {
        mView = view;
    }

    @Override
    public void clickSearchAction() {
        mView.openSearchPage();
    }

    @Override
    public void clickContactPage() {
        mView.openContactPage();
    }

    @Override
    public void setView(ArticleListContainerContract.View view) {
        mView = view;
    }
}
