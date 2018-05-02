package com.kayako.sdk.android.k5.helpcenter.articlepage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.utils.ImageUtils;
import com.kayako.sdk.android.k5.common.utils.ViewUtils;
import com.kayako.sdk.android.k5.common.view.CircleImageView;
import com.kayako.sdk.android.k5.common.viewhelpers.DefaultStateViewHelper;
import com.kayako.sdk.helpcenter.articles.Article;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.api.support.membermodification.MemberModifier.suppress;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        ArticleFragment.class,
        Bundle.class,
        ArticleFactory.class,
        ImageUtils.class,
        DateUtils.class,
        Intent.class,
        Uri.class,
        ViewUtils.class,
        Snackbar.class,
        Looper.class
})
public class ArticleFragmentTest {
    private static final String ARG_ARTICLE = "article";
    private static final String NAME = "name";
    private static final String AVATAR_URL = "testurl";
    private static final String TITLE = "title";
    private static final String DIRECTORY_PATH = "path";
    private static final String HTML = "html";
    private static final String LAST_UPDATED = "lastUpdated";
    private static final String LAST_POSTED = "lastPosted";
    private static final long TIME = 123L;
    private static final String ERROR_MESSAGE = "error";
    private ArticleFragment articleFragment;

    @Mock
    private Article article;

    @Mock
    private Bundle bundle;

    @Mock
    private ArticleContract.Presenter presenter;

    @Mock
    private View view;

    @Mock
    private LayoutInflater layoutInflater;

    @Mock
    private ViewGroup viewGroup;

    @Mock
    private DefaultStateViewHelper defaultStateViewHelper;

    @Mock
    private TextView textView;

    @Mock
    private CircleImageView circleImageView;

    @Mock
    private WebView webView;

    @Mock
    private WebSettings webSettings;

    @Mock
    private Intent intent;

    @Mock
    private Uri uri;

    @Test
    public void givenArticleWhenNewInstanceThenReturnFragment()
            throws Exception {
        //Arrange
        mockStatic(ArticleFragment.class);
        whenNew(ArticleFragment.class).withAnyArguments()
                .thenReturn(articleFragment);
        whenNew(Bundle.class).withAnyArguments().thenReturn(bundle);

        //Act
        ArticleFragment returnedValue = ArticleFragment
                .newInstance(article);

        //Assert
        assertEquals(articleFragment, returnedValue);
    }

    @Test
    public void givenBundleWhenOnCreateThenGetPresenter() {
        //Arrange
        mockStatic(ArticleFactory.class);
        when(ArticleFactory.getPresenter(articleFragment))
                .thenReturn(presenter);

        //Act
        articleFragment = new ArticleFragment();
        articleFragment.onCreate(bundle);

        //Assert
        verifyStatic();
        ArticleFactory.getPresenter(articleFragment);
    }

    @Test
    public void givenLayoutViewGroupBundleWhenOnCreateViewThenReturnView()
            throws Exception {
        //Arrange
        when(layoutInflater
                .inflate(R.layout.ko__fragment_article_content, null))
                .thenReturn(view);
        whenNew(DefaultStateViewHelper.class).withAnyArguments()
                .thenReturn(defaultStateViewHelper);
        articleFragment = new ArticleFragment();

        //Act
        View returnedValue = articleFragment
                .onCreateView(layoutInflater, viewGroup, bundle);

        //Assert
        assertEquals(view, returnedValue);
    }

    @Test
    public void givenViewBundleWhenOnViewCreatedThenPresenterInitPage() {
        //Arrange
        ArticleFragment articleFragmentSpy =
                spy(new ArticleFragment());
        articleFragmentSpy.setArguments(bundle);
        when(bundle.getSerializable(ARG_ARTICLE)).thenReturn(article);
        mockStatic(ArticleFactory.class);
        when(ArticleFactory.getPresenter(articleFragmentSpy))
                .thenReturn(presenter);
        articleFragmentSpy.onCreate(bundle);

        //Act
        articleFragmentSpy.onViewCreated(view, bundle);

        //Assert
        verify(presenter).initPage(article);
    }

    @Test
    public void givenStringWhenSetAuthorNameThenSetText()
            throws Exception {
        //Arrange
        ArticleFragment articleFragmentSpy =
                spy(new ArticleFragment());
        when(layoutInflater
                .inflate(R.layout.ko__fragment_article_content, null))
                .thenReturn(view);
        whenNew(DefaultStateViewHelper.class).withAnyArguments()
                .thenReturn(defaultStateViewHelper);
        articleFragmentSpy
                .onCreateView(layoutInflater, viewGroup, bundle);
        when(view.findViewById(R.id.ko__article_author_name))
                .thenReturn(textView);

        //Act
        articleFragmentSpy.setAuthorName(NAME);

        //Assert
        verify(textView).setText(NAME);
    }

    @Test
    public void givenStringWhenSetAuthorAvatarThenSetAvatarImage()
            throws Exception {
        //Arrange
        ArticleFragment articleFragmentSpy =
                spy(new ArticleFragment());
        when(layoutInflater
                .inflate(R.layout.ko__fragment_article_content, null))
                .thenReturn(view);
        whenNew(DefaultStateViewHelper.class).withAnyArguments()
                .thenReturn(defaultStateViewHelper);
        articleFragmentSpy
                .onCreateView(layoutInflater, viewGroup, bundle);
        when(view.findViewById(R.id.ko__article_author_avatar))
                .thenReturn(circleImageView);
        suppress(ImageUtils.class
                .getMethod("setAvatarImage",
                        Context.class,
                        CircleImageView.class, String.class));

        //Act
        articleFragmentSpy.setAuthorAvatar(AVATAR_URL);

        //Assert
        verify(view).findViewById(R.id.ko__article_author_avatar);
    }

    @Test
    public void givenStringWhenSetArticleTitleThenSetText()
            throws Exception {
        //Arrange
        ArticleFragment articleFragmentSpy =
                spy(new ArticleFragment());
        when(layoutInflater
                .inflate(R.layout.ko__fragment_article_content, null))
                .thenReturn(view);
        whenNew(DefaultStateViewHelper.class).withAnyArguments()
                .thenReturn(defaultStateViewHelper);
        articleFragmentSpy
                .onCreateView(layoutInflater, viewGroup, bundle);
        when(view.findViewById(R.id.ko__article_title))
                .thenReturn(textView);

        //Act
        articleFragmentSpy.setArticleTitle(TITLE);

        //Assert
        verify(textView).setText(TITLE);
    }

    @Test
    public void givenStringWhenSetArticleDirectoryPathThenSetText()
            throws Exception {
        //Arrange
        ArticleFragment articleFragmentSpy =
                spy(new ArticleFragment());
        when(layoutInflater
                .inflate(R.layout.ko__fragment_article_content, null))
                .thenReturn(view);
        whenNew(DefaultStateViewHelper.class).withAnyArguments()
                .thenReturn(defaultStateViewHelper);
        articleFragmentSpy
                .onCreateView(layoutInflater, viewGroup, bundle);
        when(view.findViewById(R.id.ko__article_directory))
                .thenReturn(textView);

        //Act
        articleFragmentSpy.setArticleDirectoryPath(DIRECTORY_PATH);

        //Assert
        verify(textView).setText(DIRECTORY_PATH);
    }

    @Test
    public void givenStringWhenSetArticleContentThenSetText()
            throws Exception {
        //Arrange
        ArticleFragment articleFragmentSpy =
                spy(new ArticleFragment());
        when(layoutInflater
                .inflate(R.layout.ko__fragment_article_content, null))
                .thenReturn(view);
        whenNew(DefaultStateViewHelper.class).withAnyArguments()
                .thenReturn(defaultStateViewHelper);
        articleFragmentSpy
                .onCreateView(layoutInflater, viewGroup, bundle);
        when(view.findViewById(R.id.ko__article_web_view))
                .thenReturn(webView);
        when(webView.getSettings()).thenReturn(webSettings);

        //Act
        articleFragmentSpy.setArticleContent(HTML);

        //Assert
        verify(view).findViewById(R.id.ko__article_web_view);
    }

    @Test
    public void givenStringWhenArticleLastUpdatedThenSetText()
            throws Exception {
        //Arrange
        ArticleFragment articleFragmentSpy =
                spy(new ArticleFragment());
        when(layoutInflater
                .inflate(R.layout.ko__fragment_article_content, null))
                .thenReturn(view);
        whenNew(DefaultStateViewHelper.class).withAnyArguments()
                .thenReturn(defaultStateViewHelper);
        articleFragmentSpy
                .onCreateView(layoutInflater, viewGroup, bundle);
        when(view.findViewById(R.id.ko__article_last_updated))
                .thenReturn(textView);

        //Act
        articleFragmentSpy.setArticleLastUpdated(LAST_UPDATED);

        //Assert
        verify(textView).setText(LAST_UPDATED);
    }

    @Test
    public void givenStringWhenArticleLastPostedThenSetText()
            throws Exception {
        //Arrange
        ArticleFragment articleFragmentSpy =
                spy(new ArticleFragment());
        when(layoutInflater
                .inflate(R.layout.ko__fragment_article_content, null))
                .thenReturn(view);
        whenNew(DefaultStateViewHelper.class).withAnyArguments()
                .thenReturn(defaultStateViewHelper);
        articleFragmentSpy
                .onCreateView(layoutInflater, viewGroup, bundle);
        when(view.findViewById(R.id.ko__article_last_created))
                .thenReturn(textView);

        //Act
        articleFragmentSpy.setArticleLastPosted(LAST_POSTED);

        //Assert
        verify(textView).setText(LAST_POSTED);
    }

    @Test
    public void givenLongWhenFormatTimeReturnString(){
        //Arrange
        String expectedValue = "";
        articleFragment = new ArticleFragment();
        mockStatic(DateUtils.class);
        when(DateUtils.getRelativeTimeSpanString
                (eq(TIME), any(long.class), eq(DateUtils.DAY_IN_MILLIS)))
                .thenReturn(expectedValue);

        //Act
        String returnedValue = articleFragment.formatTime(TIME);

        //Assert
        assertEquals(expectedValue, returnedValue);
    }

    @Test
    public void givenViewWhenHideContentScrollBarThenHide()
            throws Exception {
        //Arrange
        ArticleFragment articleFragmentSpy =
                spy(new ArticleFragment());
        when(layoutInflater
                .inflate(R.layout.ko__fragment_article_content, null))
                .thenReturn(view);
        whenNew(DefaultStateViewHelper.class).withAnyArguments()
                .thenReturn(defaultStateViewHelper);
        articleFragmentSpy
                .onCreateView(layoutInflater, viewGroup, bundle);
        when(view.findViewById(R.id.ko__article_web_view))
                .thenReturn(webView);

        //Act
        articleFragmentSpy.hideContentScrollbarsWhileAllowingScroll();

        //Assert
        verify(webView).setHorizontalScrollBarEnabled(Boolean.FALSE);
    }

    @Test
    public void givenUrlWhenOpenUrlIntentThenSetData() throws Exception {
        //Arrange
        ArticleFragment articleFragmentSpy =
                spy(new ArticleFragment());
        whenNew(Intent.class).withAnyArguments().thenReturn(intent);
        mockStatic(Uri.class);
        when(Uri.parse(AVATAR_URL)).thenReturn(uri);

        //Act
        articleFragmentSpy.openUrlIntent(AVATAR_URL);

        //Assert
        verifyStatic();
        Uri.parse(AVATAR_URL);
    }

    @Test
    public void givenActivityWhenShowFailedToLoadErrorMessageThenShowMessage() throws Exception {
        //Arrange
        ArticleFragment articleFragmentSpy =
                spy(new ArticleFragment());
        when(articleFragmentSpy.isAdded()).thenReturn(Boolean.TRUE);
        when(layoutInflater
                .inflate(R.layout.ko__fragment_article_content, null))
                .thenReturn(view);
        whenNew(DefaultStateViewHelper.class).withAnyArguments()
                .thenReturn(defaultStateViewHelper);
        articleFragmentSpy.onCreateView(layoutInflater, viewGroup, bundle);
        mockStatic(ViewUtils.class);
        mockStatic(Looper.class);
        Snackbar snackBar = PowerMockito.mock(Snackbar.class);
        when(articleFragmentSpy.getClass().getMethod("getString", View.class, String.class, int.class)).thenReturn(articleFragment);
        when(ViewUtils.createSnackBar(view, articleFragmentSpy.getString(R.string.ko__msg_error_unable_to_article), Snackbar.LENGTH_SHORT)).thenReturn(snackBar);

        //Act
        articleFragmentSpy.showFailedToLoadErrorMessage();

        //Assert
        verify(articleFragmentSpy).isAdded();
    }
}
