package com.kayako.sdk.android.k5.helpcenter.sectionbycategorypage;

import com.kayako.sdk.android.k5.core.HelpCenterPref;
import com.kayako.sdk.helpcenter.HelpCenter;
import com.kayako.sdk.helpcenter.category.Category;
import com.kayako.sdk.helpcenter.section.Section;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@PrepareForTest(HelpCenterPref.class)
@RunWith(PowerMockRunner.class)
public class SectionByCategoryRepositoryTest {

    private static final String helpCenterUrl = "/helpUrl";
    private final List<Category> categories = new ArrayList<>();
    private SectionByCategoryRepository sectionByCategoryRepository;
    private HelpCenterPref helpCenterPref;

    @Mock
    private HelpCenter helpCenter;

    @Mock
    private Locale locale;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        sectionByCategoryRepository = new SectionByCategoryRepository(helpCenterUrl, locale);
        mockStatic(HelpCenterPref.class);
        helpCenterPref = mock(HelpCenterPref.class);
        when(HelpCenterPref.getInstance()).thenReturn(helpCenterPref);
    }

    @Test
    public void getCategories() throws Exception {
        //Arrange
        Whitebox.setInternalState(sectionByCategoryRepository, "mHelpCenter", helpCenter);
        final boolean useCache = true;
        when(helpCenter.getCategories(0, 999)).thenReturn(categories);

        //Act
        final List<Category> categoriesLocal = sectionByCategoryRepository.getCategories(useCache);

        //Assert
        assertEquals(categories, categoriesLocal);
    }

    @Test
    public void whenCategoriesNotFetchedThenGetSectionsByCategoryThrowException() throws Exception {
        //Arrange
        final String exceptionMessage =
                "Categories have not been fetched yet. Please call getCategories() first";

        //Assert
        thrown.expectMessage(exceptionMessage);
        thrown.expect(NullPointerException.class);

        //Act
        sectionByCategoryRepository.getSectionsByCategory(categories, true);
    }

    @Test
    public void getSectionsByCategory() throws Exception {
        //Arrange
        final Category category = new Category();
        category.setId(1L);
        categories.add(category);
        Whitebox.setInternalState(sectionByCategoryRepository, "mHelpCenter", helpCenter);
        when(helpCenter.getCategories(0, 999)).thenReturn(categories);

        //Act
        sectionByCategoryRepository.getCategories(true);
        final Map<Category, List<Section>> categoryListMap =
                sectionByCategoryRepository.getSectionsByCategory(categories, true);

        //Assert
        assertEquals(1, categoryListMap.size());
    }

    @Test
    public void isCached() {
        //Act & Assert
        assertFalse(sectionByCategoryRepository.isCached());
    }

    @Test
    public void doHelpCenterPreferencesMatch() {
        //Arrange
        when(helpCenterPref.getHelpCenterUrl()).thenReturn(helpCenterUrl);
        when(helpCenterPref.getLocale()).thenReturn(locale);

        //Act & Assert
        assertTrue(sectionByCategoryRepository.doHelpCenterPreferencesMatch());
    }
}
