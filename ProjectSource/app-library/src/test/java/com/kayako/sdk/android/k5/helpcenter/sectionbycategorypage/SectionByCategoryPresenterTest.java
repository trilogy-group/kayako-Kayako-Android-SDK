package com.kayako.sdk.android.k5.helpcenter.sectionbycategorypage;

import com.kayako.sdk.helpcenter.category.Category;
import com.kayako.sdk.helpcenter.section.Section;
import static junit.framework.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import org.mockito.junit.MockitoJUnitRunner;
import java.util.List;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class SectionByCategoryPresenterTest {

    private SectionByCategoryPresenter sectionByCategoryPresenter;

    @Mock
    private SectionByCategoryContract.View view;

    @Mock
    private SectionByCategoryContract.Data data;

    @Mock
    private List<Category> categories;

    @Mock
    private Map<Category, List<Section>> sectionsByCategory;

    @Before
    public void setUp() {
        sectionByCategoryPresenter = new SectionByCategoryPresenter(view, data);
    }

    @Test
    public void initPage() {
        //Act
        sectionByCategoryPresenter.initPage();

        //Assert
        verify(view, times(1)).showOnlyLoadingView();
        verify(view, times(1)).startBackgroundTask();
    }

    @Test
    public void onClickSearch() {
        //Act
        sectionByCategoryPresenter.onClickSearch();

        //Assert
        verify(view, times(1)).openSearchPage();
    }

    @Test
    public void reloadPage() {
        //Act
        sectionByCategoryPresenter.reloadPage();

        //Assert
        verify(view, times(1)).showOnlyLoadingView();
        verify(view, times(1)).startBackgroundTask();
    }

    @Test
    public void onDataLoadedWhenSuccessfulTrue() {
        //Arrange
        boolean isSuccessful = Boolean.TRUE;

        //Act
        sectionByCategoryPresenter.onDataLoaded(isSuccessful);

        //Assert
        verify(view, times(1)).showOnlyListView();
    }

    @Test
    public void onDataLoadedWhenSuccessfulFalse() {
        //Arrange
        boolean isSuccessful = Boolean.FALSE;

        //Act
        sectionByCategoryPresenter.onDataLoaded(isSuccessful);

        //Assert
        verify(view, times(1)).showOnlyErrorView();
    }

    @Test
    public void loadDataInBackground() throws Exception {
        //Arrange
        when(data.getCategories(true)).thenReturn(categories);
        when(data.getSectionsByCategory(categories, true)).thenReturn(sectionsByCategory);

        //Act & Assert
        assertTrue(sectionByCategoryPresenter.loadDataInBackground());
    }
}
