package com.kayako.sdk.android.k5.articlelistpage;

import android.text.Html;

import com.kayako.sdk.android.k5.common.data.ListItem;
import com.kayako.sdk.helpcenter.articles.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class ArticleListPresenter implements ArticleListContract.Presenter {

    private ArticleListContract.View mView;
    private ArticleListContract.Data mData;
    private List<ListItem> mListItems;
    private long mSectionId;

    public ArticleListPresenter(ArticleListContract.View view) {
        mView = view;
        mData = ArticleListFactory.getDataSource("https://support.kayako.com", new Locale("en", "us"));
        // TODO: Figure out the best way to handle HelpCenterUrl data and locale later.
    }

    // TODO: Get sectionId

    @Override
    public void initPage(long sectionId) {
        mSectionId = sectionId;
        mView.showOnlyLoadingView();
        mView.startBackgroundTask();
    }

    @Override
    public boolean loadDataInBackground() {
        // TODO: Add flags to load more or refresh page
        try {
            List<Article> articles = mData.getArticles(mSectionId, 0, 20);
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
            } else {
                mView.showOnlyListView();
                mView.setUpList(mListItems);
            }
        } else {
            mView.showOnlyErrorView();
        }
    }

    @Override
    public void onClickListItem(ListItem listItem) {
        // TODO: Open Activity to show Article Details
    }

    @Override
    public void reloadPage() {
        // TODO
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
