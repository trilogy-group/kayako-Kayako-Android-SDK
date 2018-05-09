package com.kayako.sdk.android.k5.helpcenter.searcharticlepage;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentHostCallback;
import android.view.View;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.searchlist.SearchListItem;
import com.kayako.sdk.android.k5.common.fragments.BaseListFragment;
import com.kayako.sdk.android.k5.common.task.BackgroundTask;
import com.kayako.sdk.android.k5.common.utils.ViewUtils;
import com.kayako.sdk.android.k5.common.viewhelpers.CustomStateViewHelper;
import com.kayako.sdk.android.k5.common.viewhelpers.DefaultStateViewHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.method;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.suppress;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        ViewUtils.class,
        SearchArticleFactory.class,
        AsyncTask.class,
        BaseListItem.class,
        SearchArticleFragment.class
})
public class SearchArticleFragmentTest {

    private SearchArticleFragment searchArticleFragment;

    @Mock
    private SearchArticleContract.Presenter presenter;

    @Mock
    private Bundle savedInstance;

    @Mock
    private View view;

    @Mock
    private BackgroundTask backgroundTask;

    @Mock
    private AsyncTask asyncTask;

    @Mock
    private Context mockContext;

    @Mock
    private Resources resources;

    @Mock
    private BaseListFragment baseListFragment;

    @Mock
    private FragmentHostCallback mHost;

    @Mock
    private SearchListItem searchListItem;

    @Mock
    private CustomStateViewHelper mCustomStateViewHelper;

    @Mock
    private DefaultStateViewHelper mDefaultStateViewHelper;

    @Before
    public void setUp() throws Exception {
        searchArticleFragment = spy(new SearchArticleFragment());

        doNothing().when(presenter).reloadPage();

        Whitebox.setInternalState(searchArticleFragment, "mRoot", view);
        Whitebox.setInternalState(searchArticleFragment, "mPresenter",
                presenter);

        Whitebox.setInternalState(searchArticleFragment, "mSearchTask",
                backgroundTask);
    }

    @Test
    public void newInstance() throws Exception {
        // Arrange
        whenNew(SearchArticleFragment.class).withNoArguments().thenReturn
                (searchArticleFragment);

        // Act
        SearchArticleFragment returned = SearchArticleFragment.newInstance();

        // Assert
        assertThat(returned, is(searchArticleFragment));
    }

    @Test
    public void onCreate() throws Exception {
        // Arrange
        mockStatic(SearchArticleFactory.class);
        when(SearchArticleFactory.getPresenter(searchArticleFragment))
                .thenReturn(presenter);
        searchArticleFragment.onCreate(savedInstance);

        // Act
        SearchArticleContract.Presenter returned = Whitebox.getInternalState
                (searchArticleFragment, "mPresenter");

        // Assert
        assertThat(returned, is(presenter));
    }

    @Test
    public void onViewCreated() throws Exception {
        // Arrange
        ArgumentCaptor<String> captor1 = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> captor2 = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<View.OnClickListener> captor3 = ArgumentCaptor
                .forClass(View.OnClickListener.class);

        when(searchArticleFragment, "getDefaultStateViewHelper").thenReturn
                (mDefaultStateViewHelper);

        // Act
        searchArticleFragment.onViewCreated(view, savedInstance);
        verify(mDefaultStateViewHelper).setupErrorView(captor1.capture(),
                captor2.capture(), captor3.capture());

        View.OnClickListener listener = captor3.getValue();
        listener.onClick(view);

        // Assert
        verify(presenter).reloadPage();
    }

    @Test
    public void onActivityCreated() throws Exception {
        // Act
        searchArticleFragment.onActivityCreated(savedInstance);

        // Assert
        verify(presenter).initPage();
    }

    @Test
    public void onDestroyView() throws Exception {
        // Arrange
        Whitebox.setInternalState(searchArticleFragment, "mLoadMoreTask",
                backgroundTask);

        // Act
        searchArticleFragment.onDestroyView();

        BackgroundTask mSearchTask = Whitebox.getInternalState
                (searchArticleFragment, "mSearchTask");
        BackgroundTask mLoadMoreTask = Whitebox.getInternalState
                (searchArticleFragment, "mLoadMoreTask");

        // Assert
        assertNull(mSearchTask);
        assertNull(mLoadMoreTask);
    }

    @Test
    public void reloadPage() throws Exception {
        // Act
        searchArticleFragment.reloadPage();

        // Assert
        verify(presenter).reloadPage();
    }

    @Test
    public void startSearchTask() throws Exception {
        // Arrange
        suppress(method(AsyncTask.class, "executeOnExecutor"));

        // Act
        searchArticleFragment.startSearchTask();

        // Assert
        verify(backgroundTask).cancelTask();
    }

    @Test
    public void showErrorToLoadMoreMessage() throws Exception {
        // Arrange
        Whitebox.setInternalState(searchArticleFragment, "mHost", mHost);
        when(searchArticleFragment.getContext()).thenReturn(mockContext);
        when(mockContext.getResources()).thenReturn(resources);
        when(resources.getString(R.string
                .ko__msg_error_unable_to_load_more_items)).thenReturn
                ("Something went wrong. Unable to load more items");
        mockStatic(ViewUtils.class);

        // Act
        searchArticleFragment.showErrorToLoadMoreMessage();

        // Assert
        verifyStatic(ViewUtils.class, times(1));
        ViewUtils.showSnackBar(view, "Something went wrong. Unable to load " +
                "more items");
    }

    @Test
    public void loadMoreItems() throws Exception {
        // Act
        searchArticleFragment.loadMoreItems();

        // Assert
        verify(presenter).loadMoreData();
    }

    @Test
    public void showSearchResults() throws Exception {
        // Act
        searchArticleFragment.showSearchResults("test");

        // Assert
        verify(presenter).searchArticles("test");
    }

    @Test
    public void clearSearchResults() throws Exception {
        // Act
        searchArticleFragment.clearSearchResults();

        // Assert
        verify(presenter).clearSearchResults();
    }

    @Test
    public void onClickSearchedArticle() throws Exception {
        // Act
        searchArticleFragment.onClickSearchedArticle(searchListItem);

        // Assert
        verify(presenter).onClickListItem(searchListItem);
    }
}