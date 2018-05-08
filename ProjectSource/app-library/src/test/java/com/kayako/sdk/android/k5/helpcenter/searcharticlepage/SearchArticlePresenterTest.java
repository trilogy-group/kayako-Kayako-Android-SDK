package com.kayako.sdk.android.k5.helpcenter.searcharticlepage;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.searchlist.SearchListItem;
import com.kayako.sdk.helpcenter.search.SearchArticle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class SearchArticlePresenterTest {
    private SearchArticlePresenter searchArticlePresenter;

    @Mock
    private SearchArticleContract.View mView;

    @Mock
    private SearchArticleContract.Data mData;

    @Mock
    private SearchArticle searchArticle;

    private final static String TEST_VALUE = "test";

    private List<SearchArticle> searchArticleList;

    @Before
    public void setUp() throws Exception {
        searchArticlePresenter = new SearchArticlePresenter(mView, mData);
        searchArticle.setTitle(TEST_VALUE);
        searchArticle.setCategoryName(TEST_VALUE);
        searchArticle.setSectionName(TEST_VALUE);
        searchArticleList = Collections.singletonList(searchArticle);
    }

    @Test
    public void initPage() throws Exception {
        // Act
        searchArticlePresenter.initPage();

        // Assert
        verify(mView).showBlankView();
    }

    @Test
    public void searchArticles() throws Exception {
        // Act
        searchArticlePresenter.searchArticles(TEST_VALUE);

        String returnedQuery = (String) Whitebox.getInternalState
                (searchArticlePresenter, "mQuery");

        // Assert
        verify(mView).startSearchTask();
        assertThat(returnedQuery, is(returnedQuery));
    }

    @Test
    public void searchArticlesWithNullQuery() throws Exception {
        // Act
        searchArticlePresenter.searchArticles("");

        // Assert
        verify(mView).cancelBackgroundTasks();
        verify(mView).showBlankView();
    }

    @Test
    public void clearSearchResults() throws Exception {
        // Act
        searchArticlePresenter.clearSearchResults();

        // Assert
        verify(mView).showBlankView();
    }

    @Test
    public void loadDataInBackground() throws Exception {
        // Arrange
        Whitebox.setInternalState(searchArticlePresenter, "mQuery", TEST_VALUE);

        when(mData.searchArticles(TEST_VALUE, 0, 20)).thenReturn(Collections
                .singletonList(searchArticle));

        // Act
        boolean returned = searchArticlePresenter.loadDataInBackground();

        // Assert
        verify(mData).searchArticles(TEST_VALUE, 0, 20);
        assertTrue(returned);
    }

    @Test
    public void onDataLoadedLessThanRequestLimit() throws Exception {
        // Arrange
        List<BaseListItem> mListItems = convertToBaseList(searchArticleList);
        Whitebox.setInternalState(searchArticlePresenter, "mListItems",
                mListItems);

        // Act
        searchArticlePresenter.onDataLoaded(true);

        // Assert
        verify(mView).showOnlyListView();
        verify(mView).setUpList(mListItems);
        verify(mView).setListHasMoreItems(false);
    }

    @Test
    public void onDataLoadedGreaterThanRequestLimit() throws Exception {
        // Arrange
        List<SearchArticle> searchArticleListManyItems = new ArrayList<>();
        for (int i = 0; i < 22; i++) {
            searchArticleListManyItems.add(searchArticle);
        }

        List<BaseListItem> baseListItems = convertToBaseList
                (searchArticleListManyItems);

        Whitebox.setInternalState(searchArticlePresenter, "mListItems",
                baseListItems);

        // Act
        searchArticlePresenter.onDataLoaded(true);

        // Assert
        verify(mView).setUpList(baseListItems);
        verify(mView).showOnlyListView();
        verify(mView).setListHasMoreItems(true);
    }

    @Test
    public void onDataLoadedEmoty() throws Exception {
        // Arrange
        Whitebox.setInternalState(searchArticlePresenter, "mListItems", new
                ArrayList<BaseListItem>());

        // Act
        searchArticlePresenter.onDataLoaded(true);

        // Assert
        verify(mView).showOnlyEmptyView();
    }

    @Test
    public void onDataLoadedShowErrorView() throws Exception {
        // Arrange
        searchArticlePresenter.onDataLoaded(false);

        // Assert
        verify(mView).showOnlyErrorView();
    }

    @Test
    public void loadMoreDataInBackground() throws Exception {
        // Arrange
        Whitebox.setInternalState(searchArticlePresenter, "mQuery", TEST_VALUE);
        when(mData.searchArticles(TEST_VALUE, 20, 20)).thenReturn(Collections
                .singletonList(searchArticle));

        // Act
        boolean returned = searchArticlePresenter.loadMoreDataInBackground();

        // Assert
        assertTrue(returned);
    }

    @Test
    public void onMoreDataLoadedEmpty() throws Exception {
        // Arrange
        Whitebox.setInternalState(searchArticlePresenter, "mMoreItems", new
                ArrayList<BaseListItem>());

        // Act
        searchArticlePresenter.onMoreDataLoaded(true);

        // Assert
        verify(mView).setListHasMoreItems(false);
    }

    @Test
    public void onMoreDataLoadedLessThanRequestLimit() throws Exception {
        // Arrange
        List<BaseListItem> baseListItems = convertToBaseList(searchArticleList);
        Whitebox.setInternalState(searchArticlePresenter, "mMoreItems",
                baseListItems);

        // Act
        searchArticlePresenter.onMoreDataLoaded(true);

        // Assert
        verify(mView).addItemsToList(baseListItems);
        verify(mView).setListHasMoreItems(false);
    }

    @Test
    public void onMoreDataLoadedGreaterThanRequestLimit() throws Exception {
        // Arrange
        List<SearchArticle> searchArticleListManyItems = new ArrayList<>();
        for (int i = 0; i < 22; i++) {
            searchArticleListManyItems.add(searchArticle);
        }

        List<BaseListItem> baseListItems = convertToBaseList
                (searchArticleListManyItems);

        Whitebox.setInternalState(searchArticlePresenter, "mMoreItems",
                baseListItems);

        // Act
        searchArticlePresenter.onMoreDataLoaded(true);

        // Assert
        verify(mView).addItemsToList(baseListItems);
    }

    @Test
    public void onMoreDataLoadedNotSuccessful() throws Exception {
        // Act
        searchArticlePresenter.onMoreDataLoaded(false);

        // Assert
        verify(mView).showErrorToLoadMoreMessage();
    }

    @Test
    public void onClickListItem() throws Exception {
        // Arrange
        List<BaseListItem> baseListItems = convertToBaseList(searchArticleList);
        BaseListItem baseListItem = baseListItems.get(0);

        // Act
        searchArticlePresenter.onClickListItem(baseListItem);

        // Assert
        verify(mView).openArticleActivity(searchArticle.getOriginalArticle());
    }

    @Test
    public void reloadPage() throws Exception {
        // Act
        searchArticlePresenter.reloadPage();

        // Assert
        verify(mView).cancelBackgroundTasks();
        verify(mView).showBlankView();
    }

    @Test
    public void loadMoreData() throws Exception {
        // Act
        searchArticlePresenter.loadMoreData();

        // Assert
        verify(mView).showLoadingMoreItemsProgress();
        verify(mView).startLoadMoreTask();
    }

    @Test
    public void setView() throws Exception {
        // Act
        searchArticlePresenter.setView(mView);
        SearchArticleContract.View returned = Whitebox.getInternalState
                (searchArticlePresenter, "mView");

        // Assert
        assertThat(returned, is(mView));
    }

    private List<BaseListItem> convertToBaseList(List<SearchArticle>
                                                         searchArticles) {
        List<BaseListItem> items = new ArrayList<>();
        for (SearchArticle searchArticle : searchArticles) {
            String subtitle = String.format("%s > %s", searchArticle
                    .getCategoryName(), searchArticle.getSectionName());
            items.add(new SearchListItem(searchArticle.getTitle(), subtitle,
                    searchArticle));
        }

        return items;
    }

}