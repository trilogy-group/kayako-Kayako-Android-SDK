package com.kayako.sdk.android.k5.helpcenter.articlepage;

import static junit.framework.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ArticleFactoryTest {

    @Mock
    private ArticleContract.View view;

    @Test
    public void getPresenter() {
        //Act & Assert
        assertNotNull(ArticleFactory.getPresenter(view));
    }
}
