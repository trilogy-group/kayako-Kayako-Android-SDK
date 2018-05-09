package com.kayako.sdk.android.k5.helpcenter.searcharticlepage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import com.kayako.sdk.android.k5.common.adapter.searchlist.SearchListItem;
import com.kayako.sdk.android.k5.common.fragments.BaseListFragment;
import com.kayako.sdk.android.k5.common.task.BackgroundTask;
import com.kayako.sdk.android.k5.common.viewhelpers.DefaultStateViewHelper;
import com.kayako.sdk.android.k5.core.HelpCenterPref;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import static junit.framework.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Locale;

@PrepareForTest({
        HelpCenterPref.class,
        BaseListFragment.class,
        SearchArticlePresenter.class,
        Fragment.class,
        BackgroundTask.class
})
@RunWith(PowerMockRunner.class)
public class SearchArticleFragmentTest {

    private SearchArticleFragment searchArticleFragment;

    @Mock
    private Bundle bundle;

    @Mock
    private View view;

    @Mock
    private HelpCenterPref helpCenterPref;

    @Mock
    private Locale locale;

    @Mock
    private DefaultStateViewHelper defaultStateViewHelper;

    @Mock
    private SearchArticleContract.Presenter presenter;

    @Mock
    private BackgroundTask backgroundTask;

    @Mock
    private SearchListItem item;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @Captor
    private ArgumentCaptor<SearchListItem> searchListItemArgumentCaptor;

    @Before
    public void setUp() {
        mockStatic(HelpCenterPref.class);
        when(HelpCenterPref.getInstance()).thenReturn(helpCenterPref);
        when(helpCenterPref.getHelpCenterUrl()).thenReturn("/helpUrl");
        when(helpCenterPref.getLocale()).thenReturn(locale);
        searchArticleFragment = SearchArticleFragment.newInstance();
    }

    @Test
    public void onCreate() {
        //Act
        searchArticleFragment.onCreate(bundle);

        //Assert
        final SearchArticleContract.Presenter presenterLocal =
                Whitebox.getInternalState(searchArticleFragment, "mPresenter");
        assertNotNull(presenterLocal);
    }

    @Test
    public void onViewCreated() throws NoSuchMethodException {
        //Arrange
        final String nullString = null;
        Whitebox.setInternalState(searchArticleFragment, "mPresenter", presenter);
        PowerMockito.suppress(PowerMockito.methods(SearchArticlePresenter.class, "reloadPage"));
        Method superGetDefaultStateViewHelper = BaseListFragment.class.getDeclaredMethod("getDefaultStateViewHelper");
        PowerMockito.replace(superGetDefaultStateViewHelper).with(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return defaultStateViewHelper;
            }
        });
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object arguments[] = invocationOnMock.getArguments();
                View.OnClickListener onClickListener = (View.OnClickListener)arguments[2];

                onClickListener.onClick(view);
                return null;
            }
        }).when(defaultStateViewHelper).setupErrorView(eq(nullString), eq(nullString), any(View.OnClickListener.class));

        //Act
        searchArticleFragment.onViewCreated(view, bundle);

        //Assert
        verify(presenter, times(1)).reloadPage();
    }

    @Test
    public void onActivityCreated() {
        //Arrange
        Whitebox.setInternalState(searchArticleFragment, "mPresenter", presenter);

        //Act
        searchArticleFragment.onActivityCreated(bundle);

        //Assert
        verify(presenter, times(1)).initPage();
    }

    @Test
    public void onDestroyView() {
        //Arrange
        Whitebox.setInternalState(searchArticleFragment, "mSearchTask", backgroundTask);
        Whitebox.setInternalState(searchArticleFragment, "mLoadMoreTask", backgroundTask);

        //Act
        searchArticleFragment.onDestroyView();

        //Assert
        final BackgroundTask backgroundTaskLocal = Whitebox.getInternalState(searchArticleFragment, "mSearchTask");
        verify(backgroundTask, times(2)).cancelTask();
        assertNull(backgroundTaskLocal);
    }

    @Test
    public void reloadPage() {
        //Arrange
        Whitebox.setInternalState(searchArticleFragment, "mPresenter", presenter);

        //Act
        searchArticleFragment.reloadPage();

        //Assert
        verify(presenter, times(1)).reloadPage();
    }

    @Test
    public void loadMoreItems() {
        //Arrange
        Whitebox.setInternalState(searchArticleFragment, "mPresenter", presenter);

        //Act
        searchArticleFragment.loadMoreItems();

        //Assert
        verify(presenter, times(1)).loadMoreData();
    }

    @Test
    public void clearSearchResults() {
        //Arrange
        Whitebox.setInternalState(searchArticleFragment, "mPresenter", presenter);

        //Act
        searchArticleFragment.clearSearchResults();

        //Assert
        verify(presenter, times(1)).clearSearchResults();
    }

    @Test
    public void showSearchResults() {
        //Arrange
        final String query = "query_String";
        Whitebox.setInternalState(searchArticleFragment, "mPresenter", presenter);

        //Act
        searchArticleFragment.showSearchResults(query);
        verify(presenter).searchArticles(stringArgumentCaptor.capture());

        //Assert
        assertEquals(query, stringArgumentCaptor.getValue());
    }

    @Test
    public void onClickSearchedArticle() {
        //Arrange
        Whitebox.setInternalState(searchArticleFragment, "mPresenter", presenter);

        //Act
        searchArticleFragment.onClickSearchedArticle(item);
        verify(presenter).onClickListItem(searchListItemArgumentCaptor.capture());

        //Assert
        assertEquals(item, searchListItemArgumentCaptor.getValue());
    }
}
