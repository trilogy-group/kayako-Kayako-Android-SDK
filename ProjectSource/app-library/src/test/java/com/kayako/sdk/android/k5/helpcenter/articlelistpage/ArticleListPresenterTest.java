package com.kayako.sdk.android.k5.helpcenter.articlelistpage;

import android.text.Html;
import android.text.Spanned;
import com.kayako.sdk.android.k5.common.adapter.list.ListItem;
import com.kayako.sdk.helpcenter.articles.Article;
import com.kayako.sdk.helpcenter.section.Section;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import java.util.ArrayList;
import java.util.List;

@PrepareForTest(Html.class)
@RunWith(PowerMockRunner.class)
public class ArticleListPresenterTest {

    private static final long SECTION_ID = 0L;
    private static final int OFFSET = 0;
    private static final int LIMIT = 20;
    private static final boolean USE_CACHE = Boolean.TRUE;
    private final List<Article> articles = new ArrayList<>();
    private ArticleListPresenter articleListPresenter;

    @Mock
    private ArticleListContract.View view;

    @Mock
    private ArticleListContract.Data data;

    @Mock
    private Spanned spanned;

    @Mock
    private ListItem listItem;

    @Mock
    private Article mArticle;

    @Captor
    private ArgumentCaptor<Boolean> booleanArgumentCaptor;

    @Captor
    private ArgumentCaptor<Article> articleArgumentCaptor;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        articleListPresenter = new ArticleListPresenter(view, data);
        mockStatic(Html.class);
    }

    @Test
    public void setDataAndView() {
        //Act
        articleListPresenter.setData(data);
        articleListPresenter.setView(view);

        //Assert
        final ArticleListContract.Data mData =
                Whitebox.getInternalState(articleListPresenter, "mData");
        final ArticleListContract.View mView =
                Whitebox.getInternalState(articleListPresenter, "mView");
        errorCollector.checkThat(mData, is(data));
        errorCollector.checkThat(mView, is(view));
    }

    @Test
    public void initPageWhenSectionId() {
        //Arrange
        final long sectionId = 10L;

        //Act
        articleListPresenter.initPage(sectionId);

        //Assert
        final long mSectionId =
                Whitebox.getInternalState(articleListPresenter, "mSectionId");
        errorCollector.checkThat(mSectionId, is(sectionId));
        verify(view, times(1)).showOnlyLoadingView();
        verify(view, times(1)).startBackgroundTaskToLoadData();
    }

    @Test
    public void initPageWhenSection() {
        //Arrange
        final long id = 10L;
        final String title = "title";
        final String description = "description";
        final Section section = new Section();
        section.setId(id);
        section.setTitle(title);
        section.setDescription(description);

        //Act
        articleListPresenter.initPage(section);

        //Assert
        final long mSectionId =
                Whitebox.getInternalState(articleListPresenter, "mSectionId");
        final String mSectionTitle =
                Whitebox.getInternalState(articleListPresenter, "mSectionTitle");
        final String mSectionDescription =
                Whitebox.getInternalState(articleListPresenter, "mSectionDescription");
        errorCollector.checkThat(mSectionId, is(id));
        errorCollector.checkThat(mSectionTitle, is(title));
        errorCollector.checkThat(mSectionDescription, is(description));
        verify(view, times(1)).showOnlyLoadingView();
        verify(view, times(1)).startBackgroundTaskToLoadData();
    }

    @Test
    public void onDataLoadedWhenIsSuccessfulFalse() {
        //Arrange
        final boolean isSuccessful = Boolean.FALSE;

        //Act
        articleListPresenter.onDataLoaded(isSuccessful);

        //Assert
        verify(view, times(1)).showOnlyErrorView();
    }

    @Test
    public void onDataLoadedWhenZeroBaseListItem() throws Exception {
        //Arrange
        final boolean isSuccessful = Boolean.TRUE;
        when(data.getArticles(SECTION_ID, OFFSET, LIMIT ,USE_CACHE)).thenReturn(articles);
        articleListPresenter.fetchDataInBackground();

        //Act
        articleListPresenter.onDataLoaded(isSuccessful);

        //Assert
        verify(view, times(1)).showOnlyEmptyView();
    }

    @Test
    public void onDataLoadedWhenBaseListItemOnLimit() throws Exception {
        //Arrange
        final boolean isSuccessful = Boolean.TRUE;
        final String contents = "contents";
        final Article article = new Article();
        article.setContents(contents);
        articles.add(article);
        when(Html.fromHtml(article.getContents())).thenReturn(spanned);
        when(spanned.toString()).thenReturn(contents);
        when(data.getArticles(SECTION_ID, OFFSET, LIMIT ,USE_CACHE)).thenReturn(articles);
        articleListPresenter.fetchDataInBackground();

        //Act
        articleListPresenter.onDataLoaded(isSuccessful);

        //Assert
        verify(view).setListHasMoreItems(booleanArgumentCaptor.capture());
        verify(view, times(1)).showOnlyListView();
        errorCollector.checkThat(booleanArgumentCaptor.getValue(), is(false));
    }

    @Test
    public void onDataLoadedWhenBaseListItemOverLimit() throws Exception {
        //Arrange
        final boolean isSuccessful = Boolean.TRUE;
        final String contents = "contents";
        final Article article = new Article();
        article.setContents(contents);
        for(int i = 0; i <= LIMIT+5; i++)
            articles.add(article);
        when(Html.fromHtml(article.getContents())).thenReturn(spanned);
        when(spanned.toString()).thenReturn(contents);
        when(data.getArticles(SECTION_ID, OFFSET, LIMIT ,USE_CACHE)).thenReturn(articles);
        articleListPresenter.fetchDataInBackground();

        //Act
        articleListPresenter.onDataLoaded(isSuccessful);

        //Assert
        verify(view).setListHasMoreItems(booleanArgumentCaptor.capture());
        verify(view, times(1)).showOnlyListView();
        errorCollector.checkThat(booleanArgumentCaptor.getValue(), is(true));
    }

    @Test
    public void onMoreDataLoadedWhenIsSuccessfulFalse() {
        //Arrange
        final boolean isSuccessful = Boolean.FALSE;

        //Act
        articleListPresenter.onMoreDataLoaded(isSuccessful);

        //Assert
        verify(view, times(1)).showLoadMoreErrorMessage();
        verify(view, times(1)).hideLoadingMoreItemsProgress();
    }

    @Test
    public void onMoreDataLoadedWhenZeroBaseListItem() throws Exception {
        //Arrange
        final boolean isSuccessful = Boolean.TRUE;
        when(data.getArticles(SECTION_ID, OFFSET, LIMIT ,USE_CACHE)).thenReturn(articles);
        articleListPresenter.fetchMoreDataInBackground();

        //Act
        articleListPresenter.onMoreDataLoaded(isSuccessful);

        //Assert
        verify(view).setListHasMoreItems(booleanArgumentCaptor.capture());
        errorCollector.checkThat(booleanArgumentCaptor.getValue(), is(false));
    }

    @Test
    public void onMoreDataLoadedWhenBaseListItemOnLimit() throws Exception {
        //Arrange
        final boolean isSuccessful = Boolean.TRUE;
        final String contents = "contents";
        final Article article = new Article();
        article.setContents(contents);
        articles.add(article);
        when(Html.fromHtml(article.getContents())).thenReturn(spanned);
        when(spanned.toString()).thenReturn(contents);
        when(data.getArticles(SECTION_ID, OFFSET+LIMIT, LIMIT ,false)).thenReturn(articles);
        articleListPresenter.fetchMoreDataInBackground();

        //Act
        articleListPresenter.onMoreDataLoaded(isSuccessful);

        //Assert
        verify(view).setListHasMoreItems(booleanArgumentCaptor.capture());
        errorCollector.checkThat(booleanArgumentCaptor.getValue(), is(false));
        verify(view, times(1)).hideLoadingMoreItemsProgress();
    }

    @Test
    public void onMoreDataLoadedWhenBaseListItemOverLimit() throws Exception {
        //Arrange
        final boolean isSuccessful = Boolean.TRUE;
        final String contents = "contents";
        final Article article = new Article();
        article.setContents(contents);
        for(int i = 0; i <= LIMIT+5; i++)
            articles.add(article);
        when(Html.fromHtml(article.getContents())).thenReturn(spanned);
        when(spanned.toString()).thenReturn(contents);
        when(data.getArticles(SECTION_ID, OFFSET+LIMIT, LIMIT ,false)).thenReturn(articles);
        articleListPresenter.fetchMoreDataInBackground();

        //Act
        articleListPresenter.onMoreDataLoaded(isSuccessful);

        //Assert
        verify(view, times(1)).hideLoadingMoreItemsProgress();
    }

    @Test
    public void reloadPage() {
        //Act
        articleListPresenter.reloadPage();

        //Assert
        verify(view, times(1)).cancelBackgroundTasks();
        verify(view, times(1)).showOnlyLoadingView();
        verify(view, times(1)).startBackgroundTaskToLoadData();
    }

    @Test
    public void loadMoreData() {
        //Act
        articleListPresenter.loadMoreData();

        //Assert
        verify(view, times(1)).showLoadingMoreItemsProgress();
        verify(view, times(1)).startBackgroundTaskToLoadMoreData();
    }

    @Test
    public void onClickListItem() {
        //Arrange
        when(listItem.getResource()).thenReturn(mArticle);

        //Act
        articleListPresenter.onClickListItem(listItem);

        //Assert
        verify(view).openArticleActivity(articleArgumentCaptor.capture());
        errorCollector.checkThat(articleArgumentCaptor.getValue(), is(mArticle));
    }
}
