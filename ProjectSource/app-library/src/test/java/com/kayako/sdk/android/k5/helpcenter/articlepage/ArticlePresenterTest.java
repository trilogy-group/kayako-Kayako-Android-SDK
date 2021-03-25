package com.kayako.sdk.android.k5.helpcenter.articlepage;

import com.kayako.sdk.helpcenter.articles.Article;
import com.kayako.sdk.helpcenter.category.Category;
import com.kayako.sdk.helpcenter.section.Section;
import com.kayako.sdk.helpcenter.user.UserMinimal;
import junit.framework.Assert;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import java.util.List;

@RunWith(PowerMockRunner.class)
public class ArticlePresenterTest {

    private ArticlePresenter articlePresenter;

    @Mock
    private ArticleContract.View view;

    @Mock
    private UserMinimal userMinimal;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        articlePresenter = new ArticlePresenter(view);
    }

    @Test
    public void setView() {
        //Act
        articlePresenter.setView(view);

        //Assert
        final ArticleContract.View viewLocal =
                Whitebox.getInternalState(articlePresenter, "mView");
        Assert.assertEquals(view, viewLocal);
    }

    @Test
    public void initPage() {
        //Arrange
        final String fullName = "full_name";
        final String avatarUrl = "/avatarUrl";
        final String title = "title";
        final String contents = "contents";
        final long lastUpdated = 2_222L;
        final long lastPosted = 1_111L;
        final String lastUpdatedFormattedString = "Thu Jan 01 1970 00:00:02 GMT+0000";
        final String lastPostedFormattedString = "Thu Jan 01 1970 00:00:01 GMT+0000";
        final Article article = new Article();
        final Section section = new Section();
        final Category category = new Category();
        article.setAuthor(userMinimal);
        when(userMinimal.getFullName()).thenReturn(fullName);
        when(userMinimal.getAvatarUrl()).thenReturn(avatarUrl);
        article.setTitle(title);
        category.setTitle(title);
        section.setTitle(title);
        article.setContents(contents);
        article.setLastUpdated(lastUpdated);
        article.setLastPosted(lastPosted);
        section.setCategory(category);
        article.setSection(section);
        when(view.formatTime(lastUpdated)).thenReturn(lastUpdatedFormattedString);
        when(view.formatTime(lastPosted)).thenReturn(lastPostedFormattedString);

        //Act
        articlePresenter.initPage(article);
        verify(view).setAuthorName(stringArgumentCaptor.capture());
        verify(view).setAuthorAvatar(stringArgumentCaptor.capture());
        verify(view).setArticleTitle(stringArgumentCaptor.capture());
        verify(view).setArticleDirectoryPath(stringArgumentCaptor.capture());
        verify(view).setArticleContent(stringArgumentCaptor.capture());
        verify(view).setArticleLastUpdated(stringArgumentCaptor.capture());
        verify(view).setArticleLastPosted(stringArgumentCaptor.capture());

        //Assert
        final List<String> capturedValues = stringArgumentCaptor.getAllValues();
        errorCollector.checkThat(capturedValues.get(0), is(fullName));
        errorCollector.checkThat(capturedValues.get(1), is(avatarUrl));
        errorCollector.checkThat(capturedValues.get(2), is(title));
        errorCollector.checkThat(capturedValues.get(3), is(title+" > "+title));
        errorCollector.checkThat(capturedValues.get(4), is(contents));
        errorCollector.checkThat(capturedValues.get(5), is("Updated "+lastUpdatedFormattedString));
        errorCollector.checkThat(capturedValues.get(6), is("Posted "+lastPostedFormattedString));
        verify(view, times(1)).hideArticleContent();
        verify(view, times(1)).showLoading();
    }

    @Test
    public void onContentLoaded() {
        //Act
        articlePresenter.onContentLoaded();

        //Assert
        verify(view, times(1)).hideContentScrollbarsWhileAllowingScroll();
        verify(view, times(1)).showArticleContent();
        verify(view, times(1)).hideLoading();
    }

    @Test
    public void onClickLinkInArticle() {
        //Arrange
        final String url = "/url";

        //Act
        articlePresenter.onClickLinkInArticle(url);

        //Assert
        verify(view).openUrlIntent(stringArgumentCaptor.capture());
        errorCollector.checkThat(stringArgumentCaptor.getValue(), is(url));
    }

    @Test
    public void onFailureToLoadContent() {
        //Act
        articlePresenter.onFailureToLoadContent();

        //Assert
        verify(view, times(1)).showFailedToLoadErrorMessage();
    }
}
