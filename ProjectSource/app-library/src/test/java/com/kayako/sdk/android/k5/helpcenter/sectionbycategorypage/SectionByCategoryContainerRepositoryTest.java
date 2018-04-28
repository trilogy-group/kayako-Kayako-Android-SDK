package com.kayako.sdk.android.k5.helpcenter.sectionbycategorypage;


import com.kayako.sdk.android.k5.core.HelpCenterPref;
import com.kayako.sdk.helpcenter.HelpCenter;
import com.kayako.sdk.helpcenter.category.Category;
import com.kayako.sdk.helpcenter.section.Section;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@PrepareForTest(HelpCenterPref.class)
@RunWith(PowerMockRunner.class)
public class SectionByCategoryContainerRepositoryTest {

    private static final String helpCenterUrl = "/helpUrl";
    private final List<Category> categories = new ArrayList<>();
    private SectionByCategoryContainerRepository sectionByCategoryContainerRepository;
    private HelpCenterPref helpCenterPref;

    @Mock
    private HelpCenter helpCenter;

    @Mock
    private Locale locale;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        sectionByCategoryContainerRepository = new SectionByCategoryContainerRepository(helpCenterUrl, locale);
        mockStatic(HelpCenterPref.class);
        helpCenterPref = mock(HelpCenterPref.class);
        when(HelpCenterPref.getInstance()).thenReturn(helpCenterPref);
    }

    @Test
    public void isCached() {
        //Act & Assert
        assertFalse(sectionByCategoryContainerRepository.isCached());
    }

    @Test
    public void doHelpCenterPreferencesMatch() {
        //Arrange
        when(helpCenterPref.getHelpCenterUrl()).thenReturn(helpCenterUrl);
        when(helpCenterPref.getLocale()).thenReturn(locale);

        //Act & Assert
        assertTrue(sectionByCategoryContainerRepository.doHelpCenterPreferencesMatch());
    }
}
