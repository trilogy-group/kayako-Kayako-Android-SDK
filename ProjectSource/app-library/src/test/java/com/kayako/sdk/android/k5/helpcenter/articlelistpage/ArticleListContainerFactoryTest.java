package com.kayako.sdk.android.k5.helpcenter.articlelistpage;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ArticleListContainerFactoryTest {

    @Mock
    private ArticleListContainerContract.View view;

    @Test
    public void getPresenter() {
        //Act
        final ArticleListContainerContract.Presenter presenterOne =
                ArticleListContainerFactory.getPresenter(view);
        final ArticleListContainerContract.Presenter presenterSecond =
                ArticleListContainerFactory.getPresenter(view);

        //Assert
        assertEquals(presenterOne, presenterSecond);
    }
}
