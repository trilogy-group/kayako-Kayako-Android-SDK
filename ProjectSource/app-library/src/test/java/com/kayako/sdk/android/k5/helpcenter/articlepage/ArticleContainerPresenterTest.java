package com.kayako.sdk.android.k5.helpcenter.articlepage;

import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;

@RunWith(MockitoJUnitRunner.class)
public class ArticleContainerPresenterTest {

    private ArticleContainerPresenter articleContainerPresenter;

    @Mock
    private ArticleContainerContract.View view;

    @Before
    public void setUp() {
        articleContainerPresenter = new ArticleContainerPresenter(view);
    }

    @Test
    public void setView() {
        //Act
        articleContainerPresenter.setView(view);

        //Assert
        final ArticleContainerContract.View mView =
                Whitebox.getInternalState(articleContainerPresenter, "mView");
        assertEquals(view, mView);
    }

    @Test
    public void onClickSearch() {
        //Act
        articleContainerPresenter.onClickSearch();

        //Assert
        verify(view, times(1)).openSearchActivity();
    }
}
