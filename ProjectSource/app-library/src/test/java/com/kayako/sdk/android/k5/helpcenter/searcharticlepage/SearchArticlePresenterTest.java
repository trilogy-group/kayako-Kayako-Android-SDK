package com.kayako.sdk.android.k5.helpcenter.searcharticlepage;

import com.kayako.sdk.android.k5.common.adapter.searchlist.SearchListItem;
import com.kayako.sdk.error.KayakoException;
import com.kayako.sdk.helpcenter.articles.Article;
import com.kayako.sdk.helpcenter.search.SearchArticle;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class SearchArticlePresenterTest {

    private static final String CATEGORY_NAME = "category_name";
    private static final String SECTION_NAME = "section_name";
    private static final String TITLE = "title";
    private final List<SearchArticle> searchArticleList = new ArrayList<>();
    private SearchArticlePresenter searchArticlePresenter;

    @Mock
    private SearchArticleContract.View view;

    @Mock
    private SearchArticleContract.Data data;

    @Mock
    private SearchListItem item;

    @Mock
    private Article article;

    @Captor
    private ArgumentCaptor<Boolean> booleanArgumentCaptor;

    @Captor
    private ArgumentCaptor<Article> articleArgumentCaptor;


    @Before
    public void setUp() {
        searchArticlePresenter = new SearchArticlePresenter(view, data);
    }

    @Test
    public void setView() {
        //Act
        searchArticlePresenter.setView(view);

        //Assert
        final SearchArticleContract.View mView =
                Whitebox.getInternalState(searchArticlePresenter, "mView");
        assertEquals(view, mView);
    }

    @Test
    public void initPage() {
        //Act
        searchArticlePresenter.initPage();

        //Assert
        verify(view, times(1)).showBlankView();
    }

    @Test
    public void clearSearchResults() {
        //Act
        searchArticlePresenter.clearSearchResults();

        //Assert
        verify(view, times(1)).showBlankView();
    }

    @Test
    public void searchArticlesWhenQueryStringNull() {
        //Arrange
        final String query = null;

        //Act
        searchArticlePresenter.searchArticles(query);

        //Assert
        verify(view, times(1)).cancelBackgroundTasks();
        verify(view, times(1)).showBlankView();
    }

    @Test
    public void searchArticleWhenQueryStringLengthMoreThanThree() {
        //Arrange
        final String query = "Query_String";

        //Act
        searchArticlePresenter.searchArticles(query);
        final String mQuery =
                Whitebox.getInternalState(searchArticlePresenter, "mQuery");

        //Assert
        assertEquals(query, mQuery);
        verify(view, times(1)).cancelBackgroundTasks();
        verify(view, times(1)).showOnlyLoadingView();
        verify(view, times(1)).startSearchTask();
    }

    @Test
    public void onDataLoadedWhenIsSuccessfulFalse() {
        //Arrange
        final boolean isSuccessful = Boolean.FALSE;

        //Act
        searchArticlePresenter.onDataLoaded(isSuccessful);

        //Assert
        verify(view, times(1)).showOnlyErrorView();
    }

    @Test
    public void onDataLoadedWhenSearchArticleListEmpty() {
        //Arrange
        final boolean isSuccessful = Boolean.TRUE;
        searchArticlePresenter.loadDataInBackground();

        //Act
        searchArticlePresenter.onDataLoaded(isSuccessful);

        //Assert
        verify(view, times(1)).showOnlyEmptyView();
    }

    @Test
    public void onDataLoadedWhenSearchArticleListSizeLessThanRequestLimit() throws KayakoException {
        //Arrange
        final SearchArticle searchArticle = new SearchArticle();
        searchArticle.setCategoryName(CATEGORY_NAME);
        searchArticle.setSectionName(SECTION_NAME);
        searchArticle.setTitle(TITLE);
        searchArticleList.add(searchArticle);
        Whitebox.setInternalState(searchArticlePresenter, "mQuery", "query");
        when(data.searchArticles("query", 0, 20)).thenReturn(searchArticleList);
        final boolean isSuccessful = Boolean.TRUE;
        searchArticlePresenter.loadDataInBackground();

        //Act
        searchArticlePresenter.onDataLoaded(isSuccessful);
        verify(view).setListHasMoreItems(booleanArgumentCaptor.capture());

        //Assert
        verify(view, times(1)).showOnlyListView();
        assertFalse(booleanArgumentCaptor.getValue());
    }

    @Test
    public void onDataLoadedWhenSearchArticleListSizeMoreThanRequestLimit() throws KayakoException {
        //Arrange
        final SearchArticle searchArticle = new SearchArticle();
        searchArticle.setCategoryName(CATEGORY_NAME);
        searchArticle.setSectionName(SECTION_NAME);
        searchArticle.setTitle(TITLE);
        for(int i = 0; i < 24; i++) {
            searchArticleList.add(searchArticle);
        }
        Whitebox.setInternalState(searchArticlePresenter, "mQuery", "query");
        when(data.searchArticles("query", 0, 20)).thenReturn(searchArticleList);
        final boolean isSuccessful = Boolean.TRUE;
        searchArticlePresenter.loadDataInBackground();

        //Act
        searchArticlePresenter.onDataLoaded(isSuccessful);
        verify(view).setListHasMoreItems(booleanArgumentCaptor.capture());

        //Assert
        verify(view, times(1)).showOnlyListView();
        assertTrue(booleanArgumentCaptor.getValue());
    }

    @Test
    public void onMoreDataLoadedWhenIsSuccessfulFalse() {
        //Arrange
        final boolean isSuccessful = Boolean.FALSE;

        //Act
        searchArticlePresenter.onMoreDataLoaded(isSuccessful);

        //Assert
        verify(view, times(1)).showErrorToLoadMoreMessage();
        verify(view, times(1)).hideLoadingMoreItemsProgress();
    }

    @Test
    public void onMoreDataLoadedWhenSearchArticleListEmpty() {
        //Arrange
        final boolean isSuccessful = Boolean.TRUE;
        searchArticlePresenter.loadMoreDataInBackground();

        //Act
        searchArticlePresenter.onMoreDataLoaded(isSuccessful);
        verify(view).setListHasMoreItems(booleanArgumentCaptor.capture());

        //Assert
        assertFalse(booleanArgumentCaptor.getValue());
        verify(view, times(1)).hideLoadingMoreItemsProgress();
    }

    @Test
    public void onMoreDataLoadedWhenSearchArticleListSizeLessThanRequestLimit() throws KayakoException {
        //Arrange
        final SearchArticle searchArticle = new SearchArticle();
        searchArticle.setCategoryName(CATEGORY_NAME);
        searchArticle.setSectionName(SECTION_NAME);
        searchArticle.setTitle(TITLE);
        searchArticleList.add(searchArticle);
        Whitebox.setInternalState(searchArticlePresenter, "mQuery", "query");
        when(data.searchArticles("query", 20, 20)).thenReturn(searchArticleList);
        final boolean isSuccessful = Boolean.TRUE;
        searchArticlePresenter.loadMoreDataInBackground();

        //Act
        searchArticlePresenter.onMoreDataLoaded(isSuccessful);
        verify(view).setListHasMoreItems(booleanArgumentCaptor.capture());

        //Assert
        assertFalse(booleanArgumentCaptor.getValue());
    }

    @Test
    public void onMoreDataLoadedWhenSearchArticleListSizeMoreThanRequestLimit() throws KayakoException {
        //Arrange
        final SearchArticle searchArticle = new SearchArticle();
        searchArticle.setCategoryName(CATEGORY_NAME);
        searchArticle.setSectionName(SECTION_NAME);
        searchArticle.setTitle(TITLE);
        for(int i = 0; i < 24; i++) {
            searchArticleList.add(searchArticle);
        }
        Whitebox.setInternalState(searchArticlePresenter, "mQuery", "query");
        when(data.searchArticles("query", 20, 20)).thenReturn(searchArticleList);
        final boolean isSuccessful = Boolean.TRUE;
        searchArticlePresenter.loadMoreDataInBackground();

        //Act
        searchArticlePresenter.onMoreDataLoaded(isSuccessful);

        //Assert
        verify(view, times(1)).hideLoadingMoreItemsProgress();
    }

    @Test
    public void reloadPage() {
        //Act
        searchArticlePresenter.reloadPage();

        //Assert
        verify(view, times(1)).cancelBackgroundTasks();
        verify(view, times(1)).showBlankView();
    }

    @Test
    public void loadMoreData() {
        //Act
        searchArticlePresenter.loadMoreData();

        //Assert
        verify(view, times(1)).showLoadingMoreItemsProgress();
        verify(view, times(1)).startLoadMoreTask();
    }

    @Test
    public void onClickListItem() {
        //Arrange
        final SearchArticle searchArticle = new SearchArticle();
        searchArticle.setOriginalArticle(article);
        when(item.getResource()).thenReturn(searchArticle);

        //Act
        searchArticlePresenter.onClickListItem(item);
        verify(view).openArticleActivity(articleArgumentCaptor.capture());

        //Assert
        assertEquals(article, articleArgumentCaptor.getValue());
    }
}
