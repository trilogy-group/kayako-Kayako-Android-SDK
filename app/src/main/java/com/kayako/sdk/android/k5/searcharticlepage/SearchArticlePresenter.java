package com.kayako.sdk.android.k5.searcharticlepage;

import com.kayako.sdk.android.k5.common.data.ListItem;
import com.kayako.sdk.helpcenter.search.SearchArticle;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SearchArticlePresenter implements SearchArticleContract.Presenter {

    private static final int REQUEST_LIMIT = 20;
    private int mOffset = 0;
    private SearchArticleContract.View mView;
    private SearchArticleContract.Data mData;
    private String mQuery;
    private List<ListItem> mListItems;

    public SearchArticlePresenter(SearchArticleContract.View view) {
        this.mView = view;
        mData = SearchArticleFactory.getDataSource("https://support.kayako.com", new Locale("en", "us"));
        // TODO: Figure out the best way to handle HelpCenter later.
    }

    @Override
    public void initPage() {
        mView.showBlankView();
    }

    @Override
    public void searchArticles(String query) {
        // Only perform search if valid query string (at least 3 characters)
        if (query == null || query.length() < 3) {
            mView.cancelBackgroundTasks();
            mView.showBlankView();
            return;
        }

        // Clear existing tasks
        mView.cancelBackgroundTasks();
        mView.showOnlyLoadingView();

        // Start Search task
        mQuery = query;
        mView.startSearchTask();
    }

    @Override
    public void clearSearchResults() {
        mView.showBlankView();
    }

    @Override
    public boolean loadDataInBackground() {
        try {
            List<SearchArticle> searchArticleList = mData.searchArticles(mQuery, mOffset, REQUEST_LIMIT);
            mListItems = convertToListItems(searchArticleList);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void onDataLoaded(boolean isSuccessful) {
        if (isSuccessful) {
            if (mListItems.size() == 0) {
                mView.showOnlyEmptyView();
            } else {
                mView.setUpList(mListItems);
                mView.showOnlyListView();
            }
        } else {
            mView.showOnlyErrorView();
        }
    }

    @Override
    public boolean loadMoreDataInBackground() {
        return false; // TODO
    }

    @Override
    public void onMoreDataLoaded(boolean isSuccessful) {
        // TODO
    }

    @Override
    public void onClickListItem(ListItem listItem) {
        //TODO
    }

    @Override
    public void reloadPage() {
        // TODO
    }

    @Override
    public void loadMoreData() {
//        mView.startLoadMoreTask(); // TODO
    }

    @Override
    public void setView(SearchArticleContract.View view) {
        mView = view;
    }

    private List<ListItem> convertToListItems(List<SearchArticle> searchArticleList) {
        if (searchArticleList.size() == 0) {
            return new ArrayList<>();
        } else {
            List<ListItem> items = new ArrayList<>();
            for (SearchArticle searchArticle : searchArticleList) {
                String subtitle = String.format("%s > %s", searchArticle.getCategoryName(), searchArticle.getSectionName());
                items.add(new ListItem(false, searchArticle.getTitle(), subtitle, searchArticle));
            }
            return items;
        }
    }
}
