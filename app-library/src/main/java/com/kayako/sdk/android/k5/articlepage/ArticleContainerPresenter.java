package com.kayako.sdk.android.k5.articlepage;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class ArticleContainerPresenter implements ArticleContainerContract.Presenter {

    private ArticleContainerContract.View mView;

    public ArticleContainerPresenter(ArticleContainerContract.View view) {
        mView = view;
    }

    @Override
    public void onClickSearch() {
        mView.openSearchActivity();
    }

    @Override
    public void onClickContact() {
        mView.openContactActivity();
    }

    @Override
    public void setView(ArticleContainerContract.View view) {
        mView = view;
    }
}
