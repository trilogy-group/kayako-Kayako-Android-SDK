package com.kayako.sdk.android.k5.articlelistpage;

import android.text.Html;

import com.kayako.sdk.android.k5.common.data.ListItem;
import com.kayako.sdk.helpcenter.articles.Article;
import com.kayako.sdk.helpcenter.section.Section;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class ArticleListPresenter implements ArticleListContract.Presenter {

    private static final int REQUEST_LIMIT = 20;
    private int mOffset = 0;

    private ArticleListContract.View mView;
    private ArticleListContract.Data mData;

    private List<ListItem> mListItems;
    private List<ListItem> mMoreItems;
    private long mSectionId;
    private String mSectionTitle;
    private String mSectionDescription;

    public ArticleListPresenter(ArticleListContract.View view, ArticleListContract.Data data) {
        mView = view;
        mData = data;
    }

    @Override
    public void setData(ArticleListContract.Data data) {
        mData = data;
    }

    @Override
    public void initPage(long sectionId) {
        invalidateOldValues(); // when presenter is reused for different fragments with different sectionIds, the older values should be cleared
        mSectionId = sectionId;
        if (!mData.isCached(mSectionId)) { // show loading only if data not cached
            mView.showOnlyLoadingView();
        }
        mView.startBackgroundTaskToLoadData();
    }

    @Override
    public void initPage(Section section) {
        mSectionId = section.getId();
        mSectionTitle = section.getTitle();
        mSectionDescription = section.getDescription();
        if (!mData.isCached(mSectionId)) {
            mView.showOnlyLoadingView();
        } // show loading only if data not cached
        mView.startBackgroundTaskToLoadData();
    }

    @Override
    public boolean fetchDataInBackground() {
        try {
            List<Article> articles = mData.getArticles(mSectionId, mOffset = 0, REQUEST_LIMIT, true);
            mListItems = convertToListItems(articles);
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
            } else if (mListItems.size() < REQUEST_LIMIT) {
                mView.showOnlyListView();
                mView.setUpList(mListItems, mSectionTitle, mSectionDescription);
                mView.setListHasMoreItems(false);
            } else {
                mView.showOnlyListView();
                mView.setUpList(mListItems, mSectionTitle, mSectionDescription);
                mView.setListHasMoreItems(true);
            }
        } else {
            mView.showOnlyErrorView();
        }
    }

    @Override
    public boolean fetchMoreDataInBackground() {
        try {
            List<Article> articles = mData.getArticles(mSectionId, mOffset + REQUEST_LIMIT, REQUEST_LIMIT, false);
            mMoreItems = convertToListItems(articles);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void onMoreDataLoaded(boolean isSuccessful) {
        if (isSuccessful) {
            mOffset += REQUEST_LIMIT;
            if (mMoreItems.size() == 0) {
                mView.setListHasMoreItems(false);
            } else if (mMoreItems.size() < REQUEST_LIMIT) {
                mView.addItemsToList(mMoreItems);
                mView.setListHasMoreItems(false);
            } else {
                mView.addItemsToList(mMoreItems);
            }
        } else {
            mView.showLoadMoreErrorMessage();
        }

        mView.hideLoadingMoreItemsProgress();
    }

    @Override
    public void onClickListItem(ListItem listItem) {
        mView.openArticleActivity((Article) listItem.getResource());
    }

    @Override
    public void reloadPage() {
        mView.cancelBackgroundTasks();
        mView.showOnlyLoadingView();
        mView.startBackgroundTaskToLoadData();
    }

    @Override
    public void loadMoreData() {
        mView.showLoadingMoreItemsProgress();
        mView.startBackgroundTaskToLoadMoreData();
    }

    @Override
    public void setView(ArticleListContract.View view) {
        mView = view;
    }

    private List<ListItem> convertToListItems(List<Article> articles) {
        if (articles.size() == 0) {
            return new ArrayList<>();
        } else {
            List<ListItem> items = new ArrayList<>();
            for (Article article : articles) {

                String contentsWithoutHtml = "";
                if (article.getContents() != null && article.getContents().length() > 0) {
                    contentsWithoutHtml = Html.fromHtml(article.getContents()).toString();
                }

                ListItem item = new ListItem(false, article.getTitle(), contentsWithoutHtml, article);
                items.add(item);
            }
            return items;
        }
    }

    private void invalidateOldValues() {
        mListItems = null;
        mMoreItems = null;
        mSectionId = 0L;
        mSectionTitle = null;
        mSectionDescription = null;
        mOffset = 0;
    }
}
