package com.kayako.sdk.android.k5.helpcenter.searcharticlepage;

import static junit.framework.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SearchArticleContainerFactoryTest {

    @Mock
    private SearchArticleContainerContract.View view;

    @Test
    public void getPresenter() {
        //Act & Assert
        assertNotNull(SearchArticleContainerFactory.getPresenter(view));
    }
}
