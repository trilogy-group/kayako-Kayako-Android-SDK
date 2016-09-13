package com.kayako.sdk.android.k5.articlelistpage;

import android.text.Html;

import com.kayako.sdk.android.k5.common.core.HelpCenterPref;
import com.kayako.sdk.android.k5.common.data.ListItem;
import com.kayako.sdk.helpcenter.articles.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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


    public ArticleListPresenter(ArticleListContract.View view) {
        mView = view;
        mData = ArticleListFactory.getDataSource(HelpCenterPref.getInstance().getHelpCenterUrl(), HelpCenterPref.getInstance().getLocale());
    }

    @Override
    public void initPage(long sectionId) {
        mSectionId = sectionId;
        mView.showOnlyLoadingView();
        mView.startBackgroundTaskToLoadData();
    }

    @Override
    public boolean fetchDataInBackground() {
        try {
            List<Article> articles = mData.getArticles(mSectionId, mOffset = 0, REQUEST_LIMIT);
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
                mView.setUpList(mListItems);
                mView.setListHasMoreItems(false);
            } else {
                mView.showOnlyListView();
                mView.setUpList(mListItems);
                mView.setListHasMoreItems(true);
            }
        } else {
            mView.showOnlyErrorView();
        }
    }

    @Override
    public boolean fetchMoreDataInBackground() {
        try {
            List<Article> articles = mData.getArticles(mSectionId, mOffset + REQUEST_LIMIT, REQUEST_LIMIT);
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
            // TODO: Show error message? Show toast instead of replacing whole screen with error.
        }

        mView.hideLoadingMoreItemsProgress();
    }

    @Override
    public void onClickListItem(ListItem listItem) {
        mView.openArticleActivity((Article) listItem.getResource());
    }

    @Override
    public void reloadPage() {
        mView.showOnlyLoadingView();
        mView.startBackgroundTaskToLoadData();
        // TODO: Cancel any ongoing tasks?
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
}
