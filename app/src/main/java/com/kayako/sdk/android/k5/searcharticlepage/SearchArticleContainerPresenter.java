package com.kayako.sdk.android.k5.searcharticlepage;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SearchArticleContainerPresenter implements SearchArticleContainerContract.Presenter {

    SearchArticleContainerContract.View mView;

    public SearchArticleContainerPresenter(SearchArticleContainerContract.View view) {
        mView = view;
    }

    @Override
    public void onTextEntered(String query) {
        if (query != null && query.length() > 3) {
            mView.showSearchResults(query);
        }
    }

    @Override
    public void onEnterPressed(String query) {
        if (query != null && query.length() > 3) {
            mView.showSearchResults(query);
        }else {
            mView.showLessCharactersTypedErrorMessage();
        }
    }

    @Override
    public void setView(SearchArticleContainerContract.View view) {
        mView = view;
    }
}
