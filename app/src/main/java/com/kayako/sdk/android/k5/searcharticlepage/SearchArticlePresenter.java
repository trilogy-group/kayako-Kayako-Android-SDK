package com.kayako.sdk.android.k5.searcharticlepage;

import com.kayako.sdk.android.k5.common.data.ListItem;
import com.kayako.sdk.android.k5.sectionbycategorypage.SectionByCategoryPageFactory;

import java.util.Locale;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SearchArticlePresenter implements SearchArticleContract.Presenter {

    private SearchArticleContract.View mView;
    private SearchArticleContract.Data mData;

    public SearchArticlePresenter(SearchArticleContract.View view) {
        this.mView = view;
        mData = SearchArticleFactory.getDataSource("https://support.kayako.com", new Locale("en", "us"));
        // TODO: Figure out the best way to handle HelpCenter later.
    }

    @Override
    public void initPage(long sectionId) {

    }

    @Override
    public void searchArticles(String query) {

    }

    @Override
    public boolean loadDataInBackground() {
        return false;
    }

    @Override
    public void onDataLoaded(boolean isSuccessful) {

    }

    @Override
    public void onClickListItem(ListItem listItem) {

    }

    @Override
    public void reloadPage() {

    }

    @Override
    public void setView(SearchArticleContract.View view) {
        mView = view;
    }
}
