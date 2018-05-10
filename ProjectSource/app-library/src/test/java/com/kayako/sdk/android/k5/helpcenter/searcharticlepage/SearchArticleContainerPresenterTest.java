package com.kayako.sdk.android.k5.helpcenter.searcharticlepage;

import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;

@RunWith(MockitoJUnitRunner.class)
public class SearchArticleContainerPresenterTest {

    private SearchArticleContainerPresenter searchArticleContainerPresenter;

    @Mock
    private SearchArticleContainerContract.View view;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @Before
    public void setUp() {
        searchArticleContainerPresenter = new SearchArticleContainerPresenter(view);
    }

    @Test
    public void setView() {
        //Act
        searchArticleContainerPresenter.setView(view);

        //Assert
        final SearchArticleContainerContract.View mView =
                Whitebox.getInternalState(searchArticleContainerPresenter, "mView");
        assertEquals(view, mView);
    }

    @Test
    public void onTextEnteredWhenQueryStringNull() {
        //Arrange
        final String query = null;

        //Act
        searchArticleContainerPresenter.onTextEntered(query);

        //Assert
        verify(view, times(1)).clearSearchResults();
    }

    @Test
    public void onTextEnteredWhenQueryStringPresent() {
        //Arrange
        final String query = "query_string";

        //Act
        searchArticleContainerPresenter.onTextEntered(query);

        //Assert
        verify(view).showSearchResults(stringArgumentCaptor.capture());
        assertEquals(query, stringArgumentCaptor.getValue());
    }

    @Test
    public void onEnterPressedWhenQueryStringNull() {
        //Arrange
        final String query = null;

        //Act
        searchArticleContainerPresenter.onEnterPressed(query);

        //Assert
        verify(view, times(1)).showLessCharactersTypedErrorMessage();
    }

    @Test
    public void onEnterPressedWhenQueryStringPresent() {
        //Arrange
        final String query = "query_string";

        //Act
        searchArticleContainerPresenter.onEnterPressed(query);

        //Assert
        verify(view).showSearchResults(stringArgumentCaptor.capture());
        assertEquals(query, stringArgumentCaptor.getValue());
    }
}

