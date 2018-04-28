package com.kayako.sdk.android.k5.helpcenter.articlepage;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ArticleContainerFactoryTest {

    @Mock
    private ArticleContainerContract.View view;

    @Test
    public void getPresenter() {
        final ArticleContainerContract.Presenter presenterOne =
                ArticleContainerFactory.getPresenter(view);
        final ArticleContainerContract.Presenter presenterSecond =
                ArticleContainerFactory.getPresenter(view);
        assertEquals(presenterOne, presenterSecond);
    }
}
